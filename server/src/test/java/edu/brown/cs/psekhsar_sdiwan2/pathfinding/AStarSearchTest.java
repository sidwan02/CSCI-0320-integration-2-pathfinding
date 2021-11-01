package edu.brown.cs.psekhsar_sdiwan2.pathfinding;

import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps.MapCommandHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps.NodeDistanceCalculators;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSearchTest {
  GraticuleNode pn = new GraticuleNode("/n/0", 2.22222222222, -39);
  GraticuleNode pn2 = new GraticuleNode("/n/1", 2.222222222, -39.29292);
  GraticuleNode pn3 = new GraticuleNode("/n/2", 2.22222222222, -39);
  GraticuleNode pn4 = new GraticuleNode("/n/3", 12.22222229292922222, -39);
  GraticuleNode pn5 = new GraticuleNode("/n/4", 2.22222222222, -39.000001);

  GraticuleEdge w = new GraticuleEdge("/w/0", "some random street y u want to know huh?", "baby street", pn, pn2);
  GraticuleEdge w2 = new GraticuleEdge("/w/1", "street of broken dreams", "", pn, pn3);
  GraticuleEdge w3 = new GraticuleEdge("/w/2", "street of broken dreams", "", pn, pn4);
  GraticuleEdge w4 = new GraticuleEdge("/w/3", "street of broken dreams", "", pn, pn5);

  Set<GraphEdge<String, String, GraticuleNode>> neighboringEdges = new HashSet<>();

  DijkstraAStar<String, String, GraticuleNode> dijk
    = new DijkstraAStar<>(
    NodeDistanceCalculators::getHaversineDistance,
    MapCommandHandler.getdBProxiedReader()::get,
    HeuristicFuncs::aStarDist);

  Comparator<PathWeightHeuristic<List<GraphEdge<String, String, GraticuleNode>>>> sortByDistance = Comparator
    .comparing(PathWeightHeuristic::getTotalDistance);
  PriorityQueue<PathWeightHeuristic<List<GraphEdge<String, String, GraticuleNode>>>> candidatePaths = new PriorityQueue<>(sortByDistance);

  List<GraphEdge<String, String, GraticuleNode>> path1 = new ArrayList<>();

  List<GraphEdge<String, String, GraticuleNode>> path2 = new ArrayList<>();

  List<GraphEdge<String, String, GraticuleNode>> path3 = new ArrayList<>();

  List<GraphEdge<String, String, GraticuleNode>> path4 = new ArrayList<>();

  GraticuleNode smallMapsN0 = new GraticuleNode("/n/0", 41.82, -71.4);
  GraticuleNode smallMapsN1 = new GraticuleNode("/n/1", 41.8203, -71.4);
  GraticuleNode smallMapsN2 = new GraticuleNode("/n/2", 41.8206, -71.4);
  GraticuleNode smallMapsN3 = new GraticuleNode("/n/3", 41.82, -71.4003);
  GraticuleNode smallMapsN4 = new GraticuleNode("/n/4", 41.8203, -71.4003);
  GraticuleNode smallMapsN5 = new GraticuleNode("/n/5", 41.8206, -71.4003);

  GraticuleEdge smallMapsW0 = new GraticuleEdge("/w/0", "Chihiro Ave", "residential", smallMapsN0, smallMapsN1);
  GraticuleEdge smallMapsW1 = new GraticuleEdge("/w/1", "Chihiro Ave", "residential", smallMapsN1, smallMapsN2);
  GraticuleEdge smallMapsW4 = new GraticuleEdge("/w/4", "Kamaji Pl", "residential", smallMapsN2, smallMapsN5);

  PathWeightHeuristic<List<GraphEdge<String, String, GraticuleNode>>> tuple1;
  PathWeightHeuristic<List<GraphEdge<String, String, GraticuleNode>>> tuple2;
  PathWeightHeuristic<List<GraphEdge<String, String, GraticuleNode>>> tuple3;
  PathWeightHeuristic<List<GraphEdge<String, String, GraticuleNode>>> tuple4;

  @Before
  public void setup() {
    neighboringEdges.add(w);
    neighboringEdges.add(w2);
    neighboringEdges.add(w3);
    neighboringEdges.add(w4);

    path1.add(w);
    path1.add(w2);
    path1.add(w3);
    path1.add(w4);

    path2.add(w);
    path2.add(w2);
    path2.add(w3);

    path3.add(w);

    path4.add(w);
    path4.add(w2);

    tuple1 = new PathWeightHeuristic<>(path1, 10.0, 0.0);
    tuple2 = new PathWeightHeuristic<>(path2, 1.0, 0.0);
    tuple3 = new PathWeightHeuristic<>(path3, 4.0, 0.0);
    tuple4 = new PathWeightHeuristic<>(path4, 9.0, 0.0);

    candidatePaths.add(tuple1);
    candidatePaths.add(tuple2);
    candidatePaths.add(tuple3);
    candidatePaths.add(tuple4);
  }
//
//  @Test
//  public void testPathFromStartToEnd_StartAndEndSame() {
//    assertEquals(dijk.pathFromStartToEnd(smallMapsN3, smallMapsN3), new ArrayList<>());
//  }
//
//  @Test
//  public void testPathFromStartToEnd_StartAndEndNoPath() {
//    assertEquals(dijk.pathFromStartToEnd(smallMapsN4, smallMapsN0), new ArrayList<>());
//  }

  @Test
  public void testPathFromStartToEnd_StartAndEndPathExists() {
    List<GraticuleEdge> expectedPath = new ArrayList<>();
    expectedPath.add(smallMapsW0);
    expectedPath.add(smallMapsW1);
    expectedPath.add(smallMapsW4);

    assertEquals(dijk.runDijkstraAStar(smallMapsN0, smallMapsN5), expectedPath);
  }
//
//  @Test
//  public void testGetMinCandidatePath_PathEndNeverSeen() {
//    assertEquals(dijk.getMinCandPath(new HashSet<>(), candidatePaths), tuple2);
//  }
//
//  @Test
//  public void testGetMinCandidatePath_PathEndSeenBefore() {
//    Set<String> seen = new HashSet<>();
//    seen.add("/n/3");
//
//    assertEquals(dijk.getMinCandPath(seen, candidatePaths), tuple3);
//  }
//
//  @Test
//  public void testGetEdgesFromTargetNode_NoEdges() {
//    GraticuleNode n5 = new GraticuleNode("/n/5", 41.8206, -71.4003);
//    assertEquals(dijk.getEdgesFromTargetNode(n5), new HashSet<>());
//  }
//
//  @Test
//  public void testGetEdgesFromTargetNode_SomeEdges() {
//    GraticuleNode n1 = new GraticuleNode("/n/1", 41.8203, -71.4);
//    GraticuleNode n2 = new GraticuleNode("/n/2", 41.8206, -71.4);
//    GraticuleNode n4 = new GraticuleNode("/n/4", 41.8203, -71.4003);
//
//    GraticuleEdge w1 = new GraticuleEdge("/w/1", "Chihiro Ave", "residential", n1, n2);
//    GraticuleEdge w3 = new GraticuleEdge("/w/3", "Sootball Ln", "residential", n1, n4);
//
//    Set<GraticuleEdge> expectedEdges = new HashSet<>();
//    expectedEdges.add(w1);
//    expectedEdges.add(w3);
//
//    assertEquals(dijk.getEdgesFromTargetNode(n1), expectedEdges);
//  }
//
//  @Test
//  public void testGetNeverSeenNeighbors_NoSeenNeighbors() {
//    Set<String> seenIDs = new HashSet<>();
//    assertEquals(dijk.getNeverSeenNeighbors(neighboringEdges, seenIDs), neighboringEdges);
//  }
//
//  @Test
//  public void testGetNeverSeenNeighbors_SomeSeenNeighbors() {
//    Set<String> seenIDs = new HashSet<>();
//    seenIDs.add("/n/2");
//    seenIDs.add("/n/3");
//
//    Set<GraphEdge<String, String, GraticuleNode>> finalEdges = new HashSet<>();
//    finalEdges.add(w);
//    finalEdges.add(w4);
//
//    assertEquals(dijk.getNeverSeenNeighbors(neighboringEdges, seenIDs), finalEdges);
//  }
//
//  @Test
//  public void testGetNeverSeenNeighbors_AllSeenNeighbors() {
//    Set<String> seenIDs = new HashSet<>();
//    seenIDs.add("/n/1");
//    seenIDs.add("/n/2");
//    seenIDs.add("/n/3");
//    seenIDs.add("/n/4");
//    seenIDs.add("/n/5");
//
//    assertEquals(dijk.getNeverSeenNeighbors(neighboringEdges, seenIDs), new HashSet<>());
//  }
}
