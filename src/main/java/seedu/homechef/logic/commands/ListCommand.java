package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.homechef.model.Model;
import seedu.homechef.model.order.Date;
import seedu.homechef.model.order.Order;

/**
 * Lists all orders in the HomeChef to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "Listed all orders";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists orders, optionally filtered by fulfillment date.\n"
            + "Format: " + COMMAND_WORD + " [d/DATE]\n"
            + "DATE must be in the format dd-MM-yyyy.\n"
            + "Example: " + COMMAND_WORD + "\n"
            + "Example: " + COMMAND_WORD + " d/16-04-2003";

    private final Optional<Date> targetDate;

    public ListCommand() {
        this.targetDate = Optional.empty();
    }

    public ListCommand(Date targetDate) {
        requireNonNull(targetDate);
        this.targetDate = Optional.of(targetDate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Order> predicate = targetDate
                .<Predicate<Order>>map(targetDate -> order -> order.getDate().equals(targetDate))
                .orElse(PREDICATE_SHOW_ALL_ORDERS);

        model.updateFilteredOrderList(predicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherListCommand = (ListCommand) other;
        return Objects.equals(targetDate, otherListCommand.targetDate);
    }
}
