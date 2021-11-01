package edu.brown.cs.ta.stars;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchAlgoPropertyBasedTestingTest {
  List<Star> starsListAllSame = new ArrayList<>();
  List<Star> starsListNoTied = new ArrayList<>();

  @Before
  public void setUp() {
    starsListAllSame.add(new Star(1, "star1", 0, 0, 0));
    starsListAllSame.add(new Star(2, "star2", 0, 0, 0));
    starsListAllSame.add(new Star(3, "star3", 0, 0, 0));
    starsListAllSame.add(new Star(4, "star4", 0, 0, 0));
    starsListAllSame.add(new Star(5, "star5", 0, 0, 0));
    starsListAllSame.add(new Star(6, "star6", 0, 0, 0));
    starsListAllSame.add(new Star(7, "star7", 0, 0, 0));
    starsListAllSame.add(new Star(8, "star8", 0, 0, 0));
    starsListAllSame.add(new Star(9, "star9", 0, 0, 0));
    starsListAllSame.add(new Star(10, "star10", 0, 0, 0));

    starsListNoTied.add(new Star(1, "star1", 0, 0, 0));
    starsListNoTied.add(new Star(2, "star2", 10, 1, 0));
    starsListNoTied.add(new Star(3, "star3", 0, 0, 2.3));
    starsListNoTied.add(new Star(4, "star4", 3, 0, 0));
    starsListNoTied.add(new Star(5, "star5", 30, 4.33, 0));
    starsListNoTied.add(new Star(6, "star6", 0, 0, 5));
    starsListNoTied.add(new Star(7, "star7", 6, 0, 0));
    starsListNoTied.add(new Star(8, "star8", 0, 7, 0));
    starsListNoTied.add(new Star(9, "star9", 0, 0, 0.08));
    starsListNoTied.add(new Star(10, "star10", 9, 0, 20));
  }

  @Test
  public void testTestModels() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();
    assertTrue(pbt.testModels(100));
  }

  @Test
  public void testOracle_ValidSolutions_Neighbors_TiedDistances_5Args() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n9\n3\n";
    String sol2 = "6\n4\n10\n8\n7\n";

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertTrue(pbt.oracle(sol1, sol2, coords, -1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_ValidSolutions_Neighbors_TiedDistances_3Args() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "10\n2\n5\n9\n3\n";
    String sol2 = "6\n4\n10\n8\n7\n";

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertTrue(pbt.oracle(sol1, sol2, coords, 1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_ValidSolutions_Neighbors_NoTiedDistances_5Args() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n9\n3\n4\n6\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertTrue(pbt.oracle(sol1, sol2, coords, -1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_ValidSolutions_Neighbors_NoTiedDistances_3Args() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "9\n3\n4\n6\n7\n";
    String sol2 = "9\n3\n4\n6\n7\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertTrue(pbt.oracle(sol1, sol2, coords, 1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_ValidSolutions_Radius_TiedDistances_5Args() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n9\n3\n4\n7\n10\n5\n6\n8\n";
    String sol2 = "6\n4\n9\n10\n8\n7\n1\n3\n5\n2\n";

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertTrue(pbt.oracle(sol1, sol2, coords, -1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_ValidSolutions_Radius_TiedDistances_3Args() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n9\n3\n4\n10\n5\n6\n8\n";
    String sol2 = "6\n4\n9\n10\n8\n1\n3\n5\n2\n";

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertTrue(pbt.oracle(sol1, sol2, coords, 7, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_ValidSolutions_Radius_NoTiedDistances_5Args() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n9\n3\n4\n6\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertTrue(pbt.oracle(sol1, sol2, coords, -1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_ValidSolutions_Radius_NoTiedDistances_3Args() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "9\n3\n4\n6\n";
    String sol2 = "9\n3\n4\n6\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertTrue(pbt.oracle(sol1, sol2, coords, 1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_Neighbors_IdNotInStarsList() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n90\n3\n";
    String sol2 = "6\n4\n10\n8\n7\n";

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_Neighbors_SizeDiscrepancy() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n9\n3\n";
    String sol2 = "6\n4\n10\n8\n";

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_Neighbors_IncorrectIdOrderInFirstSolution() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n9\n3\n";
    String sol2 = "1\n9\n3\n4\n6\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_Neighbors_IncorrectIdOrderInSecondSolution() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n2\n5\n9\n3\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_Neighbors_IncorrectIdOrderInBothSolutions() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n9\n3\n";
    String sol2 = "6\n4\n10\n8\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_Neighbors_CorrectOrderSkippedStars() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n9\n4\n6\n7\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_Neighbors_BothSolutionsInvalidK() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n9\n4\n6\n7\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 4,
        pbt::validNearestNeighborsResult));
  }

  @Test
  public void testOracle_Radius_IdNotInStarsList() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n90\n3\n";
    String sol2 = "6\n4\n10\n8\n7\n";

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_Radius_SizeDiscrepancy() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n9\n3\n";
    String sol2 = "6\n4\n10\n8\n";

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_Radius_IncorrectIdOrderInFirstSolution() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n9\n3\n";
    String sol2 = "1\n9\n3\n4\n6\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_Radius_IncorrectIdOrderInSecondSolution() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n2\n5\n9\n3\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_Radius_IncorrectIdOrderInBothSolutions() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n2\n5\n9\n3\n";
    String sol2 = "6\n4\n10\n8\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_Radius_CorrectOrderSkippedStars() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n9\n4\n6\n7\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 5.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_Radius_BothSolutionsDontReachR() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n9\n4\n6\n7\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 20.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testOracle_Radius_BothSolutionsCrossR() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    String sol1 = "1\n9\n3\n4\n6\n";
    String sol2 = "1\n9\n4\n6\n7\n";

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.oracle(sol1, sol2, coords, -1, 1.0,
        pbt::validRadiusResult));
  }

  @Test
  public void testValidNearestNeighborsResult_IdNotInStarsList() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(2);
    sol1.add(5);
    sol1.add(90);
    sol1.add(3);

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validNearestNeighborsResult(sol1, coords, -1, 5));
  }

  @Test
  public void testValidNearestNeighborsResult_IncorrectIdOrder() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(2);
    sol1.add(5);
    sol1.add(9);
    sol1.add(3);

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validNearestNeighborsResult(sol1, coords, -1, 5));
  }

  @Test
  public void testValidNearestNeighborsResult_CorrectOrderSkippedStars() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(9);
    sol1.add(4);
    sol1.add(6);
    sol1.add(7);

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validNearestNeighborsResult(sol1, coords, -1, 5));
  }

  @Test
  public void testValidNearestNeighborsResult_InvalidK() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(9);
    sol1.add(3);
    sol1.add(4);
    sol1.add(6);

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validNearestNeighborsResult(sol1, coords, -1, 4));
  }

  @Test
  public void testValidRadiusResult_IdNotInStarsList() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(2);
    sol1.add(5);
    sol1.add(90);
    sol1.add(3);

    pbt.setCurrentStarsList(starsListAllSame);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validRadiusResult(sol1, coords, -1, 5.0));
  }

  @Test
  public void testValidRadiusResult_IncorrectIdOrderDistances() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(2);
    sol1.add(5);
    sol1.add(9);
    sol1.add(3);

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validRadiusResult(sol1, coords, -1, 5.0));
  }

  @Test
  public void testValidRadiusResult_CorrectOrderSkippedStars() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(9);
    sol1.add(4);
    sol1.add(6);
    sol1.add(7);

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validRadiusResult(sol1, coords, -1, 5.0));
  }

  @Test
  public void testValidRadiusResult_SolutionDoesntReachR() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(9);
    sol1.add(3);
    sol1.add(4);
    sol1.add(6);


    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validRadiusResult(sol1, coords, -1, 20.0));
  }

  @Test
  public void testValidRadiusResult_SolutionCrossesR() {
    SearchAlgoPropertyBasedTesting pbt = new SearchAlgoPropertyBasedTesting();

    List<Integer> sol1 = new ArrayList<>();
    sol1.add(1);
    sol1.add(9);
    sol1.add(3);
    sol1.add(4);
    sol1.add(6);

    pbt.setCurrentStarsList(starsListNoTied);
    List<Double> coords = new ArrayList<>();
    coords.add(0.0);
    coords.add(0.0);
    coords.add(0.0);
    assertFalse(pbt.validRadiusResult(sol1, coords, -1, 1.0));
  }
}
