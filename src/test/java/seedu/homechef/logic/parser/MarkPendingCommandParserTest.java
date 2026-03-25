package seedu.homechef.logic.parser;

import static seedu.homechef.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.homechef.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.homechef.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import org.junit.jupiter.api.Test;

import seedu.homechef.logic.commands.MarkPendingCommand;

public class MarkPendingCommandParserTest {

    private MarkPendingCommandParser parser = new MarkPendingCommandParser();

    @Test
    public void parse_validArgs_returnsMarkPendingCommand() {
        assertParseSuccess(parser, "1", new MarkPendingCommand(INDEX_FIRST_ORDER));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(
                parser,
                "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPendingCommand.MESSAGE_USAGE));
    }
}
