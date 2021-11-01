package edu.brown.cs.ta.pathfinding;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PathfinderPropertyBasedTestingTest {
  @Test
  public void testTestModels() {
    PathfinderPropertyBasedTesting pbt = new PathfinderPropertyBasedTesting();
    String possibleDB = "data/pathfinding/smallMaps.sqlite3";
    // replace with the pathfinding DB here!

    assertTrue(pbt.testModels(100, 10, possibleDB));
  }
}
