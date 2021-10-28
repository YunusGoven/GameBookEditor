package model;

import model.impl.Action;
import model.impl.Book;
import model.impl.Paragraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    static SortedMap<Integer, Paragraph> para;
    static Book book;
    static HashSet<Action> ac = new HashSet<>();
    static Action action = new Action("blbla",2);
    static Action action1 = new Action("blbla",3);
    static Action action2 = new Action("blbla",4);
    static Action action3 = new Action("blbla",5);

    @BeforeAll
    public static void init(){
        ac.add(action);
        ac.add(action1);
        ac.add(action2);
        ac.add(action3);
        Paragraph p = new Paragraph(1,"zz",ac);
        Paragraph p1 = new Paragraph(4,"zaz",ac);
        Paragraph p2 = new Paragraph(3,"zaaz",ac);

        para=new TreeMap<>();
        para.put(1,p);
        para.put(4,p1);
        para.put(3,p2);
        book = new Book("La mere noir", (TreeMap<Integer, Paragraph>) para);
    }

    @Test
    void testIsLivre(){
        assertEquals(book.getClass(), Book.class);
    }

    @Test
    public void testIsLivre2(){
        Book book = new Book("title", (TreeMap<Integer, Paragraph>) para);
        assertEquals(book.getClass(), Book.class);
    }
    @Test
    public void testIterator(){
        Book book = new Book("ad");
        Iterator<Paragraph> it = book.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    void testGetNumberOfParagraph(){
        Book l = new Book("a");
        assertEquals(0,l.getNumberOfParagraph());
        l.addParagraph(1);
        assertEquals(1,l.getNumberOfParagraph());
    }
    @Test
    void testIsEmpty(){
        assertFalse(book.isEmpty());
        Book l = new Book("a");
        assertTrue(l.isEmpty());
    }
    @Test
    void testLastParagraph(){
        assertEquals(7, book.getLastParagraph());
        Book l = new Book("a");
        assertEquals(0,l.getLastParagraph());
    }
    @Test
    void testContainsKey(){
        assertTrue(book.containsKey(1));
        assertFalse(book.containsKey(2));
    }
    @Test
    void testGetName(){
        assertEquals(book.getTitle(),"La mere noir");
    }



    @Test
    void testSetName(){
        book.setTitle("");
        assertNotEquals("La mere noir", book.getTitle());
        book.setTitle("Vide");
        assertEquals("Vide", book.getTitle());
    }

    @Test
    void addParagraph(){
        book.addParagraph(2);
        assertTrue(book.containsKey(2));
    }
    @Test
    void addParagraphExistingNum(){
        book.addParagraph(4);
        assertTrue(book.containsKey(5));
    }

    @Test
    void getLatestParag(){
        book.addParagraph(7);
        Set<Integer> in= book.getLastParagraphs();
        assertEquals(in.size(),2);
        assertFalse(in.contains(1));
    }

    @Test
    void getPara() throws Exception {
        Paragraph p = new Paragraph(1,"zz",ac);
        assertEquals(p, book.getThisParagraph(1));
    }


    @Test
    void remove() throws Exception {
        Book l = new Book("fd");
        Paragraph p = new Paragraph(1,"zz",ac);
        Paragraph p1 = new Paragraph(2,"zz",ac);
        l.addParagraph(p);
        l.addParagraph(p1);
        l.deleteParagraph(1);
        assertFalse(l.containsKey(2));
    }


    @Test
    void getAllNumAction(){
        Book book = new Book("ti", (TreeMap<Integer, Paragraph>) para);

        List<Integer> out = book.getAllNumberOfAction();
        assertEquals(12,out.size());
    }
    @Test
    void getAllNumPara(){
        Book l = new Book("hh");
        l.addParagraph(1);
        l.addParagraph(2);
        l.addParagraph(3);
        Set<Integer>in = new HashSet<>();
        in.add(1);
        in.add(2);
        in.add(3);
        Set<Integer>out = l.getAllNumberOfParagraphs();
        for(int i:out){
            assertTrue(in.contains(i));
        }
    }

   /* @Test
    public void testDeleteNotExistingParagraph() throws Exception {
        Book b = new Book("aa", (TreeMap<Integer, Paragraph>) para);
        b.deleteParagraph(15);
    }*/
    @Test
    public void testNodeAbsentBookEmpty() throws Exception {
        Book book = new Book("");
        assertEquals(0,book.getNodeAbsent().size());
        assertEquals(0,book.getInaccesibleParagraph().size());
    }
    @Test
    public void testNodeAbsentWithNo() throws Exception {
        Book b = new Book("az");
        Paragraph p = new Paragraph(1,"m");
        Paragraph p1 = new Paragraph(2,"a");
        p.addAction(new Action("e",3));
        p1.addAction(new Action("en",4));
        p1.addAction(new Action("ee",3));
        b.addParagraph(p);
        b.addParagraph(p1);
        b.addParagraph(3);
        b.addParagraph(4);
        assertEquals(1,b.getNodeAbsent().size());
        assertEquals(1,b.getInaccesibleParagraph().size());

    }
    @Test
    public void getGraphVide() throws Exception {
        Book b = new Book("a");
        var g = b.getGraph();
        assertTrue(g.isEmpty());
    }
    @Test
    public void getGraph() throws Exception{
        Book b = new Book("zz");
        Paragraph p = new Paragraph(1,"m");
        Paragraph p1 = new Paragraph(2,"a");
        p.addAction(new Action("e",3));
        p.addAction(new Action("ae",2));
        p1.addAction(new Action("en",4));
        p1.addAction(new Action("ee",3));
        b.addParagraph(p);
        b.addParagraph(p1);
        b.addParagraph(3);
        b.addParagraph(4);
        var g = b.getGraph();
        assertEquals(2,g.getNoeud(1).size());
        assertEquals(2,g.getNoeud(2).size());
        assertEquals(0,g.getNoeud(3).size());
        assertEquals(0,g.getNoeud(4).size());
    }

}
