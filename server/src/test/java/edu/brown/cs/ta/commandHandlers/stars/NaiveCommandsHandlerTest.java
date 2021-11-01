package edu.brown.cs.ta.commandHandlers.stars;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;

import static org.junit.Assert.*;

public class NaiveCommandsHandlerTest {
  @Before
  public void setUp() {
    NaiveCommandsHandler.setParseFunc(StarsResultParsers::parseSearchResultToRepl);
  }

  @Test
  public void testCheckNaiveNeighborsCommand_5Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 0 0 0", "repl"),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNaiveNeighborsCommand_5Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 0 0 0", "repl"),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666");
  }

  @Test
  public void testCheckNaiveNeighborsCommand_5Args_InvalidCommand() {
    // invalid command
    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 0", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 0 0 0 0", "repl"),
        "ERROR: Invalid no.of arguments passed for naive_neighbors");

  }

  @Test
  public void testCheckNaiveNeighborsCommand_5Args_InvalidK() {
    // invalid value for k
    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors -5 0 0 0", "repl"),
        "ERROR: k must be a non-negative integer");

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 0.1 0 0 0", "repl"),
        "ERROR: k must be a non-negative integer");
  }

  @Test
  public void CheckNaiveNeighborsCommand_3Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 \"Sol\"", "repl"),
        "ERROR: No csv loaded");
  }

  @Test
  public void CheckNaiveNeighborsCommand_3Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 \"Sol\"", "repl"),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void CheckNaiveNeighborsCommand_3Args_NameNotInQuotes() {
    // star name not in quotes
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 Sol", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 \"Sol", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 Sol\"", "repl"),
        "ERROR: Star name must be within \"\"");
  }

  @Test
  public void CheckNaiveNeighborsCommand_3Args_InvalidK() {
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    // invalid value for k
    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 0.1 \"Sol\"", "repl"),
        "ERROR: k must be a non-negative integer");

    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 0.1 \"Sol\"", "repl"),
        "ERROR: k must be a non-negative integer");
  }

  @Test
  public void CheckNaiveNeighborsCommand_3Args_NameNotInCSV() {
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    // star name not in csv
    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 \"sol\"", "repl"),
        "ERROR: Star name not found in loaded csv");
  }

  @Test
  public void CheckNaiveNeighborsCommand_3Args_EmptyName() {
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    // star name is empty
    assertEquals(NaiveCommandsHandler.naiveNeighborsCommand(
        "naive_neighbors 5 \"\"", "repl"),
        "ERROR: Star name cannot be empty");
  }

  @Test
  public void testCheckNaiveRadiusCommand_5Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 0 0 0", "repl"),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNaiveRadiusCommand_5Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 0 0 0", "repl"),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5.121 \"Sol\"", "repl"),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckNaiveRadiusCommand_5Args_InvalidCommand() {
    // invalid command
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 0", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 0 0 0 0", "repl"),
        "ERROR: Invalid no.of arguments passed for naive_radius");
  }

  @Test
  public void testCheckNaiveRadiusCommand_5Args_InvalidR() {
    // invalid value for r
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius -5 0 0 0", "repl"),
        "ERROR: r must be a non-negative real number");
  }

  @Test
  public void testCheckNaiveRadiusCommand_3Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 \"Sol\"", "repl"),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNaiveRadiusCommand_3Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 \"Sol\"", "repl"),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5.121 \"Sol\"", "repl"),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckNaiveRadiusCommand_3Args_NameNotInQuotes() {
    // star name not in quotes
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 Sol", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 \"Sol", "repl"),
        "ERROR: Star name must be within \"\"");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 Sol\"", "repl"),
        "ERROR: Star name must be within \"\"");
  }

  @Test
  public void testCheckNaiveRadiusCommand_3Args_InvalidR() {
    // invalid value for r
    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius -5 \"Sol\"", "repl"),
        "ERROR: r must be a non-negative real number");
  }

  @Test
  public void testCheckNaiveRadiusCommand_3Args_NameNotInCSV() {
    // star name not in csv
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 \"sol\"", "repl"),
        "ERROR: Star name not found in loaded csv");
  }

  @Test
  public void testCheckNaiveRadiusCommand_3Args_EmptyName() {
    // star name is empty
    assertEquals(NaiveCommandsHandler.naiveRadiusCommand(
        "naive_radius 5 \"\"", "repl"),
        "ERROR: Star name cannot be empty");
  }

  @Test
  public void testCheckNaiveNeighbors_5Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "0", "0", "0"}),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNaiveNeighbors_5Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "0", "0", "0"}),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666");
  }

  @Test
  public void testCheckNaiveNeighbors_5Args_InvalidCommand() {
    // invalid command
    assertThrows(IllegalArgumentException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "0"}));

    assertThrows(IllegalArgumentException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "0", "0", "0", "0"}));
  }

  @Test
  public void testCheckNaiveNeighbors_5Args_InvalidK() {
    // invalid value for k
    assertThrows(NumberFormatException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "-5", "0", "0", "0"}));

    assertThrows(NumberFormatException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "0.1", "0", "0", "0"}));
  }

  @Test
  public void testCheckNaiveNeighbors_3Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "\"Sol\""}),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNaiveNeighbors_3Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "\"Sol\""}),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckNaiveNeighbors_3Args_NameNotInQuotes() {
    // star name not in quotes
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(InvalidParameterException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "Sol"}));

    assertThrows(InvalidParameterException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "\"Sol"}));

    assertThrows(InvalidParameterException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "Sol\""}));
  }

  @Test
  public void testCheckNaiveNeighbors_3Args_InvalidR() {
    // invalid value for r
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(NumberFormatException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "-5", "\"Sol\""}));

    assertThrows(NumberFormatException.class, () -> NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "0.1", "\"Sol\""}));
  }

  @Test
  public void testCheckNaiveNeighbors_3Args_NameNotInCSV() {
    // star name not in csv
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "\"sol\""}),
        "ERROR: Star name not found in loaded csv");
  }

  @Test
  public void testCheckNaiveNeighbors_3Args_EmptyName() {
    // star name is empty
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.checkNaiveNeighborsArgs(
        new String[]{"naive_neighbors", "5", "\"\""}),
        "ERROR: Star name cannot be empty");
  }

  @Test
  public void testCheckNaiveRadius_5Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "0", "0", "0"}),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNaiveRadius_5Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "0", "0", "0"}),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");

    assertEquals(NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5.121", "0", "0", "0"}),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckNaiveRadius_5Args_InvalidCommand() {
    // invalid command
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(IllegalArgumentException.class, () -> NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "0"}));

    assertThrows(IllegalArgumentException.class, () -> NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "0", "0", "0", "0"}));
  }

  @Test
  public void testCheckNaiveRadius_5Args_InvalidR() {
    // invalid value for r
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(NumberFormatException.class, () -> NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "-5", "0", "0", "0"}));
  }

  @Test
  public void testCheckNaiveRadius_3Args_ValidCommand_NoLoadedFile() {
    // valid command + no loaded file
    StarsCommandHandler.clearLoadedData();

    assertEquals(NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "\"Sol\""}),
        "ERROR: No csv loaded");
  }

  @Test
  public void testCheckNaiveRadius_3Args_ValidCommand_LoadedFile() {
    // valid command + loaded file
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "\"Sol\""}),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");

    assertEquals(NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5.121", "\"Sol\""}),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testCheckNaiveRadius_3Args_NameNotInQuotes() {
    // star name not in quotes
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertThrows(InvalidParameterException.class, () -> NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "Sol"}));

    assertThrows(InvalidParameterException.class, () -> NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "\"Sol"}));

    assertThrows(InvalidParameterException.class, () -> NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "Sol\""}));
  }

  @Test
  public void testCheckNaiveRadius_3Args_InvalidR() {
    // invalid value for r
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertThrows(NumberFormatException.class, () -> NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "-5", "\"Sol\""}));
  }

  @Test
  public void testCheckNaiveRadius_3Args_NameNotInCSV() {
    // star name not in csv
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "\"sol\""}),
        "ERROR: Star name not found in loaded csv");
  }

  @Test
  public void testCheckNaiveRadius_3Args_EmptyName() {
    // star name is empty
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.checkNaiveRadiusArgs(
        new String[]{"naive_radius", "5", "\"\""}),
        "ERROR: Star name cannot be empty");
  }

  @Test
  public void testGetStarDistances_5Args_ThroughNaiveNeighbors() {
    // through naive_neighbors
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.getStarDistances(5, 0, 0, 0,
        NaiveCommandsHandler::getNaiveNearestNeighborsResult),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666");
  }

  @Test
  public void testGetStarDistances_5Args_ThroughNaiveRadius() {
    // through naive_radius
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.getStarDistances(5, 0, 0, 0,
        NaiveCommandsHandler::getNaiveRadiusSearchResult),
        "0\n" +
            "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testGetStarDistances_5Args_NullStarsList() {
    // null starsList
    StarsCommandHandler.clearLoadedData();

    assertThrows(NullPointerException.class, () -> NaiveCommandsHandler.getStarDistances(5, 0, 0, 0,
        NaiveCommandsHandler::getNaiveRadiusSearchResult));
  }

  @Test
  public void testGetStarDistances_3Args_ThroughNaiveNeighbors() {
    // through naive_neighbors
    StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl");

    assertEquals(NaiveCommandsHandler.getStarDistances(5, "Sol",
        NaiveCommandsHandler::getNaiveNearestNeighborsResult),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testGetStarDistances_3Args_ThroughNaiveRadius() {
    // through naive_radius
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertEquals(NaiveCommandsHandler.getStarDistances(5, "Sol",
        NaiveCommandsHandler::getNaiveRadiusSearchResult),
        "70667\n" +
            "71454\n" +
            "71457\n" +
            "87666\n" +
            "118721");
  }

  @Test
  public void testGetStarDistances_3Args_NullStarsList() {
    // null starsList
    StarsCommandHandler.clearLoadedData();

    assertThrows(NullPointerException.class, () -> NaiveCommandsHandler.getStarDistances(5, "Sol",
        NaiveCommandsHandler::getNaiveRadiusSearchResult));
  }

  @Test
  public void testGetStarDistances_3Args_InvalidStar() {
    // invalid star
    StarsCommandHandler.starsCommand("stars data/stars/ten-star.csv", "repl");

    assertThrows(IndexOutOfBoundsException.class, () -> NaiveCommandsHandler.getStarDistances(5, "sol",
        NaiveCommandsHandler::getNaiveRadiusSearchResult));

    assertThrows(IllegalArgumentException.class, () -> NaiveCommandsHandler.getStarDistances(5, "",
        NaiveCommandsHandler::getNaiveRadiusSearchResult));
  }
//
//  @Test
//  public void testGetNaiveNearestNeighborsResult_FewerThanKStars() {
//    // fewer stars than k
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 11.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 12.0));
//
//    assertEquals(NaiveCommandsHandler.getNaiveNearestNeighborsResult(5, starsDistances), "1\n2\n3");
//  }
//
//  @Test
//  public void testGetNaiveNearestNeighborsResult_0Stars() {
//    // 0 stars
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//
//    assertEquals(NaiveCommandsHandler.getNaiveNearestNeighborsResult(5, starsDistances), "");
//  }
//
//  @Test
//  public void testGetNaiveNearestNeighborsResult_AllUniqueDistances() {
//    // all unique distances
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 11.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 12.0));
//
//    assertEquals(NaiveCommandsHandler.getNaiveNearestNeighborsResult(2, starsDistances), "1\n2");
//  }
//
//  @Test
//  public void testGetNaiveNearestNeighborsResult_OnlyDupes_AllWithinK() {
//    // only duplicates present but within k
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 10.0));
//
//    String consoleResult = NaiveCommandsHandler.getNaiveNearestNeighborsResult(3, starsDistances);
//
//    assertTrue(consoleResult.equals("1\n2\n3")
//        || consoleResult.equals("2\n3\n1")
//        || consoleResult.equals("3\n1\n2")
//        || consoleResult.equals("3\n2\n1")
//        || consoleResult.equals("2\n1\n3")
//        || consoleResult.equals("1\n3\n2"));
//  }
//
//  @Test
//  public void testGetNaiveNearestNeighborsResult_OnlyDupes_CrossingK() {
//    // only duplicates present but crossing k
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 10.0));
//
//    String consoleResult = NaiveCommandsHandler.getNaiveNearestNeighborsResult(2, starsDistances);
//
//    assertTrue(consoleResult.equals("1\n2")
//        || consoleResult.equals("1\n3")
//        || consoleResult.equals("3\n1")
//        || consoleResult.equals("2\n1")
//        || consoleResult.equals("2\n3")
//        || consoleResult.equals("3\n2"));
//  }
//
//  @Test
//  public void testGetNaiveNearestNeighborsResult_SomeDupes() {
//    // some duplicates present
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 9.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 9.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 10.0));
//
//    String consoleResult = NaiveCommandsHandler.getNaiveNearestNeighborsResult(3, starsDistances);
//
//    assertTrue(consoleResult.equals("1\n2\n3")
//        || consoleResult.equals("2\n1\n3"));
//  }
//
//  @Test
//  public void testGetNaiveRadiusSearchResult_NoStarsWithinR() {
//    // no stars within radius
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 11.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 12.0));
//
//    assertEquals(NaiveCommandsHandler.getNaiveRadiusSearchResult(5, starsDistances), "");
//  }
//
//  @Test
//  public void testGetNaiveRadiusSearchResult_AllUniqueDistances() {
//    // all unique distances
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 11.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 12.0));
//
//    assertEquals(NaiveCommandsHandler.getNaiveRadiusSearchResult(12, starsDistances), "1\n2\n3");
//  }
//
//  @Test
//  public void testGetNaiveRadiusSearchResult_OnlyDupes() {
//    // only duplicates present
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 10.0));
//
//    String consoleResult = NaiveCommandsHandler.getNaiveRadiusSearchResult(10, starsDistances);
//
//    assertTrue(consoleResult.equals("1\n2\n3")
//        || consoleResult.equals("2\n3\n1")
//        || consoleResult.equals("3\n1\n2")
//        || consoleResult.equals("3\n2\n1")
//        || consoleResult.equals("2\n1\n3")
//        || consoleResult.equals("1\n3\n2"));
//  }
//
//  @Test
//  public void testGetNaiveRadiusSearchResult_SomeDupes() {
//    // some duplicates present
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 9.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 9.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 10.0));
//
//    String consoleResult = NaiveCommandsHandler.getNaiveRadiusSearchResult(10, starsDistances);
//
//    assertTrue(consoleResult.equals("1\n2\n3")
//        || consoleResult.equals("2\n1\n3"));
//  }
//
//  @Test
//  public void testGetNaiveRadiusSearchResult_SomeDistancesAboveR() {
//    // some distances above r
//    List<KeyDistance<Coordinate<Integer>>> starsDistances = new ArrayList<>();
//    starsDistances.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 9.0));
//    starsDistances.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), 10.0));
//    starsDistances.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 11.0));
//
//    String consoleResult = NaiveCommandsHandler.getNaiveRadiusSearchResult(10, starsDistances);
//
//    assertEquals(consoleResult, "1\n2");
//  }
//
//  @Test
//  public void testSortStarDistances() {
//    List<KeyDistance<Coordinate<Integer>>> starDists = new ArrayList<>();
//    starDists.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    starDists.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), -10.0));
//    starDists.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 13.0));
//    starDists.add(new KeyDistance<>(new Star(4, "star4", 8, 4, 2), 22.7));
//    starDists.add(new KeyDistance<>(new Star(5, "star5", 2, 1, 1), 4.0));
//
//    List<KeyDistance<Star>> sorted = new ArrayList<>();
//    sorted.add(new KeyDistance<>(new Star(2, "star2", 10, 31, 0.3), -10.0));
//    sorted.add(new KeyDistance<>(new Star(5, "star5", 2, 1, 1), 4.0));
//    sorted.add(new KeyDistance<>(new Star(1, "star1", 1, 3, 3), 10.0));
//    sorted.add(new KeyDistance<>(new Star(3, "star3", 1, 1, 3), 13.0));
//    sorted.add(new KeyDistance<>(new Star(4, "star4", 8, 4, 2), 22.7));
//
//    assertEquals(sorted.size(), NaiveCommandsHandler.sortStarsDistances(starDists).size());
//    assertEquals(NaiveCommandsHandler.sortStarsDistances(starDists).get(0).getDistance(), sorted.get(0).getDistance(), 0.0);
//    assertEquals(NaiveCommandsHandler.sortStarsDistances(starDists).get(1).getDistance(), sorted.get(1).getDistance(), 0.0);
//    assertEquals(NaiveCommandsHandler.sortStarsDistances(starDists).get(2).getDistance(), sorted.get(2).getDistance(), 0.0);
//    assertEquals(NaiveCommandsHandler.sortStarsDistances(starDists).get(3).getDistance(), sorted.get(3).getDistance(), 0.0);
//    assertEquals(NaiveCommandsHandler.sortStarsDistances(starDists).get(4).getDistance(), sorted.get(4).getDistance(), 0.0);
//  }
}
