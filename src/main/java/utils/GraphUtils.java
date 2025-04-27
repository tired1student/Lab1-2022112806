package main.java.utils;

import main.java.Graph;

import java.util.*;

public class GraphUtils {
    public static void printGraph(Graph graph) {
        System.out.println("有向图结构：");
        for (Map.Entry<String, Map<String, Integer>> entry : graph.getAdjacencyList().entrySet()) {
            String from = entry.getKey();
            Map<String, Integer> edges = entry.getValue();
            System.out.print(from + " -> ");
            for (Map.Entry<String, Integer> edge : edges.entrySet()) {
                System.out.print(edge.getKey() + " (" + edge.getValue() + "), ");
            }
            System.out.println();
        }
    }
}