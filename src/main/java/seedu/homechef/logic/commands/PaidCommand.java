package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.homechef.commons.core.index.Index;
import seedu.homechef.commons.util.ToStringBuilder;
import seedu.homechef.logic.Messages;
import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.order.Order;
import seedu.homechef.model.order.PaymentStatus;

/**
 * Marks an order as paid for in the HomeChef.
 */
public class PaidCommand extends Command {

    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_MARK_PAID_SUCCESS = "Marked order as paid: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks as paid the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final Index targetIndex;

    public PaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToEdit = lastShownList.get(targetIndex.getZeroBased());
        PaymentStatus paidStatus = PaymentStatus.PAID;
        Order editedOrder = new Order(
                orderToEdit.getFood(), orderToEdit.getCustomer(), orderToEdit.getPhone(),
                orderToEdit.getEmail(), orderToEdit.getAddress(), orderToEdit.getDate(),
                orderToEdit.getCompletionStatus(), paidStatus, orderToEdit.getTags());

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);

        return new CommandResult(generateSuccessMessage(editedOrder));
    }

    /**
     * Generates a command execution success message when an order is marked as paid.
     */
    private String generateSuccessMessage(Order order) {
        return String.format(MESSAGE_MARK_PAID_SUCCESS, Messages.format(order));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaidCommand)) {
            return false;
        }

        PaidCommand otherPaidCommand = (PaidCommand) other;
        return targetIndex.equals(otherPaidCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
