package storage;

import model.impl.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestStorage {
    @Test
    public void testSave(){
        IManager m = mock(IManager.class);
        Book book = new Book("");
        when(m.save(book,"aa")).thenReturn(true);


        assertTrue(m.save(book,"aa"));

    }
    @Test
    public void testLoad(){
        IManager m = mock(IManager.class);
        Book book = new Book("");
        when(m.load("aa")).thenReturn(book);
        assertEquals(book,m.load("aa"));

    }
}
