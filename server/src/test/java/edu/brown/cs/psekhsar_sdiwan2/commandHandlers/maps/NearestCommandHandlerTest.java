package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import edu.brown.cs.psekhsar_sdiwan2.database.DatabaseHandler;
import edu.brown.cs.psekhsar_sdiwan2.pathfinding.GraticuleNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class NearestCommandHandlerTest {
  private final String bigMapsPath = "data/maps/maps.sqlite3";

  @Test
  public void testNearestCommand_NoTraversableNodes() {
    List<String> fileExtensions = new ArrayList<>();
    fileExtensions.add("sqlite3");
    fileExtensions.add("db");

    DatabaseHandler.setValidExtensionFormats(fileExtensions);

    MapCommandHandler.mapCommand("map data/maps/singleNodeSingleWay.sqlite3", "repl");
    assertEquals(NearestCommandHandler.nearestCommand("nearest 0 0", "repl"),
      "ERROR: No Database loaded");
  }

  @Test
  public void testNearestCommand_ManyNodes() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(NearestCommandHandler.nearestCommand("nearest 0 0", "repl"),
      "/n/0");

    // maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(NearestCommandHandler.nearestCommand("nearest 0 0", "repl"),
      "/n/4151.7110.72985834");
  }

  @Test
  public void testNearestCommand_InvalidNumberOfArguments() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(NearestCommandHandler.nearestCommand("nearest 100", "repl"),
      "ERROR: Invalid no.of arguments passed for nearest");

    assertEquals(NearestCommandHandler.nearestCommand("nearest 0 100 0", "repl"),
      "ERROR: Invalid no.of arguments passed for nearest");

    assertEquals(NearestCommandHandler.nearestCommand("nearest", "repl"),
      "ERROR: Invalid no.of arguments passed for nearest");
  }

  @Test
  public void testNearestCommand_NonNumberArguments() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(NearestCommandHandler.nearestCommand("nearest hi 100", "repl"),
      "ERROR: Latitudes and longitudes must be numbers");

    assertEquals(NearestCommandHandler.nearestCommand("nearest 100 hundred", "repl"),
      "ERROR: Latitudes and longitudes must be numbers");
  }

  @Test
  public void testNearestCommand_NoDBLoaded() {
    MapCommandHandler.reset();
    assertEquals(NearestCommandHandler.nearestCommand("nearest 100 -100", "repl"),
      "ERROR: No Database loaded");
  }

  @Test
  public void testCheckNearestArgs_TraversableNodes() {
    // No Traversable Nodes
    MapCommandHandler.mapCommand("map data/maps/singleNodeSingleWay.sqlite3", "repl");

    assertThrows(NullPointerException.class, () ->
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest", "0", "0"}));

    // Many Nodes: smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest", "100", "0"}),
      "/n/2");

    // Many Nodes: maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest", "100", "0"}),
      "/n/4201.7137.71709391");
  }

  @Test
  public void testCheckNearestArgs_InvalidNumberOfArguments() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertThrows(IllegalArgumentException.class, () ->
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest", "100", "0", "0"}));

    assertThrows(IllegalArgumentException.class, () ->
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest", "0"}));

    assertThrows(IllegalArgumentException.class, () ->
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest"}));
  }

  @Test
  public void testCheckNearestArgs_NonNumberArguments() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertThrows(NumberFormatException.class, () ->
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest", "hi", "0"}));

    assertThrows(NumberFormatException.class, () ->
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest", "100", "hundred"}));
  }

  @Test
  public void testCheckNearestArgs_NoDBLoaded() {
    DatabaseHandler.setConn(null);

    assertThrows(NullPointerException.class, () ->
      NearestCommandHandler.checkNearestArgs(new String[]{"nearest", "100", "-100"}));
  }

  @Test
  public void testGetNearestNode_AtExactLatLon() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    GraticuleNode expectedNode = new GraticuleNode("/n/0", 41.82, -71.4);
    assertEquals(NearestCommandHandler.getNearestNode(41.82, -71.4),
      expectedNode);

    // maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    GraticuleNode expectedNode2 = new GraticuleNode("/n/4182.7140.1955940309", 41.826908, -71.404716);
    assertEquals(NearestCommandHandler.getNearestNode(41.826908, -71.404716),
      expectedNode2);
  }

  @Test
  public void testGetNearestNode_NotAtExactLatLon() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    GraticuleNode expectedNode = new GraticuleNode("/n/0", 41.82, -71.4);
    assertEquals(NearestCommandHandler.getNearestNode(0, 0),
      expectedNode);

    // maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    GraticuleNode expectedNode2 = new GraticuleNode("/n/4151.7110.72985834", 41.519758, -71.101115);
    assertEquals(NearestCommandHandler.getNearestNode(0, 0),
      expectedNode2);
  }

  @Test
  public void testGetNearestNode_TieSituation() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    GraticuleNode expectedNode = new GraticuleNode("/n/0", 41.82, -71.4);
    assertEquals(NearestCommandHandler.getNearestNode((41.82 + 41.8203) / 2, -71.4),
      expectedNode);
  }

  @Test
  public void testParseToRepl() {
    GraticuleNode testNode = new GraticuleNode("/n/0", 41.82, -71.4);
    assertEquals(NearestCommandHandler.parseToRepl(testNode), "/n/0");
  }
}
