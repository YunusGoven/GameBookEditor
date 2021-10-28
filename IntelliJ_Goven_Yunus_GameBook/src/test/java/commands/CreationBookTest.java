package commands;

import console.Console;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import model.impl.*;
import storage.IManager;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CreationBookTest {
    Console console = mock(Console.class);
    static SortedMap<Integer, Paragraph> para;
    static Set<Action> ac = new HashSet<>();
    IManager fs = mock(IManager.class);

    @BeforeAll
    public static void init(){
        para=new TreeMap<>();
    }

    @Test
    void testCreateLivre(){
        Book book = new Book("");
        CreationLivreCommand cre = new CreationLivreCommand(console, book,fs);
        when(console.readLine("Quel serait le nom du livre ?  ")).thenReturn("le livre");

        cre.execute();
        assertEquals("le livre", book.getTitle());
    }





}
