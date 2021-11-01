package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars;


import edu.brown.cs.psekhsar_sdiwan2.coordinates.Coordinate;
import edu.brown.cs.psekhsar_sdiwan2.coordinates.KdTree;
import edu.brown.cs.psekhsar_sdiwan2.stars.Star;
import edu.brown.cs.psekhsar_sdiwan2.main.ErrorMessages;
import edu.brown.cs.psekhsar_sdiwan2.utils.Utils;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Class that checks and executes stars, naive_neighbors and naive_radius commands
 by loading CSV files and extracting necessary data from aforementioned files.
 */
public final class KdTreeCommandsHandler {
  private KdTreeCommandsHandler() {
  }

  private static Function<List<Coordinate<Integer>>, String> parseFunc;

  /** Sets the parse function that determines how Star results will be displayed to the user.
   @param parseFunc A Function that parses a List of Coordinate of Integer into the desired
   String representation.
   */
  public static void setParseFunc(Function<List<Coordinate<Integer>>, String> parseFunc) {
    KdTreeCommandsHandler.parseFunc = parseFunc;
  }

  private static final Map<String, Function<List<Coordinate<Integer>>, String>> VALID_PARSERS
      = new HashMap<>() {{
          put("repl", StarsResultParsers::parseSearchResultToRepl);
          put("gui", StarsResultParsers::parseSearchResultToGui);
        }};

