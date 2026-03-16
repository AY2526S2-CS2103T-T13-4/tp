package seedu.homechef.model.order;

import java.time.LocalDate;
import java.util.function.Predicate;

public class DateIsOnDatePredicate implements Predicate<Order> {
    private final LocalDate date;

    public DateIsOnDatePredicate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean test(Order order) {
        return order.getDate().value.equals(this.date);
    }

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
