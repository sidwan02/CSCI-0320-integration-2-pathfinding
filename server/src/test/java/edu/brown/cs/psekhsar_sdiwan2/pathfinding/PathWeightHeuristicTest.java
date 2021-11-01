package edu.brown.cs.psekhsar_sdiwan2.pathfinding;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PathWeightHeuristicTest {
  @Test
  public void testGetters() {
    PathWeightHeuristic<Integer> pathWeiHeur = new PathWeightHeuristic<>(1, 10.0, 20.0);

    assertEquals(1, pathWeiHeur.getPath(), 0);
    assertEquals(10, pathWeiHeur.getDistance(), 0.0);
    assertEquals(30, pathWeiHeur.getTotalDistance(), 0);
  }

  @Test
  public void testToString() {
    PathWeightHeuristic<Integer> pathWeiHeur = new PathWeightHeuristic<>(1, 10.0, 0.0);

    assertEquals("PathWeightHeuristic{path=1, distance=10.0, heuristic=0.0}", pathWeiHeur.toString());
  }

  @Test
  public void testEquals() {
    PathWeightHeuristic<Integer> pathWeiHeur1 = new PathWeightHeuristic<>(1, 10.0, 0.0);
    PathWeightHeuristic<Integer> pathWeiHeur2 = new PathWeightHeuristic<>(1, 10.0, 0.0);
    PathWeightHeuristic<Integer> pathWeiHeur3 = new PathWeightHeuristic<>(1, 10.01, 0.0);

    assertEquals(pathWeiHeur1, pathWeiHeur2);
    assertEquals(pathWeiHeur1, pathWeiHeur1);
    assertNotEquals(pathWeiHeur1, pathWeiHeur3);
  }
}
