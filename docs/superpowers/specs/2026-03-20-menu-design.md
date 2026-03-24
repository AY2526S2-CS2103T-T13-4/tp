# Menu Feature Design

**Date:** 2026-03-20
**Branch:** `feat-add-menu`

---

## Context

HomeChef Helper currently has no concept of a menu. The `Food` field on an order is a free-text string with no validation — owners can enter any food name, including typos or items they no longer sell. This creates data integrity issues and makes it harder to track what the business actually offers.

This feature adds a persistent menu list (name, price, availability per item), validates orders against it, and shows the menu permanently alongside the order list in a split-panel layout so the owner always has a reference while entering orders.

---

## Requirements

- Menu items have: **name** (unique, case-insensitive), **price** (positive decimal), **availability** (boolean, defaults to true)
- Orders are **hard-blocked** if the food name does not match an active menu item
- The menu panel is **always visible** alongside the order list — no toggle command
- Menu is managed via `add-menu`, `edit-menu`, `delete-menu` commands
- Menu persists in a separate `menu.json` file
- All code follows the existing AB3/HomeChef code style
- Deleting a menu item does not retroactively invalidate existing orders — orders retain their food name and are not re-validated
- Stock tracking is out of scope for v1 (availability flag covers the use case manually)

---

## Data Model

### `MenuItem` (immutable value object)

Fields:
- `MenuItemName name` — validated non-empty string
- `Price price` — validated positive decimal string (stored as string to preserve precision)
- `boolean available` — defaults to `true`

Identity: `isSameMenuItem()` matches on name (case-insensitive). Two items with the same name cannot coexist in the menu.

`equals()`/`hashCode()` use all three fields.

### `MenuItemName`

Wraps a validated non-empty string. Validation regex: `[\\p{Alnum}][\\p{Alnum} ]*` — alphanumeric characters and spaces only, must start with an alphanumeric character. This matches the `Food` class regex exactly so that food names on orders can always have a corresponding menu item.

### `Price`

Wraps a validated string. Validation regex: `[1-9][0-9]*(\\.[0-9]{1,2})?|0\\.[0-9]{1,2}` — a positive decimal with at most 2 decimal places. Stored as string to avoid floating-point representation issues.

Valid examples: `"5.50"`, `"12"`, `"0.01"`, `"100"`, `"1.5"`
Invalid examples: `"0"`, `"0.00"`, `"-1"`, `"abc"`, `"1.999"`, `"1."`

### `UniqueMenuItemList`

Enforces no duplicate names. Wraps `ObservableList<MenuItem>`. Mirrors `UniqueOrderList`.

Throws `DuplicateMenuItemException` on duplicate add. Throws `MenuItemNotFoundException` on remove of absent item.

### `MenuBook` / `ReadOnlyMenuBook`

`ReadOnlyMenuBook` interface exposes:
- `ObservableList<MenuItem> getMenuItemList()`

`MenuBook` is the root model class containing a `UniqueMenuItemList`. Implements `ReadOnlyMenuBook`. Mirrors `HomeChef`.

### Exception classes

- `DuplicateMenuItemException` — thrown by `UniqueMenuItemList.add()` on duplicate name
- `MenuItemNotFoundException` — thrown by `UniqueMenuItemList.remove()` on absent item

---

## Storage

The storage layer follows the same pattern as the existing `HomeChefStorage` sub-interface.

- `MenuBookStorage` interface — declares `readMenuBook()` and `saveMenuBook(ReadOnlyMenuBook)`
- `JsonMenuBookStorage` — implements `MenuBookStorage`; reads/writes `menu.json`
- `JsonAdaptedMenuItem` — `@JsonProperty` fields: `name` (String), `price` (String), `available` (Boolean)
- `JsonSerializableMenuBook` — wraps `List<JsonAdaptedMenuItem>`
- `Storage` extends `HomeChefStorage, MenuBookStorage, UserPrefsStorage` (adds `MenuBookStorage`)
- `StorageManager` implements the new `MenuBookStorage` methods; holds a `JsonMenuBookStorage` instance
- `MainApp` constructs `JsonMenuBookStorage` with path `[dataDir]/menu.json`, loads on startup (empty menu if file missing), passes `ReadOnlyMenuBook` to `ModelManager`

---

## Model Interface

New methods on `Model` / `ModelManager`:

