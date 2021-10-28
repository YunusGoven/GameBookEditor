package commands;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandMapTest {

    @Test
    void mapCreate(){
        CommandMap cm = new CommandMap(new LinkedList<Command>());
        assertEquals(cm.getClass(),CommandMap.class);
    }

    @Test
    void testGet(){
        List<Command> cmlis = new LinkedList<Command>();
        FakeCommand c1 = new FakeCommand(1,"Creer");
        FakeCommand c2 = new FakeCommand(2,"Modifier");
        FakeCommand c3 = new FakeCommand(3,"Quitter");
        cmlis.add(c1);
        cmlis.add(c2);
        cmlis.add(c3);
        CommandMap cm = new CommandMap(cmlis);
        assertEquals(c1,cm.get(1));
        assertNull(cm.get(4));
    }
    @Test
    public void testIterator() {
        final List<Command> list = new LinkedList<Command>();
        final FakeCommand[] fakeCommand = { new FakeCommand(1, "Termine lexecution du programme"),
                new FakeCommand(2, "ramme"),
                new FakeCommand(3, " programme") };
        for (final FakeCommand fakeCommand2 : fakeCommand) {
            list.add(fakeCommand2);
        }
        final CommandMap commandMap = new CommandMap(list);
        final Iterator<Command> it = commandMap.iterator();
        while (it.hasNext()) {
            assertTrue(list.contains(it.next()));

        }

    }

}
