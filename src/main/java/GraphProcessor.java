package main.java;

import main.java.utils.StringUtils;
import main.java.utils.FileUtils;

import java.util.*;
import java.io.*;

public class GraphProcessor {
    private Graph graph;

    public GraphProcessor() {
        graph = new Graph();
    }

    public void generateGraph(String[] words) {
        for (int i = 0; i < words.length - 1; i++) {
            String from = words[i];
            String to = words[i + 1];
            graph.addEdge(from, to);
            graph.addNode(from);
            graph.addNode(to);
        }
    }

    public void showDirectedGraph() {
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

    public String queryBridgeWords(String word1, String word2) {
        if (!graph.hasNode(word1) || !graph.hasNode(word2)) {
            return "No word1 or word2 in the graph!";
        }

        List<String> bridgeWords = new ArrayList<>();
        for (String node : graph.getNodes()) {
            if (graph.hasEdge(word1, node) && graph.hasEdge(node, word2)) {
                bridgeWords.add(node);
            }
        }

        if (bridgeWords.isEmpty()) {
            return "No bridge words from " + word1 + " to " + word2 + "!";
        } else {
            return "The bridge words from " + word1 + " to " + word2 + " are: " + StringUtils.join(bridgeWords, ", ");
        }
    }

    public String generateNewText(String inputText) {
        String[] words = inputText.split(" ");
        List<String> result = new ArrayList<>();

        for (int i = 0; i < words.length - 1; i++) {
            result.add(words[i]);
            if (graph.hasNode(words[i]) && graph.hasNode(words[i + 1])) {
                List<String> bridgeWords = new ArrayList<>();
                for (String node : graph.getNodes()) {
                    if (graph.hasEdge(words[i], node) && graph.hasEdge(node, words[i + 1])) {
                        bridgeWords.add(node);
                    }
                }
                if (!bridgeWords.isEmpty()) {
                    Collections.shuffle(bridgeWords);
                    result.add(bridgeWords.get(0));
                }
            }
        }
        result.add(words[words.length - 1]);

        return StringUtils.join(result, " ");
    }

    public String calcShortestPath(String word1, String word2) {
        if (!graph.hasNode(word1) || !graph.hasNode(word2)) {
            return "No word1 or word2 in the graph!";
        }

        Map<String, Double> distances = new HashMap<>();
        Map<String, String> predecessors = new HashMap<>();
        Set<String> visited = new HashSet<>();

        for (String node : graph.getNodes()) {
            distances.put(node, Double.MAX_VALUE);
        }
        distances.put(word1, 0.0);

        while (!visited.containsAll(graph.getNodes())) {
            String current = null;
            double currentDistance = Double.MAX_VALUE;
            for (Map.Entry<String, Double> entry : distances.entrySet()) {
                if (!visited.contains(entry.getKey()) && entry.getValue() < currentDistance) {
                    current = entry.getKey();
                    currentDistance = entry.getValue();
                }
            }

            if (current == null) {
                break;
            }

            visited.add(current);

            for (Map.Entry<String, Integer> edge : graph.getEdgesFrom(current).entrySet()) {
                String neighbor = edge.getKey();
                double weight = edge.getValue();
                double distance = currentDistance + weight;
                if (distance < distances.get(neighbor)) {
                    distances.put(neighbor, distance);
                    predecessors.put(neighbor, current);
                }
            }
        }

        if (distances.get(word2) == Double.MAX_VALUE) {
            return word1 + " to " + word2 + " is unreachable.";
        }

        List<String> path = new ArrayList<>();
        String current = word2;
        while (current != null && !current.equals(word1)) {
            path.add(current);
            current = predecessors.get(current);
        }
        if (current == null) {
            return word1 + " to " + word2 + " is unreachable.";
        }
        path.add(word1);
        Collections.reverse(path);

        return "Shortest path from " + word1 + " to " + word2 + ": " +
                StringUtils.join(path, " -> ") + " (Length: " + distances.get(word2) + ")";
    }

    public double calPageRank(String word) {
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

    public String randomWalk() {
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

            // 检查是否有重复的边
            if (path.size() >= 2 && path.get(path.size() - 2).equals(current)) {
                break;
            }

            if (path.size() >= 100) {
                break;
            }
        }

        String result = "Random walk path: " + StringUtils.join(path, " -> ");
        FileUtils.writeFile("random_walk_result.txt", result);
        return result;
    }

    public String calcAllShortestPathsFrom(String word) {
        if (!graph.hasNode(word)) {
            return "单词 " + word + " 不在图中！";
        }

        StringBuilder result = new StringBuilder();
        for (String target : graph.getNodes()) {
            if (!target.equals(word)) {
                result.append(calcShortestPath(word, target)).append("\n");
            }
        }
        return result.toString();
    }
}
