package edu.brown.cs.psekhsar_sdiwan2.stars;

import edu.brown.cs.psekhsar_sdiwan2.coordinates.KeyDistance;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars.KdTreeCommandsHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars.NaiveCommandsHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars.StarsCommandHandler;
import edu.brown.cs.psekhsar_sdiwan2.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/** Class to perform Model Based Testing on the naive and kdTree implementations
 of the Nearest Neighbors and Radius Search algorithms.
 */
public class SearchAlgoPropertyBasedTesting {
  private List<Star> currentStarsList;

  /** Set the currentStarsList when testing the oracle.
   @param currentStarsList A List of Stars.
   */
  public void setCurrentStarsList(List<Star> currentStarsList) {
    this.currentStarsList = currentStarsList;
  }

  /** Check whether the naive and kdTree implementations give the same results
   when fed randomly generated commands as input.
   @param iterations An integer that represents the number of randomly generated
   inputs to test naive and kdTree implementations against.
   @return true or false if all comparisons succeeded.
   */
  public boolean testModels(int iterations) {

    int i = 0;
    while (i < iterations) {
      String finalCommand = "";
      HashMap<Integer, String> possibleCsv = new HashMap<>();
      possibleCsv.put(0, "data/stars/one-star.csv");
      possibleCsv.put(1, "data/stars/tie-star.csv");
      possibleCsv.put(2, "data/stars/stardata.csv");

      // choice of 0 represents 3Args i.e. threshold and star name
      // choice of 1 represents 5Args i.e. threshold and three coordinates
      int argSizeChoice = ThreadLocalRandom.current().nextInt(0, 1 + 1);

      // choice of 0 represents neighbors implementation
      // choice of 1 represents radius implementation
      int commandChoice = ThreadLocalRandom.current().nextInt(0, 1 + 1);

      int csvChoice = ThreadLocalRandom.current().nextInt(0, 2 + 1);
      String csvPath = possibleCsv.get(csvChoice);

      String csvLoadCommand = "stars " + csvPath;
      StarsCommandHandler.starsCommand(csvLoadCommand, "repl");

      currentStarsList = StarsCommandHandler.getStarsList();

      Double targetX = null;
      Double targetY = null;
      Double targetZ = null;

      final int upperThreshBound = 25;
      final int lowerCoordinateBound = -1000;
      final int upperCoordinateBound = 1000;

      // represents what k or r should be
      Number threshold = null;

      // represents the target ID to exclude when getting the IDs after searching
      // or -1 if the target ID should not be excluded
      int excludeId = -1;

      if (argSizeChoice == 0) {
        int starChoice = ThreadLocalRandom.current().nextInt(0, currentStarsList.size());
        String chosenStar = currentStarsList.get(starChoice).getName();

        int index = 0;
        // get index of star with 'name'
        while (!currentStarsList.get(index).getName().equals(chosenStar)) {
          index++;
        }

        targetX = currentStarsList.get(index).getX();
        targetY = currentStarsList.get(index).getY();
        targetZ = currentStarsList.get(index).getZ();

        excludeId = currentStarsList.get(index).getId();

        if (commandChoice == 0) {
          int k = ThreadLocalRandom.current().nextInt(0, upperThreshBound + 1);
          threshold = k;

          finalCommand = k + " " + "\"" + chosenStar + "\"";
        } else if (commandChoice == 1) {
          double r = ThreadLocalRandom.current().nextDouble(0, upperThreshBound + 1);
          threshold = r;

          finalCommand = r + " " + "\"" + chosenStar + "\"";
        }

      } else if (argSizeChoice == 1) {
        List<Double> commandArgs = new ArrayList<>();
        int argSize = 3;
        while (argSize > 0) {
          commandArgs.add(ThreadLocalRandom.current().nextDouble(lowerCoordinateBound,
              upperCoordinateBound + 1));
          argSize--;
        }

        targetX = commandArgs.get(0);
        targetY = commandArgs.get(1);
        targetZ = commandArgs.get(2);

        List<String> commandArgsString = new ArrayList<>();
        for (Double arg : commandArgs) {
          commandArgsString.add(arg.toString());
        }

        if (commandChoice == 0) {
          int k = ThreadLocalRandom.current().nextInt(0, upperThreshBound + 1);

          threshold = k;

          finalCommand = k + " " + String.join(" ", commandArgsString);
        } else if (commandChoice == 1) {
          double r = ThreadLocalRandom.current().nextDouble(0, upperThreshBound + 1);

          threshold = r;

          finalCommand = r + " " + String.join(" ", commandArgsString);
        }
      }

      // print to console for debug purposes
      if (commandChoice == 0) {
        System.out.println("neighbors [naive/kdtree]: " + finalCommand);
      } else if (commandChoice == 1) {
        System.out.println("radius [naive/kdtree]: " + finalCommand);
      }

      List<Double> coords = new ArrayList<>();

      String naiveOutput;
      String kdTreeOutput;

      // call the oracle on the results of the commands on naive and kdtree implementations
      try {
        if (commandChoice == 0) {
          naiveOutput = NaiveCommandsHandler
              .naiveNeighborsCommand("naive_neighbors " + finalCommand,
              "repl");
          kdTreeOutput = KdTreeCommandsHandler
              .neighborsCommand("neighbors " + finalCommand,
              "repl");

          coords.add(targetX);
          coords.add(targetY);
          coords.add(targetZ);
          assertTrue(oracle(naiveOutput, kdTreeOutput, coords, excludeId,
              threshold, this ::validNearestNeighborsResult));
        } else if (commandChoice == 1) {
          naiveOutput = NaiveCommandsHandler
              .naiveRadiusCommand("naive_radius " + finalCommand, "repl");
          kdTreeOutput = KdTreeCommandsHandler
              .radiusCommand("radius " + finalCommand, "repl");

          coords.add(targetX);
          coords.add(targetY);
          coords.add(targetZ);
          assertTrue(oracle(naiveOutput, kdTreeOutput, coords, excludeId,
              threshold, this::validRadiusResult));
        }
      } catch (AssertionError e) {
        return false;
      }

      i++;
    }
    return true;
  }

