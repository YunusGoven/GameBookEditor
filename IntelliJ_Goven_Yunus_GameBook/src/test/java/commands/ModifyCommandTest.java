package commands;

import console.Console;
import model.impl.Action;
import org.junit.jupiter.api.Test;
import model.impl.*;
import storage.IManager;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ModifyCommandTest {

    Console console = mock(Console.class);
    static SortedMap<Integer, Paragraph> para;
    static Set<Action> ac = new HashSet<>();
    IManager fs = mock(IManager.class);



    @Test
    void testLivreSansTitre() throws Exception {
        Book book = new Book("");
        ModifyCommand mc = new ModifyCommand(console, book,fs);
        when(console.readInteger("1. Modifier le titre du livre\n" + "2. Ajouter un nouveau paragraphe\n"
                + "3. Modifier un paragraphe existant\n" + "4. Supprimer un paragraphe\n"
                + "5. Arreter la modification\n" + "Votre choix : ")).thenReturn(2,5);


        mc.execute();
        verify(console,times(1)).printLine("Veuillez d'abord creer un livre merci");

    }
    @Test
    void testLivreAvecTitre() throws Exception {
        Book book = new Book("ics");
        ModifyCommand mc = new ModifyCommand(console, book,fs);
        when(console.readInteger("1. Modifier le titre du livre\n" + "2. Ajouter un nouveau paragraphe\n"
                + "3. Modifier un paragraphe existant\n" + "4. Supprimer un paragraphe\n"
                + "5. Arreter la modification\n" + "Votre choix : ")).thenReturn(5);

        mc.execute();
        verify(fs,times(1)).save(book,"console");
    }

    @Test
    void testSetName() throws Exception {
        Book book = new Book("ics");
        ModifyCommand mc = new ModifyCommand(console, book,fs);

        when(console.readInteger("1. Modifier le titre du livre\n" + "2. Ajouter un nouveau paragraphe\n"
                + "3. Modifier un paragraphe existant\n" + "4. Supprimer un paragraphe\n"
                + "5. Arreter la modification\n" + "Votre choix : ")).thenReturn(1,5);
        when(console.readLine("ics")).thenReturn("New");



        final String[]userInput = {"1","New","5"};
        mc.execute();
        assertEquals(book.getTitle(),"New");
    }

    @Test
    public void testBookNoName() throws Exception {
        Console csl = mock(Console.class);
        IManager st = mock(IManager.class);
        Book book = new Book("");
        ModifyCommand md = new ModifyCommand(csl,book,st);
        doReturn(2).when(csl).readInteger(anyString());
        doReturn(5).when(csl).readInteger(anyString());
        md.execute();
        verify(csl).printLine("Veuillez d'abord creer un livre merci");
    }

    @Test
    public void testBookWithNameAdd() throws Exception {
        Console csl = mock(Console.class,withSettings().verboseLogging());
        IManager st = mock(IManager.class);
        Book book = new Book("ad");
        ModifyCommand md = new ModifyCommand(csl,book,st);
        when(csl.readInteger(anyString())).thenReturn(2,5);
        when(csl.readLine(anyString())).thenReturn("1","para1","");

        md.execute();
        verify(csl).printLine("(Aucun Paragraphe)");
        verify(csl).printLine("P:1 para1               ... -\n");
    }
    @Test
    public void testBookEmptyModify() throws Exception {
        Console csl = mock(Console.class,withSettings().verboseLogging());
        IManager st = mock(IManager.class);
        Book book = new Book("ad");
        ModifyCommand md = new ModifyCommand(csl,book,st);
        when(csl.readInteger(anyString())).thenReturn(3,5);

        md.execute();
        verify(csl, times(2)).printLine("(Aucun Paragraphe)");
        verify(csl).printLine("Erreur");

    }
    @Test
    public void testBookNotEmptyModiy() throws Exception {
        Console csl = mock(Console.class,withSettings().verboseLogging());
        IManager st = mock(IManager.class);
        Book book = new Book("ad");
        ModifyCommand md = new ModifyCommand(csl,book,st);
        when(csl.readInteger(anyString())).thenReturn(2,3,0,5);
        when(csl.readLine(anyString())).thenReturn("1","para1","");

        md.execute();
        verify(csl).printLine("(Aucun Paragraphe)");
        verify(csl,times(2)).printLine("P:1 para1               ... -\n");
    }
    @Test
    public void testBookEmptyDelete() throws Exception {
        Console csl = mock(Console.class,withSettings().verboseLogging());
        IManager st = mock(IManager.class);
        Book book = new Book("ad");
        ModifyCommand md = new ModifyCommand(csl,book,st);
        when(csl.readInteger(anyString())).thenReturn(4,1,5);

        md.execute();
        verify(csl,times(2)).printLine("(Aucun Paragraphe)");
    }

}