  /** Return a String error or computation outcome of the neighbors command passed
   back to the REPL.
   @param command A String representing the full command entered.
   @param parseKey A String key representing the function which should parse
   any successful output into the desired format.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  public static String neighborsCommand(String command, String parseKey) {
    parseFunc = VALID_PARSERS.get(parseKey);
    String[] splitCommand = splitCommandComponents(command);
    try {
      return checkNeighborsArgs(splitCommand);
    } catch (InvalidParameterException e) {
      return ErrorMessages.STAR_NAME_NOT_IN_QUOTES;
    } catch (NumberFormatException e) {
      return ErrorMessages.INVALID_K;
    } catch (IllegalArgumentException e) {
      return ErrorMessages.NEIGHBORS_INVALID_NUMBER_ARGUMENTS;
    }
  }

  /** Return a String error or computation outcome of the radius command passed
   back to the REPL.
   @param command A String representing the full command entered.
   @param parseKey A String key representing the function which should parse
   any successful output into the desired format.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  public static String radiusCommand(String command, String parseKey) {
    parseFunc = VALID_PARSERS.get(parseKey);
    String[] splitCommand = splitCommandComponents(command);
    try {
      return checkRadiusArgs(splitCommand);
    } catch (InvalidParameterException e) {
      return ErrorMessages.STAR_NAME_NOT_IN_QUOTES;
    } catch (NumberFormatException e) {
      return ErrorMessages.INVALID_R;
    } catch (IllegalArgumentException e) {
      return ErrorMessages.RADIUS_INVALID_NUMBER_ARGUMENTS;
    }
  }

  /** Checks whether the neighbors command passed has valid arguments.
   @param splitCommand An Array of Strings representing each part of an entered
   command
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static String checkNeighborsArgs(String[] splitCommand) {
    if (splitCommand.length == 5) {
      int k = Integer.parseInt(splitCommand[1]);
      if (k < 0) {
        throw new NumberFormatException();
      }
      double x = Double.parseDouble(splitCommand[2]);
      double y = Double.parseDouble(splitCommand[3]);
      double z = Double.parseDouble(splitCommand[4]);
      return getClosestStarsCoordinate(k, x, y, z, KdTreeCommandsHandler::getTargetCoordinate,
          KdTreeCommandsHandler::getNearestNeighborsResult);

    } else if (splitCommand.length == 3) {
      int k = Integer.parseInt(splitCommand[1]);
      if (k < 0) {
        throw new NumberFormatException();
      }
      String candidateName = splitCommand[2];
      if (candidateName.startsWith("\"") && candidateName.endsWith("\"")) {
        String name = candidateName.substring(1, candidateName.length() - 1);
        return getClosestStarsCoordinate(k, name, KdTreeCommandsHandler::getTargetCoordinate,
            KdTreeCommandsHandler::getNearestNeighborsResult);
      } else {
        throw new InvalidParameterException();
      }
    } else {
      throw new IllegalArgumentException();
    }
  }

  /** Checks whether the radius command passed has valid arguments.
   @param splitCommand An Array of Strings representing each part of an entered
   command
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static String checkRadiusArgs(String[] splitCommand) {
    if (splitCommand.length == 5) {
      double r = Double.parseDouble(splitCommand[1]);
      if (r < 0) {
        throw new NumberFormatException();
      }
      double x = Double.parseDouble(splitCommand[2]);
      double y = Double.parseDouble(splitCommand[3]);
      double z = Double.parseDouble(splitCommand[4]);
      return getClosestStarsCoordinate(r, x, y, z, KdTreeCommandsHandler::getTargetCoordinate,
          KdTreeCommandsHandler::getRadiusSearchResult);

    } else if (splitCommand.length == 3) {
      double r = Double.parseDouble(splitCommand[1]);
      if (r < 0) {
        throw new NumberFormatException();
      }
      String candidateName = splitCommand[2];
      if (candidateName.startsWith("\"") && candidateName.endsWith("\"")) {
        String name = candidateName.substring(1, candidateName.length() - 1);
        return getClosestStarsCoordinate(r, name, KdTreeCommandsHandler::getTargetCoordinate,
            KdTreeCommandsHandler::getRadiusSearchResult);
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

  /** Handle all errors that may be thrown from calling getTargetCoordinate when
   creating the target Star.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param thresh An N threshold representing either k or r from naive_neighbors
   or naive_radius respectively.
   @param name A String representing the name of the Star.
   @param convert A function that converts the provided thresholds into a StarDistance or
   Coordinate depending on which function calls StarsCollection method.
   @param search A function that is either getNearestNeighborsResult or getRadiusResult
   depending on which command invokes StarsCollection method.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getClosestStarsCoordinate(N thresh, String name,
                                                                    Utils.Function3To1<N,
                                                                      String,
                                                                      Utils.Function3To1<N, Star,
                                                                        Boolean, String>,
                                                                      String> convert,
                                                                    Utils.Function3To1<N, Star,
                                                                      Boolean, String> search) {
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

  /** Handle all errors that may be thrown from calling getTargetCoordinate when
   creating the target Star.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param thresh An N threshold representing either k or r from naive_neighbors
   or naive_radius respectively.
   @param x A double representing the x-coordinate threshold.
   @param y A double representing the y-coordinate threshold.
   @param z A double representing the z-coordinate threshold.
   @param convert A function that converts the provided thresholds into a StarDistance or
   Coordinate depending on which function calls StarsCollection method.
   @param search A function that is either getNearestNeighborsResult or getRadiusResult
   depending on which command invokes StarsCollection method.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getClosestStarsCoordinate(N thresh,
                                                                    double x,
                                                                    double y,
                                                                    double z,
                                                                    Utils.Function5To1<N,
                                                                      Double, Double, Double,
                                                                      Utils.Function3To1<N, Star,
                                                                        Boolean, String>,
                                                                      String> convert,
                                                                    Utils.Function3To1<N, Star,
                                                                      Boolean, String> search) {
    try {
      return convert.apply(thresh, x, y, z, search);
    } catch (NullPointerException e) {
      return ErrorMessages.NO_CSV_LOADED;
    }
  }

  /** Create a Coordinate representing the Star from its passed name, and using that
   perform the Search Algorithm specified through the function passed.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param thresh An N threshold representing either k or r from naive_neighbors or naive_radius
   respectively.
   @param name A String representing the name of the target Star.
   @param search A function that is either getNearestNeighborsResult or getRadiusResult
   depending on which command invokes StarsCollection method.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getTargetCoordinate(N thresh, String name,
                                                              Utils.Function3To1<N,
                                                                Star,
                                                                Boolean,
                                                                String> search) {
    if (name.equals("")) {
      throw new IllegalArgumentException();
    }
    int index = 0;
    // get index of star with 'name' if it exists
    while (!StarsCommandHandler.getStarsList().get(index).getName().equals(name)) {
      index++;
    }

    Star star = StarsCommandHandler.getStarsList().get(index);

    return search.apply(thresh, star, true);

  }

  /** Create a Coordinate representing the Star from the passed coordinate values
   to perform the Search Algorithm specified through the function passed.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param thresh An N threshold representing either k or r from naive_neighbors or naive_radius
   respectively.
   @param x A Double representing the x-coordinate of the target Coordinate.
   @param y A Double representing the y-coordinate of the target Coordinate.
   @param z A Double representing the z-coordinate of the target Coordinate.
   @param search A function that is either getNearestNeighborsResult or getRadiusResult
   depending on which command invokes StarsCollection method.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getTargetCoordinate(N thresh,
                                                              double x,
                                                              double y,
                                                              double z,
                                                              Utils.Function3To1<N,
                                                                Star,
                                                                Boolean,
                                                                String> search) {
    Star star = new Star(-1, "target", x, y, z);
    return search.apply(thresh, star, false);
  }

  /** Get the first few IDs of KeyDistances within the passed threshold with the least distances,
   randomizing the order of any with tied distances, by using a Nearest Neighbors Algorithm.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param k An N threshold representing the maximum number of IDs that can be
   returned.
   @param targetPoint A Coordinate of type specified when constructing the KdTreeSearch
   representing the point around which all distances will be calculated.
   @param excludeTarget A Boolean representing whether the targetPoint should be included
   or excluded within the List of IDs returned
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getNearestNeighborsResult(N k, Star targetPoint,
                                                                    boolean excludeTarget) {
    int kCast = (int) k;

    KdTree<Integer, Coordinate<Integer>> tree = StarsCommandHandler.getLoadedTree();

    List<Coordinate<Integer>> kNearestNeighbors
        = tree.getNearestNeighborsResult(kCast, targetPoint, excludeTarget);

    return parseFunc.apply(kNearestNeighbors);
  }

  /** Get all IDs of KeyDistances within the passed threshold by using a Radius Search
   Algorithm.
   @param <N> A parametric type that represents any Number, here
   int for k and double for r.
   @param r An N threshold representing the maximum radius within which IDs of valid points
   may be returned.
   @param targetPoint A Coordinate of type specified when constructing the KdTreeSearch
   representing the point around which all distances will be calculated.
   @param excludeTarget A Boolean representing whether the targetPoint should be included
   or excluded within the List of IDs returned
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static <N extends Number> String getRadiusSearchResult(N r,
                                                                Star targetPoint,
                                                                Boolean excludeTarget) {
    double rCast = (double) r;

    KdTree<Integer, Coordinate<Integer>> tree = StarsCommandHandler.getLoadedTree();

    List<Coordinate<Integer>> kNearestNeighbors
              = tree.getRadiusSearchResult(rCast, targetPoint, excludeTarget);

    return parseFunc.apply(kNearestNeighbors);
  }
}