  /** Check whether the naive and kdTree results have valid properties in the form
   of containing the right IDs in ascending order of corresponding distances from the target point
   and having the same number of IDs.
   @param naiveOutput The String output from executing the naive implementation.
   @param kdTreeOutput The String output from executing the kdTree implementation.
   @param coords Contains the x, y and z coordinate values of the target point.
   @param excludeId Integer value that holds the ID to exclude, or -1 if no ID should be excluded
   @param threshold A Number representing either int k or double r depending on the search
   function passed.
   @param search A function that represents either validNearestNeighborsSearch or
   validRadiusSearch depending on where this method is called from.
   @return true or false depending on whether the properties of both naive result and
   kdTree result are satisfied.
   */
  boolean oracle(String naiveOutput, String kdTreeOutput,
                        List<Double> coords, Integer excludeId,
                        Number threshold,
                        Utils.Function4To1<List<Integer>, List<Double>,
                          Integer, Number, Boolean> search) {
    List<String> naiveIdStrings = Arrays.stream(naiveOutput.split("\n"))
        .collect(Collectors.toList());

    List<Integer> naiveIds = new ArrayList<>();

    // parse the naive answer
    for (String strId : naiveIdStrings) {
      if (!strId.equals("")) {
        naiveIds.add(Integer.parseInt(strId));
      }
    }

    List<String> kdTreeIdStrings = Arrays.stream(kdTreeOutput.split("\n"))
        .collect(Collectors.toList());

    List<Integer> kdTreeIds = new ArrayList<>();

    // parse the kdTree answer
    for (String strId : kdTreeIdStrings) {
      if (!strId.equals("")) {
        kdTreeIds.add(Integer.parseInt(strId));
      }
    }

    return naiveIds.size() == kdTreeIds.size()
        && search.apply(naiveIds, coords, excludeId, threshold)
        && search.apply(kdTreeIds, coords, excludeId, threshold);
  }

