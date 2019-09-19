package edu.sdsu.cs.datastructures;

import java.util.LinkedList;
import java.util.List;
import java.util.*;

/**
 * Natalia Alvarez
 */

public class DirectedGraph<V extends Comparable<V>> implements IGraph<V> {
    final Map<V, List<V>> verticies;
    private DirectedGraph<V> ourGraph;

    public DirectedGraph() {
        verticies = new HashMap<>();
    }

    @Override
    public void add(V vertexName) {
        if (!contains(vertexName)) verticies.put(vertexName, new LinkedList<>());
    }

    @Override
    public void connect(V start, V destination) {
        if (!contains(start) || !contains(destination))
            throw new NoSuchElementException("Vertex is not present in graph");
        for (V v : neighbors(start)) {
            if (v.compareTo(destination) == 0) return;
        }verticies.get(start).add(destination);
    }

    @Override
    public void clear() {
        verticies.clear();
    }

    @Override
    public boolean contains(V label) {
        for (V v : verticies.keySet()) {
            if (v.compareTo(label) == 0) return true;
        }return false;
    }

    @Override
    public void disconnect(V start, V destination) {
        if (!contains(start) || !contains(destination)) throw new NoSuchElementException("Vertex is not present in graph");
        verticies.get(start).remove(destination);
    }

    @Override
    public boolean isConnected(V start, V destination) {
        if (!contains(start) || !contains(destination)) throw new NoSuchElementException("Vertex is not present in graph");
        return !shortestPath(start, destination).isEmpty();
    }

    @Override
    public Iterable<V> neighbors(V vertexName) {
        if (!contains(vertexName)) throw new NoSuchElementException("Vertex is not present in graph");
        List<V> vertexNeighbors = new LinkedList<>();
        for (int i = 0; i < verticies.get(vertexName).size(); i++) {
            vertexNeighbors.add(verticies.get(vertexName).get(i));
        }return vertexNeighbors;
    }

    @Override
    public void remove(V vertexName) {
        if (!contains(vertexName)) throw new NoSuchElementException("Vertex is not present in graph");
        verticies.remove(vertexName);
        for (V v : verticies.keySet()) {
            verticies.get(v).remove(vertexName);
        }
    }

    @Override
    public List<V> shortestPath(V start, V destination) {
        Map<V, Integer> distance = new HashMap<>();
        List<V> visited = new LinkedList<>();
        List<V> pq = new LinkedList<>();
        List<V> shortPath = new LinkedList<>();
        Map<V, V> bread = new HashMap<>();
        if (!contains(start) || !contains(destination)) throw new NoSuchElementException();
        bread.put(start, null);
        distance.put(start, 0);
        pq.add(start);
        if (verticies.get(start).contains(destination)) {
            shortPath.add(start);
            shortPath.add(destination);
            return shortPath;
        }
        while (!pq.isEmpty()) {
            V currNode = ((LinkedList<V>) pq).poll();
            if (currNode.equals(destination)) break;
            if (verticies.get(currNode).size() == 0) return shortPath;
            for (V neighbor : verticies.get(currNode)) {
                if (!distance.containsKey(neighbor)) {
                    distance.put(neighbor, distance.get(currNode) + 1);
                    bread.put(neighbor, currNode);
                    pq.add(neighbor);
                }
            }visited.add(currNode);
        }
        V entry = destination;
        while (entry != null) {
            shortPath.add(entry);
            entry = bread.get(entry);
        }Collections.reverse(shortPath);
        return shortPath;
    }

    @Override
    public int size() { return verticies.size(); }

    @Override
    public Iterable<V> vertices() { return verticies.keySet(); }

    @Override
    public IGraph<V> connectedGraph(V origin) {
        ourGraph= new DirectedGraph<>();
        ourGraph.add(origin);
        Map<V, Integer> distance = new HashMap<>();
        List<V> pq = new LinkedList<>();
        if (!contains(origin)) throw new NoSuchElementException();
        distance.put(origin, 0);
        pq.add(origin);
        while (!pq.isEmpty()) {
            V currNode = ((LinkedList<V>) pq).poll();
            if (verticies.get(currNode).size() == 0)
                return ourGraph;
            for (V neighbor : verticies.get(currNode)) {
                if (!distance.containsKey(neighbor)) {
                    ourGraph.add(neighbor);
                    ourGraph.connect(currNode,neighbor);
                }
            }
        }return ourGraph;
    }

    @Override
    public String toString() {
        String stats = new String();
        for (V vert : verticies.keySet()) {
            String vertex = (String) vert;
            stats = stats.concat(String.format("Vertex:%20s\n", vertex));
            stats = stats.concat(String.format("neighbors of vertex:%20s\n", neighbors(vert)));
            stats = stats.concat("\n");
        }return stats;
    }

}
