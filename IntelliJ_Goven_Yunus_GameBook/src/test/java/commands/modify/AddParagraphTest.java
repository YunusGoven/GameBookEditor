package commands.modify;

import commands.Command;
import console.Console;
import model.impl.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddParagraphTest {

    Console console = mock(Console.class);
    static SortedMap<Integer, Paragraph> para;
    static Book book;
    static Set<Action> ac = new HashSet<>();

    @BeforeAll
    public static void init(){
        para=new TreeMap<>();
        book = new Book("La mere noir", (TreeMap<Integer, Paragraph>) para);
    }

    @Test
    void testCommandCreating() {
        Command add = new AddParagraph(book,4,console);
        assertEquals(add.getClass(), AddParagraph.class);
    }
    @Test
    void AddJustParagraph() throws Exception {
        Command add = new AddParagraph(book,4,console);
        when(console.readLine("Numero du nouveau paragraphe")).thenReturn("1");
        when(console.readLine("Texte du nouveau paragraphe")).thenReturn("Para1");
        when(console.readLine("Texte de l'action possible (ENTER si aucune")).thenReturn("");
        add.execute();
        assertTrue(book.containsKey(1));
    }
    @Test
    void AddParagraphNegative() throws Exception {
        book = new Book("e");
        Command add = new AddParagraph(book,4,console);
        when(console.readLine("Numero du nouveau paragraphe")).thenReturn("-1");

        add.execute();
        assertTrue(book.isEmpty());
    }

    @Test
    void AddParagraphNumNotInt() throws Exception {
        book = new Book("e");
        Command add = new AddParagraph(book,4,console);
        when(console.readLine("Numero du nouveau paragraphe")).thenReturn("adf");

        add.execute();
        assertTrue(book.isEmpty());
    }

    @Test
    void AddParagraphNumEmpty() throws Exception {
        book = new Book("e");
        Command add = new AddParagraph(book, book.getLastParagraph(),console);
        when(console.readLine("Numero du nouveau paragraphe")).thenReturn("");
        when(console.readLine("Texte du nouveau paragraphe")).thenReturn("Para1");
        when(console.readLine("Texte de l'action possible (ENTER si aucune")).thenReturn("");

        add.execute();
        assertTrue(book.containsKey(1));
    }

    @Test
    void AddParagraphTextEmpty() throws Exception {
        book = new Book("e");
        Command add = new AddParagraph(book, book.getLastParagraph(),console);
        when(console.readLine("Numero du nouveau paragraphe")).thenReturn("1");
        when(console.readLine("Texte du nouveau paragraphe")).thenReturn("","abig");

        when(console.readLine("Texte de l'action possible (ENTER si aucune")).thenReturn("");

        add.execute();
        assertTrue(book.containsKey(1));
    }
    @Test
    void AddParagraphNumEmptyWithAction() throws Exception {
        book = new Book("e");
        Command add = new AddParagraph(book, book.getLastParagraph(),console);

        when(console.readLine("Numero du nouveau paragraphe")).thenReturn("1");
        when(console.readLine("Texte du nouveau paragraphe")).thenReturn("Para1");
        when(console.readLine("Texte de l'action possible (ENTER si aucune")).thenReturn("go","");
        when(console.readInteger("Numero du paragraphe de destination: ")).thenReturn(2);

        add.execute();
        assertTrue(book.containsKey(2));
    }

    @Test
    void addActionParagraphExist() throws Exception {
        book = new Book("e");
        Command add = new AddParagraph(book, book.getLastParagraph(),console);
        book.addParagraph(1);
        book.addParagraph(2);
        when(console.readLine("Numero du nouveau paragraphe")).thenReturn("3");
        when(console.readLine("Texte du nouveau paragraphe")).thenReturn("abid");
        when(console.readLine("Texte de l'action possible (ENTER si aucune")).thenReturn("go","");
        when(console.readInteger("Numero du paragraphe de destination: ")).thenReturn(2);


        add.execute();
        assertTrue(book.containsKey(3));
    }
}
