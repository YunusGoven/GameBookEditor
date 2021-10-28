package presenter;

import commands.Command;
import commands.CommandMap;
import commands.ExitCommand;
import console.Console;
import presenter.FrontController;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class FrontControllerTest {

    @Test
    public void fontCreater(){
        Console csl = mock(Console.class);
        Command cm = mock(Command.class);
        Collection<Command> ca= new LinkedList<>();
        ca.add(cm);
        CommandMap com = new CommandMap(ca);
        FrontController ft = new FrontController(csl, com);
        assertEquals(ft.getClass(),FrontController.class);
    }
    @Test
    public void testLoop() throws Exception {
        Console csl = mock(Console.class);
        Command exit = new ExitCommand(csl);
        Collection<Command> ca= new LinkedList<>();
        ca.add(exit);
        CommandMap com = new CommandMap(ca);
        when(csl.readLine(anyString())).thenReturn("list","7","5");
        FrontController ft = new FrontController(csl, com);
        ft.loop();
        verify(csl,times(2)).printLine("%s%3s%s\n", "Num", " ", "Nom");
        verify(csl,times(2)).printLine("%d%5s%s",5," ","Quitter l'application");
        verify(csl).printLine("Cette commande n'existe pas ! ");
    }
}