| Method | Description |
|---|---|
| `hasMenuItem(MenuItem)` | Duplicate check by name |
| `addMenuItem(MenuItem)` | Add to menu |
| `deleteMenuItem(MenuItem)` | Remove from menu |
| `setMenuItem(MenuItem, MenuItem)` | Replace (for edit) |
| `getMenuBook()` | Returns `ReadOnlyMenuBook` |
| `getFilteredMenuItemList()` | `ObservableList<MenuItem>` for UI binding |
| `updateFilteredMenuItemList(Predicate<MenuItem>)` | For future filtering support |

`ModelManager` holds a `MenuBook` and a `FilteredList<MenuItem>`.

`ModelManager(ReadOnlyHomeChef, ReadOnlyMenuBook, ReadOnlyUserPrefs)` — 3-arg constructor updated to accept menu book.
`ModelManager()` no-arg constructor delegates to `this(new HomeChef(), new MenuBook(), new UserPrefs())`.
`ModelManager.equals()` updated to include `menuBook` equality.

---

## Logic Interface

New methods on `Logic` / `LogicManager`:

| Method | Description |
|---|---|
| `getFilteredMenuItemList()` | Exposed to UI for `MenuListPanel` binding |

`LogicManager.execute()` calls `storage.saveMenuBook(model.getMenuBook())` after every command execution (mirrors the existing `storage.saveHomeChef(model.getHomeChef())` call).

---

## Commands & Parsers

### New prefixes (added to `CliSyntax`)

| Prefix | Constant | Meaning |
|---|---|---|
| `n/` | `PREFIX_MENU_NAME` | Menu item name (menu commands only — never parsed by order command parsers) |
| `x/` | `PREFIX_PRICE` | Price |
| `v/` | `PREFIX_AVAILABILITY` | Availability (`true`/`false`) |

Note: `p/` is already `PREFIX_PHONE` and is not reused.

### Commands

| Command | Syntax | Success message |
|---|---|---|
| `add-menu` | `add-menu n/NAME x/PRICE [v/AVAILABILITY]` | `"New menu item added: [NAME] $[PRICE]"` |
| `edit-menu` | `edit-menu INDEX [n/NAME] [x/PRICE] [v/AVAILABILITY]` | `"Edited menu item: [NAME] $[PRICE] (available: [BOOL])"` |
| `delete-menu` | `delete-menu INDEX` | `"Deleted menu item: [NAME]"` |

`add-menu`: availability defaults to `true` if `v/` is omitted.
`edit-menu`: uses `EditMenuDescriptor` inner class (mirrors `EditOrderDescriptor`). At least one field must be present.

---

## Order Validation

Validation runs at **execute time** in `AddCommand` and `EditCommand` (not parse time — menu state is only available in the model).

**Trigger in `EditCommand`:** validation is only triggered when `editOrderDescriptor.getFood().isPresent()` is true. If the food field is not being edited, validation is skipped entirely — this prevents existing orders from being invalidated when their food item is later removed from the menu.

**Logic:**

1. Look up food name in menu (case-insensitive exact match)
2. Found + available → proceed normally
3. Found + unavailable → reject:
   > `"'[NAME]' is currently unavailable. Check the menu panel on the right for available items."`
4. Not found → reject:
   > `"No menu item '[INPUT]'. Use 'add-menu' to add it to the menu first."`

No fuzzy matching — exact case-insensitive match only. Keeping logic simple.

---

## UI

The main window uses a permanent horizontal `SplitPane` with the order list on the left (70%) and the menu list on the right (30%). There is no toggle — both panels are always visible.

### New files

| File | Purpose |
|---|---|
| `ui/MenuCard.java` | Displays a single `MenuItem` (name, price, availability badge) |
| `ui/MenuListPanel.java` | `ListView<MenuItem>`, mirrors `OrderListPanel` |
| `view/MenuListCard.fxml` | FXML for `MenuCard` |
| `view/MenuListPanel.fxml` | FXML for `MenuListPanel` |

`MainWindow.java` calls `logic.getFilteredMenuItemList()` to obtain the list for `MenuListPanel` — consistent with how it calls `logic.getFilteredOrderList()` for `OrderListPanel`.

### `MainWindow.fxml` changes

Wrap the existing `orderListPanelPlaceholder` in a horizontal `SplitPane` with a fixed divider at `0.7`. Add `menuListPanelPlaceholder` as the second item. Both panels are always visible — no `visible`/`managed` toggling needed. No changes to `CommandResult`.

---

