package edu.sdsu.cs;

import edu.sdsu.cs.datastructures.DirectedGraph;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Natalia Alvarez
 */
public class App {
    static Path curPath;
    private static ArrayList<String> lines = new ArrayList<>();
    private static ArrayList<String> vertex = new ArrayList<>();
    private static List<String> connections = new ArrayList<>();
    private static DirectedGraph<String> theGraph = new DirectedGraph<>();
    private static ArrayList<String> errorArray = new ArrayList<>();
    public static void main(String[] args) {
        String defPath;
        if (args.length == 0) defPath = "./layout.csv";
         else defPath = args[0];
        File curFile = new File(defPath);
        if(getAllFiles(curFile)==errorArray) throw new NoSuchElementException("Error: Unable to open filename. Verify the file exists, is accessible, and meets the syntax requirements.");
        for (String files : lines) {
            fileToString(files);
        }
    }

    private App(String[] args) { curPath = Paths.get(args[0]); }

    private static ArrayList<String> getAllFiles(File root) {
        errorArray.add("Error: Unable to open filename. Verify the file exists, is accessible, and meets the syntax requirements.");
        try {
            if (root.exists()) {
                String s = root.getCanonicalPath();
                if (s.substring(s.length() - 4).equals(".csv")) lines.add(s);
            } else {
                System.out.println("hwy");
                return errorArray;
            }
        } catch (IOException E) {
            E.printStackTrace();
        }return lines;
    }

    private static void fileToString(String stringPath) {
        try {
            Path filePath = Paths.get(stringPath);
            List<String> lines = Files.readAllLines(filePath, Charset.defaultCharset());
            verticies(lines);
            unique(vertex);
            connections = vertex.subList(44, vertex.size() - 1);
            for (int i = 0, j = 1; i < connections.size() && j < connections.size(); i += 2, j += 2) { theGraph.connect(connections.get(i), connections.get(j)); }
            System.out.println(theGraph);
            Scanner reader = new Scanner(System.in);
            System.out.println("Get the shortest path between the objects of your choice:");
            String object = reader.nextLine();
            String objectTwo = reader.nextLine();
            System.out.println(theGraph.shortestPath(object, objectTwo));
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    private static void verticies(List<String> lines) {
        for (String line : lines) {
            String[] split = line.split(",");
            for (String token : split) { vertex.add(token); }
        }
    }

    private static void unique(List<String> vertex) {
        for (String uniqueToken : vertex) { theGraph.add(uniqueToken); }
        System.out.println(theGraph.size());
    }
}
