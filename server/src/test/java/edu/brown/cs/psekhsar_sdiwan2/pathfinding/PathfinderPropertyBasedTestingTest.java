package edu.brown.cs.psekhsar_sdiwan2.pathfinding;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PathfinderPropertyBasedTestingTest {
  @Test
  public void testTestModels() {
    PathfinderPropertyBasedTesting pbt = new PathfinderPropertyBasedTesting();
    String possibleDB = "data/maps/smallMaps.sqlite3";
    // replace with the maps DB here!

    assertTrue(pbt.testModels(100, 10, possibleDB));
  }
}
