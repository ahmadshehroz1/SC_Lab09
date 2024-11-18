package graph;

import java.util.*;

/**
 * An implementation of Graph with mutable vertices and edges.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   - A ConcreteVerticesGraph is represented by a list of vertices, each of which contains a label and a list of edges.
    // Representation invariant:
    //   - Each vertex is uniquely identified by its label and has a list of outgoing edges with weights.
    // Safety from rep exposure:
    //   - vertices is a private field and is never exposed directly.
    
    // Adds a new vertex to the graph, if not already present.
    @Override
    public boolean add(String vertex) {
        if (getVertex(vertex) == null) {
            vertices.add(new Vertex(vertex));
            return true;
        }
        return false;
    }

    // Sets an edge from source to target with a given weight.
    @Override
    public int set(String source, String target, int weight) {
        Vertex src = getVertex(source);
        if (src == null) {
            throw new NoSuchElementException("Source vertex not found");
        }
        return src.setEdge(target, weight);
    }

    // Removes a vertex and its associated edges.
    @Override
    public boolean remove(String vertex) {
        Vertex v = getVertex(vertex);
        if (v != null) {
            vertices.remove(v);
            // Remove edges pointing to this vertex
            for (Vertex vertexObj : vertices) {
                vertexObj.removeEdge(vertex);
            }
            return true;
        }
        return false;
    }

    // Returns the set of all vertex labels.
    @Override
    public Set<String> vertices() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex v : vertices) {
            vertexLabels.add(v.getLabel());
        }
        return vertexLabels;
    }

    // Returns a map of source vertices and their associated edge weights for the given target vertex.
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            Map<String, Integer> edges = v.getOutgoingEdges();
            if (edges.containsKey(target)) {
                sources.put(v.getLabel(), edges.get(target));
            }
        }
        return sources;
    }

    // Returns a map of target vertices and their associated edge weights for the given source vertex.
    @Override
    public Map<String, Integer> targets(String source) {
        Vertex src = getVertex(source);
        if (src == null) {
            throw new NoSuchElementException("Source vertex not found");
        }
        return src.getOutgoingEdges();
    }

    // Returns a string representation of the graph.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) {
            sb.append(v.toString()).append("\n");
        }
        return sb.toString();
    }

    // Helper method to get a vertex by its label.
    private Vertex getVertex(String label) {
        for (Vertex v : vertices) {
            if (v.getLabel().equals(label)) {
                return v;
            }
        }
        return null;
    }
}

/**
 * Represents a vertex in the graph.
 * This class is mutable.
 */
class Vertex {
    
    private final String label;
    private final List<Edge> edges;
    
    // Constructor to initialize a vertex with a label.
    Vertex(String label) {
        this.label = label;
        this.edges = new ArrayList<>();
    }

    // Gets the label of this vertex
    public String getLabel() {
        return label;
    }

    // Adds or updates an edge from this vertex to the target vertex with the given weight.
    public int setEdge(String target, int weight) {
        for (Edge e : edges) {
            if (e.getTarget().equals(target)) {
                e.setWeight(weight);  // If the edge exists, update its weight
                return weight;
            }
        }
        edges.add(new Edge(target, weight));  // Otherwise, add a new edge
        return weight;
    }

    // Removes the edge to the target vertex.
    public void removeEdge(String target) {
        edges.removeIf(edge -> edge.getTarget().equals(target));
    }

    // Returns a map of outgoing edges with the target vertices and their weights.
    public Map<String, Integer> getOutgoingEdges() {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge e : edges) {
            targets.put(e.getTarget(), e.getWeight());
        }
        return targets;
    }

    // String representation of the vertex, including its edges and weights.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(label).append(" -> ");
        if (edges.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[");
            for (Edge e : edges) {
                sb.append(e.getTarget()).append(" (").append(e.getWeight()).append("), ");
            }
            sb.setLength(sb.length() - 2); // Remove trailing comma and space
            sb.append("]");
        }
        return sb.toString();
    }
}

/**
 * Represents an edge in the graph from a vertex to another vertex with a specified weight.
 */
class Edge {
    private final String target;
    private int weight;

    // Constructor
    Edge(String target, int weight) {
        this.target = target;
        this.weight = weight;
    }

    // Gets the target vertex of this edge
    public String getTarget() {
        return target;
    }

    // Gets the weight of the edge
    public int getWeight() {
        return weight;
    }

    // Sets the weight of the edge
    public void setWeight(int weight) {
        this.weight = weight;
    }
}