  /** Check if a List of IDs is a valid NearestNeighborsSearch result under the
   passed constraints.
   @param ids The List of IDs.
   @param coords Contains the x, y and z coordinate values of the target point.
   @param excludeId Integer value that holds the ID to exclude, or -1 if no ID should be excluded
   @param thresh Threshold Number which represents k for maximum neighbors count.
   or excluded when comparing against the nave or kdTree solutions.
   @return true or false depending on whether the List of IDs is arranged in ascending order
   of corresponding distances from the target point.
   */
  boolean validNearestNeighborsResult(List<Integer> ids, List<Double> coords,
                                             Integer excludeId, Number thresh) {
    int k = (int) thresh;

    // check whether the size of the solution is reasonable
    if (ids.size() == Math.min(k, ids.size())) {
      Comparator<KeyDistance<Star>> byDistance = Comparator.comparing(KeyDistance::getDistance);
      PriorityQueue<KeyDistance<Star>> pq = new PriorityQueue<>(byDistance);
      // order the loaded stars by distance to the target
      for (Star star : currentStarsList) {
        if (!(star.getId().equals(excludeId))) {
          double starDist = Math.sqrt(Math.pow(star.getX() - coords.get(0), 2)
              + Math.pow(star.getY() - coords.get(1), 2)
              + Math.pow(star.getZ() - coords.get(2), 2));
          pq.add(new KeyDistance<>(star, starDist));
        }
      }

      try {
        for (Integer id : ids) {
          Star currentStar = currentStarsList.stream().filter(star -> star.getId().equals(id))
              .collect(Collectors.toList()).get(0);

          double currentDist = Math.sqrt(Math.pow(currentStar.getX() - coords.get(0), 2)
              + Math.pow(currentStar.getY() - coords.get(1), 2)
              + Math.pow(currentStar.getZ() - coords.get(2), 2));

          KeyDistance<Star> priorityQueueStar = pq.remove();
          // check if the next loaded star has the same distance as the next solution star
          if (priorityQueueStar.getDistance() != currentDist) {
            return false;
          }
        }
      } catch (IndexOutOfBoundsException e) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  /** Check if a List of IDs is a valid RadiusSearch result under the
   passed constraints.
   @param ids The List of IDs.
   @param coords Contains the x, y and z coordinate values of the target point.
   @param excludeId Integer value that holds the ID to exclude, or -1 if no ID should be excluded.
   @param thresh Threshold Number which represents r for radius inclusive bound.
   @return true or false depending on whether the List of IDs is arranged in ascending order
   of corresponding distances from the target point.
   */
  boolean validRadiusResult(List<Integer> ids, List<Double> coords,
                                   Integer excludeId, Number thresh) {
    double r = (double) thresh;

    Comparator<KeyDistance<Star>> byDistance = Comparator.comparing(KeyDistance::getDistance);
    PriorityQueue<KeyDistance<Star>> pq = new PriorityQueue<>(byDistance);
    // order the loaded stars by distance to the target
    for (Star star : currentStarsList) {
      if (!(star.getId().equals(excludeId))) {
        double starDist = Math.sqrt(Math.pow(star.getX() - coords.get(0), 2)
            + Math.pow(star.getY() - coords.get(1), 2)
            + Math.pow(star.getZ() - coords.get(2), 2));
        pq.add(new KeyDistance<>(star, starDist));
      }
    }

    try {
      for (Integer id : ids) {
        Star currentStar = currentStarsList.stream().filter(star -> star.getId().equals(id))
            .collect(Collectors.toList()).get(0);

        double currentDist = Math.sqrt(Math.pow(currentStar.getX() - coords.get(0), 2)
            + Math.pow(currentStar.getY() - coords.get(1), 2)
            + Math.pow(currentStar.getZ() - coords.get(2), 2));

        KeyDistance<Star> priorityQueueStar = pq.remove();
        // check if the next loaded star has the same distance as the next solution star
        // and the next solution star is within the radius bound
        if (priorityQueueStar.getDistance() != currentDist || currentDist > r) {
          return false;
        }
      }
    } catch (IndexOutOfBoundsException e) {
      return false;
    }

    // check if the solution did not cover all stars in the threshold
    return pq.size() == 0 || pq.remove().getDistance() > r;
  }
}
