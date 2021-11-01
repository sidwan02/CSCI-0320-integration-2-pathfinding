package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.psekhsar_sdiwan2.pathfinding.GraticuleEdge;
import edu.brown.cs.psekhsar_sdiwan2.main.ErrorMessages;

import java.awt.geom.IllegalPathStateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/** Class that checks and executes the way comand.
 */
public final class WaysCommandHandler {
  private WaysCommandHandler() {
  }

  private static Function<List<GraticuleEdge>, String> parseFunc;

  private static final Map<String, Function<List<GraticuleEdge>, String>> VALID_PARSERS
      = new HashMap<>() {{
          put("repl", WaysCommandHandler::parseToRepl); }};

  /** Return a String error or computation outcome of the mock command passed
   back to the REPL.
   @param command A String representing the full command entered.
   @param parseKey A String key representing the function which should parse
   any successful output into the desired format.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  public static String waysCommand(String command, String parseKey) {
    parseFunc = VALID_PARSERS.get(parseKey);
    String[] splitCommand = command.split(" ");
    try {
      return checkWaysArgs(splitCommand);
    }  catch (NumberFormatException e) {
      return ErrorMessages.NON_NUMBER_LAT_AND_LON;
    } catch (IllegalArgumentException e) {
      return ErrorMessages.WAYS_INVALID_NUMBER_ARGUMENTS;
    }
  }

  /** Checks whether the ways command passed has valid arguments.
   @param splitCommand An Array of Strings representing each part of an entered
   command
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static String checkWaysArgs(String[] splitCommand) {
    if (splitCommand.length == 5) {
      double lat1 = Double.parseDouble(splitCommand[1]);
      double lon1 = Double.parseDouble(splitCommand[2]);
      double lat2 = Double.parseDouble(splitCommand[3]);
      double lon2 = Double.parseDouble(splitCommand[4]);
      return handleWaysWithinBox(lat1, lon1, lat2, lon2);
    } else {
      throw new IllegalArgumentException();
    }
  }

  /** Wrapper for getAllWays; parses the List of found edges to REPL.
   * @param lat1 is a double that represents a latitude
   * @param lon1 is a double that represents a longitude
   * @param lat2 is a double that represents a latitude
   * @param lon2 is a double that represents a longitude
   * @return a String that represents the found GraticuleEdge between coordinates
   */
  static String handleWaysWithinBox(double lat1, double lon1, double lat2, double lon2) {
    try {
      if (!(lat1 >= lat2 && lon1 <= lon2)) {
        throw new IllegalPathStateException();
      }
      List<GraticuleEdge> allGraticuleEdges = getAllWays(lat1, lon1, lat2, lon2);
      return parseFunc.apply(allGraticuleEdges);
    } catch (SQLException e) {
      return ErrorMessages.INVALID_SQL_QUERY;
    } catch (NullPointerException e) {
      return ErrorMessages.NO_DATABASE_LOADED;
    } catch (IllegalPathStateException e) {
      return ErrorMessages.INVALID_BOUNDING_BOX;
    }
  }

  /** Return all the ways between lat1, lon1 and lat2, lon2.
   * @param lat1 is a double that represents a latitude
   * @param lon1 is a double that represents a longitude
   * @param lat2 is a double that represents a latitude
   * @param lon2 is a double that represents a longitude
   * @return a list of GraticuleEdges that represent all the ways between lat1, lon2,
   * and lat2, lon2
   * @throws SQLException if SQL fails to execute commands
   * @throws NullPointerException if no database has been loaded.
   */
  static List<GraticuleEdge> getAllWays(double lat1, double lon1, double lat2, double lon2)
    throws SQLException, NullPointerException {
    return MapDBResultSetHandler.queryDBWays(
      "SELECT way.id AS wayID, way.name, way.type, way.start, way.end,\n"
        + "N1.latitude as lat1, N1.longitude as lon1,\n"
        + "N2.latitude as lat2, N2.longitude as lon2\n"
        + "FROM way\n"
        + "INNER JOIN node as N1\n"
        + "INNER JOIN node as N2\n"
        + "ON (way.start=N1.id) AND (way.end=N2.id)" + "\n"
        + "WHERE lat1 BETWEEN " + lat2 + " AND " + lat1
        + " AND lon1 BETWEEN " + lon1 + " AND " + lon2 + "\n"
        + "OR lat2 BETWEEN " + lat2 + " AND " + lat1
        + " AND lon2 BETWEEN " + lon1 + " AND " + lon2 + "\n"
        + "ORDER BY wayID ASC"
        + ";"
    );
  }

  /** Parses a list of graticuleEdges to REPL.
   * @param graticuleEdges is a list of edges between certain coordinates
   * @return a String that represents graticuleEdges
   */
  static String parseToRepl(List<GraticuleEdge> graticuleEdges) {
    List<String> wayIDs = new ArrayList<>();

    for (GraticuleEdge w : graticuleEdges) {
      wayIDs.add(w.getId() + "");
    }

    return String.join("\n", wayIDs);
  }

  /**
   * Return all the ways between (lat1, lon1) and (lat2, lon2)
   * or an informative error message if this could not be done
   * in a format compatible with the front-end.
   *
   * @param lat1 - a double that represents a latitude
   * @param lon1 - a double that represents a longitude
   * @param lat2 - a double that represents a latitude
   * @param lon2 - a double that represents a longitude
   * @return a map in a format compatible with the front-end
   * representing the required ways or an error message if this could
   * not be done
   */
  public static Map<String, Object> handleWaysWithinBoxGui(
          double lat1, double lon1, double lat2, double lon2) {
    try {
      if (!(lat1 >= lat2 && lon1 <= lon2)) {
        throw new IllegalPathStateException();
      }
      List<GraticuleEdge> allGraticuleEdges = getAllWays(lat1, lon1, lat2, lon2);
      return parseToGui(allGraticuleEdges);
    } catch (SQLException e) {
      return ImmutableMap.of(
        "map", "",
        "route", "",
        "ways", "",
        "nearest", "",
        "error",
        "ERROR: SQL failure executing ways");
    } catch (NullPointerException e) {
      return ImmutableMap.of(
        "map", "",
        "route", "",
        "ways", "",
        "nearest", "",
        "error", "ERROR: No DB loaded");
    } catch (IllegalPathStateException e) {
      return ImmutableMap.of(
        "map", "",
        "route", "",
        "ways", "",
        "nearest", "",
        "error", "ERROR: Coordinates not northwest & southeast");
    }
  }

  /**
   * Return all the ways in the given graticuleEdges list
   * in a format compatible with the front-end.
   *
   * @param graticuleEdges - a list of GraticuleEdges representing
   *                       ways within a particular bounding box.
   * @return a map in a format compatible with the front-end
   * representing the required ways
   */
  static Map<String, Object> parseToGui(List<GraticuleEdge> graticuleEdges) {
    List<String[]> waysAndNodes = new ArrayList<>();
    for (GraticuleEdge w : graticuleEdges) {
      waysAndNodes.add(new String[]{w.getId() + "", w.getName(), w.getType(),
              w.getStartNode().getId() + "", w.getStartNode().getLatitude() + "",
              w.getStartNode().getLongitude() + "",
              w.getEndNode().getId() + "", w.getEndNode().getLatitude() + "",
              w.getEndNode().getLongitude() + ""
      });
    }

    return ImmutableMap.of(
      "map", "",
      "route", "",
      "ways", waysAndNodes,
      "nearest", "",
      "error", "");
  }
}
