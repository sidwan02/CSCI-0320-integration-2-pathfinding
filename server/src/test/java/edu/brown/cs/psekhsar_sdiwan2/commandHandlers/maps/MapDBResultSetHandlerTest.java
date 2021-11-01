package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import edu.brown.cs.psekhsar_sdiwan2.coordinates.KdTree;
import edu.brown.cs.psekhsar_sdiwan2.database.DatabaseHandler;
import edu.brown.cs.psekhsar_sdiwan2.pathfinding.GraticuleEdge;
import edu.brown.cs.psekhsar_sdiwan2.pathfinding.GraticuleNode;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class MapDBResultSetHandlerTest {
  @Test
  public void testQueryDBWays_0RowsOutput()
      throws SQLException, ClassNotFoundException, FileNotFoundException {
    DatabaseHandler.loadDB("data/maps/smallMaps.sqlite3");
    assertEquals(MapDBResultSetHandler.queryDBWays(
        "SELECT way.id AS wayID, way.name, way.type, way.start, way.end,\n"
            + "N1.latitude as lat1, N1.longitude as lon1,\n"
            + "N2.latitude as lat2, N2.longitude as lon2\n"
            + "FROM way\n"
            + "INNER JOIN node as N1\n"
            + "INNER JOIN node as N2\n"
            + "ON (way.start=N1.id) AND (way.end=N2.id)" + "\n"
            + "WHERE lat1 BETWEEN 0 AND 100"
            + " AND lon1 BETWEEN 0 AND 100\n"
            + "OR lat2 BETWEEN 0 AND 100"
            + " AND lon2 BETWEEN 0 AND 100\n"
            + "ORDER BY wayID ASC"
            + ";"), new ArrayList<>());
  }

  @Test
  public void testQueryDBWays_ManyRowsOutput()
      throws SQLException, ClassNotFoundException, FileNotFoundException {
    DatabaseHandler.loadDB("data/maps/smallMaps.sqlite3");
    GraticuleNode n1 = new GraticuleNode("/n/1", 41.8203, -71.4);
    GraticuleNode n2 = new GraticuleNode("/n/2", 41.8206, -71.4);
    GraticuleNode n4 = new GraticuleNode("/n/4", 41.8203, -71.4003);
    GraticuleNode n5 = new GraticuleNode("/n/5", 41.8206, -71.4003);

    List<GraticuleEdge> expectedGraticuleEdges = new ArrayList<>();
    expectedGraticuleEdges.add(new GraticuleEdge("/w/1", "Chihiro Ave", "residential", n1, n2));
    expectedGraticuleEdges.add(new GraticuleEdge("/w/4", "Kamaji Pl", "residential", n2, n5));
    expectedGraticuleEdges.add(new GraticuleEdge("/w/6", "Yubaba St", "residential", n4, n5));

    assertEquals(MapDBResultSetHandler.queryDBWays(
        "SELECT way.id AS wayID, way.name, way.type, way.start, way.end,\n"
            + "N1.latitude as lat1, N1.longitude as lon1,\n"
            + "N2.latitude as lat2, N2.longitude as lon2\n"
            + "FROM way\n"
            + "INNER JOIN node as N1\n"
            + "INNER JOIN node as N2\n"
            + "ON (way.start=N1.id) AND (way.end=N2.id)" + "\n"
            + "WHERE lat1 BETWEEN 41.8204 AND 100"
            + " AND lon1 BETWEEN -100 AND 100\n"
            + "OR lat2 BETWEEN 41.8204 AND 100"
            + " AND lon2 BETWEEN -100 AND 100\n"
            + "ORDER BY wayID ASC"
            + ";"), expectedGraticuleEdges);
  }

  @Test
  public void testQueryDBGetWaysAroundTarget_0RowsOutput()
      throws SQLException, ClassNotFoundException, FileNotFoundException {
    DatabaseHandler.loadDB("data/maps/smallMaps.sqlite3");
    assertEquals(MapDBResultSetHandler.queryDBWays(
        "SELECT way.id AS wayID, way.name, way.type, way.start, way.end,\n"
            + "N1.latitude as lat1, N1.longitude as lon1,\n"
            + "N2.latitude as lat2, N2.longitude as lon2\n"
            + "FROM way\n"
            + "INNER JOIN node as N1\n"
            + "INNER JOIN node as N2\n"
            + "ON (way.start=N1.id) AND (way.end=N2.id)" + "\n"
            + "WHERE lat1 BETWEEN 0 AND 100"
            + " AND lon1 BETWEEN 0 AND 100\n"
            + "OR lat2 BETWEEN 0 AND 100"
            + " AND lon2 BETWEEN 0 AND 100\n"
            + "ORDER BY wayID ASC"
            + ";"), new ArrayList<>());
  }

  @Test
  public void testQueryDBGetWaysAroundTarget_ManyRowsOutput()
      throws SQLException, ClassNotFoundException, FileNotFoundException {
    DatabaseHandler.loadDB("data/maps/smallMaps.sqlite3");
    GraticuleNode n1 = new GraticuleNode("/n/1", 41.8203, -71.4);
    GraticuleNode n2 = new GraticuleNode("/n/2", 41.8206, -71.4);
    GraticuleNode n4 = new GraticuleNode("/n/4", 41.8203, -71.4003);
    GraticuleNode n5 = new GraticuleNode("/n/5", 41.8206, -71.4003);

    Set<GraticuleEdge> expectedGraticuleEdges = new HashSet<>();
    expectedGraticuleEdges.add(new GraticuleEdge("/w/1", "Chihiro Ave", "residential", n1, n2));
    expectedGraticuleEdges.add(new GraticuleEdge("/w/4", "Kamaji Pl", "residential", n2, n5));
    expectedGraticuleEdges.add(new GraticuleEdge("/w/6", "Yubaba St", "residential", n4, n5));

    assertEquals(MapDBResultSetHandler.queryDBGetWaysAroundTarget(
        "SELECT way.id AS wayID, way.name, way.type, way.start, way.end,\n"
            + "N1.latitude as lat1, N1.longitude as lon1,\n"
            + "N2.latitude as lat2, N2.longitude as lon2\n"
            + "FROM way\n"
            + "INNER JOIN node as N1\n"
            + "INNER JOIN node as N2\n"
            + "ON (way.start=N1.id) AND (way.end=N2.id)" + "\n"
            + "WHERE lat1 BETWEEN 41.8204 AND 100"
            + " AND lon1 BETWEEN -100 AND 100\n"
            + "OR lat2 BETWEEN 41.8204 AND 100"
            + " AND lon2 BETWEEN -100 AND 100\n"
            + "ORDER BY wayID ASC"
            + ";"), expectedGraticuleEdges);
  }

  @Test
  public void testQueryGetDBNodes_ManyRowsOutput()
      throws SQLException, ClassNotFoundException, FileNotFoundException, IllegalAccessException {
    DatabaseHandler.loadDB("data/maps/smallMaps.sqlite3");
    GraticuleNode n0 = new GraticuleNode("/n/0", 41.82, -71.4);
    GraticuleNode n1 = new GraticuleNode("/n/1", 41.8203, -71.4);
    GraticuleNode n2 = new GraticuleNode("/n/2", 41.8206, -71.4);
    GraticuleNode n3 = new GraticuleNode("/n/3", 41.82, -71.4003);
    GraticuleNode n4 = new GraticuleNode("/n/4", 41.8203, -71.4003);
    GraticuleNode n5 = new GraticuleNode("/n/5", 41.8206, -71.4003);
    List<GraticuleNode> allNodes = new ArrayList<>();
    allNodes.add(n5);
    allNodes.add(n4);
    allNodes.add(n3);
    allNodes.add(n2);
    allNodes.add(n1);
    allNodes.add(n0);


    KdTree<String, GraticuleNode> expectedKdTree = new KdTree<>(2, allNodes);
    expectedKdTree.buildTree();

    assertEquals(MapDBResultSetHandler.queryDBNodes(
        "SELECT N1.id, N1.latitude as lat1, N1.longitude as lon1, "
            + "N2.id, N2.latitude as lat2, N2.longitude as lon2, way.id "
            + "FROM way\n"
            + "INNER JOIN node as N1\n"
            + "INNER JOIN node as N2\n"
            + "ON (way.start=N1.id) AND (way.end=N2.id)"
            + "AND NOT(way.type=\"\" OR way.type=\"unclassified\")\n"
            + ";"), expectedKdTree);
  }
}
