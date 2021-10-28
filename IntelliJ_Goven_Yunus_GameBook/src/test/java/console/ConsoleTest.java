package console;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ConsoleTest {

    @Test
    public void testReadLine(){
        Console csl = mock(Console.class);
        when(csl.readLine("")).thenReturn("sisi");
        String coco = csl.readLine("");
        assertEquals("sisi",coco);

    }
    @Test
    public void testReadInt(){
        Console csl = mock(Console.class);
        when(csl.readInteger("")).thenReturn(4);
        int coco = csl.readInteger("");
        assertTrue(4==coco);

    }
    @Test
    public void testPrintLine(){
        Console csl = mock(Console.class);
        csl.printLine("");
        verify(csl).printLine("");

    }
    @Test
    public void testPrint(){
        Console csl = mock(Console.class);
        csl.print("");
        verify(csl).print("");

    }
    @Test
    public void testPrintJustLine(){
        Console csl = mock(Console.class);
        csl.printLine();
        verify(csl).printLine();

    }
}
