package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.gui;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps.CheckinObjectPersistence;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps.MapCommandHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps.NearestCommandHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps.RouteCommandsHandler;
import edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps.WaysCommandHandler;
import spark.Request;
import spark.Response;
import spark.Route;

import org.json.JSONObject;

import java.util.Map;

/**
 * MapsGuiHandler class to manage all handlers to the stars page.
 *
 */
public class MapsGuiHandler {

  /**
   * Handles requests to the front-end for maps.
   */
  public static class RouteHandler implements Route {
    private static final Gson GSON = new Gson();

    /**
     * Handles Axios requests from the javascript front-end and returns
     * the appropriate JSON object to be used by the front-end.
     *
     * @param request - request object for Axios request
     * @param response - response object for Axios request
     * @return a JSON object representing information to be used by the front end
     * @throws Exception if data cannot be accessed from given JSON object
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

      JSONObject data = new JSONObject(request.body());
      String filename = data.getString("filename");

      String street1 = data.getString("street1");
      String crossStreet1 = data.getString("crossStreet1");
      String street2 = data.getString("street2");
      String crossStreet2 = data.getString("crossStreet2");

      String coordinateCmd = data.getString("coordinateCmd");

      Map<String, Object> variables;

      if (!filename.equals("")) {
        // map
        System.out.println("in map");
        variables = MapCommandHandler.loadDBGui("data/maps/" + filename);
      } else if (coordinateCmd.equals("ways")) {
        // ways
        double lat1 = Double.parseDouble(data.getString("lat1"));
        double lon1 = Double.parseDouble(data.getString("lon1"));
        double lat2 = Double.parseDouble(data.getString("lat2"));
        double lon2 = Double.parseDouble(data.getString("lon2"));
        variables = WaysCommandHandler.handleWaysWithinBoxGui(lat1, lon1, lat2, lon2);
      } else if (coordinateCmd.equals("route")) {
        // route coordinates
        double lat1 = Double.parseDouble(data.getString("lat1"));
        double lon1 = Double.parseDouble(data.getString("lon1"));
        double lat2 = Double.parseDouble(data.getString("lat2"));
        double lon2 = Double.parseDouble(data.getString("lon2"));
        variables = RouteCommandsHandler.getPathLatLonGui(lat1, lon1, lat2, lon2);
      } else if (coordinateCmd.equals("nearest")) {
        double lat1 = Double.parseDouble(data.getString("lat1"));
        double lon1 = Double.parseDouble(data.getString("lon1"));
        variables = NearestCommandHandler.handleNearestGui(lat1, lon1);
      } else if (coordinateCmd.equals("connection")) {
//        System.out.println("connection stuff: " + MapCommandHandler.getCurDb());
        variables = ImmutableMap.of(
          "map", MapCommandHandler.getCurDb(),
          "route", "",
          "ways", "",
          "nearest", "",
          "error", "");
      } else {
        variables =
                RouteCommandsHandler.getPathStreetGui(street1, crossStreet1, street2, crossStreet2);
      }

      return GSON.toJson(variables);
    }
  }


  /**
   * Handles requests to the front-end for checkins.
   */
  public static class CheckinHandler implements Route {
    private static final Gson GSON = new Gson();

    /**
     * Handles Axios requests from the javascript front-end and returns
     * the appropriate JSON object to be used by the front-end.
     *
     * @param request - request object for Axios request
     * @param response - response object for Axios request
     * @return a JSON object representing information to be used by the front end
     * @throws Exception if data cannot be accessed from given JSON object
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

      JSONObject data = new JSONObject(request.body());
      Map<String, Object> variables;

      String purpose = data.getString("purpose"); // either refresh or click
//      System.out.println("purpose: " + purpose);

      if (purpose.equals("refresh")) {
        variables = CheckinObjectPersistence.getCheckinObj().getLatestCheckinsGui();
//      } else if (purpose.equals("click")) {
      } else {
        int userId = Integer.parseInt(data.getString("userId"));
//        System.out.println("userId: " + userId);
        variables = CheckinObjectPersistence.getCheckinObj().setUserDataFromId(userId);
      }

      return GSON.toJson(variables);
    }
  }
}
