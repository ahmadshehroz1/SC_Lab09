package graph;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Map;
import java.util.Set;

/**
 * Tests for ConcreteVerticesGraph.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override 
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    @Test
    public void testToString() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);

        String expected = "A -> [B (10)]\nB -> []\n";
        assertEquals("Expected toString output", expected, graph.toString());
        
        graph.set("A", "B", 20);  // update edge weight
        expected = "A -> [B (20)]\nB -> []\n";
        assertEquals("Expected updated toString output", expected, graph.toString());
        
        graph.add("C");
        graph.set("B", "C", 5);
        expected = "A -> [B (20)]\nB -> [C (5)]\nC -> []\n";
        assertEquals("Expected updated toString output with additional vertex and edge", expected, graph.toString());
    }

    @Test
    public void testAddVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertTrue("Vertex A should be added", graph.add("A"));
        assertFalse("Vertex A should not be added again", graph.add("A"));
    }

    @Test
    public void testAddEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");

        assertEquals("Expected weight to be 10", 10, graph.set("A", "B", 10));

        Map<String, Integer> targetsA = graph.targets("A");
        assertEquals("A should have one target B with weight 10", 1, targetsA.size());
        assertEquals("Weight of edge from A to B should be 10", Integer.valueOf(10), targetsA.get("B"));
    }

    @Test
    public void testUpdateEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");

        graph.set("A", "B", 10);
        assertEquals("Expected weight to be updated to 20", 20, graph.set("A", "B", 20));

        Map<String, Integer> targetsA = graph.targets("A");
        assertEquals("A should have one target B with weight 20", Integer.valueOf(20), targetsA.get("B"));
    }

    @Test
    public void testRemoveVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);

        assertTrue("Vertex A should be removed", graph.remove("A"));

        Set<String> vertices = graph.vertices();
        assertFalse("Vertex A should not be in the graph", vertices.contains("A"));
        Map<String, Integer> sourcesB = graph.sources("B");
        assertFalse("B should have no sources after removing A", sourcesB.containsKey("A"));
    }

    @Test
    public void testSources() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);

        Map<String, Integer> sourcesB = graph.sources("B");
        assertEquals("Expected source for B to be A with weight 10", Integer.valueOf(10), sourcesB.get("A"));
    }

    @Test
    public void testTargets() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);

        Map<String, Integer> targetsA = graph.targets("A");
        assertEquals("Expected target for A to be B with weight 10", Integer.valueOf(10), targetsA.get("B"));
    }
}
