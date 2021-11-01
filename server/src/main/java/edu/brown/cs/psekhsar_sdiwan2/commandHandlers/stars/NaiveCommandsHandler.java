package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars;

import edu.brown.cs.psekhsar_sdiwan2.coordinates.Coordinate;
import edu.brown.cs.psekhsar_sdiwan2.searchAlgorithms.ListNaiveSearch;
import edu.brown.cs.psekhsar_sdiwan2.stars.Star;
import edu.brown.cs.psekhsar_sdiwan2.main.ErrorMessages;
import edu.brown.cs.psekhsar_sdiwan2.utils.Utils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Class that checks and executes stars, naive_neighbors and naive_radius commands
 by loading CSV files and extracting necessary data from aforementioned files.
 */
public final class NaiveCommandsHandler {
  private NaiveCommandsHandler() {
  }

  private static Function<List<Coordinate<Integer>>, String> parseFunc;

  /** Sets the parse function that determines how Star results will be displayed to the user.
   @param parseFunc A Function that parses a List of Coordinate of Integer into the desired
   String representation.
   */
  public static void setParseFunc(Function<List<Coordinate<Integer>>, String> parseFunc) {
    NaiveCommandsHandler.parseFunc = parseFunc;
  }

  private static final Map<String, Function<List<Coordinate<Integer>>, String>> VALID_PARSERS
      = new HashMap<>() {{
            put("repl", StarsResultParsers::parseSearchResultToRepl);
            put("gui", StarsResultParsers::parseSearchResultToGui);
        }};

  /** Return a String error or computation outcome of the naive_neighbors command passed
   back to the REPL.
   @param command A String representing the full command entered.
   @param parseKey A String key representing the function which should parse
   any successful output into the desired format.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  public static String naiveNeighborsCommand(String command, String parseKey) {
    parseFunc = VALID_PARSERS.get(parseKey);
    String[] splitCommand = splitCommandComponents(command);
    try {
      return checkNaiveNeighborsArgs(splitCommand);
    } catch (InvalidParameterException e) {
      return ErrorMessages.STAR_NAME_NOT_IN_QUOTES;
    } catch (NumberFormatException e) {
      return ErrorMessages.INVALID_K;
    } catch (IllegalArgumentException e) {
      return ErrorMessages.NAIVE_NEIGHBORS_INVALID_NUMBER_ARGUMENTS;
    }
  }

  /** Return a String error or computation outcome of the naive_radius command passed
   back to the REPL.
   @param command A String representing the full command entered.
   @param parseKey A String key representing the function which should parse
   any successful output into the desired format.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  public static String naiveRadiusCommand(String command, String parseKey) {
    parseFunc = VALID_PARSERS.get(parseKey);
    String[] splitCommand = splitCommandComponents(command);
    try {
      return checkNaiveRadiusArgs(splitCommand);
    } catch (InvalidParameterException e) {
      return ErrorMessages.STAR_NAME_NOT_IN_QUOTES;
    } catch (NumberFormatException e) {
      return ErrorMessages.INVALID_R;
    } catch (IllegalArgumentException e) {
      return ErrorMessages.NAIVE_RADIUS_INVALID_NUMBER_ARGUMENTS;
    }
  }

  /** Checks whether the naive_neighbors command passed has valid arguments.
   @param splitCommand An Array of Strings representing each part of an entered
   command
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static String checkNaiveNeighborsArgs(String[] splitCommand) {
    if (splitCommand.length == 5) {
      int k = Integer.parseInt(splitCommand[1]);
      if (k < 0) {
        throw new NumberFormatException();
      }
      double x = Double.parseDouble(splitCommand[2]);
      double y = Double.parseDouble(splitCommand[3]);
      double z = Double.parseDouble(splitCommand[4]);
      return getClosestStars(k, x, y, z, NaiveCommandsHandler::getStarDistances,
          NaiveCommandsHandler::getNaiveNearestNeighborsResult);

    } else if (splitCommand.length == 3) {
      int k = Integer.parseInt(splitCommand[1]);
      if (k < 0) {
        throw new NumberFormatException();
      }
      String candidateName = splitCommand[2];
      if (candidateName.startsWith("\"") && candidateName.endsWith("\"")) {
        String name = candidateName.substring(1, candidateName.length() - 1);
        return getClosestStars(k, name, NaiveCommandsHandler::getStarDistances,
            NaiveCommandsHandler::getNaiveNearestNeighborsResult);
      } else {
        throw new InvalidParameterException();
      }
    } else {
      throw new IllegalArgumentException();
    }
  }

  /** Checks whether the naive_radius command passed has valid arguments.
   @param splitCommand An Array of Strings representing each part of an entered
   command
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static String checkNaiveRadiusArgs(String[] splitCommand) {
    if (splitCommand.length == 5) {
      double r = Double.parseDouble(splitCommand[1]);
      if (r < 0) {
        throw new NumberFormatException();
      }
      double x = Double.parseDouble(splitCommand[2]);
      double y = Double.parseDouble(splitCommand[3]);
      double z = Double.parseDouble(splitCommand[4]);
      return getClosestStars(r, x, y, z, NaiveCommandsHandler::getStarDistances,
          NaiveCommandsHandler::getNaiveRadiusSearchResult);

    } else if (splitCommand.length == 3) {
      double r = Double.parseDouble(splitCommand[1]);
      if (r < 0) {
        throw new NumberFormatException();
      }
      String candidateName = splitCommand[2];
      if (candidateName.startsWith("\"") && candidateName.endsWith("\"")) {
        String name = candidateName.substring(1, candidateName.length() - 1);
        return getClosestStars(r, name, NaiveCommandsHandler::getStarDistances,
            NaiveCommandsHandler::getNaiveRadiusSearchResult);
      } else {
        throw new InvalidParameterException();
      }
    } else {
      throw new IllegalArgumentException();
    }
  }

  /** Split the passed command at any spaces not included within a pair of quotes.
   @param command A String representing the full command entered.
   @return An ArrayList of Strings representing each part of the command.
   */
  static String[] splitCommandComponents(String command) {
    List<String> matchList = new ArrayList<>();
    Pattern regex = Pattern.compile("[^\\s\"']+|\"[^\"]*\"|'[^']*'");
    Matcher regexMatcher = regex.matcher(command);
    while (regexMatcher.find()) {
      matchList.add(regexMatcher.group());
    }
    return matchList.toArray(new String[0]);
  }

