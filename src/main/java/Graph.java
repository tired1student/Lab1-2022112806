package main.java;

import java.util.*;

public class Graph {
    private Map<String, Map<String, Integer>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(String from, String to) {
        adjacencyList.putIfAbsent(from, new HashMap<>());
        adjacencyList.putIfAbsent(to, new HashMap<>());
        adjacencyList.get(from).merge(to, 1, Integer::sum);
    }

    public void addNode(String node) {
        adjacencyList.putIfAbsent(node, new HashMap<>());
    }

    public boolean hasNode(String node) {
        return adjacencyList.containsKey(node);
    }

    public Set<String> getNodes() {
        return new HashSet<>(adjacencyList.keySet());
    }

    public List<String> getNeighbors(String node) {
        return new ArrayList<>(adjacencyList.getOrDefault(node, new HashMap<>()).keySet());
    }

    public Map<String, Integer> getEdgesFrom(String node) {
        return adjacencyList.getOrDefault(node, new HashMap<>());
    }

    public double getEdgeWeight(String from, String to) {
        return adjacencyList.getOrDefault(from, new HashMap<>()).getOrDefault(to, 0);
    }

    public boolean hasEdge(String from, String to) {
        return adjacencyList.getOrDefault(from, new HashMap<>()).containsKey(to);
    }

    public Map<String, Map<String, Integer>> getAdjacencyList() {
        return adjacencyList;
    }
}

