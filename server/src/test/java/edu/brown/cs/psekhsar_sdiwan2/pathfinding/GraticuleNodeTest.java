package edu.brown.cs.psekhsar_sdiwan2.pathfinding;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GraticuleNodeTest {
  GraticuleNode pn = new GraticuleNode("/n/0", 2.22222222222, -39);
  GraticuleNode pn2 = new GraticuleNode("/n/0", 2.22222222222, -39);
  GraticuleNode pn3 = new GraticuleNode("/n/1", 2.22222222222, -39);
  GraticuleNode pn4 = new GraticuleNode("/n/0", 2.2222222222, -39);
  GraticuleNode pn5 = new GraticuleNode("/n/0", 2.22222222222, -39.000001);

  @Test
  public void testGetLatitude() {
    assertEquals(pn.getLatitude(), 2.22222222222, 0.0);
  }

  @Test
  public void testGetLongitude() {
    assertEquals(pn.getLongitude(), -39, 0.0);
  }

  @Test
  public void testGetID() {
    assertEquals("/n/0", pn.getId());
  }

  @Test
  public void testGetCoordinateVal() {
    assertEquals(pn.getCoordinateVal(0), 2.22222222222, 0.0);
    assertEquals(pn.getCoordinateVal(1), -39, 0.0);
  }

  @Test
  public void testGetCoordinates() {
    List<Double> coordinates = new ArrayList<>();
    coordinates.add(2.22222222222);
    coordinates.add(-39.0);
    assertEquals(pn.getCoordinates(), coordinates);
  }

  @Test
  public void testEquals() {
    assertEquals(pn, pn);
    assertEquals(pn, pn2);
    assertNotEquals(pn, pn3);
    assertNotEquals(pn, pn4);
    assertNotEquals(pn, pn5);
  }
}
