package model;

import model.impl.Action;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ActionTest {

    @Test
    public void testActionCreated(){
        Action action = new Action("blbla",1);
        assertEquals(action.getClass(), Action.class);
    }

    @Test
    public void testGetNum(){
        Action action = new Action("blbla",1);
        assertEquals(1,action.getDestination());
    }
    @Test
    public void testGetTexte(){
        Action action = new Action("blbla",1);
        assertEquals("blbla",action.getContent());
    }

    @Test
    public void testSetNum(){
        Action action = new Action("blbla",1);
        action.setDestination(4);
        assertEquals(4,action.getDestination());
    }
    @Test
    public void testSetTexte(){
        Action action = new Action("blbla",1);
        action.setContent("et oui.");
        assertEquals("et oui.", action.getContent());
    }
    @Test
    public void testEqualsTrue(){
        Action action = new Action("blbla",1);
        assertEquals(action,action);
    }
    @Test
    public void testEqualsFalse(){
        Action action = new Action("blbla",1);
        Action actions = new Action("blbla",2);
        assertFalse(action.equals(actions));
        assertFalse(action.equals(11));
    }
    @Test
    public void testHash(){
        int a = Integer.hashCode(12);
        Action action = new Action("blbla",12);
        assertEquals(action.hashCode(),a);
    }
}
