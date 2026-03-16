package seedu.homechef.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.homechef.model.Model;
import seedu.homechef.model.order.DateIsOnDatePredicate;

/**
 * Lists all orders in the HomeChef to the user.
 */
public class TodayCommand extends Command {

    public static final String COMMAND_WORD = "today";
    public static final String MESSAGE_SUCCESS = "Listed all orders due today";

    private final LocalDate date;

    public TodayCommand() {
        this(LocalDate.now());
    }
    // package-private constructor for testing
    TodayCommand(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOrderList(new DateIsOnDatePredicate(LocalDate.now()));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