## Test Cases

All tests follow existing AB3/HomeChef style (JUnit 5, `ModelStub` pattern for command unit tests, `Assert*` helpers from `testutil`).

### Test setup utility

Add `getTypicalMenuBook()` in `testutil/TypicalMenuItems.java` — a `MenuBook` containing at least the food names used in `TypicalOrders` (so existing integration tests do not break when order validation is active). All integration tests that exercise `AddCommand` or `EditCommand` must initialise `ModelManager` with both `getTypicalHomeChef()` and `getTypicalMenuBook()`.

### Model

**`MenuItemNameTest`**
- Valid: non-empty alphanumeric string, names with spaces, names starting with digit
- Invalid: null → `NullPointerException`; empty string, leading space → `IllegalArgumentException`

**`PriceTest`**
- Valid: `"5.50"`, `"12"`, `"0.01"`, `"100"`, `"1.5"`
- Invalid: null, empty, `"0"`, `"0.00"`, `"-1"`, `"abc"`, `"1.999"`, `"1."`, `".50"` → `IllegalArgumentException`

**`MenuItemTest`**
- `isSameMenuItem()` returns true for same name regardless of case
- `isSameMenuItem()` returns false for different name
- `equals()` requires all three fields to match

**`UniqueMenuItemListTest`**
- `add()` succeeds for unique name
- `add()` throws `DuplicateMenuItemException` for duplicate name (case-insensitive)
- `remove()` throws `MenuItemNotFoundException` for absent item
- `setMenuItem()` replaces correctly; throws on duplicate target name

### Commands

**`AddMenuCommandTest`**
- Adds valid `MenuItem` to model; success message matches template
- Duplicate name → `CommandException` with duplicate message

**`EditMenuCommandTest`**
- Edits name, price, availability independently; success message correct
- No fields provided → `CommandException` (nothing to edit)
- Invalid index → `CommandException`
- Editing name to duplicate of existing item → `CommandException`

**`DeleteMenuCommandTest`**
- Valid index → item removed; success message contains item name
- Out-of-range index → `CommandException`

### Order Validation

**`AddCommandIntegrationTest`** (extends existing — model initialised with `getTypicalMenuBook()`)
- Food name matches available item → order added
- Food name matches unavailable item → `CommandException` with unavailability message
- Food name is a typo (Levenshtein distance ≤ 2) → `CommandException` with suggestion
- Food name is unknown (distance > 2) → `CommandException` with `add-menu` suggestion

**`EditCommandIntegrationTest`** (extends existing — model initialised with `getTypicalMenuBook()`)
- Editing food name to valid available item → succeeds
- Editing food name to unavailable item → `CommandException`
- Editing food name to unknown food → `CommandException`
- Editing fields other than food (e.g. phone only) → validation not triggered, succeeds

### Utility

**`StringUtilTest`** (extends existing)
- `findClosestMatch()` returns correct candidate within distance ≤ 2
- Returns empty `Optional` when list is empty
- Returns empty `Optional` when all candidates exceed max distance
- Exact match (distance 0) always returned regardless of `maxDistance`

### Storage

**`JsonAdaptedMenuItemTest`**
- Valid JSON with all fields → `MenuItem` with correct values
- Missing `name` → `IllegalValueException`
- Missing `price` → `IllegalValueException`
- Invalid price format → `IllegalValueException`
- `available` absent → defaults to `true`

---

## File Summary

### New files to create

**Model:**
- `src/main/java/seedu/homechef/model/menu/MenuItemName.java`
- `src/main/java/seedu/homechef/model/menu/Price.java`
- `src/main/java/seedu/homechef/model/menu/MenuItem.java`
- `src/main/java/seedu/homechef/model/menu/UniqueMenuItemList.java`
- `src/main/java/seedu/homechef/model/menu/MenuBook.java`
- `src/main/java/seedu/homechef/model/menu/ReadOnlyMenuBook.java`
- `src/main/java/seedu/homechef/model/menu/exceptions/DuplicateMenuItemException.java`
- `src/main/java/seedu/homechef/model/menu/exceptions/MenuItemNotFoundException.java`

**Storage:**
- `src/main/java/seedu/homechef/storage/MenuBookStorage.java`
- `src/main/java/seedu/homechef/storage/JsonMenuBookStorage.java`
- `src/main/java/seedu/homechef/storage/JsonAdaptedMenuItem.java`
- `src/main/java/seedu/homechef/storage/JsonSerializableMenuBook.java`

