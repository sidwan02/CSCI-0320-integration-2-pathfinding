package edu.brown.cs.ta.database;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseHandlerTest {
  @Test
  public void testLoadDB_FileDoesNotExist() {
    assertThrows(FileNotFoundException.class, () ->
        DatabaseHandler.loadDB("PINGU"));

    assertThrows(FileNotFoundException.class, () ->
        DatabaseHandler.loadDB("PINGU.sqlite3"));
  }

  @Test
  public void testLoadDB_FileExists_DefaultExtension()
      throws SQLException, FileNotFoundException, ClassNotFoundException {
    DatabaseHandler.loadDB("data/pathfinding/smallMaps.sqlite3");

    assertNotNull(DatabaseHandler.getConn());
  }

  @Test
  public void testLoadDB_FileExists_SupportedExtension()
      throws SQLException, FileNotFoundException, ClassNotFoundException {
    List<String> fileExtensions = new ArrayList<>();
    fileExtensions.add("sqlite3");
    fileExtensions.add("sql");
    fileExtensions.add("sqlitedb");
    fileExtensions.add("db");

    DatabaseHandler.setValidExtensionFormats(fileExtensions);
    DatabaseHandler.loadDB("data/pathfinding/smallMaps-sqlformat.sql");

    assertNotNull(DatabaseHandler.getConn());
  }

  @Test
  public void testLoadDB_FileExists_NonDefaultExtension() {
    assertThrows(IllegalAccessError.class, () ->
        DatabaseHandler.loadDB("data/pathfinding/smallMaps-sqlformat.sql"));
  }

  @Test
  public void testLoadDB_FileExists_UnsupportedExtension() {
    List<String> fileExtensions = new ArrayList<>();
    fileExtensions.add("sqlite3");
    fileExtensions.add("sqlitedb");
    fileExtensions.add("db");

    DatabaseHandler.setValidExtensionFormats(fileExtensions);
    assertThrows(IllegalAccessError.class, () ->
        DatabaseHandler.loadDB("data/pathfinding/smallMaps-sqlformat.sql"));
  }

  @Test
  public void testQueryLoadedDB_ValidQuery()
      throws SQLException, FileNotFoundException, ClassNotFoundException {
    DatabaseHandler.loadDB("data/pathfinding/smallMaps.sqlite3");
    ResultSet rs = DatabaseHandler.queryLoadedDB("SELECT id FROM node");

    List<String> allIds = new ArrayList<>();
    while (rs.next()) {
//      System.out.println(rs.getString(1));
      allIds.add(rs.getString(1));
    }

    List<String> expectedIDs = new ArrayList<>();
    expectedIDs.add("/n/0");
    expectedIDs.add("/n/1");
    expectedIDs.add("/n/2");
    expectedIDs.add("/n/3");
    expectedIDs.add("/n/4");
    expectedIDs.add("/n/5");

    assertEquals(allIds, expectedIDs);
  }

  @Test
  public void testQueryLoadedDB_InvalidQuery()
      throws SQLException, FileNotFoundException, ClassNotFoundException {
    DatabaseHandler.loadDB("data/pathfinding/smallMaps.sqlite3");
    assertThrows(SQLException.class, () ->
        DatabaseHandler.queryLoadedDB("haha hehe"));
  }
}
