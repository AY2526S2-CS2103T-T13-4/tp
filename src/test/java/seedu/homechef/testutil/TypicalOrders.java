package seedu.homechef.testutil;

import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_CUSTOMER_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_FOOD_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_FOOD_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.homechef.model.HomeChef;
import seedu.homechef.model.order.Order;


/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {

    public static final Order ALICE = new OrderBuilder().withFood("Birthday Cake").withCustomer("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withDate("26-03-2026")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Order BENSON = new OrderBuilder().withFood("Sourdough Bread").withCustomer("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withDate("26-03-2026")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Order CARL = new OrderBuilder().withFood("Cupcakes (24pcs)").withCustomer("Carl Kurz")
            .withPhone("95352563").withDate("03-04-2026")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Order DANIEL = new OrderBuilder().withFood("Wedding Cake - 3 Tier")
            .withCustomer("Daniel Meier").withPhone("87652533").withDate("07-04-2026")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Order ELLE = new OrderBuilder().withFood("Chocolate Chip Cookies (3pcs)")
            .withCustomer("Elle Meyer").withPhone("9482224").withDate("02-04-2026")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Order FIONA = new OrderBuilder().withFood("Muffin (4pc)").withCustomer("Fiona Kunz")
            .withPhone("9482427").withDate("17-04-2026")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Order GEORGE = new OrderBuilder().withFood("Doughnut Assortment")
            .withCustomer("George Best").withPhone("9482442").withDate("19-04-2026")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Order HOON = new OrderBuilder().withFood("Curry Puff").withCustomer("Hoon Meier")
            .withPhone("8482424").withDate("20-10-2026")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Order IDA = new OrderBuilder().withFood("Doner Pie").withCustomer("Ida Mueller")
            .withPhone("8482131").withDate("19-04-2026")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Order's details found in {@code CommandTestUtil}
    public static final Order AMY = new OrderBuilder().withFood(VALID_FOOD_AMY).withCustomer(VALID_CUSTOMER_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withDate(VALID_DATE_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Order BOB = new OrderBuilder().withFood(VALID_FOOD_BOB).withCustomer(VALID_CUSTOMER_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withDate(VALID_DATE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalOrders() {
    } // prevents instantiation

    /**
     * Returns an {@code HomeChef} with all the typical orders.
     */
    public static HomeChef getTypicalHomeChef() {
        HomeChef ab = new HomeChef();
        for (Order order : getTypicalOrders()) {
            ab.addOrder(order);
        }
        return ab;
    }

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
