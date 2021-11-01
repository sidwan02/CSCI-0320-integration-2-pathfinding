package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class KdTreeCommandsHandlerTest {
  @Before
  public void setUp() {
    KdTreeCommandsHandler.setParseFunc(StarsResultParsers::parseSearchResultToRepl);
  }

  @Test
  public void testCheckNeighborsCommand_5Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 0 0 0", "repl"),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNeighborsCommand_5Args_ValidCommand_LoadedFile() {
    // valid command + loaded file


    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 0 0 0", "repl"),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666");
  }

  @Test
  public void testCheckNeighborsCommand_5Args_InvalidCommand() {
    // invalid command


    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 0", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 0 0 0 0", "repl"),
        "ERROR: Invalid no.of arguments passed for neighbors");

  }

  @Test
  public void testCheckNeighborsCommand_5Args_InvalidK() {
    // invalid value for k


    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors -5 0 0 0", "repl"),
        "ERROR: k must be a non-negative integer");

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 0.1 0 0 0", "repl"),
        "ERROR: k must be a non-negative integer");
  }

  @Test
  public void CheckNeighborsCommand_3Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 \"Sol\"", "repl"),
        "ERROR: No csv loaded");
  }

  @Test
  public void CheckNeighborsCommand_3Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 \"Sol\"", "repl"),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void CheckNeighborsCommand_3Args_NameNotInQuotes() {
    // star name not in quotes
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 Sol", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 \"Sol", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 Sol\"", "repl"),
        "ERROR: Star name must be within \"\"");
  }

  @Test
  public void CheckNeighborsCommand_3Args_InvalidK() {
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    // invalid value for k
    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 0.1 \"Sol\"", "repl"),
        "ERROR: k must be a non-negative integer");

    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 0.1 \"Sol\"", "repl"),
        "ERROR: k must be a non-negative integer");
  }

  @Test
  public void CheckNeighborsCommand_3Args_NameNotInCSV() {
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    // star name not in csv
    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 \"sol\"", "repl"),
        "ERROR: Star name not found in loaded csv");
  }

  @Test
  public void CheckNeighborsCommand_3Args_EmptyName() {
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    // star name is empty
    assertEquals(KdTreeCommandsHandler.neighborsCommand(
        "neighbors 5 \"\"", "repl"),
        "ERROR: Star name cannot be empty");
  }

  @Test
  public void testCheckRadiusCommand_5Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 0 0 0", "repl"),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckRadiusCommand_5Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 0 0 0", "repl"),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5.121 \"Sol\"", "repl"),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckRadiusCommand_5Args_InvalidCommand() {
    // invalid command
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 0", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 0 0 0 0", "repl"),
        "ERROR: Invalid no.of arguments passed for radius");
  }

  @Test
  public void testCheckRadiusCommand_5Args_InvalidR() {
    // invalid value for r
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius -5 0 0 0", "repl"),
        "ERROR: r must be a non-negative real number");
  }

  @Test
  public void testCheckRadiusCommand_3Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 \"Sol\"", "repl"),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckRadiusCommand_3Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 \"Sol\"", "repl"),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5.121 \"Sol\"", "repl"),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckRadiusCommand_3Args_NameNotInQuotes() {
    // star name not in quotes
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 Sol", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 \"Sol", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 Sol\"", "repl"),
        "ERROR: Star name must be within \"\"");
  }

  @Test
  public void testCheckRadiusCommand_3Args_InvalidR() {
    // invalid value for r
    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius -5 \"Sol\"", "repl"),
        "ERROR: r must be a non-negative real number");
  }

  @Test
  public void testCheckRadiusCommand_3Args_NameNotInCSV() {
    // star name not in csv
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 \"sol\"", "repl"),
        "ERROR: Star name not found in loaded csv");
  }

  @Test
  public void testCheckRadiusCommand_3Args_EmptyName() {
    // star name is empty
    assertEquals(KdTreeCommandsHandler.radiusCommand(
        "radius 5 \"\"", "repl"),
        "ERROR: Star name cannot be empty");
  }

  @Test
  public void testCheckNeighbors_5Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "0", "0", "0"}),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNeighbors_5Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "0", "0", "0"}),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666");
  }

  @Test
  public void testCheckNeighbors_5Args_InvalidCommand() {
    // invalid command
    assertThrows(IllegalArgumentException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "0"}));

    assertThrows(IllegalArgumentException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "0", "0", "0", "0"}));
  }

  @Test
  public void testCheckNeighbors_5Args_InvalidK() {
    // invalid value for k
    assertThrows(NumberFormatException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "-5", "0", "0", "0"}));

    assertThrows(NumberFormatException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "0.1", "0", "0", "0"}));
  }

  @Test
  public void testCheckNeighbors_3Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "\"Sol\""}),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNeighbors_3Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "\"Sol\""}),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckNeighbors_3Args_NameNotInQuotes() {
    // star name not in quotes
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(InvalidParameterException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "Sol"}));

    assertThrows(InvalidParameterException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "\"Sol"}));

    assertThrows(InvalidParameterException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "Sol\""}));
  }

  @Test
  public void testCheckNeighbors_3Args_InvalidR() {
    // invalid value for r
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(NumberFormatException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "-5", "\"Sol\""}));

    assertThrows(NumberFormatException.class, () -> KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "0.1", "\"Sol\""}));
  }

  @Test
  public void testCheckNeighbors_3Args_NameNotInCSV() {
    // star name not in csv
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "\"sol\""}),
        "ERROR: Star name not found in loaded csv");
  }

  @Test
  public void testCheckNeighbors_3Args_EmptyName() {
    // star name is empty
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.checkNeighborsArgs(
        new String[]{"neighbors", "5", "\"\""}),
        "ERROR: Star name cannot be empty");
  }

  @Test
  public void testCheckRadius_5Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "0", "0", "0"}),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckRadius_5Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "0", "0", "0"}),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");

    assertEquals(KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5.121", "0", "0", "0"}),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckRadius_5Args_InvalidCommand() {
    // invalid command
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(IllegalArgumentException.class, () -> KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "0"}));

    assertThrows(IllegalArgumentException.class, () -> KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "0", "0", "0", "0"}));
  }

  @Test
  public void testCheckRadius_5Args_InvalidR() {
    // invalid value for r
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(NumberFormatException.class, () -> KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "-5", "0", "0", "0"}));
  }

  @Test
  public void testCheckRadius_3Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "\"Sol\""}),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckRadius_3Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "\"Sol\""}),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");

    assertEquals(KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5.121", "\"Sol\""}),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckRadius_3Args_NameNotInQuotes() {
    // star name not in quotes
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(InvalidParameterException.class, () -> KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "Sol"}));

    assertThrows(InvalidParameterException.class, () -> KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "\"Sol"}));

    assertThrows(InvalidParameterException.class, () -> KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "Sol\""}));
  }

  @Test
  public void testCheckRadius_3Args_InvalidR() {
    // invalid value for r
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertThrows(NumberFormatException.class, () -> KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "-5", "\"Sol\""}));
  }

  @Test
  public void testCheckRadius_3Args_NameNotInCSV() {
    // star name not in csv
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "\"sol\""}),
        "ERROR: Star name not found in loaded csv");
  }

  @Test
  public void testCheckRadius_3Args_EmptyName() {
    // star name is empty
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.checkRadiusArgs(
        new String[]{"radius", "5", "\"\""}),
        "ERROR: Star name cannot be empty");
  }

  @Test
  public void testGetTargetCoordinate_5Args_ThroughNeighbors() {
    // through neighbors
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.getTargetCoordinate(5, 0, 0, 0,
        KdTreeCommandsHandler::getNearestNeighborsResult),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666");
  }

  @Test
  public void testGetTargetCoordinate_5Args_ThroughRadius() {
    // through radius
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.getTargetCoordinate(5.0, 0, 0, 0,
        KdTreeCommandsHandler::getRadiusSearchResult),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testGetTargetCoordinate_5Args_NullStarsList() {
    // null starsList
    StarsCommandHandler.clearLoadedData();

    assertThrows(NullPointerException.class, () -> KdTreeCommandsHandler.getTargetCoordinate(5.0, 0, 0, 0,
        KdTreeCommandsHandler::getRadiusSearchResult));
  }

  @Test
  public void testGetTargetCoordinate_3Args_ThroughNeighbors() {
    // through neighbors
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(KdTreeCommandsHandler.getTargetCoordinate(5, "Sol",
        KdTreeCommandsHandler::getNearestNeighborsResult),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testGetTargetCoordinate_3Args_ThroughRadius() {
    // through radius
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(KdTreeCommandsHandler.getTargetCoordinate(5.0, "Sol",
        KdTreeCommandsHandler::getRadiusSearchResult),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testGetTargetCoordinate_3Args_NullStarsList() {
    // null starsList
    StarsCommandHandler.clearLoadedData();

    assertThrows(NullPointerException.class, () -> KdTreeCommandsHandler.getTargetCoordinate(5, "Sol",
        KdTreeCommandsHandler::getRadiusSearchResult));
  }

  @Test
  public void testGetTargetCoordinate_3Args_InvalidStar() {
    // invalid star
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertThrows(IndexOutOfBoundsException.class, () -> KdTreeCommandsHandler.getTargetCoordinate(5, "sol",
        KdTreeCommandsHandler::getRadiusSearchResult));

    assertThrows(IllegalArgumentException.class, () -> KdTreeCommandsHandler.getTargetCoordinate(5, "",
        KdTreeCommandsHandler::getRadiusSearchResult));
  }
}