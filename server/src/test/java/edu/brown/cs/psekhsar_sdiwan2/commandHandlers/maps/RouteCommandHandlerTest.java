package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import edu.brown.cs.psekhsar_sdiwan2.pathfinding.GraticuleEdge;
import edu.brown.cs.psekhsar_sdiwan2.pathfinding.GraphEdge;
import edu.brown.cs.psekhsar_sdiwan2.pathfinding.GraticuleNode;
import org.junit.Test;

import java.awt.geom.IllegalPathStateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RouteCommandHandlerTest {
  private final String bigMapsPath = "data/maps/maps.sqlite3";

  GraticuleNode pn = new GraticuleNode("/n/0", 2.22222222222, -39);
  GraticuleNode pn2 = new GraticuleNode("/n/1", 2.222222222, -39.29292);
  GraticuleNode pn3 = new GraticuleNode("/n/2", 2.22222222222, -39);
  GraticuleNode pn4 = new GraticuleNode("/n/3", 12.22222229292922222, -39);
  GraticuleNode pn5 = new GraticuleNode("/n/4", 2.22222222222, -39.000001);

  GraticuleEdge w = new GraticuleEdge("/w/0", "some random street y u want to know huh?", "baby street", pn, pn2);
  GraticuleEdge w2 = new GraticuleEdge("/w/1", "street of broken dreams", "", pn2, pn3);
  GraticuleEdge w3 = new GraticuleEdge("/w/2", "street of broken dreams", "", pn3, pn4);
  GraticuleEdge w4 = new GraticuleEdge("/w/3", "street of broken dreams", "", pn4, pn5);

  @Test
  public void testRouteCommand_InvalidArgsCount() {
    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Kamaji Pl\" \"Yubaba St\"", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");

    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Kamaji Pl\" \"Yubaba St\" \"Yubaba St\" Yubaba St", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");

    assertEquals(RouteCommandsHandler.routeCommand("route 100 -20 29.9239", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");

    assertEquals(RouteCommandsHandler.routeCommand("route 100 -20 29.9239 020.2 1331", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");

    assertEquals(RouteCommandsHandler.routeCommand("route", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");
  }

  @Test
  public void testRouteCommand_Streets_NoQuotes() {
    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" Kamaji Pl \"Yubaba St\"", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");

    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" Kamaji \"Yubaba St\"", "repl"),
      "ERROR: street names must be within \"\"");

    assertEquals(RouteCommandsHandler.routeCommand("route Chihiro Ave Radish Spirit Blvd Kamaji Pl Yubaba St", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");
  }

  @Test
  public void testRouteCommand_Streets_IncompleteQuotes() {
    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" \"Kamaji Pl \"Yubaba St\"", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");

    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" \"Kamaji \"Yubaba St\"", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");

    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" Kamaji\" \"Yubaba St\"", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");

    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" Kamaji Pl\" \"Yubaba St\"", "repl"),
      "ERROR: Invalid no.of arguments passed for route / streets within incomplete quotes");
  }

  @Test
  public void testRouteCommand_Streets_PathToReachableNode() {
    // smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.routeCommand("route \"Sootball Ln\" \"Chihiro Ave\" \"Sootball Ln\" \"Yubaba St\"", "repl"),
      "/n/1 -> /n/4 : /w/3");

    // maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(RouteCommandsHandler.routeCommand("route \"Sunset Avenue\" \"Holyoke Avenue\" \"Draper Avenue\" \"Sunset Avenue\"", "repl"),
      "/n/4170.7137.201135672 -> /n/4170.7137.201135668 : /w/4170.7137.19352877.0.2\n"
      + "/n/4170.7137.201135668 -> /n/4170.7137.201135706 : /w/4170.7137.19352878.5.2\n"
      + "/n/4170.7137.201135706 -> /n/4170.7137.201135702 : /w/4170.7137.19352878.4.2\n"
      + "/n/4170.7137.201135702 -> /n/4170.7137.201135698 : /w/4170.7137.19352878.3.2\n"
      + "/n/4170.7137.201135698 -> /n/4170.7137.201135694 : /w/4170.7137.19352878.2.2\n"
      + "/n/4170.7137.201135694 -> /n/4170.7137.201135690 : /w/4170.7137.19352878.1.2\n"
      + "/n/4170.7137.201135690 -> /n/4170.7137.201135685 : /w/4170.7137.19352878.0.2");
  }

  @Test
  public void testRouteCommand_Coordinates_NonNumbers() {
    assertEquals(RouteCommandsHandler.routeCommand("route 100 -20 29.9239 hundred", "repl"),
            "ERROR: Latitude and longitude must be real numbers");
  }

  @Test
  public void testRouteCommand_Streets_PathToUnreachableNode() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.routeCommand("route \"Kamaji Pl\" \"Yubaba St\" \"Chihiro Ave\" \"Radish Spirit Blvd\"", "repl"),
      "/n/5 -/- /n/0");
  }

  @Test
  public void testRouteCommand_Streets_PathToSelf() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" \"Chihiro Ave\" \"Radish Spirit Blvd\"", "repl"),
      "/n/0 -/- /n/0");

    assertEquals(RouteCommandsHandler.routeCommand("route \"Kamaji Pl\" \"Yubaba St\" \"Kamaji Pl\" \"Yubaba St\"", "repl"),
      "/n/5 -/- /n/5");
  }

  @Test
  public void testRouteCommand_Streets_NoDBLoaded() {
    MapCommandHandler.reset();
    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" \"Kamaji Pl\" \"Yubaba St\"", "repl"),
    "ERROR: No Database loaded");
  }

  @Test
  public void testCheckRouteArgs_InvalidArgsCount() {
    assertThrows(IllegalArgumentException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Chihiro Ave\"", "\"Kamaji Pl\"", "\"Yubaba St\""))));

    assertThrows(IllegalArgumentException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Chihiro Ave\"", "\"Kamaji Pl\"", "\"Yubaba St\"", "\"Yubaba St\"", "Yubaba St"))));

    assertThrows(IllegalArgumentException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "100", "-20", "29.9239"))));

    assertThrows(IllegalArgumentException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "100", "-20", "29.9239", "020.2", "1331"))));

    assertThrows(IllegalArgumentException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route"))));
  }

  @Test
  public void testCheckRouteArgs_Streets_NoQuotes() {
    assertThrows(IllegalPathStateException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Chihiro Ave\"", "\"Radish Spirit Blvd\"", "Kamaji Pl", "\"Yubaba St\""))));

    assertThrows(IllegalPathStateException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Chihiro Ave\"", "\"Radish Spirit Blvd\"", "Kamaji", "\"Yubaba St\""))));

    assertThrows(IllegalArgumentException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "Chihiro Ave", "Radish Spirit Blvd", "Kamaji Pl", "Yubaba St"))));
  }

  @Test
  public void testCheckRouteArgs_Streets_IncompleteQuotes() {
    assertThrows(IllegalPathStateException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Chihiro Ave\"", "\"Radish Spirit Blvd\"", "\"Kamaji Pl", "\"Yubaba St\""))));

    assertThrows(IllegalPathStateException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Chihiro Ave\"", "\"Radish Spirit Blvd\"", "Kamaji Pl\"", "\"Yubaba St\""))));
  }

  @Test
  public void testCheckRouteArgs_Streets_PathToReachableNode() throws SQLException {
    // smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Sootball Ln\"", "\"Chihiro Ave\"", "\"Sootball Ln\"", "\"Yubaba St\""))),
      "/n/1 -> /n/4 : /w/3");

    // maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Sunset Avenue\"", "\"Holyoke Avenue\"", "\"Draper Avenue\"",
      "\"Sunset Avenue\""))),
      "/n/4170.7137.201135672 -> /n/4170.7137.201135668 : /w/4170.7137.19352877.0.2\n"
        + "/n/4170.7137.201135668 -> /n/4170.7137.201135706 : /w/4170.7137.19352878.5.2\n"
        + "/n/4170.7137.201135706 -> /n/4170.7137.201135702 : /w/4170.7137.19352878.4.2\n"
        + "/n/4170.7137.201135702 -> /n/4170.7137.201135698 : /w/4170.7137.19352878.3.2\n"
        + "/n/4170.7137.201135698 -> /n/4170.7137.201135694 : /w/4170.7137.19352878.2.2\n"
        + "/n/4170.7137.201135694 -> /n/4170.7137.201135690 : /w/4170.7137.19352878.1.2\n"
        + "/n/4170.7137.201135690 -> /n/4170.7137.201135685 : /w/4170.7137.19352878.0.2");
  }

  @Test
  public void testCheckRouteArgs_Streets_PathToUnreachableNode() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.routeCommand("route \"Kamaji Pl\" \"Yubaba St\" \"Chihiro Ave\" \"Radish Spirit Blvd\"", "repl"),
      "/n/5 -/- /n/0");
  }

  @Test
  public void testCheckRouteArgs_Streets_PathToSelf() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.routeCommand("route \"Chihiro Ave\" \"Radish Spirit Blvd\" \"Chihiro Ave\" \"Radish Spirit Blvd\"", "repl"),
      "/n/0 -/- /n/0");

    assertEquals(RouteCommandsHandler.routeCommand("route \"Kamaji Pl\" \"Yubaba St\" \"Kamaji Pl\" \"Yubaba St\"", "repl"),
      "/n/5 -/- /n/5");
  }

  @Test
  public void testCheckRouteArgs_Streets_NoDBLoaded() throws SQLException {
    MapCommandHandler.reset();
    assertEquals(RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "\"Chihiro Ave\"", "\"Radish Spirit Blvd\"", "\"Kamaji Pl\"", "\"Yubaba St\""))),
      "ERROR: No Database loaded");
  }

  @Test
  public void testCheckRouteArgs_Coordinates_NonNumbers() {
    assertThrows(NumberFormatException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "100", "-20", "29.9239", "hundred"))));
  }

  @Test
  public void testCheckRouteArgs_Coordinates_PathToUnreachableNode() throws SQLException {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "41.8206", "-71.4003", "41.82", "-71.4"))),
      "/n/5 -/- /n/0");
  }

  @Test
  public void testCheckRouteArgs_Coordinates_PathToReachableNode() throws SQLException {
    // smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "41.8", "-71.3", "42", "-72"))),
      "/n/0 -> /n/1 : /w/0\n" +
        "/n/1 -> /n/2 : /w/1\n" +
        "/n/2 -> /n/5 : /w/4");

    // maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "41.8206", "-71.4003", "41.82", "-71.4"))),
      "/n/4182.7140.201267761 -> /n/4182.7140.201267757 : /w/4182.7140.19361344.4.2\n" +
        "/n/4182.7140.201267757 -> /n/4182.7140.201267753 : /w/4182.7140.19361344.3.2\n" +
        "/n/4182.7140.201267753 -> /n/4182.7140.201267749 : /w/4182.7140.19361344.2.2\n" +
        "/n/4182.7140.201267749 -> /n/4181.7140.201761469 : /w/4182.7140.19400159.2.1\n" +
        "/n/4181.7140.201761469 -> /n/4181.7139.201444109 : /w/4181.7140.19401592.0.1");
  }

  @Test
  public void testCheckRouteArgs_Coordinates_NoDBLoaded() {
    assertThrows(NumberFormatException.class, () ->
      RouteCommandsHandler.checkRouteArgs(new ArrayList<>(List.of("route", "100", "-20", "29.9239", "hundred"))));
  }

  @Test
  public void testGetPath_Streets_PathToReachableNode() throws SQLException {
    // smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.getPath("Chihiro Ave", "Radish Spirit Blvd", "Kamaji Pl", "Yubaba St"),
      "/n/0 -> /n/1 : /w/0\n" +
        "/n/1 -> /n/2 : /w/1\n" +
        "/n/2 -> /n/5 : /w/4");

    assertEquals(RouteCommandsHandler.getPath("Chihiro Ave", "Kamaji Pl", "Yubaba St", "Kamaji Pl"),
      "/n/2 -> /n/5 : /w/4");

    assertEquals(RouteCommandsHandler.getPath("Radish Spirit Blvd", "Chihiro Ave", "Radish Spirit Blvd", "Yubaba St"),
      "/n/0 -> /n/3 : /w/2");

    // maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(RouteCommandsHandler.getPath("Sunset Avenue", "Holyoke Avenue", "Draper Avenue",
      "Sunset Avenue"),
      "/n/4170.7137.201135672 -> /n/4170.7137.201135668 : /w/4170.7137.19352877.0.2\n"
        + "/n/4170.7137.201135668 -> /n/4170.7137.201135706 : /w/4170.7137.19352878.5.2\n"
        + "/n/4170.7137.201135706 -> /n/4170.7137.201135702 : /w/4170.7137.19352878.4.2\n"
        + "/n/4170.7137.201135702 -> /n/4170.7137.201135698 : /w/4170.7137.19352878.3.2\n"
        + "/n/4170.7137.201135698 -> /n/4170.7137.201135694 : /w/4170.7137.19352878.2.2\n"
        + "/n/4170.7137.201135694 -> /n/4170.7137.201135690 : /w/4170.7137.19352878.1.2\n"
        + "/n/4170.7137.201135690 -> /n/4170.7137.201135685 : /w/4170.7137.19352878.0.2");
  }

  @Test
  public void testGetPath_Streets_PathToUnreachableNode() throws SQLException {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.getPath("Kamaji Pl", "Yubaba St", "Chihiro Ave", "Radish Spirit Blvd"),
      "/n/5 -/- /n/0");
  }

  @Test
  public void testGetPath_Streets_PathToSelf() throws SQLException {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.getPath("Chihiro Ave", "Radish Spirit Blvd", "Chihiro Ave", "Radish Spirit Blvd"),
      "/n/0 -/- /n/0");

    assertEquals(RouteCommandsHandler.getPath("Kamaji Pl", "Yubaba St", "Kamaji Pl", "Yubaba St"),
      "/n/5 -/- /n/5");
  }

  @Test
  public void testGetPath_Streets_NoDBLoaded() throws SQLException {
    MapCommandHandler.reset();
    assertEquals(RouteCommandsHandler.getPath("Chihiro Ave", "Radish Spirit Blvd", "Kamaji Pl", "Yubaba St"),
      "ERROR: No Database loaded");
  }

  @Test
  public void testGetPath_Streets_StreetsDoNotIntersect() throws SQLException {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    // one street and cross street do not intersect
    assertEquals(RouteCommandsHandler.getPath("Chihiro Ave", "Yubaba St", "Yubaba St", "Kamaji Pl"),
      "ERROR: Paths do not intersect / street not found");

    // both street and cross street do not intersect
    assertEquals(RouteCommandsHandler.getPath("Chihiro Ave", "Yubaba St", "Radish Spirit Blvd", "Kamaji Pl"),
      "ERROR: Paths do not intersect / street not found");
  }

  @Test
  public void testGetPath_Streets_StreetSelfIntersection() throws SQLException {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    // self intersection of one street
    assertEquals(RouteCommandsHandler.getPath("Radish Spirit Blvd", "Radish Spirit Blvd", "Kamaji Pl", "Yubaba St"),
      "ERROR: Street self-intersection");

    assertEquals(RouteCommandsHandler.getPath("Chihiro Ave", "Radish Spirit Blvd", "Sootball Ln", "Sootball Ln"),
      "ERROR: Street self-intersection");

    // self intersection of both streets
    assertEquals(RouteCommandsHandler.getPath("Radish Spirit Blvd", "Radish Spirit Blvd", "Sootball Ln", "Sootball Ln"),
      "ERROR: Street self-intersection");
  }

  @Test
  public void testGetPath_Streets_InvalidStreet() throws SQLException {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    // one street is invalid
    assertEquals(RouteCommandsHandler.getPath("blah", "Radish Spirit Blvd", "Kamaji Pl", "Yubaba St"),
      "ERROR: Paths do not intersect / street not found");

    // one street is invalid in each pair
    assertEquals(RouteCommandsHandler.getPath("blah", "Radish Spirit Blvd", "bleeh", "Yubaba St"),
      "ERROR: Paths do not intersect / street not found");

    // one pair is invalid
    assertEquals(RouteCommandsHandler.getPath("blah", "bloh", "Kamaji Pl", "Yubaba St"),
      "ERROR: Paths do not intersect / street not found");

    // both pairs are invalid
    assertEquals(RouteCommandsHandler.getPath("blah", "bling", "blong", "pingu"),
      "ERROR: Paths do not intersect / street not found");
  }

  @Test
  public void testGetPath_Coordinates_PathToNode() {
    // Reachable Node
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.getPath(41.8206, -71.4003, 41.82, -71.4),
      "/n/5 -/- /n/0");

    // Unreachable Node
    assertEquals(RouteCommandsHandler.getPath(41.82, -71.4, 41.8206, -71.4003),
      "/n/0 -> /n/1 : /w/0\n" +
        "/n/1 -> /n/2 : /w/1\n" +
        "/n/2 -> /n/5 : /w/4");
  }

  @Test
  public void testGetPath_Coordinates_PathToSelf() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.getPath(41.82, -71.4, 41.82, -71.4),
      "/n/0 -/- /n/0");

    assertEquals(RouteCommandsHandler.getPath(41.8206, -71.4003, 41.8206, -71.4003),
      "/n/5 -/- /n/5");
  }

  @Test
  public void testGetPath_Coordinates_NoDBLoaded() {
    MapCommandHandler.reset();
    assertEquals(RouteCommandsHandler.getPath(41.82, -71.4, 41.8206, -71.4003),
      "ERROR: No Database loaded");
  }

  @Test
  public void testGetTargetPathNode_Streets_IntersectionExists() throws SQLException {
    // smallMaps
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertEquals(RouteCommandsHandler.getTargetPathNode("Chihiro Ave", "Kamaji Pl").getId(),
      "/n/2");

    assertEquals(RouteCommandsHandler.getTargetPathNode("Kamaji Pl", "Yubaba St").getId(),
      "/n/5");

    // maps
    MapCommandHandler.mapCommand("map " + bigMapsPath, "repl");
    assertEquals(RouteCommandsHandler.getTargetPathNode("Waterman Street", "Prospect Street").getId(),
      "/n/4182.7140.1955940309");
  }

  @Test
  public void testGetTargetPathNode_Streets_Streets_NoDBLoaded() {
    MapCommandHandler.reset();
    assertThrows(NullPointerException.class, () ->
        RouteCommandsHandler.getTargetPathNode("Chihiro Ave", "Radish Spirit Blvd"));
  }

  @Test
  public void testGetTargetPathNode_Streets_Streets_StreetsDoNotIntersect() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertThrows(IllegalPathStateException.class, () ->
        RouteCommandsHandler.getTargetPathNode("Chihiro Ave", "Yubaba St"));
  }

  @Test
  public void testGetTargetPathNode_Streets_Streets_StreetSelfIntersection() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertThrows(IllegalAccessError.class, () ->
        RouteCommandsHandler.getTargetPathNode("Radish Spirit Blvd", "Radish Spirit Blvd"));
  }

  @Test
  public void testGetTargetPathNode_Streets_Streets_InvalidStreet() {
    MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl");
    assertThrows(IllegalPathStateException.class, () ->
        RouteCommandsHandler.getTargetPathNode("blah", "Radish Spirit Blvd"));

    assertThrows(IllegalPathStateException.class, () ->
        RouteCommandsHandler.getTargetPathNode("blah", "bling"));
  }

  @Test
  public void testParseToRepl_NonEmptyListOfWays() {
    List<GraphEdge<String, String, GraticuleNode>> edges = new ArrayList<>();
    edges.add(w);
    edges.add(w2);
    edges.add(w3);
    edges.add(w4);

    assertEquals(RouteCommandsHandler.parseToRepl(edges, pn, pn2),
      "/n/0 -> /n/1 : /w/0\n" +
        "/n/1 -> /n/2 : /w/1\n" +
        "/n/2 -> /n/3 : /w/2\n" +
        "/n/3 -> /n/4 : /w/3");
  }

  @Test
  public void testParseToRepl_EmptyListOfWays() {
    List<GraphEdge<String, String, GraticuleNode>> edges = new ArrayList<>();

    assertEquals(RouteCommandsHandler.parseToRepl(edges, pn, pn2),
      "/n/0 -/- /n/1");
  }
}
