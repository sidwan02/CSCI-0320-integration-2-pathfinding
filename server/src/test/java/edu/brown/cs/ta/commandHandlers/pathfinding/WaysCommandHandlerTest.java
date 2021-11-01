package edu.brown.cs.ta.commandHandlers.pathfinding;

import edu.brown.cs.ta.pathfinding.GraticuleEdge;
import edu.brown.cs.ta.pathfinding.GraticuleNode;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class WaysCommandHandlerTest {
  private final String bigMapsPath = "data/pathfinding/pathfinding.sqlite3";

  @Test
  public void testWaysCommand_ManyWaysOutput() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.waysCommand("ways 40 -71 39.9 -70.9", "repl"),
      "");

    // pathfinding
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(WaysCommandHandler.waysCommand("ways 40.1581762 -73.7485663 40.1581762 -73.7485663", "repl"),
      "/w/4015.7374.42295268.0.1\n" +
        "/w/4016.7374.42295268.0.2");
  }

  @Test
  public void testWaysCommand_0WaysOutput() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.waysCommand("ways 100 0 0 100", "repl"),
      "");

    // pathfinding
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(WaysCommandHandler.waysCommand("ways 100 0 0 100", "repl"),
      "");
  }

  @Test
  public void testWaysCommand_InvalidNumberOfArguments() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.waysCommand("ways 100 0 0 100 0", "repl"),
      "ERROR: Invalid no.of arguments passed for ways");

    assertEquals(WaysCommandHandler.waysCommand("ways 0 100 0", "repl"),
      "ERROR: Invalid no.of arguments passed for ways");

    assertEquals(WaysCommandHandler.waysCommand("ways", "repl"),
      "ERROR: Invalid no.of arguments passed for ways");
  }

  @Test
  public void testWaysCommand_NonNumberArguments() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.waysCommand("ways hi 0 0 100", "repl"),
      "ERROR: Latitudes and longitudes must be numbers");

    assertEquals(WaysCommandHandler.waysCommand("ways 100 -100 -100 hundred", "repl"),
      "ERROR: Latitudes and longitudes must be numbers");
  }

  @Test
  public void testWaysCommand_InvalidBoundingBox() {
    // lat1 < lat2
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.waysCommand("ways -100 -100 100 100", "repl"),
      "ERROR: Coordinates not northwest & southeast");

    // lon1 > lon2
    assertEquals(WaysCommandHandler.waysCommand("ways 100 100 -100 -100", "repl"),
      "ERROR: Coordinates not northwest & southeast");

    // lat1 < lat2 && lon1 > lon2
    assertEquals(WaysCommandHandler.waysCommand("ways -100 100 100 -100", "repl"),
      "ERROR: Coordinates not northwest & southeast");
  }

  @Test
  public void testWaysCommand_NoDBLoaded() {
    MapCommandHandler.reset();
    assertEquals(WaysCommandHandler.waysCommand("ways 100 -100 -100 100", "repl"),
      "ERROR: No Database loaded");
  }

  @Test
  public void testCheckWaysArgs_ManyWaysOutput() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.checkWaysArgs(new String[]{"ways", "100", "-100", "-100", "100"}),
      "/w/0\n" +
        "/w/1\n" +
        "/w/2\n" +
        "/w/3\n" +
        "/w/4\n" +
        "/w/5\n" +
        "/w/6");

    // pathfinding
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(WaysCommandHandler.checkWaysArgs(new String[]{"ways", "40.1581762", "-73.7485663", "40.1581762", "-73.7485663"}),
      "/w/4015.7374.42295268.0.1\n" +
        "/w/4016.7374.42295268.0.2");
  }

  @Test
  public void testCheckWaysArgs_0WaysOutput() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.checkWaysArgs(new String[]{"ways", "100", "0", "0", "100"}),
      "");
  }

  @Test
  public void testCheckWaysArgs_InvalidNumberOfArguments() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertThrows(IllegalArgumentException.class, () ->
        WaysCommandHandler.checkWaysArgs(new String[]{"ways", "100", "0", "0", "100", "0"}));

    assertThrows(IllegalArgumentException.class, () ->
      WaysCommandHandler.checkWaysArgs(new String[]{"ways", "0", "100", "0"}));

    assertThrows(IllegalArgumentException.class, () ->
        WaysCommandHandler.checkWaysArgs(new String[]{"ways"}));
  }

  @Test
  public void testCheckWaysArgs_NonNumberArguments() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertThrows(NumberFormatException.class, () ->
      WaysCommandHandler.checkWaysArgs(new String[]{"ways", "hi", "0", "0", "100"}));

    assertThrows(NumberFormatException.class, () ->
      WaysCommandHandler.checkWaysArgs(new String[]{"ways", "100", "-100", "-100", "hundred"}));
  }

  @Test
  public void testCheckWaysArgs_InvalidBoundingBox() {
    // lat1 < lat2
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.checkWaysArgs(new String[]{"ways", "-100", "-100", "100", "100"}),
      "ERROR: Coordinates not northwest & southeast");

    // lon1 > lon2
    assertEquals(WaysCommandHandler.checkWaysArgs(new String[]{"ways", "100", "100", "-100", "-100"}),
      "ERROR: Coordinates not northwest & southeast");

    // lat1 < lat2 && lon1 > lon2
    assertEquals(WaysCommandHandler.checkWaysArgs(new String[]{"ways", "-100", "100", "100", "-100"}),
      "ERROR: Coordinates not northwest & southeast");
  }

  @Test
  public void testCheckWaysArgs_NoDBLoaded() {
    MapCommandHandler.reset();

    assertEquals(WaysCommandHandler.checkWaysArgs(new String[]{"ways", "100", "-100", "-100", "100"}),
      "ERROR: No Database loaded");
  }

  @Test
  public void testHandleWaysWithinBox_ManyWaysOutput() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.handleWaysWithinBox(100, -100, -100, 100),
      "/w/0\n" +
        "/w/1\n" +
        "/w/2\n" +
        "/w/3\n" +
        "/w/4\n" +
        "/w/5\n" +
        "/w/6");

    // pathfinding
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(WaysCommandHandler.handleWaysWithinBox(40.1581762, -73.7485663, 40.1581762, -73.7485663),
      "/w/4015.7374.42295268.0.1\n" +
        "/w/4016.7374.42295268.0.2");
  }

  @Test
  public void testHandleWaysWithinBox_0WaysOutput() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.handleWaysWithinBox(100, 0, 0, 100),
      "");

    // pathfinding
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(WaysCommandHandler.handleWaysWithinBox(100, 0, 0, 100),
      "");
  }

  @Test
  public void testHandleWaysWithinBox_BoundingBoxExtremes() {
    // lat1 == lon1
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.handleWaysWithinBox(41.82, -100, 41.82, 100),
      "/w/0\n" +
        "/w/2\n" +
        "/w/5");

    // lon1 == lon2
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.handleWaysWithinBox(100, -71.4003, 0, -71.4003),
      "/w/2\n" +
        "/w/3\n" +
        "/w/4\n" +
        "/w/5\n" +
        "/w/6");

    // lat1 == lat2 == lon1 == lon2
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.handleWaysWithinBox(41.82, -71.4003, 41.82, -71.4003),
      "/w/2\n" +
        "/w/5");
  }

  @Test
  public void testHandleWaysWithinBox_InvalidBoundingBox() {
    // lat1 < lat2
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    assertEquals(WaysCommandHandler.handleWaysWithinBox(-100, -100, 100, 100),
      "ERROR: Coordinates not northwest & southeast");

    // lon1 > lon2
    assertEquals(WaysCommandHandler.handleWaysWithinBox(100, 100, -100, -100),
      "ERROR: Coordinates not northwest & southeast");

    // lat1 < lat2 && lon1 > lon2
    assertEquals(WaysCommandHandler.handleWaysWithinBox(-100, 100, 100, -100),
      "ERROR: Coordinates not northwest & southeast");
  }

  @Test
  public void getAllWays_NoWays() throws SQLException {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    List<GraticuleEdge> expectedWays = new ArrayList<>();

    assertEquals(WaysCommandHandler.getAllWays(0, 0, 0, 0),
      expectedWays);
  }

  @Test
  public void getAllWays_WayOnBoundingBox() throws SQLException {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    List<GraticuleEdge> expectedWays = new ArrayList<>();

    assertEquals(WaysCommandHandler.getAllWays(0, 0, 0, 0),
      expectedWays);
  }

  @Test
  public void getAllWays_ManyWays() throws SQLException {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    GraticuleNode n0 = new GraticuleNode("/n/0", 41.82, -71.4);
    GraticuleNode n1 = new GraticuleNode("/n/1", 41.8203, -71.4);
    GraticuleNode n2 = new GraticuleNode("/n/2", 41.8206, -71.4);
    GraticuleNode n3 = new GraticuleNode("/n/3", 41.82, -71.4003);
    GraticuleNode n4 = new GraticuleNode("/n/4", 41.8203, -71.4003);
    GraticuleNode n5 = new GraticuleNode("/n/5", 41.8206, -71.4003);

    GraticuleEdge w0 = new GraticuleEdge("/w/0", "Chihiro Ave", "residential", n0, n1);
    GraticuleEdge w1 = new GraticuleEdge("/w/1", "Chihiro Ave", "residential", n1, n2);
    GraticuleEdge w2 = new GraticuleEdge("/w/2", "Radish Spirit Blvd", "residential", n0, n3);
    GraticuleEdge w3 = new GraticuleEdge("/w/3", "Sootball Ln", "residential", n1, n4);
    GraticuleEdge w4 = new GraticuleEdge("/w/4", "Kamaji Pl", "residential", n2, n5);
    GraticuleEdge w5 = new GraticuleEdge("/w/5", "Yubaba St", "residential", n3, n4);
    GraticuleEdge w6 = new GraticuleEdge("/w/6", "Yubaba St", "residential", n4, n5);

    List<GraticuleEdge> expectedWays = new ArrayList<>();
    expectedWays.add(w0);
    expectedWays.add(w1);
    expectedWays.add(w2);
    expectedWays.add(w3);
    expectedWays.add(w4);
    expectedWays.add(w5);
    expectedWays.add(w6);

    assertEquals(WaysCommandHandler.getAllWays(100, -100, -100, 100),
      expectedWays);
  }

  @Test
  public void testParseToRepl_NoEdges() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    List<GraticuleEdge> graticuleEdges = new ArrayList<>();

    assertEquals(WaysCommandHandler.parseToRepl(graticuleEdges),
      "");
  }

  @Test
  public void testParseToRepl_ManyEdges() {
    MapCommandHandler.mapCommand("map data/pathfinding/smallMaps.sqlite3", "repl");
    GraticuleNode pn = new GraticuleNode("/n/0", 2.22222222222, -39);
    GraticuleNode pn2 = new GraticuleNode("/n/1", 2.222222222, -39.29292);
    GraticuleNode pn3 = new GraticuleNode("/n/2", 2.22222222222, -39);
    GraticuleNode pn4 = new GraticuleNode("/n/3", 12.22222229292922222, -39);
    GraticuleNode pn5 = new GraticuleNode("/n/4", 2.22222222222, -39.000001);

    GraticuleEdge w = new GraticuleEdge("/w/0", "some random street y u want to know huh?", "baby street", pn, pn2);
    GraticuleEdge w2 = new GraticuleEdge("/w/1", "street of broken dreams", "", pn3, pn4);
    GraticuleEdge w3 = new GraticuleEdge("/w/2", "street of broken dreams", "", pn2, pn5);
    GraticuleEdge w4 = new GraticuleEdge("/w/3", "street of broken dreams", "", pn5, pn2);
    GraticuleEdge w5 = new GraticuleEdge("/w/4", "street of broken dreams", "", pn3, pn4);

    List<GraticuleEdge> graticuleEdges = new ArrayList<>();
    graticuleEdges.add(w);
    graticuleEdges.add(w2);
    graticuleEdges.add(w3);
    graticuleEdges.add(w4);
    graticuleEdges.add(w5);

    assertEquals(WaysCommandHandler.parseToRepl(graticuleEdges),
      "/w/0\n" +
        "/w/1\n" +
        "/w/2\n" +
        "/w/3\n" +
        "/w/4");
  }
}
