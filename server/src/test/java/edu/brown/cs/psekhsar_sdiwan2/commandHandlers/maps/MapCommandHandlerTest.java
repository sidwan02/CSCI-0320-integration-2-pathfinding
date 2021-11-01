package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import edu.brown.cs.psekhsar_sdiwan2.database.DatabaseHandler;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class MapCommandHandlerTest {
  private final String bigMapsPath = "data/maps/maps.sqlite3";

  @Test
  public void testMapCommand_ValidDB() {
    // valid DB
    assertEquals(MapCommandHandler.mapCommand("map data/maps/smallMaps.sqlite3", "repl"),
        "map set to data/maps/smallMaps.sqlite3");

    assertEquals(MapCommandHandler.mapCommand("map " + bigMapsPath, "repl"),
        "map set to " + bigMapsPath);
  }

  @Test
  public void testMapCommand_NonexistentDB() {
    // nonexistent DB
    assertEquals(MapCommandHandler.mapCommand("map blah.sqlite3", "repl"),
        "ERROR: Database at blah.sqlite3 not found");
  }

  @Test
  public void testMapCommand_UnsupportedFileType_Default() {
    // unsupported DB
    assertEquals(MapCommandHandler.mapCommand("map data/maps/smallMaps-sqlformat.sql", "repl"),
        "ERROR: Database must be of the provided extension types");
  }

  @Test
  public void testMapCommand_UnsupportedFileType_Provided() {
    // unsupported DB
    List<String> fileExtensions = new ArrayList<>();
    fileExtensions.add("sqlite3");
    fileExtensions.add("sqlitedb");
    fileExtensions.add("db");

    DatabaseHandler.setValidExtensionFormats(fileExtensions);

    assertEquals(MapCommandHandler.mapCommand("map data/maps/smallMaps-sqlformat.sql", "repl"),
        "ERROR: Database must be of the provided extension types");
  }

  @Test
  public void testMapCommand_InvalidArgs() {
    // illegal number of arguments
    assertEquals(MapCommandHandler.mapCommand("map hi data/maps/smallMaps.sqlite3", "repl"),
        "ERROR: Invalid no.of arguments passed for map");

    assertEquals(MapCommandHandler.mapCommand("map", "repl"),
        "ERROR: Invalid no.of arguments passed for map");
  }

  @Test
  public void testCheckMapArgs_ValidDB() {
    // valid DB
    assertEquals(MapCommandHandler.checkMapArgs(new String[]{"map", "data/maps/smallMaps.sqlite3"}),
        "map set to data/maps/smallMaps.sqlite3");

    assertEquals(MapCommandHandler.checkMapArgs(new String[]{"map", bigMapsPath}),
        "map set to " + bigMapsPath);
  }

  @Test
  public void testCheckMapArgs_NonexistentDB() {
    // nonexistent DB
    assertEquals(MapCommandHandler.checkMapArgs(new String[]{"map", "haha"}),
            "ERROR: Database at haha not found");
  }

  @Test
  public void testCheckMapArgs_UnsupportedFileType_Default() {
    // unsupported DB
    assertEquals(MapCommandHandler.checkMapArgs(new String[]{"map", "data/maps/smallMaps-sqlformat.sql"}),
        "ERROR: Database must be of the provided extension types");
  }

  @Test
  public void testCheckMapArgs_UnsupportedFileType_Provided() {
    // unsupported DB
    List<String> fileExtensions = new ArrayList<>();
    fileExtensions.add("sqlite3");
    fileExtensions.add("sqlitedb");
    fileExtensions.add("db");

    DatabaseHandler.setValidExtensionFormats(fileExtensions);

    assertEquals(MapCommandHandler.checkMapArgs(new String[]{"map", "data/maps/smallMaps-sqlformat.sql"}),
        "ERROR: Database must be of the provided extension types");
  }

  @Test
  public void testCheckMapArgs_InvalidArgs() {
    // illegal number of arguments
    assertThrows(IllegalArgumentException.class, () ->
        MapCommandHandler.checkMapArgs(new String[]{"map", "hi", "data/maps/smallMaps.sqlite3"}));

    assertThrows(IllegalArgumentException.class, () ->
        MapCommandHandler.checkMapArgs(new String[]{"map"}));
  }

  @Test
  public void testLoadDB_ValidDB() {

  }

  @Test
  public void testLoadDB_DBPresence() {
    // nonexistent DB
    assertEquals(MapCommandHandler.loadDB("googoogaga"),
            "ERROR: Database at googoogaga not found");

    // valid DB
    assertEquals(MapCommandHandler.loadDB("data/maps/smallMaps.sqlite3"),
      "map set to data/maps/smallMaps.sqlite3");

    assertEquals(MapCommandHandler.loadDB(bigMapsPath),
        "map set to " + bigMapsPath);
  }

  @Test
  public void testLoadDB_UnsupportedFileType_Default() {
    // unsupported DB
    assertEquals(MapCommandHandler.loadDB("data/maps/smallMaps-sqlformat.sql"),
        "ERROR: Database must be of the provided extension types");
  }

  @Test
  public void testLoadDB_UnsupportedFileType_Provided() {
    // unsupported DB
    List<String> fileExtensions = new ArrayList<>();
    fileExtensions.add("sqlite3");
    fileExtensions.add("sqlitedb");
    fileExtensions.add("db");

    DatabaseHandler.setValidExtensionFormats(fileExtensions);

    assertEquals(MapCommandHandler.loadDB("data/maps/smallMaps-sqlformat.sql"),
        "ERROR: Database must be of the provided extension types");
  }

  @Test
  public void testLoadTree_NonNullTree() throws SQLException, ClassNotFoundException, IllegalAccessException,
    FileNotFoundException {
    MapCommandHandler.reset();
    DatabaseHandler.loadDB("data/maps/smallMaps.sqlite3");

    assertNull(MapCommandHandler.getKdTree());
    MapCommandHandler.loadTree();
    assertEquals(MapCommandHandler.getKdTree().toString(),
      "KdTree{dimensions=2, tree=TreeNode{value=GraticuleNode{id='/n/1', coordinates=[41.8203, -71.4]}, " +
        "left=TreeNode{value=GraticuleNode{id='/n/0', coordinates=[41.82, -71.4]}, " +
        "left=TreeNode{value=GraticuleNode{id='/n/3', coordinates=[41.82, -71.4003]}, left=NULL, right=NULL}, right=NULL}, " +
        "right=TreeNode{value=GraticuleNode{id='/n/4', coordinates=[41.8203, -71.4003]}, left=NULL, " +
        "right=TreeNode{value=GraticuleNode{id='/n/2', coordinates=[41.8206, -71.4]}, left=NULL, " +
        "right=TreeNode{value=GraticuleNode{id='/n/5', coordinates=[41.8206, -71.4003]}, left=NULL, right=NULL}}}}}");
  }

  @Test
  public void testParseToRepl() {
    assertEquals(MapCommandHandler.parseToRepl("you-never-expected-this-name.sqlite3"),
      "map set to you-never-expected-this-name.sqlite3");
  }
}
