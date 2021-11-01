package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars;

import edu.brown.cs.psekhsar_sdiwan2.coordinates.Coordinate;
import edu.brown.cs.psekhsar_sdiwan2.stars.Star;

import java.util.ArrayList;
import java.util.List;

/**
 * The StarsResultParsers class manages all parses that may be called on
 * Lists of Coordinates of Integers i.e. Stars to display the results to the user.
 *
 */
public final class StarsResultParsers {
  private StarsResultParsers() {
  }

  /** Parses the passed Coordinates into a display String using ID.
   @param result A List of Coordinate of Integer, for instance, a List of Stars.
   @return A String concatenating the IDs of each Coordinate.
   */
  public static String parseSearchResultToRepl(List<Coordinate<Integer>> result) {
    List<String> allIDs = new ArrayList<>();

    for (Coordinate<Integer> s : result) {
      allIDs.add(s.getId() + "");
    }

    return String.join("\n", allIDs);
  }

  /** Parses the passed Coordinates into a display String using ID, Name and Position.
   @param result A List of Coordinate of Integer, for instance, a List of Stars.
   @return A String concatenating the IDs, Name and Position of each Coordinate.
   */
  public static String parseSearchResultToGui(List<Coordinate<Integer>> result) {
    List<String> allStarComponents = new ArrayList<>();
    allStarComponents.add(
        "<table style=\"width:100%\">"
            + "  <tr>"
            + "    <th>Star ID</th>"
            + "    <th>Star Name</th>"
            + "    <th>Position</th>"
            + "  </tr>");

    for (Coordinate<Integer> c : result) {
      Star s = (Star) c;
      allStarComponents.add(
          "  <tr>"
              + "    <td>" + s.getId() + "</td>"
              + "    <td>" + s.getName() + "</td>"
              + "    <td>(" + s.getX() + ", " + s.getY() + ", " + s.getZ() + ")</td>"
              + "  </tr>");
    }

    allStarComponents.add("</table>");

    return String.join("", allStarComponents);
  }
}