**Logic:**
- `src/main/java/seedu/homechef/logic/commands/AddMenuCommand.java`
- `src/main/java/seedu/homechef/logic/commands/EditMenuCommand.java`
- `src/main/java/seedu/homechef/logic/commands/DeleteMenuCommand.java`
- `src/main/java/seedu/homechef/logic/parser/AddMenuCommandParser.java`
- `src/main/java/seedu/homechef/logic/parser/EditMenuCommandParser.java`
- `src/main/java/seedu/homechef/logic/parser/DeleteMenuCommandParser.java`

**UI:**
- `src/main/java/seedu/homechef/ui/MenuCard.java`
- `src/main/java/seedu/homechef/ui/MenuListPanel.java`
- `src/main/resources/view/MenuListCard.fxml`
- `src/main/resources/view/MenuListPanel.fxml`

**Tests:**
- `src/test/java/seedu/homechef/model/menu/MenuItemNameTest.java`
- `src/test/java/seedu/homechef/model/menu/PriceTest.java`
- `src/test/java/seedu/homechef/model/menu/MenuItemTest.java`
- `src/test/java/seedu/homechef/model/menu/UniqueMenuItemListTest.java`
- `src/test/java/seedu/homechef/logic/commands/AddMenuCommandTest.java`
- `src/test/java/seedu/homechef/logic/commands/EditMenuCommandTest.java`
- `src/test/java/seedu/homechef/logic/commands/DeleteMenuCommandTest.java`
- `src/test/java/seedu/homechef/storage/JsonAdaptedMenuItemTest.java`
- `src/test/java/seedu/homechef/testutil/TypicalMenuItems.java`

### Files to modify

| File | Change |
|---|---|
| `model/Model.java` | Add menu CRUD + query methods + `getMenuBook()` |
| `model/ModelManager.java` | Implement menu methods; hold `MenuBook` + `FilteredList<MenuItem>`; update constructor, `equals()`, no-arg constructor |
| `storage/Storage.java` | Extend `MenuBookStorage` |
| `storage/StorageManager.java` | Implement `MenuBookStorage` via `JsonMenuBookStorage` delegate |
| `MainApp.java` | Construct `JsonMenuBookStorage`; load `menu.json` on startup; pass to `ModelManager` |
| `logic/Logic.java` | Add `getFilteredMenuItemList()` |
| `logic/LogicManager.java` | Implement `getFilteredMenuItemList()`; call `storage.saveMenuBook()` in `execute()` |
| `logic/parser/CliSyntax.java` | Add `PREFIX_MENU_NAME` (`n/`), `PREFIX_PRICE` (`x/`), `PREFIX_AVAILABILITY` (`v/`) |
| `logic/parser/HomeChefParser.java` | Register `add-menu`, `edit-menu`, `delete-menu` |
| `logic/commands/AddCommand.java` | Validate food name against menu at execute time |
| `logic/commands/EditCommand.java` | Validate food name if `editOrderDescriptor.getFood().isPresent()` |
| `commons/util/StringUtil.java` | Add `findClosestMatch(String, List<String>, int)` |
| `commons/util/StringUtilTest.java` | Add `findClosestMatch()` tests |
| `ui/MainWindow.java` | Call `logic.getFilteredMenuItemList()`; initialize `MenuListPanel`; inject into placeholder |
| `src/main/resources/view/MainWindow.fxml` | Wrap content in permanent `SplitPane` (divider 0.7) |
| `logic/commands/AddCommandIntegrationTest.java` | Initialise model with `getTypicalMenuBook()`; add food validation cases |
| `logic/commands/EditCommandIntegrationTest.java` | Initialise model with `getTypicalMenuBook()`; add food validation cases |

---

## Verification

1. `./gradlew build` — must pass with no errors
2. `./gradlew test` — all existing and new tests pass
3. Manual run (`./gradlew run`):
   - Menu panel always visible on the right; order list on the left
   - `add-menu n/Chicken Rice x/5.50` → item appears in menu panel
   - `edit-menu 1 v/false` → marks unavailable
   - `delete-menu 1` → item removed
   - Add order with valid food → succeeds
   - Add order with unavailable food → blocked with correct message
   - Add order with typo (e.g. `Chiken Rice`) → fuzzy suggestion shown
   - Add order with unknown food → `add-menu` suggestion shown
   - Edit order phone only → no food validation triggered
