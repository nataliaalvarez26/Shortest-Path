package edu.sdsu.cs;

import static org.junit.Assert.assertTrue;

import edu.sdsu.cs.datastructures.DirectedGraph;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public static void main(String [] args)
    {
        DirectedGraph<String> theGraph = new DirectedGraph<>();

        theGraph.add("0");
        theGraph.add("1");
        theGraph.add("2");
        theGraph.add("3");
        theGraph.add("4");

        theGraph.connect("0", "1");
        theGraph.connect("1", "2");
        theGraph.connect("2", "3");
        theGraph.connect("3", "4");
        theGraph.connect("0", "4");

        System.out.println(theGraph.connectedGraph("1"));

//        System.out.println(theGraph.vertices());
//        //theGraph.disconnect("think", "good");
//        System.out.println(theGraph.neighbors("0"));
//        System.out.println(theGraph.isConnected("0","4"));
//        System.out.println(theGraph.shortestPath("0", "4"));
//        System.out.println(theGraph.toString());
    }
}
