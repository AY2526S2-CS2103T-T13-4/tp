package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Optional;

import seedu.homechef.logic.commands.ListCommand;
import seedu.homechef.logic.parser.exceptions.ParseException;
import seedu.homechef.model.order.Date;

public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        Optional<String> rawDate = map.getValue(PREFIX_DATE);

        // Keep existing "list ignores extra params" behaviour:
        // if no d/ prefix is present, just list everything.
        if (rawDate.isEmpty()) {
            return new ListCommand();
        }

        // If there is a d/ prefix, disallow preamble (consistent with other parsers)
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
