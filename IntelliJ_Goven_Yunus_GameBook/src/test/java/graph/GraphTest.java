package graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GraphTest {

    @Test
    public void testGraphCreated(){
        Graph graph = new Graph(2);
        assertEquals(graph.getClass(), Graph.class);
    }
    @Test
    public void testNodeCreated(){
        Graph graph = new Graph(4);
        for(int i=1;i<=4;++i){
            assertTrue(graph.getNoeud(i).isEmpty());
        }
    }
    @Test
    public void testGraphIsEmpty(){
        Graph g = new Graph(0);
        assertTrue(g.isEmpty());
    }
    @Test
    public void testGraphIsNotEmpty(){
        Graph g = new Graph(1);
        assertFalse(g.isEmpty());
    }
    @Test
    public void testAddDestination(){
        Graph g = new Graph(2);
        g.addDest(1,2);
        assertEquals(1,g.getNoeud(1).size());
    }
}
