package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class ConcreteEdgesGraph implements Graph<String> {

    // Set of vertices
    private final Set<String> vertices = new HashSet<>();
    
    // List of edges (Edge objects)
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    // - The graph consists of a set of vertices and a list of edges.
    // - Each edge connects a source vertex to a target vertex with a specified weight.
    
    // Representation invariant:
    // - The vertices set contains only the vertices that appear as source or target in the edges.
    // - Each source/target vertex in edges must be present in the vertices set.
    
    // Safety from rep exposure:
    // - The vertices and edges fields are private and cannot be accessed directly outside the class.
    // - We return copies of the collections when appropriate to prevent external modifications.

    @Override
    public boolean add(String vertex) {
        return vertices.add(vertex);  // adds vertex if not already present
    }

    @Override
    public int set(String source, String target, int weight) {
        // Ensure both vertices exist
        add(source);  // add source if it doesn't exist
        add(target);  // add target if it doesn't exist
        
        // Create a new edge object and add it to the list of edges
        for (Edge edge : edges) {
            if (edge.source.equals(source) && edge.target.equals(target)) {
                edge.weight = weight;  // if the edge exists, update its weight
                return weight;
            }
        }

        // If the edge doesn't exist, add a new edge
        edges.add(new Edge(source, target, weight));
        return weight;
    }

    @Override
    public boolean remove(String vertex) {
        // Remove the vertex from the vertices set
        if (!vertices.contains(vertex)) {
            return false;
        }

        // Remove any edges that include the vertex as source or target
        edges.removeIf(edge -> edge.source.equals(vertex) || edge.target.equals(vertex));

        // Remove the vertex itself from the set of vertices
        vertices.remove(vertex);
        return true;
    }

    @Override
    public Set<String> vertices() {
        return new HashSet<>(vertices);  // return a copy to prevent external modification
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sourceMap = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.target.equals(target)) {
                sourceMap.put(edge.source, edge.weight);
            }
        }
        return sourceMap;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targetMap = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.source.equals(source)) {
                targetMap.put(edge.target, edge.weight);
            }
        }
        return targetMap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String vertex : vertices) {
            sb.append(vertex).append(" -> ").append(targets(vertex)).append("\n");
        }
        return sb.toString();
    }
    
    class Edge {
        String source;
        String target;
        int weight;

        Edge(String source, String target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        // toString method for debugging
        @Override
        public String toString() {
            return source + " -> " + target + " (" + weight + ")";
        }
    }

    
}

