package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import edu.brown.cs.psekhsar_sdiwan2.pathfinding.GraticuleNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NodeDistanceCalculatorsTest {
  @Test
  public void testGetHaversineDistance_SamePoint() {
    GraticuleNode n1 = new GraticuleNode("n1", 100, 100);
    GraticuleNode n2 = new GraticuleNode("n2", 100, 100);
    assertEquals(NodeDistanceCalculators.getHaversineDistance(n1, n2),
      0, 0);
  }

  @Test
  public void testGetHaversineDistance_FullRevolution() {
    GraticuleNode n1 = new GraticuleNode("n1", 0, 0);
    GraticuleNode n2 = new GraticuleNode("n2", 180, 180);
    assertEquals(NodeDistanceCalculators.getHaversineDistance(n1, n2),
      0, 0);
  }

  @Test
  public void testGetHaversineDistance_SmallDistance() {
    GraticuleNode n1 = new GraticuleNode("n1", 41.82, -71.4);
    GraticuleNode n2 = new GraticuleNode("n2", 41.8203, -71.4);
    assertEquals(NodeDistanceCalculators.getHaversineDistance(n1, n2),
      0.033358524070397313, 0);
  }

  @Test
  public void testGetHaversineDistance_LargeDistance() {
    GraticuleNode n1 = new GraticuleNode("n1", 0, -180);
    GraticuleNode n2 = new GraticuleNode("n2", 0, 0);
    assertEquals(NodeDistanceCalculators.getHaversineDistance(n1, n2),
      20015.114442035923, 0);
  }

  @Test
  public void testGetHaversineDistance_MoreThan180() {
    GraticuleNode n1 = new GraticuleNode("n1", 1000, -8389.2);
    GraticuleNode n2 = new GraticuleNode("n2", -282, 292.39393);
    assertEquals(NodeDistanceCalculators.getHaversineDistance(n1, n2),
      17728.612428608085, 0);
  }
}
