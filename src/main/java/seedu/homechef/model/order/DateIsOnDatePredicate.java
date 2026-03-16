package seedu.homechef.model.order;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests if a given date matches a specific condition.
 */
public class DateIsOnDatePredicate implements Predicate<Order> {
    private final LocalDate date;

    public DateIsOnDatePredicate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns true if the order's date is on the specified date.
     */
    @Override
    public boolean test(Order order) {
        return order.getDate().value.equals(this.date);
    }

    /**
     * Returns true if both predicates have the same date.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DateIsOnDatePredicate)) {
            return false;
        }
        DateIsOnDatePredicate otherPredicate = (DateIsOnDatePredicate) other;
        return this.date.equals(otherPredicate.getDate());
    }
}
