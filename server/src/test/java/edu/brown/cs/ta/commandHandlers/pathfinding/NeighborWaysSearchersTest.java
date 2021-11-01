package edu.brown.cs.ta.commandHandlers.pathfinding;

import edu.brown.cs.ta.pathfinding.GraticuleEdge;
import edu.brown.cs.ta.pathfinding.GraticuleNode;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class NeighborWaysSearchersTest {
  GraticuleNode n5 = new GraticuleNode("/n/5", 41.8206, -71.4003);
  GraticuleNode n0 = new GraticuleNode("/n/0", 41.82, -71.4);
  GraticuleNode n1 = new GraticuleNode("/n/1", 41.8203, -71.4);
  GraticuleNode n3 = new GraticuleNode("/n/3", 41.82, -71.4003);

  GraticuleEdge w0 = new GraticuleEdge("/w/0", "Chihiro Ave", "residential", n0, n1);
  GraticuleEdge w2 = new GraticuleEdge("/w/2", "Radish Spirit Blvd", "residential", n0, n3);

  @Test
  public void testGetWaysFromTargetNode_SomeNeighbors() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    Set<GraticuleEdge> expectedNeighbors = new HashSet<>();
    assertEquals(NeighborWaySearchers.getWaysFromTargetNode(n5),
      expectedNeighbors);
  }

  @Test
  public void testGetWaysFromTargetNode_NoNeighbors() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    Set<GraticuleEdge> expectedNeighbors = new HashSet<>();
    expectedNeighbors.add(w0);
    expectedNeighbors.add(w2);
    assertEquals(NeighborWaySearchers.getWaysFromTargetNode(n0),
      expectedNeighbors);
  }
}
