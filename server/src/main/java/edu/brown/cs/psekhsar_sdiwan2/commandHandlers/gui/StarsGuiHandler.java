package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.gui;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars.KdTreeCommandsHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars.NaiveCommandsHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars.StarsCommandHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * The StarsGuiHandler class that manages all handlers to the stars page.
 *
 */
public class StarsGuiHandler {
  private static final String STARS_URL = "https://www.youtube.com/embed/FbhzEWOuQbA?autoplay=1";
  private static final String LOAD_URL = "https://www.youtube.com/embed/_gYjh863j4g?autoplay=1";
  private static final String RESULT_URL = "https://www.youtube.com/embed/G6BaWPC4MQc?autoplay=1";
  private static final String ERROR_URL = "https://www.youtube.com/embed/hHk-LoSDLC8?autoplay=1";

  /** Handle requests to the front page of the Stars website.
   */
  public static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of(
          "title", "Stars: Query the database",
          "successful_result", "",
          "error", "",
          "music_link", STARS_URL,
          "loaded_file", StarsCommandHandler.getLoadedMessage());
      return new ModelAndView(variables, "query.ftl");
    }
  }

  /** Handle requests to get the star search results after submitting a command.
   */
  public static class SubmitHandlerCommand implements TemplateViewRoute {
    /** Display the /results or /error page after a user submits a command.
     @param req A Request containing all inputs given by the user.
     @param res A Response providing the handler response.
     @return A ModelAndView updating the variables in the HTML content through
     result.ftl or error.ftl
     */
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String threshold = qm.value("threshold");
      String argsSize = qm.value("argsSize");

      String starName = qm.value("starName");
      String targetX = qm.value("targetX");
      String targetY = qm.value("targetY");
      String targetZ = qm.value("targetZ");

      String commandChoice = qm.value("commandChoice");

      System.out.println("threshold: " + threshold);
      System.out.println("argsSize: " + argsSize);

      System.out.println("starName: " + starName);
      System.out.println("targetX: " + targetX);
      System.out.println("targetY: " + targetY);
      System.out.println("targetZ: " + targetZ);

      System.out.println("commandChoice: " + commandChoice);

      String command = commandChoice + " ";

      if (argsSize.equals("5Args")) {
        command += threshold + " " + targetX + " " + targetY + " " + targetZ + " ";
      } else if (argsSize.equals("3Args")) {
        command += threshold + " " + "\"" + starName + "\"";
      }

      System.out.println("command =====================================: " + command);

      Map<String, BiFunction<String, String, String>> validCommands = new HashMap<>() {
        {
          put("naive_neighbors", NaiveCommandsHandler::naiveNeighborsCommand);
          put("naive_radius", NaiveCommandsHandler::naiveRadiusCommand);
          put("neighbors", KdTreeCommandsHandler::neighborsCommand);
          put("radius", KdTreeCommandsHandler::radiusCommand);
        }
      };

      String result = validCommands.get(commandChoice).apply(command, "gui");

      String successfulResult;
      String error;

      if (result.startsWith("ERROR")) {
        error = result;

        Map<String, Object> variables = ImmutableMap.of(
            "title", "Stars: Query the database",
            "successful_result", "",
            "error", error,
            "music_link", ERROR_URL,
            "loaded_file", "");
        return new ModelAndView(variables, "error.ftl");
      } else {
        successfulResult = result;

        Map<String, Object> variables = ImmutableMap.of(
            "title", "Stars: Query the database",
            "successful_result", successfulResult,
            "error", "",
            "music_link", RESULT_URL,
            "loaded_file", "");
        return new ModelAndView(variables, "result.ftl");
      }
    }
  }

  /** Handle requests to load a chosen CSV.
   */
  public static class SubmitHandlerCsv implements TemplateViewRoute {
    /** Display the /csvLoaded page or /error page after the user loads a CSV.
     @param req A Request containing all inputs given by the user.
     @param res A Response providing the handler response.
     @return A ModelAndView updating the variables in the HTML content through
     query.ftl or error.ftl
     */
    public ModelAndView handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String fileName = qm.value("fileName");

      System.out.println("fileName =====================================: " + fileName);

      String csvLoadCommand = "stars data/stars/" + fileName;
      System.out.println("csvLoadCommand: " + csvLoadCommand);

      String loadResult = StarsCommandHandler.starsCommand(csvLoadCommand, "gui");

      System.out.println("result: " + loadResult);

      if (loadResult.startsWith("ERROR")) {
        Map<String, Object> variables = ImmutableMap.of(
            "title", "Stars: Query the database",
            "successful_result", "",
            "error", loadResult,
            "music_link", ERROR_URL,
            "loaded_file", "");
        return new ModelAndView(variables, "error.ftl");
      } else {

        Map<String, Object> variables = ImmutableMap.of(
            "title", "Stars: Query the database",
            "successful_result", "",
            "error", "",
            "music_link", LOAD_URL,
            "loaded_file", StarsCommandHandler.getLoadedMessage());
        return new ModelAndView(variables, "query.ftl");
      }
    }
  }

  /** Handle requests to go back to the Stars main page from the error or result screen.
   */
  public static class SubmitHandlerStars implements TemplateViewRoute {
    /** Display the /stars page.
     @param req A Request containing all inputs given by the user.
     @param res A Response providing the handler response.
     @return A ModelAndView updating the variables in the HTML content through
     query.ftl
     */
    public ModelAndView handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of(
          "title", "Stars: Query the database",
          "successful_result", "",
          "error", "",
          "music_link", STARS_URL,
          "loaded_file", StarsCommandHandler.getLoadedMessage());
      return new ModelAndView(variables, "query.ftl");
    }
  }
}
