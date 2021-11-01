package edu.brown.cs.ta.commandHandlers.stars;

import org.junit.Test;

import java.io.EOFException;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class StarsCommandHandlerTest {
  @Test
  public void testStarsCommand_ValidFile() {
    // valid file
    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/stardata.csv", "repl"),
        "Read 119617 stars from data/stars/stardata.csv");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/no-star.csv", "repl"),
        "Read 0 stars from data/stars/no-star.csv");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/one-star.csv", "repl"),
        "Read 1 stars from data/stars/one-star.csv");
  }

  @Test
  public void testStarsCommand_InvalidID() {
    // file with invalid ID
    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/non-int-id-data.csv", "repl"),
        "ERROR: Star ID must be a non-negative integer");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/negative-id-data.csv", "repl"),
        "ERROR: Star ID must be a non-negative integer");
  }

  @Test
  public void testStarsCommand_InvalidHeader() {
    // file with invalid header
    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/invalid-header-1.csv", "repl"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/invalid-header-2.csv", "repl"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/invalid-header-3.csv", "repl"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/invalid-header-4.csv", "repl"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/invalid-header-5.csv", "repl"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/no-header.csv", "repl"),
        "ERROR: File does not contain header/invalid header found");
  }

  @Test
  public void testStarsCommand_NonexistentFile() {
    // nonexistent file
    assertEquals(StarsCommandHandler.starsCommand("stars googoogaga", "repl"),
            "ERROR: File at googoogaga not found");
  }

  @Test
  public void testStarsCommand_MissingColumns() {
    // file with missing columns
    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/all-rows-missing-column.csv", "repl"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/some-rows-missing-column.csv", "repl"),
        "ERROR: Missing columns in csv");

    assertEquals(StarsCommandHandler.starsCommand("stars data/stars/empty.csv", "repl"),
        "ERROR: Missing columns in csv");
  }

  @Test
  public void testStarsCommand_InvalidArgs() {
    // illegal number of arguments
    assertEquals(StarsCommandHandler.starsCommand("stars hi data/stars/empty.csv", "repl"),
        "ERROR: Invalid no.of arguments passed for stars");

    assertEquals(StarsCommandHandler.starsCommand("stars", "repl"),
        "ERROR: Invalid no.of arguments passed for stars");
  }

  @Test
  public void testCheckStarsArgs_ValidFile() {
    // valid file
    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/stardata.csv"}),
        "Read 119617 stars from data/stars/stardata.csv");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/no-star.csv"}),
        "Read 0 stars from data/stars/no-star.csv");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/one-star.csv"}),
        "Read 1 stars from data/stars/one-star.csv");
  }

  @Test
  public void testCheckStarsArgs_InvalidID() {
    // file with invalid ID
    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/non-int-id-data.csv"}),
        "ERROR: Star ID must be a non-negative integer");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/negative-id-data.csv"}),
        "ERROR: Star ID must be a non-negative integer");
  }

  @Test
  public void testCheckStarsArgs_InvalidHeader() {
    // file with invalid header
    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/invalid-header-1.csv"}),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/invalid-header-2.csv"}),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/invalid-header-3.csv"}),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/invalid-header-4.csv"}),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/invalid-header-5.csv"}),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/no-header.csv"}),
        "ERROR: File does not contain header/invalid header found");
  }

  @Test
  public void testCheckStarsArgs_NonexistentFile() {
    // nonexistent file
    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "googoogaga"}),
            "ERROR: File at googoogaga not found");
  }

  @Test
  public void testCheckStarsArgs_MissingColumns() {
    // file with missing columns
    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/all-rows-missing-column.csv"}),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/some-rows-missing-column.csv"}),
        "ERROR: Missing columns in csv");

    assertEquals(StarsCommandHandler.checkStarsArgs(new String[]{"stars", "data/stars/empty.csv"}),
        "ERROR: Missing columns in csv");
  }

  @Test
  public void testCheckStarsArgs_InvalidArgs() {
    // illegal number of arguments
    assertThrows(IllegalArgumentException.class, () -> StarsCommandHandler.checkStarsArgs(new String[]{"stars", "hi", "data/stars/empty.csv"}));

    assertThrows(IllegalArgumentException.class, () -> StarsCommandHandler.checkStarsArgs(new String[]{"stars"}));
  }

  @Test
  public void testLoadStarsCsv_ValidFile() {
    // valid file
    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/stardata.csv"),
        "Read 119617 stars from data/stars/stardata.csv");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/no-star.csv"),
        "Read 0 stars from data/stars/no-star.csv");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/one-star.csv"),
        "Read 1 stars from data/stars/one-star.csv");
  }

  @Test
  public void testLoadStarsCsv_InvalidID() {
    // file with invalid ID
    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/non-int-id-data.csv"),
        "ERROR: Star ID must be a non-negative integer");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/negative-id-data.csv"),
        "ERROR: Star ID must be a non-negative integer");
  }

  @Test
  public void testLoadStarsCsv_InvalidHeader() {
    // file with invalid header
    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/invalid-header-1.csv"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/invalid-header-2.csv"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/invalid-header-3.csv"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/invalid-header-4.csv"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/invalid-header-5.csv"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/no-header.csv"),
        "ERROR: File does not contain header/invalid header found");
  }

  @Test
  public void testLoadStarsCsv_NonexistentFile() {
    // nonexistent file
    assertEquals(StarsCommandHandler.loadStarsCsv("googoogaga"),
            "ERROR: File at googoogaga not found");
  }

  @Test
  public void testLoadStarsCsv_MissingColumns() {
    // file with missing columns
    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/all-rows-missing-column.csv"),
        "ERROR: File does not contain header/invalid header found");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/some-rows-missing-column.csv"),
        "ERROR: Missing columns in csv");

    assertEquals(StarsCommandHandler.loadStarsCsv("data/stars/empty.csv"),
        "ERROR: Missing columns in csv");
  }

  @Test
  public void testExtractFileRows_ValidFile() throws FileNotFoundException, EOFException {
    // valid file
    assertEquals(StarsCommandHandler.extractFileRows("data/stars/stardata.csv"),
        "Read 119617 stars from data/stars/stardata.csv");

    assertEquals(StarsCommandHandler.extractFileRows("data/stars/no-star.csv"),
        "Read 0 stars from data/stars/no-star.csv");

    assertEquals(StarsCommandHandler.extractFileRows("data/stars/one-star.csv"),
        "Read 1 stars from data/stars/one-star.csv");
  }

  @Test
  public void testExtractFileRows_InvalidID() {
    // file with invalid ID
    assertThrows(NumberFormatException.class, () -> StarsCommandHandler.extractFileRows("data/stars/non-int-id-data.csv"));

    assertThrows(NumberFormatException.class, () -> StarsCommandHandler.extractFileRows("data/stars/negative-id-data.csv"));
  }

  @Test
  public void testExtractFileRows_InvalidHeader() {
    // file with invalid header
    assertThrows(EOFException.class, () -> StarsCommandHandler.extractFileRows("data/stars/invalid-header-1.csv"));

    assertThrows(EOFException.class, () -> StarsCommandHandler.extractFileRows("data/stars/invalid-header-2.csv"));

    assertThrows(EOFException.class, () -> StarsCommandHandler.extractFileRows("data/stars/invalid-header-3.csv"));

    assertThrows(EOFException.class, () -> StarsCommandHandler.extractFileRows("data/stars/invalid-header-4.csv"));

    assertThrows(EOFException.class, () -> StarsCommandHandler.extractFileRows("data/stars/invalid-header-5.csv"));

    assertThrows(EOFException.class, () -> StarsCommandHandler.extractFileRows("data/stars/no-header.csv"));
  }

  @Test
  public void testExtractFileRows_NonexistentFile() {
    // nonexistent file
    assertThrows(FileNotFoundException.class, () -> StarsCommandHandler.extractFileRows("googoogaga"));
  }

  @Test
  public void testExtractFileRows_MissingColumns() {
    // file with missing columns
    assertThrows(EOFException.class, () -> StarsCommandHandler.extractFileRows("data/stars/all-rows-missing-column.csv"));

    assertThrows(IndexOutOfBoundsException.class, () -> StarsCommandHandler.extractFileRows("data/stars/some-rows-missing-column.csv"));

    assertThrows(IndexOutOfBoundsException.class, () -> StarsCommandHandler.extractFileRows("data/stars/empty.csv"));
  }
}
