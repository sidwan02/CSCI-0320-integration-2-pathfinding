package edu.brown.cs.psekhsar_sdiwan2.searchAlgorithms;

import edu.brown.cs.psekhsar_sdiwan2.stars.Star;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListNaiveSearchTest {
    Map<Star, Double> keysNIndices = new HashMap<>();
    Star s100;
    Star s20;
    Star s1;
    Star s10;
    Star s120;
    Star s1340;

    public void setUp() {
        s100 = new Star(100, "s1", 1, 2, 3);
        keysNIndices.put(s100, 0.0);
        s20 = new Star(20, "s2", 1, 2, 3);
        keysNIndices.put(s20, 0.0000000000000001);
        s1 = new Star(1, "s3", 1, 2, 3);
        keysNIndices.put(s1, 0.0111);
        s10 = new Star(10, "s4", 1, 2, 3);
        keysNIndices.put(s10, 1.0);
        s120 = new Star(120, "s5", 1, 2, 3);
        keysNIndices.put(s120, 23.30);
        s1340 = new Star(1340, "s6", 1, 2, 3);
        keysNIndices.put(s1340, 23.50);
    }

    @Test
  public void testGetNaiveNearestNeighbor_NoDuplicateDistances_0K() {

        setUp();
    ListNaiveSearch<Integer, Star> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Integer> nearestNeighbors = new ArrayList<>();

    assertEquals(naiveSearch.getNaiveNearestNeighbors(0), nearestNeighbors);
  }

  @Test
  public void testGetNaiveNearestNeighbor_NoDuplicateDistances_KLessThanSize() {
      setUp();
    ListNaiveSearch<Integer, Star> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Star> nearestNeighbors = new ArrayList<>();
    nearestNeighbors.add(s100);
    nearestNeighbors.add(s20);
    nearestNeighbors.add(s1);

    assertEquals(naiveSearch.getNaiveNearestNeighbors(3), nearestNeighbors);
  }

  @Test
  public void testGetNaiveNearestNeighbor_NoDuplicateDistances_KGreaterThanSize() {
      setUp();
    ListNaiveSearch<Integer, Star> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Star> nearestNeighbors = new ArrayList<>();
    nearestNeighbors.add(s100);
    nearestNeighbors.add(s20);
    nearestNeighbors.add(s1);
    nearestNeighbors.add(s10);
    nearestNeighbors.add(s120);
    nearestNeighbors.add(s1340);

    assertEquals(naiveSearch.getNaiveNearestNeighbors(6), nearestNeighbors);
  }

  @Test
  public void testGetNaiveNearestNeighbor_DuplicateDistances_0K() {
        setUp();
    ListNaiveSearch<Integer, Star> naiveSearch = new ListNaiveSearch<>(keysNIndices);
    assertEquals(0, naiveSearch.getNaiveNearestNeighbors(0).size());
  }

  @Test
  public void testGetNaiveNearestNeighbor_DuplicateDistances_SomeDuplicatesShown() {
      Map<Star, Double> keysNIndices = new HashMap<>();
      Star s100 = new Star(100, "s1", 1, 2, 3);
      Star s20 = new Star(20, "s2", 1, 2, 3);
      Star s1 = new Star(1, "s3", 1, 2, 3);
      Star s4 = new Star(1340, "s4", 1, 2, 3);
      keysNIndices.put(s100, 0.0000000000000001);
     keysNIndices.put(s20, 0.0000000000000001);
    keysNIndices.put(s1, 0.0000000000000001);
    keysNIndices.put(s4, 23.50);

    ListNaiveSearch<Integer, Star> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Star> indices = naiveSearch.getNaiveNearestNeighbors(2);

    // check against all possible randomly generated solutions
    List<Star> someIndices1 = new ArrayList<>();
    someIndices1.add(s100);
    someIndices1.add(s20);

    List<Star> someIndices2 = new ArrayList<>();
    someIndices2.add(s20);
    someIndices2.add(s1);

    List<Star> someIndices3 = new ArrayList<>();
    someIndices3.add(s1);
    someIndices3.add(s100);

    assertTrue(indices.containsAll(someIndices1)
        || indices.containsAll(someIndices2)
        || indices.containsAll(someIndices3));
    assertTrue(someIndices1.containsAll(indices)
        || someIndices2.containsAll(indices)
        || someIndices3.containsAll(indices));
    assertEquals(indices.size(), 2);
  }

  @Test
  public void testGetNaiveNearestNeighbor_DuplicateDistances_AllDuplicatesShown() {
      Map<Star, Double> keysNIndices = new HashMap<>();
      Star s1 = new Star(100, "s1", 1, 2, 3);
      Star s2 = new Star(20, "s2", 1, 2, 3);
      Star s3 = new Star(1, "s3", 1, 2, 3);
      Star s4 = new Star(1340, "s4", 1, 2, 3);
      keysNIndices.put(s1, 0.0000000000000001);
      keysNIndices.put(s2, 0.0000000000000001);
      keysNIndices.put(s3, 0.0000000000000001);
      keysNIndices.put(s4, 23.50);

    ListNaiveSearch<Integer, Star> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Star> indices = naiveSearch.getNaiveNearestNeighbors(3);

    List<Star> someIndices = new ArrayList<>();
    someIndices.add(s3);
    someIndices.add(s2);
    someIndices.add(s1);

    assertTrue(indices.containsAll(someIndices));
    assertTrue(someIndices.containsAll(indices));
    assertEquals(indices.size(), 3);
  }

  @Test
  public void testAddCommonDistStars_AllIDsCanBeAdded() {
      Map<Star, Double> keysNIndices = new HashMap<>();
      Star s1 = new Star(1, "s1", 1, 2, 3);
      Star s2 = new Star(2, "s2", 1, 2, 3);
      Star s3 = new Star(3, "s3", 1, 2, 3);
      keysNIndices.put(s1, 11.5);
      keysNIndices.put(s2, 11.5);
      keysNIndices.put(s3, 11.5);

    ListNaiveSearch<Integer, Star> naiveSearch = new ListNaiveSearch<>(keysNIndices);

    List<Star> indices = new ArrayList<>();

    assertEquals(naiveSearch.addCommonDistStars(10,
            new ArrayList<>(keysNIndices.keySet()), 5, indices), 8);

    List<Star> allIndices = new ArrayList<>();
    allIndices.add(s1);
    allIndices.add(s2);
    allIndices.add(s3);

    assertTrue(indices.containsAll(allIndices));
    assertTrue(allIndices.containsAll(indices));
  }

  @Test
  public void testAddCommonDistStars_SomeIDsCanBeAdded() {
      Map<Star, Double> keysNIndices = new HashMap<>();
      Star s1 = new Star(1, "s1", 1, 2, 3);
      Star s2 = new Star(2, "s2", 1, 2, 3);
      Star s3 = new Star(3, "s3", 1, 2, 3);
      keysNIndices.put(s1, 11.5);
      keysNIndices.put(s2, 11.5);
      keysNIndices.put(s3, 11.5);

    ListNaiveSearch<Integer, Star> naiveSearch = new ListNaiveSearch<>(keysNIndices);


    List<Star> indices = new ArrayList<>();
    assertEquals(naiveSearch.addCommonDistStars(10,
            new ArrayList<>(keysNIndices.keySet()), 8, indices),10);

    // check against all possible randomly generated solutions
    List<Star> someIndices1 = new ArrayList<>();
    someIndices1.add(s1);
    someIndices1.add(s2);

    List<Star> someIndices2 = new ArrayList<>();
    someIndices2.add(s2);
    someIndices2.add(s3);

    List<Star> someIndices3 = new ArrayList<>();
    someIndices3.add(s1);
    someIndices3.add(s3);

    assertTrue(indices.containsAll(someIndices1)
        || indices.containsAll(someIndices2)
        || indices.containsAll(someIndices3));
    assertTrue(someIndices1.containsAll(indices)
        || someIndices2.containsAll(indices)
        || someIndices3.containsAll(indices));
    assertEquals(indices.size(), 2);
  }
}
