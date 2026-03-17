package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Optional;

import seedu.homechef.logic.commands.ListCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.order.Date;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Optional<String> rawDate = map.getValue(PREFIX_DATE);

        if (rawDate.isEmpty()) {
            return new ListCommand();
        }

        if (!map.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        map.verifyNoDuplicatePrefixesFor(PREFIX_DATE);

        String trimmed = rawDate.get().trim();
        if (trimmed.isEmpty() || !Date.isValidDate(trimmed)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }

        return new ListCommand(new Date(trimmed));
    }
}