  /** Handle all errors that may be thrown from calling getStarDistances when
   creating an ArrayList of StarDistances.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param thresh An N threshold representing either k or r from naive_neighbors
   or naive_radius respectively.
   @param name A String representing the name of the Star.
   @param convert A function that converts the provided thresholds into a StarDistance or
   Coordinate depending on which function calls StarsCollection method.
   @param search A function that is either getNaiveNearestNeighborsResult or getNaiveRadiusResult
   depending on which command invokes StarsCollection method.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getClosestStars(N thresh, String name,
                            Utils.Function3To1<N,
                              String,
                              BiFunction<N, Map<Coordinate<Integer>, Double>, String>,
                              String> convert,
                            BiFunction<N, Map<Coordinate<Integer>, Double>, String>
                              search) {
    try {
      return convert.apply(thresh, name, search);
    } catch (IndexOutOfBoundsException e) {
      return ErrorMessages.STAR_NAME_NOT_FOUND;
    } catch (IllegalArgumentException e) {
      return ErrorMessages.EMPTY_STAR_NAME;
    } catch (NullPointerException e) {
      return ErrorMessages.NO_CSV_LOADED;
    }
  }

  /** Handle all errors that may be thrown from calling getStarDistances when
   creating an ArrayList of StarDistances.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param thresh An N threshold representing either k or r from naive_neighbors
   or naive_radius respectively.
   @param x A double representing the x-coordinate threshold.
   @param y A double representing the y-coordinate threshold.
   @param z A double representing the z-coordinate threshold.
   @param convert A function that converts the provided thresholds into a StarDistance or
   Coordinate depending on which function calls StarsCollection method.
   @param search A function that is either getNaiveNearestNeighborsResult or getNaiveRadiusResult
   depending on which command invokes StarsCollection method.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getClosestStars(N thresh, double x, double y, double z,
                            Utils.Function5To1<N,
                              Double, Double, Double,
                              BiFunction<N, Map<Coordinate<Integer>, Double>, String>,
                              String> convert,
                            BiFunction<N, Map<Coordinate<Integer>, Double>, String>
                              search) {
    try {
      return convert.apply(thresh, x, y, z, search);
    } catch (NullPointerException e) {
      return ErrorMessages.NO_CSV_LOADED;
    }
  }

  /** Create an ArrayList of StarDistances that contains all stars except the one
   with the passed name, then calls the passed function to get the stars with
   that satisfy the passed threshold.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param thresh An N threshold representing either k or r from naive_neighbors or naive_radius
   respectively.
   @param name A String containing the name of the Star on which the command is
   entered.
   @param func A function that is either getNaiveNearestNeighborsResult or
   getNaiveRadiusSearchResult.
   depending on which command invokes StarsCollection method.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getStarDistances(N thresh, String name,
                                 BiFunction<N, Map<Coordinate<Integer>, Double>, String>
                                     func) {
    if (name.equals("")) {
      throw new IllegalArgumentException();
    }
    int index = 0;
    // get index of star with 'name' if it exists
    while (!StarsCommandHandler.getStarsList().get(index).getName().equals(name)) {
      index++;
    }

    List<Star> starsList = StarsCommandHandler.getStarsList();

    double x = starsList.get(index).getX();
    double y = starsList.get(index).getY();
    double z = starsList.get(index).getZ();

    Map<Coordinate<Integer>, Double> starsDistances = new HashMap<>();

    double distance;
    for (Star starProperty : starsList) {
      // do not include the star with 'name' in the list of StarDistance
      if (!starProperty.getName().equals(name)) {
        distance = Math.sqrt(Math.pow(starProperty.getX() - x, 2)
            + Math.pow(starProperty.getY() - y, 2)
            + Math.pow(starProperty.getZ() - z, 2));

        starsDistances.put(starProperty, distance);
      }
    }
    return func.apply(thresh, starsDistances);
  }

  /** Create an ArrayList of StarDistances that contains all stars except the one
   with the passed name, then calls the passed function to get the stars with
   that satisfy the passed threshold.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param thresh An N threshold representing either k or r from naive_neighbors or naive_radius
   respectively.
   @param x A double representing the x-coordinate threshold.
   @param y A double representing the y-coordinate threshold.
   @param z A double representing the z-coordinate threshold.
   @param func A function that is either getNearestNeighborsResult or getNaiveRadiusResult
   depending on which command invokes StarsCollection method.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getStarDistances(N thresh, double x, double y, double z,
                                 BiFunction<N, Map<Coordinate<Integer>, Double>, String>
                                     func) {
    Map<Coordinate<Integer>, Double> starsDistances = new HashMap<>();

    double distance;
    for (Star starProperty : StarsCommandHandler.getStarsList()) {
      distance = Math.sqrt(Math.pow(starProperty.getX() - x, 2)
          + Math.pow(starProperty.getY() - y, 2)
          + Math.pow(starProperty.getZ() - z, 2));

      starsDistances.put(starProperty, distance);
    }
    return func.apply(thresh, starsDistances);
  }

  /** Get the first few stars within the passed threshold with the least distances,
   randomizing the order of any with tied distances.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param k An N threshold representing the maximum number of stars that may be
   printed to console in the event of a successful command execution.
   @param starsDistances An ArrayList of Strings that contains all stars and their
   distances in ascending order of their distances.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getNaiveNearestNeighborsResult(
          N k, Map<Coordinate<Integer>, Double> starsDistances) {
    int kCast = (int) k;

    ListNaiveSearch<Integer, Coordinate<Integer>> kNN = new ListNaiveSearch<>(starsDistances);

    List<Coordinate<Integer>> nearestStars = kNN.getNaiveNearestNeighbors(kCast);

    return parseFunc.apply(nearestStars);
  }


  /** Get all possible stars close lying within or on the passed threshold,
   randomizing the order of any with tied distances.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param r An N threshold representing the maximum radial distance, inclusive,
   of stars that may be printed to console.
   @param starsDistances An ArrayList of Strings that contains all stars and their
   distances in ascending order of their distances.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getNaiveRadiusSearchResult(N r,
                                  Map<Coordinate<Integer>, Double> starsDistances) {

    double rCast = r.doubleValue();

    Map<Coordinate<Integer>, Double> sortedStarDistances = starsDistances.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
              (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    ListNaiveSearch<Integer, Coordinate<Integer>> rad = new ListNaiveSearch<>(sortedStarDistances);

    List<Coordinate<Integer>> nearestStars = rad.getNaiveRadiusSearchResult(rCast);

    return parseFunc.apply(nearestStars);
  }
}
