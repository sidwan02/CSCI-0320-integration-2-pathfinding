package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars;

import edu.brown.cs.psekhsar_sdiwan2.coordinates.Coordinate;
import edu.brown.cs.psekhsar_sdiwan2.stars.Star;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StarsResultParsersTest {
  @Test
  public void testParseSearchResultToRepl() {
    List<Coordinate<Integer>> stars = new ArrayList<>();
    stars.add(new Star(1, "star1", 1, 3, 3));
    stars.add(new Star(2, "star2", 10, 31, 0.3));
    stars.add(new Star(3, "star3", 1, 1, 3));

    assertEquals(StarsResultParsers.parseSearchResultToRepl(stars),
        "1\n2\n3");
  }

  @Test
  public void testParseSearchResultToGui() {
    List<Coordinate<Integer>> stars = new ArrayList<>();
    stars.add(new Star(1, "star1", 1, 3, 3));
    stars.add(new Star(2, "star2", 10, 31, 0.3));
    stars.add(new Star(3, "star3", 1, 1, 3));

    assertEquals(StarsResultParsers.parseSearchResultToGui(stars),
        "<table style=\"width:100%\">  " +
            "<tr>    " +
            "<th>Star ID</th>    " +
            "<th>Star Name</th>    " +
            "<th>Position</th>  " +
            "</tr>  " +
            "" +
            "<tr>    " +
            "<td>1</td>    " +
            "<td>star1</td>    " +
            "<td>(1.0, 3.0, 3.0)</td>  " +
            "</tr>  " +
            "" +
            "<tr>    " +
            "<td>2</td>    " +
            "<td>star2</td>    " +
            "<td>(10.0, 31.0, 0.3)</td>  " +
            "</tr>  " +
            "" +
            "<tr>    " +
            "<td>3</td>    " +
            "<td>star3</td>    " +
            "<td>(1.0, 1.0, 3.0)</td>  " +
            "</tr></table>");
  }
}
