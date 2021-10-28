package commands.modify;

import commands.Command;
import console.Console;
import org.junit.jupiter.api.*;
import model.impl.*;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModifyParagraphTest {
    Console console = mock(Console.class);
    static SortedMap<Integer, Paragraph> para;
    static Book book;
    static Set<Action> ac = new HashSet<>();

    @BeforeAll
    public static void init(){
        para=new TreeMap<>();
        book = new Book("La mere noir", (TreeMap<Integer, Paragraph>) para);
        ac.add(new Action("hurr",2));
        ac.add(new Action("hurry",3));
        book.addParagraph(1);
        book.addParagraph(2);
        book.addParagraph(3);
    }

    @Test
    @Order(1)
    void modifyParagrapheContent() throws Exception {
        Command mod = new ModifyParagraph(console, book);
        when(console.readInteger("Numero du paragraphe a modifier (0 pour retour au menu)")).thenReturn(1);
        when(console.readLine("Texte actuel du paragraphe %d : %s\nEncodez le nouveau texte ou Enter pour le conserver:",1,"TO-DO")).thenReturn("Para1");
        when(console.readLine("Souhaitez-vous modifier les actions possibles (o/n)? ")).thenReturn("n");


        mod.execute();
        assertEquals(book.getThisParagraph(1).getContent(),"Para1");
    }
    @Test
    @Order(2)
    void modifyParagrapheAddAction() throws Exception {
        Console console = mock(Console.class);
        Command mod = new ModifyParagraph(console, book);

        when(console.readInteger("Numero du paragraphe a modifier (0 pour retour au menu)")).thenReturn(1);
        when(console.readLine("Texte actuel du paragraphe %d : %s\nEncodez le nouveau texte ou Enter pour le conserver:",1,"Para1")).thenReturn("");
        when(console.readLine("Souhaitez-vous modifier les actions possibles (o/n)? ")).thenReturn("o");
        when(console.readLine("Souhaitez-vous ajouter une action possible (o/n) ? ")).thenReturn("o");
        when(console.readLine("Texte de laction possible (Enter si aucune) : ")).thenReturn("Go2","go3","");
        when( console.readInteger("Numero du paragraphe de destination")).thenReturn(2,3);

        mod.execute();
        assertTrue(book.getThisParagraph(1).hasAction());
    }

    @Test
    @Order(3)
    void modifyParagrapheChangeTextAct() throws Exception {
        Console console = mock(Console.class);
        Command mod = new ModifyParagraph(console, book);

        when(console.readInteger("Numero du paragraphe a modifier (0 pour retour au menu)")).thenReturn(1);
        when(console.readLine("Texte actuel du paragraphe %d : %s\nEncodez le nouveau texte ou Enter pour le conserver:",1,"Para1")).thenReturn("");
        when(console.readLine("Souhaitez-vous modifier les actions possibles (o/n)? ")).thenReturn("o");
        when(console.readLine("Souhaitez-vous ajouter une action possible (o/n) ? ")).thenReturn("n");
        when(console.readLine("Nouveau texte (Tapez enter pour pas changer)")).thenReturn("go3f","");
        when( console.readLine("Nouveau numero (num initiale %d)(Tapez enter pour pas changer)", 2)).thenReturn("3");
        when( console.readLine("Nouveau numero (num initiale %d)(Tapez enter pour pas changer)", 3)).thenReturn("");
        when(console.readLine("Supprimer (o/n) ?")).thenReturn("n","n");


        mod.execute();
        for(int a: book.getThisParagraph(1).getNumberOfAllActionPossible()){
            assertEquals(3,a);
        }
    }
    @Test
    @Order(4)
    void modifyParagrapheExit() throws Exception {
        Command mod = new ModifyParagraph(console, book);
        when(console.readInteger("Numero du paragraphe a modifier (0 pour retour au menu)")).thenReturn(0);
        mod.execute();
        assertTrue(true);
    }

    @Test
    @Order(5)
    void modifyParagrapheDeleterAction() throws Exception {
        Console console = mock(Console.class);
        Command mod = new ModifyParagraph(console, book);

        when(console.readInteger("Numero du paragraphe a modifier (0 pour retour au menu)")).thenReturn(1);
        when(console.readLine("Texte actuel du paragraphe %d : %s\nEncodez le nouveau texte ou Enter pour le conserver:",1,"Para1")).thenReturn("");
        when(console.readLine("Souhaitez-vous modifier les actions possibles (o/n)? ")).thenReturn("o");
        when(console.readLine("Supprimer (o/n) ?")).thenReturn("o","o");
        when( console.readLine("Souhaitez-vous ajouter une action possible (o/n) ? ")).thenReturn("n");
        when(console.readLine("Etes-vous sur ? ")).thenReturn("o","o");


        mod.execute();
        assertEquals(0, book.getThisParagraph(1).getActions().size());

    }

}
