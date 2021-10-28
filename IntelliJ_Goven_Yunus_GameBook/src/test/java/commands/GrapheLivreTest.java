package commands;

import console.Console;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import model.impl.*;

public class GrapheLivreTest {

    @Test
    public void CommandIsCreated(){
        Console csl = mock(Console.class);
        Book book = new Book("title");
        Command gl = new GrapheLivre(csl, book);
        assertEquals(gl.getClass(), GrapheLivre.class);
    }
    @Test
    public void CommandBookVide() throws Exception {
        Console csl = mock(Console.class);
        Book book = new Book("title");
        Command gl = new GrapheLivre(csl, book);
        gl.execute();
        verify(csl, times(1)).printLine("Graphe vide.");
    }
    @Test
    public void GrapheBookNotEmpty() throws Exception {
        Console csl = mock(Console.class);
        Book book = new Book("title");
        Set<Action> ac1 = new HashSet<Action>();
        ac1.add(new Action("et 2",2));
        ac1.add(new Action("et 3",3));
        Paragraph p1 = new Paragraph(1,"merlin1", (HashSet<Action>) ac1);
        Set<Action> ac2 = new HashSet<Action>();
        ac2.add(new Action("en 5",4));
        ac2.add(new Action("tt 3",3));
        Paragraph p2 = new Paragraph(2,"merlin2", (HashSet<Action>) ac2);
        book.addParagraph(p1);
        book.addParagraph(p2);
        book.addParagraph(3);
        book.addParagraph(4);

        Command gl = new GrapheLivre(csl, book);
        gl.execute();
        verify(csl,times(0)).printLine("Graphe vide.");
        verify(csl, times(1)).printLine("[1, 2, 4]");
        verify(csl,times(1)).printLine("[1, 2, 3]");
        verify(csl,times(1)).printLine("[1, 3]");
        verify(csl,times(1)).printLine("[2, 4]");
        verify(csl,times(1)).printLine("[2, 3]");
        verify(csl,times(1)).printLine("%d",3);
        verify(csl,times(1)).printLine("%d",4);
    }

}
