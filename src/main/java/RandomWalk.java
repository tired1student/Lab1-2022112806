package main.java;

import main.java.utils.StringUtils;
import main.java.utils.FileUtils;

import java.util.*;

public class RandomWalk {
    private Graph graph;

    public RandomWalk(Graph graph) {
        this.graph = graph;
    }

    public String perform() {
        List<String> path = new ArrayList<>();
        Set<String> nodes = graph.getNodes();
        if (nodes.isEmpty()) {
            return "Graph is empty.";
        }

        Random random = new Random();
        String current = new ArrayList<>(nodes).get(random.nextInt(nodes.size()));
        path.add(current);

        while (true) {
            Map<String, Integer> edges = graph.getEdgesFrom(current);
            if (edges.isEmpty()) {
                break;
            }

            List<String> neighbors = new ArrayList<>(edges.keySet());
            current = neighbors.get(random.nextInt(neighbors.size()));
            path.add(current);

            if (path.size() >= 100) {
                break;
            }
        }

        String result = "Random walk path: " + StringUtils.join(path, " -> ");
        FileUtils.writeFile("random_walk_result.txt", result);
        return result;
    }
}