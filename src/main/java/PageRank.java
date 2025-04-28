package main.java;

import main.java.utils.StringUtils;

import java.util.*;

public class PageRank {
    private Graph graph;

    public PageRank(Graph graph) {
        this.graph = graph;
    }

    public double calculate(String word) {
        if (!graph.hasNode(word)) {
            return 0.0;
        }

        final double d = 0.85;
        Map<String, Double> pr = new HashMap<>();
        Set<String> nodes = new HashSet<>(graph.getNodes());

        for (String node : nodes) {
            pr.put(node, 1.0 / nodes.size());
        }

        for (int i = 0; i < 100; i++) {
            Map<String, Double> newPr = new HashMap<>();
            for (String node : nodes) {
                double rank = (1 - d) / nodes.size();
                for (String neighbor : graph.getNodes()) {
                    if (graph.hasEdge(neighbor, node)) {
                        int numEdges = graph.getEdgesFrom(neighbor).size();
                        rank += d * pr.get(neighbor) / numEdges;
                    }
                }
                newPr.put(node, rank);
            }
            pr = newPr;
        }

        return pr.get(word);
    }
}

