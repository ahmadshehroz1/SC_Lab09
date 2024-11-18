/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import graph.Graph;


public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   graph represents the generated word affinity graph
    // Representation invariant:
    //   graph isn't modified by any functions
    // Safety from rep exposure:
    //   Graph<String> is never returned or modified, and is declared as private final
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {
        Scanner scanner = new Scanner(corpus);
        if (scanner.hasNext()) {
            String current = scanner.next().toLowerCase();
            graph.add(current);
            while (scanner.hasNext()) {
                String next = scanner.next().toLowerCase();
                int original = graph.set(current, next, 1);
                if (original != 0) {
                    graph.set(current, next, original + 1);
                }
                current = next;
            }
        }
        scanner.close();
    }

    /**
     * Get the generated word affinity graph
     * 
     * @return a copy of the generated word affinity graph
     */
    public Graph<String> getGraph() {
        Graph<String> result = Graph.empty();
        for (String vertex : graph.vertices()) {
            result.add(vertex);
            Map<String, Integer> targets = graph.targets(vertex);
            for (String target : targets.keySet()) {
                result.set(vertex, target, targets.get(target));
            }
        }
        return result;
    }

    /**
     * Try to find a bridge word in the word affinity graph
     * 
     * @param current current word in the input poem
     * @param next next word in the input poem
     * @return the bridge word if found, empty string if not found
     * @throws NoSuchElementException if not found
     */
    private String findBridgeWord(String current, String next) throws NoSuchElementException {
        current = current.toLowerCase();
        next = next.toLowerCase();
        Set<String> targets = graph.targets(current).keySet();
        for (String middle : targets) {
            if (graph.targets(middle).keySet().contains(next)) {
                return middle;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        Scanner scanner = new Scanner(input);
        List<String> words = new ArrayList<>();
        if (scanner.hasNext()) {
            String current = scanner.next();
            words.add(current);
            while (scanner.hasNext()) {
                String next = scanner.next();
                try {
                    words.add(findBridgeWord(current, next));
                }
                catch (NoSuchElementException e) { }
                words.add(next);
                current = next;
            }
        }
        scanner.close();
        return String.join(" ", words);
    }

    /**
     * @return string representation of the word affinity graph
     */
    @Override public String toString() {
        return this.getGraph().toString();
    }

}