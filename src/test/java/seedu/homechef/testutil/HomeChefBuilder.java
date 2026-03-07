package seedu.homechef.testutil;

import seedu.homechef.model.HomeChef;
import seedu.homechef.model.order.Order;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code HomeChef ab = new HomeChefBuilder().withOrder("John", "Doe").build();}
 */
public class HomeChefBuilder {

    private HomeChef homeChef;

    public HomeChefBuilder() {
        homeChef = new HomeChef();
    }

    public HomeChefBuilder(HomeChef homeChef) {
        this.homeChef = homeChef;
    }

    /**
     * Adds a new {@code Order} to the {@code HomeChef} that we are building.
     */
    public HomeChefBuilder withOrder(Order order) {
        homeChef.addOrder(order);
        return this;
    }

    public HomeChef build() {
        return homeChef;
    }
}
