package edu.brown.cs.psekhsar_sdiwan2.repl;

import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars.NaiveCommandsHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars.StarsCommandHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static org.junit.Assert.assertTrue;

public class ReplTest {

  // give ability to check printed messages
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  Repl repl;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));

    Map<String, BiFunction<String, String, String>> validCommands = new HashMap<>() {
      {
        put("stars", StarsCommandHandler::starsCommand);
        put("naive_neighbors", NaiveCommandsHandler::naiveNeighborsCommand);
        put("naive_radius", NaiveCommandsHandler::naiveRadiusCommand);
      }
    };

    repl = new Repl(validCommands);
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testActivate_ValidCommand() {
    // valid command
    Reader reader = new StringReader("stars data/stars/stardata.csv");
    repl.activate(reader);

    assertTrue(outContent.toString().contains("Read 119617 stars from data/stars/stardata.csv"));
  }

  @Test
  public void testActivate_ValidCommand_EmptyMessage() {
    // valid command + message is empty
    Reader reader = new StringReader("stars data/stars/one-star.csv\n" +
        "naive_neighbors 1 \"Lonely Star\"");
    repl.activate(reader);

    assertTrue(outContent.toString().contains("Read 1 stars from data/stars/one-star.csv"));
  }

  @Test
  public void testActivate_InvalidCommand() {
    // invalid command
    Reader reader = new StringReader("hi");
    repl.activate(reader);

    assertTrue(outContent.toString().contains("ERROR: Invalid command entered"));
  }

  @Test
  public void testPrintMessage() {
    Reader reader = new StringReader("hi");
    repl.activate(reader);

    repl.printMessage("forget hi I'm sayin hullo");

    assertTrue(outContent.toString().contains("forget hi I'm sayin hullo"));
  }
}
