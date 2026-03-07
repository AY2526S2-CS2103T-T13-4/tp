package seedu.homechef.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.homechef.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.homechef.testutil.Assert.assertThrows;
import static seedu.homechef.testutil.TypicalOrders.ALICE;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.exceptions.DuplicateOrderException;
import seedu.homechef.testutil.OrderBuilder;

public class HomeChefTest {

    private final HomeChef homeChef = new HomeChef();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), homeChef.getOrderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> homeChef.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyHomeChef_replacesData() {
        HomeChef newData = getTypicalHomeChef();
        homeChef.resetData(newData);
        assertEquals(newData, homeChef);
    }

    @Test
    public void resetData_withDuplicateOrders_throwsDuplicateOrderException() {
        // Two orders with the same identity fields
        Order editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Order> newOrders = Arrays.asList(ALICE, editedAlice);
        HomeChefStub newData = new HomeChefStub(newOrders);

        assertThrows(DuplicateOrderException.class, () -> homeChef.resetData(newData));
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> homeChef.hasOrder(null));
    }

    @Test
    public void hasOrder_orderNotInHomeChef_returnsFalse() {
        assertFalse(homeChef.hasOrder(ALICE));
    }

    @Test
    public void hasOrder_orderInHomeChef_returnsTrue() {
        homeChef.addOrder(ALICE);
        assertTrue(homeChef.hasOrder(ALICE));
    }

    @Test
    public void hasOrder_orderWithSameIdentityFieldsInHomeChef_returnsTrue() {
        homeChef.addOrder(ALICE);
        Order editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(homeChef.hasOrder(editedAlice));
    }

    @Test
    public void getOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> homeChef.getOrderList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = HomeChef.class.getCanonicalName() + "{orders=" + homeChef.getOrderList() + "}";
        assertEquals(expected, homeChef.toString());
    }

    /**
     * A stub ReadOnlyHomeChef whose orders list can violate interface constraints.
     */
    private static class HomeChefStub implements ReadOnlyHomeChef {
        private final ObservableList<Order> orders = FXCollections.observableArrayList();

        HomeChefStub(Collection<Order> orders) {
            this.orders.setAll(orders);
        }

        @Override
        public ObservableList<Order> getOrderList() {
            return orders;
        }
    }

}
