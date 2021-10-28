package commands.modify;

import commands.Command;
import console.Console;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import model.impl.*;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteParagraphTest {
    Console console =mock(Console.class);
    static SortedMap<Integer, Paragraph> para;
    static Book book;
    static Set<Action> ac = new HashSet<>();

    @BeforeAll
    public static void init(){
        para=new TreeMap<>();
        book = new Book("La mere noir", (TreeMap<Integer, Paragraph>) para);
    }

    @Test
    void deleteParagraphe() throws Exception {
        book.addParagraph(1);
        Command sup = new DeleteParagraphe(console, book);
        when(console.readInteger("Numero du paragraphe a supprimer ?")).thenReturn(1);
        when(console.readLine("Etes-vous sur? Le paragraphe %d va etre definitivement supprime. (o/n)",1)).thenReturn("o");

        sup.execute();
        assertFalse(book.containsKey(1));
    }

    @Test
    void deleteParagrapheNotExisting() throws Exception {
        Command sup = new DeleteParagraphe(console, book);
        when(console.readInteger("Numero du paragraphe a supprimer ?")).thenReturn(1);

        sup.execute();
        assertFalse(book.containsKey(1));
    }


}
