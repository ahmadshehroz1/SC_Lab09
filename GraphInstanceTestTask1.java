package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public abstract class GraphInstanceTest {
    
    // This method will be implemented by the subclass, e.g., ConcreteEdgesGraphTest.
    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // Testing strategy for ConcreteEdgesGraph:
    // - We test operations on an empty graph and ensure correctness when vertices and edges are added or removed.

    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        Set<String> vertices = graph.vertices();
        assertTrue("graph should contain added vertex", vertices.contains("A"));
    }

    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("A"); // adding the same vertex again
        Set<String> vertices = graph.vertices();
        assertEquals("graph should contain only one instance of 'A'", 1, vertices.size());
    }

    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        // Set an edge from A to B with weight 5
        graph.set("A", "B", 5);
        
        Map<String, Integer> targets = graph.targets("A");
        assertEquals("A should have one outgoing edge to B with weight 5", 
                     Integer.valueOf(5), targets.get("B"));
        
        Map<String, Integer> sources = graph.sources("B");
        assertEquals("B should have one incoming edge from A with weight 5", 
                     Integer.valueOf(5), sources.get("A"));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        // Add an edge between A and B
        graph.set("A", "B", 10);
        
        // Remove vertex A
        graph.remove("A");
        
        // Check that vertex A is removed
        Set<String> vertices = graph.vertices();
        assertFalse("graph should not contain removed vertex A", vertices.contains("A"));
        
        // Ensure that edges related to A are also removed
        Map<String, Integer> targets = graph.targets("A");
        assertTrue("A should have no outgoing edges", targets.isEmpty());
        
        Map<String, Integer> sources = graph.sources("A");
        assertTrue("A should have no incoming edges", sources.isEmpty());
    }

    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        
        // Set edges: A -> B (weight 5), B -> C (weight 10)
        graph.set("A", "B", 5);
        graph.set("B", "C", 10);
        
        Map<String, Integer> targetsA = graph.targets("A");
        assertEquals("A should have one outgoing edge to B with weight 5", 
                     Integer.valueOf(5), targetsA.get("B"));
        
        Map<String, Integer> sourcesB = graph.sources("B");
        assertEquals("B should have one incoming edge from A with weight 5", 
                     Integer.valueOf(5), sourcesB.get("A"));
        
        Map<String, Integer> targetsB = graph.targets("B");
        assertEquals("B should have one outgoing edge to C with weight 10", 
                     Integer.valueOf(10), targetsB.get("C"));
        
        Map<String, Integer> sourcesC = graph.sources("C");
        assertEquals("C should have one incoming edge from B with weight 10", 
                     Integer.valueOf(10), sourcesC.get("B"));
    }
    @Test
    public void testRemoveNonExistingVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");

        // Try removing a non-existing vertex "C"
        graph.remove("C");

        // Verify that the graph still contains "A" and "B"
        Set<String> vertices = graph.vertices();
        assertTrue("graph should still contain vertex A", vertices.contains("A"));
        assertTrue("graph should still contain vertex B", vertices.contains("B"));
    }


    @Test
    public void testRemoveAllVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");

        // Remove all vertices
        graph.remove("A");
        graph.remove("B");
        graph.remove("C");

        // Verify that the graph is now empty
        Set<String> vertices = graph.vertices();
        assertTrue("graph should contain no vertices after removing all", vertices.isEmpty());
    }

}

