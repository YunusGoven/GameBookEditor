package model;

import model.impl.Action;
import model.impl.Paragraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ParagraphTest {
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
    }
    @Test
    public void testParagraphCreated(){
        Paragraph p = new Paragraph(1,"zz",new HashSet<>());
        assertEquals(p.getClass(), Paragraph.class);
    }
    @Test
    public void testGetNum(){
        Paragraph p = new Paragraph(1,"zz");
        assertEquals(1,p.getNumber());
    }

    @Test
    public void testGetContenu(){
        Paragraph p = new Paragraph(1,"zz",new HashSet<>());
        assertEquals("zz",p.getContent());
    }
    @Test
    public void testsetNum(){
        Paragraph p = new Paragraph(1,"zz");
        p.setNumber(2);
        assertEquals(2,p.getNumber());
    }

    @Test
    public void testsetContenu(){
        Paragraph p = new Paragraph(1,"zz",new HashSet<>());
        p.setContent("ert");
        assertEquals("ert",p.getContent());
    }
    @Test
    public void testAddAction(){
        Paragraph p = new Paragraph(1,"zz",ac);
        Action a = new Action("33",3);
        p.addAction(a);
        ac.add(a);
        assertEquals(ac,p.getActions());
    }

    @Test
    public void testAddNullActionReturnFalse(){
        Paragraph p = new Paragraph(1,"zz",ac);
        assertFalse(p.addAction(null));
    }
    @Test
    public void testdeleteAction(){
        Paragraph p = new Paragraph(1,"zz",ac);
        p.deleteAction(1);
        ac.remove(action1);
        assertEquals(ac.size(),p.getActions().size());
    }
    @Test
    void testDeleteActionActionParam(){
        Paragraph p = new Paragraph(1,"zz",ac);
        p.removeAction(action1);
        ac.remove(action1);
        assertEquals(ac.size(),p.getActions().size());
    }
    @Test
    public void HasAction(){
        Paragraph p = new Paragraph(1,"zz",new HashSet<Action>());
        assertFalse(p.hasAction());
    }
    @Test
    public void getNumberOfAction(){
        HashSet<Action>aa = new HashSet<>();
        aa.add(new Action("h",2));
        Paragraph p = new Paragraph(1,"zz",aa);
        Set<Integer> i = new HashSet<>();
        i.add(2);
        Set<Integer> get = p.getNumberOfAllActionPossible();
        for(int val : get) {
            assertTrue(i.contains(val));
        }
    }

    @Test
    public void testSetAction(){
        Paragraph p = new Paragraph(1,"zz",ac);
        ac = new HashSet<>();
        p.setActions(ac);
        assertFalse(p.hasAction());
    }
    @Test
    public void testEqualsTrue(){
        Paragraph p = new Paragraph(1,"zz",ac);
        assertEquals(p,p);
    }
    @Test
    public void testEqualsFalse(){
        Paragraph p = new Paragraph(1,"zz",ac);
        Paragraph p1 = new Paragraph(2,"zz",ac);
        assertNotEquals(p,p1);
    }
    @Test
    public void testEqualsFalseNotParagrap(){
        Paragraph p = new Paragraph(1,"zz",ac);
        assertNotEquals(p,new Action("5f",1));
    }
    @Test
    public void testHash(){
        int ha = Integer.hashCode(1);
        Paragraph p = new Paragraph(1,"zz",ac);
        assertEquals(ha, p.hashCode());
    }

    @Test
    public void testIteratorAction(){
        Paragraph p = new Paragraph(1,"zz",ac);
        Iterator<Action> it = p.iterator();
        while(it.hasNext()){
            Action act = it.next();
            assertTrue(ac.contains(act));
        }
    }
    @Test
    public void testGetThisAction(){
        HashSet<Action> a = new HashSet<>();
        a.add(action);
        a.add(action2);
        Paragraph p = new Paragraph(1,"zz",a);
        assertEquals(action,p.getThisAction(2));
    }
    @Test
    public void testGetThisActionNotExist(){
        HashSet<Action> a = new HashSet<>();
        a.add(action);
        a.add(action2);
        Paragraph p = new Paragraph(1,"zz",a);
        assertNull(p.getThisAction(7));
    }
    @Test
    public void testDeleteAction(){
        HashSet<Action> a = new HashSet<>();
        a.add(action);
        a.add(action2);
        Paragraph p = new Paragraph(1,"zz",a);
        p.deleteAction(4);
        assertEquals(p.getActions().size(),1);
    }

    @Test
    public void getContentShortInShort(){
        Paragraph p = new Paragraph(1,"zz");
        assertEquals("1. zz",p.getInformationInShort());
    }
    @Test
    public void getContentLongInShort(){
        Paragraph p = new Paragraph(1,"123456789123456789123456");
        assertEquals("1. 12345678912345678912",p.getInformationInShort());
    }
    @Test
    public void getInformationInShort(){
        Paragraph p = new Paragraph(1,"zz");
        assertEquals("1. zz",p.getInformationInShort());
    }
}
