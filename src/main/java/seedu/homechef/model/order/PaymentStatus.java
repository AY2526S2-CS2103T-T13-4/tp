package seedu.homechef.model.order;

/**
 * Represents an Order's payment status in the HomeChef.
 * Guarantees: immutable; status is non-null
 */
public class PaymentStatus {

    public final boolean status;

    /**
     * Constructs a {@code PaymentStatus}.
     */
    public PaymentStatus(boolean status) {
        this.status = status;
    }

    /**
     * Returns true if this order has been paid for.
     */
    public boolean isPaid() {
        return status;
    }

    @Override
    public String toString() {
        return status ? "PAID" : "UNPAID";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PaymentStatus)) {
            return false;
        }

        PaymentStatus otherStatus = (PaymentStatus) other;
        return status == otherStatus.status;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(status);
    }

}
