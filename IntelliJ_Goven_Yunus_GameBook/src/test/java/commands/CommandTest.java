package commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommandTest {

    @Test
    public void testWithNoNameAndNoDescription() {
        Command fake = mock(Command.class);
        when(fake.getName()).thenReturn(" - ");
        assertEquals(" - ", fake.getName());
    }

    @Test
    public void testGetNameExit() {
        Command fake = mock(Command.class);
        when(fake.getName()).thenReturn("Exit");
        assertEquals("Exit", fake.getName());
    }

    @Test
    public void testEquals(){
        final Command fakeCommand =mock(Command.class);
        assertEquals(fakeCommand,fakeCommand);
    }








}
