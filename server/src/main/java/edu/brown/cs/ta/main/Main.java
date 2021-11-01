package edu.brown.cs.ta.main;


import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import edu.brown.cs.ta.checkin.CheckinThread;
import edu.brown.cs.ta.commandHandlers.gui.MapsGuiHandler;
import edu.brown.cs.ta.commandHandlers.gui.StarsGuiHandler;
import edu.brown.cs.ta.commandHandlers.pathfinding.MapCommandHandler;
import edu.brown.cs.ta.commandHandlers.pathfinding.NearestCommandHandler;
import edu.brown.cs.ta.commandHandlers.pathfinding.ObstacleObjectPersistence;
import edu.brown.cs.ta.commandHandlers.pathfinding.RouteCommandsHandler;
import edu.brown.cs.ta.commandHandlers.pathfinding.WaysCommandHandler;
import edu.brown.cs.ta.repl.Repl;
import edu.brown.cs.ta.commandHandlers.stars.KdTreeCommandsHandler;
import edu.brown.cs.ta.commandHandlers.stars.NaiveCommandsHandler;
import edu.brown.cs.ta.commandHandlers.stars.StarsCommandHandler;
import edu.brown.cs.ta.obstacle.ObstacleThread;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import freemarker.template.Configuration;


/**
 * The Main class of our project. This is where execution begins.
 *
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args
   *          An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("c");
    parser.accepts("o");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
    .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("c")) {
      CheckinThread checkinThread = new CheckinThread();
      checkinThread.start();

      CheckinObjectPersistence.setCheckinObj(checkinThread);

      runSparkServer((int) options.valueOf("port"));
    } else if (options.has("o")) {
      // Initialize obstacle thread connection
      ObstacleThread obstacleThread = new ObstacleThread();
      obstacleThread.start();

      ObstacleObjectPersistence.setObstacleObj(obstacleThread);

      runSparkServer((int) options.valueOf("port"));
    }

    // create a map of valid commands and command handler methods
    Map<String, BiFunction<String, String, String>> validCommands = new HashMap<>() {
      {
        put("stars", StarsCommandHandler::starsCommand);
        put("naive_neighbors", NaiveCommandsHandler::naiveNeighborsCommand);
        put("naive_radius", NaiveCommandsHandler::naiveRadiusCommand);
        put("neighbors", KdTreeCommandsHandler::neighborsCommand);
        put("radius", KdTreeCommandsHandler::radiusCommand);
        put("map", MapCommandHandler::mapCommand);
        put("ways", WaysCommandHandler::waysCommand);
        put("nearest", NearestCommandHandler::nearestCommand);
        put("route", RouteCommandsHandler::routeCommand);
        put("delete", DeleteCommandHandler::deleteCommand);
      }
    };

    // instantiate and activate the REPL
    Repl repl = new Repl(validCommands);
    repl.activate(new InputStreamReader(System.in, StandardCharsets.UTF_8));
  }

//  private static String loadedFileState = "*No file loaded*";

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

//  private void runSparkServer(int port) {
//    Spark.port(port);
//    Spark.externalStaticFileLocation("src/main/resources/static");
//    Spark.exception(Exception.class, new ExceptionPrinter());
//
//    FreeMarkerEngine freeMarker = createEngine();
//
//    // Setup Spark Routes
//    Spark.get("/stars", new StarsGuiHandler.FrontHandler(), freeMarker);
//    // get user input
//    Spark.post("/csvLoaded", new StarsGuiHandler.SubmitHandlerCsv(), freeMarker);
//    Spark.post("/results", new StarsGuiHandler.SubmitHandlerCommand(), freeMarker);
//    Spark.post("/stars", new StarsGuiHandler.SubmitHandlerStars(), freeMarker);
//  }

  private void runSparkServer(int port) {

    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");

    Spark.options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/stars", new StarsGuiHandler.FrontHandler(), freeMarker);
    // get user input
    Spark.post("/csvLoaded", new StarsGuiHandler.SubmitHandlerCsv(), freeMarker);
    Spark.post("/results", new StarsGuiHandler.SubmitHandlerCommand(), freeMarker);
    Spark.post("/stars", new StarsGuiHandler.SubmitHandlerStars(), freeMarker);

    Spark.post("/route", new MapsGuiHandler.RouteHandler());
    Spark.post("/checkin", new MapsGuiHandler.CheckinHandler());
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}
