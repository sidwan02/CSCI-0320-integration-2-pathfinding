package edu.brown.cs.ta.pathfinding;

import edu.brown.cs.ta.commandHandlers.pathfinding.NearestCommandHandler;
import edu.brown.cs.ta.commandHandlers.pathfinding.ObstacleObjectPersistence;
import edu.brown.cs.ta.obstacle.Obstacle;
import edu.brown.cs.ta.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

/** Class that performs the LPA* pathfind algorithm.
 * @param <E> is the ID type of the GraphEdge
 * @param <N> is the ID type of the GraphNode
 * @param <P> is an object type that extends GraphNode
 */
public class LPAStar<E, N, P extends GraphNode<N>> {
  private final BiFunction<P, P, Double> distanceFunc;
  private final Function<P, Set<GraphEdge<E, N, P>>>
          queryNeighborEdgesFunc;
  private final Utils.Function4To1<P, P, GraphEdge<E, N, P>,
          BiFunction<P, P, Double>, Double> heuristicFunc;
  private Map<N, GraphEdge<E, N, P>> visited;
  private PriorityQueue<GraphEdge<E, N, P>> routes;

  /**
   * Constructor for DijkstraAStar.
   *
   * @param distanceFunc           is a function that takes two GraphNodes and returns a double that
   *                               is the distance between the two nodes
   * @param queryNeighborEdgesFunc is a function that queries the
   *                               adjacent edges starting from a target node
   * @param heuristicFunc          is a function that calculates the heuristic of a node
   */
  public LPAStar(BiFunction<P, P, Double> distanceFunc,
                 Function<P, Set<GraphEdge<E, N, P>>> queryNeighborEdgesFunc,
                 Utils.Function4To1<P,
                               P,
                               GraphEdge<E, N, P>,
                               BiFunction<P, P, Double>,
                               Double> heuristicFunc) {
    this.queryNeighborEdgesFunc = queryNeighborEdgesFunc;
    this.heuristicFunc = heuristicFunc;
    this.distanceFunc = distanceFunc;
    this.visited = new HashMap<>();
    this.routes = new PriorityQueue<>((e1, e2) -> {
      Double e1Distance = e1.getTotalDistance();
      Double e2Distance = e2.getTotalDistance();
      return e1Distance.compareTo(e2Distance);
    });
  }

    /**
   * Method responsible for the Dijkstra/AStar search. Given the starting and ending
   * GraphNodes, this method finds the shortest path between them. Because graphs can
   * be really big, this method relies on querying the database every time it looks for
   * outgoing edges. This way we don't need to store and build the whole graph in memory.
   * Querying for outgoing edges is optimized further via caching.
   *
   * @param startNode the start node of the path
   * @param endNode   the end node of the path
   * @return a list of GraphEdges that represent the shortest path between startNode and
   * endNode
   */
  public List<GraphEdge<E, N, P>> runLPAStar(P startNode, P endNode) {
//    System.out.println("startNode: " + startNode);
//    System.out.println("endNode: " + endNode);
    // Get all obstacle positions
    Map.Entry<Double, List<Obstacle>> obstaclePositionsArr = ObstacleObjectPersistence.getObstacleObj().getLatestObstaclePositions();

    // For each of the obstacle lat and lon, find the associated nodes
    List<String> trafficNodes = new ArrayList<>();

    if (obstaclePositionsArr != null) {
      for (Obstacle obstaclePosition : obstaclePositionsArr.getValue()) {
        double lat = obstaclePosition.getLat();
        double lon = obstaclePosition.getLon();

        trafficNodes.add(NearestCommandHandler.getNearestNode(lat, lon).getId());
      }
    }

    System.out.println("trafficNodes: " + trafficNodes);

    Set<GraphEdge<E, N, P>> initOutwardEdges = queryNeighborEdgesFunc.apply(startNode);

    for (GraphEdge<E, N, P> edge : initOutwardEdges) {

      if (trafficNodes.contains(edge.getEndNode().getId())) {
        edge.setTotalDistance(Double.POSITIVE_INFINITY);
      } else {
        Double edgeDistance = distanceFunc.apply(edge.getStartNode(), edge.getEndNode());
        Double heuristicDistance = heuristicFunc.apply(startNode, endNode, edge, distanceFunc);

        edge.setTotalDistance(edgeDistance + heuristicDistance);
      }
    }

    routes.addAll(initOutwardEdges);
    while (!routes.isEmpty()) {
      while (visited.containsKey(routes.peek().getEndNode().getId())) {
        routes.poll();
        if (routes.isEmpty()) {
          return new ArrayList<>();
        }
      }

      GraphEdge<E, N, P> nextWay = routes.poll();

      visited.put(nextWay.getEndNode().getId(), nextWay);

      if (nextWay.getEndNode().getId().equals(endNode.getId())) {
        break;
      }

      Set<GraphEdge<E, N, P>> outWardEdges = queryNeighborEdgesFunc.apply(nextWay.getEndNode());

      double curTotalDistance = nextWay.getTotalDistance();

      for (GraphEdge<E, N, P> edge : outWardEdges) {
        if (trafficNodes.contains(edge.getEndNode().getId())) {
          edge.setTotalDistance(Double.POSITIVE_INFINITY);
        } else {
          Double edgeDistance = distanceFunc.apply(edge.getStartNode(), edge.getEndNode());
          Double heuristicDistance = heuristicFunc.apply(startNode, endNode, edge, distanceFunc);

          edge.setTotalDistance(curTotalDistance + edgeDistance + heuristicDistance);
        }

        routes.add(edge);
      }
//      System.out.println("here");
    }

    return findRoute(startNode, endNode);
  }

    /**
   * This method takes the visited hashmap built by runDijkstra and
   * uses it to backtrack from the endNode all the way to the startNode.
   * As it backtracks, it builds the list of GraphEdges that represent the
   * shortest path.
   *
   * @param startNode the start node of the path
   * @param endNode   the end node of the path
   * @return a list of GraphEdges that represent the shortest path between startNode and
   * endNode
   */
  private List<GraphEdge<E, N, P>> findRoute(P startNode, P endNode) {
    List<GraphEdge<E, N, P>> edgesList = new ArrayList<>();

    if (!visited.containsKey(endNode.getId())) {
      return new ArrayList<>();
    }

    N startNodeID = startNode.getId();
    P curNode = endNode;
    while (!curNode.getId().equals(startNodeID)) {
      GraphEdge<E, N, P> curEdge = visited.get(curNode.getId());
      edgesList.add(curEdge);
      curNode = curEdge.getStartNode();
    }

    Collections.reverse(edgesList);

//    System.out.println("edgesList: " + edgesList.size());

    if (edgesList.size() > 1) {
      visited = new HashMap<>();
      routes = new PriorityQueue<>((e1, e2) -> {
        Double e1Distance = e1.getTotalDistance();
        Double e2Distance = e2.getTotalDistance();
        return e1Distance.compareTo(e2Distance);
      });

      runLPAStar(edgesList.get(0).getEndNode(), endNode).add(0, edgesList.get(0));
    }
    return edgesList;
  }

}