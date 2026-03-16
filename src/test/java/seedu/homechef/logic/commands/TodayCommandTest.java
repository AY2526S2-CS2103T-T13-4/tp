package seedu.homechef.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.homechef.testutil.TypicalOrders.getTypicalHomeChef;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.homechef.logic.commands.exceptions.CommandException;
import seedu.homechef.model.Model;
import seedu.homechef.model.ModelManager;
import seedu.homechef.model.UserPrefs;
import seedu.homechef.model.order.DateIsOnDatePredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code TodayCommand}.
 */
public class TodayCommandTest {

    private static final LocalDate DATE_WITH_ORDERS = LocalDate.of(2026, 3, 26);

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHomeChef(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalHomeChef(), new UserPrefs());
    }

    @Test
    public void execute_filtersToOrdersDueOnGivenDate() throws CommandException {
        TodayCommand command = new TodayCommand(DATE_WITH_ORDERS);

        CommandResult result = command.execute(model);
        assertEquals(new CommandResult(TodayCommand.MESSAGE_SUCCESS), result);

        expectedModel.updateFilteredOrderList(new DateIsOnDatePredicate(DATE_WITH_ORDERS));
        assertEquals(expectedModel.getFilteredOrderList(), model.getFilteredOrderList());
        assertEquals(expectedModel.getHomeChef(), model.getHomeChef());
    }
}
