package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.homechef.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Order;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHomeChef(), new UserPrefs());
        expectedModel = new ModelManager(model.getHomeChef(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_withDate_filtersList() {
        Date target = new Date("26-03-2026");

        expectedModel.updateFilteredOrderList(order -> order.getDate().equals(target));
        assertCommandSuccess(new ListCommand(target), model, ListCommand.MESSAGE_SUCCESS, expectedModel);

        List<Order> shown = model.getFilteredOrderList();
        assertEquals(true, shown.stream().allMatch(o -> o.getDate().equals(target)));
    }
}
