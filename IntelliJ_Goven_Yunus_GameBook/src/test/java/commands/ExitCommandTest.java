package commands;

import console.Console;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ExitCommandTest {
    @Test
    public void exitsOnExecute() {
        final Console console = mock(Console.class);
        final ExitCommand cmd = new ExitCommand(console);

        cmd.execute();
        verify(console,times(1)).print("Aurevoir");
    }

}
