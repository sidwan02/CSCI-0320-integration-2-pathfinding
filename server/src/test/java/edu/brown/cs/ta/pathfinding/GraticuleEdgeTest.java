package edu.brown.cs.ta.pathfinding;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GraticuleEdgeTest {
  GraticuleNode pn = new GraticuleNode("/n/0", 2.22222222222, -39);
  GraticuleNode pn2 = new GraticuleNode("/n/1", 2.222222222, -39.29292);
  GraticuleNode pn3 = new GraticuleNode("/n/2", 2.22222222222, -39);
  GraticuleNode pn4 = new GraticuleNode("/n/3", 12.22222229292922222, -39);
  GraticuleNode pn5 = new GraticuleNode("/n/4", 2.22222222222, -39.000001);

  GraticuleEdge w = new GraticuleEdge("/w/0", "some random street y u want to know huh?", "baby street", pn, pn2);
  GraticuleEdge w2 = new GraticuleEdge("/w/1", "street of broken dreams", "", pn3, pn4);
  GraticuleEdge w3 = new GraticuleEdge("/w/2", "street of broken dreams", "", pn2, pn5);
  GraticuleEdge w4 = new GraticuleEdge("/w/3", "street of broken dreams", "", pn5, pn2);
  GraticuleEdge w5 = new GraticuleEdge("/w/1", "street of broken dreams", "", pn3, pn4);

  @Test
  public void testGetName() {
    assertEquals(pn.getLatitude(), 2.22222222222, 0.0);
  }

  @Test
  public void testGetType() {
    assertEquals(w.getType(), "baby street");
  }

  @Test
  public void testGetID() {
    assertEquals("/w/0", w.getId());
  }

  @Test
  public void testGetStart() {
    assertEquals(pn, w.getStartNode());
  }

  @Test
  public void testGetEnd() {
    assertEquals(pn2, w.getEndNode());
  }

  @Test
  public void testToString() {
    assertEquals(w.toString(), "GraticuleEdge{id='/w/0', " +
      "start=GraticuleNode{id='/n/0', " +
      "coordinates=[2.22222222222, -39.0]}, end=GraticuleNode{id='/n/1', coordinates=[2.222222222, -39.29292]}, name='some random street y u want to know huh?', type='baby street'}");
  }

  @Test
  public void testEquals() {
    assertEquals(w2, w2);
    assertEquals(w2, w5);
    assertNotEquals(w4, w3);
    assertNotEquals(w, w2);
  }
}
