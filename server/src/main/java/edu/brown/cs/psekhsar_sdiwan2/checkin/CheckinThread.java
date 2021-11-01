package edu.brown.cs.psekhsar_sdiwan2.checkin;

import com.google.common.collect.ImmutableMap;
import edu.brown.cs.psekhsar_sdiwan2.database.DatabaseHandler;
import edu.brown.cs.psekhsar_sdiwan2.main.ErrorMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * thread that continuously communicates with checkin server.
 */
public final class CheckinThread extends Thread {
  private long last = 0;
  private Map checkins;
  private boolean pause = false;
  static final long MSCONVERSION = 1000;
  private static List<Double[]> currentUserData = new ArrayList<>();

  /**
   * Constructs a ObstacleThread object.
   */
  public CheckinThread() {
    checkins = Collections.synchronizedMap(new HashMap<>());
  }

  /**
   * runs the thread by querying the url for information on user checkins.
   */
  public synchronized void run() {
    List<List<String>> updates = null;

    long lastSec = 0;

    while (true) {
      long sec = System.currentTimeMillis() / MSCONVERSION;
      if (sec != lastSec && !pause) {
        try {
          updates = this.update();
        } catch (IOException e) {
          e.printStackTrace();
        }

        if (updates != null && !updates.isEmpty()) {
          for (List<String> el : updates) {
            double timestamp = Double.parseDouble(el.get(0));
            int id = Integer.parseInt(el.get(1));
            String name = el.get(2);
            double lat = Double.parseDouble(el.get(3));
            double lon = Double.parseDouble(el.get(4));

            // put in concurrent hashmap
            SpyCheckin uc = new SpyCheckin(id, name, timestamp, lat, lon);
            checkins.put(timestamp, uc);

            Connection conn = DatabaseHandler.getConn();
            if (conn == null) {
              continue;
            }
            createCheckinsTable(conn);

            try {
              String queryToAdd = "INSERT INTO checkins VALUES (?, ?, ?, ?, ?);";

              PreparedStatement prep = conn.prepareStatement(queryToAdd);
              prep.setDouble(1, timestamp);
              prep.setInt(2, id);
              prep.setString(3, name);
              prep.setDouble(4, lat);
              prep.setDouble(5, lon);

              prep.executeUpdate();
              prep.close();
            } catch (SQLException e) {
              System.out.println(ErrorMessages.COULD_NOT_QUERY_SERVER);
            }
          }
        }
        lastSec = sec;
      }
    }
  }

  /**
   * Creates a table to store checkins in the database represented by conn
   * if it does not exist.
   *
   * @param conn - the current map database that is connected to
   */
  private void createCheckinsTable(Connection conn) {
    try {
      PreparedStatement prep;
      prep = conn.prepareStatement("CREATE TABLE IF NOT EXISTS checkins("
        + "timestamp DOUBLE,"
        + "id INTEGER,"
        + "name TEXT,"
        + "latitude DOUBLE,"
        + "longitude DOUBLE);");
      prep.executeUpdate();
      prep.close();
    } catch (SQLException e) {
      System.out.println(ErrorMessages.CHECKIN_TABLE_NOT_CREATED);
    }
  }

  /**
   * Queries and returns updated data from the check-in server.
   *
   * @return a list of string lists representing the updated data
   * queried from the check-in server
   * @throws IOException
   */
  private synchronized List<List<String>> update() throws IOException {
    URL serverURL = new URL("http://localhost:8080?last=" + last);
    last = Instant.now().getEpochSecond();

    HttpURLConnection conn = (HttpURLConnection) serverURL.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    Pattern pattern = Pattern.compile("\\[(.*?)\\, (.*?)\\, \"(.*?)\", (.*?)\\, (.*?)\\]");
    String line;

    List<List<String>> output = new ArrayList<>();
    while ((line = br.readLine()) != null) {
      Matcher matcher = pattern.matcher(line);
      while (matcher.find()) {
        List<String> data = new ArrayList<>();
        String parsedTimestamp = matcher.group(1);
        if (parsedTimestamp.charAt(0) == '[') {
          data.add(parsedTimestamp.substring(1));
        } else {
          data.add(parsedTimestamp);
        }
        data.add(matcher.group(2));
        data.add(matcher.group(3));
        data.add(matcher.group(4));
        data.add(matcher.group(5));
        output.add(data);
      }
    }
    return output;
  }

  /**
   * gets the latest checkin updates. Refreshes hashmap so only new
   * checkin updates are returned next time.
   *
   * @return map from a string to a double of timestamps to checkin objects
   */
  public Map<Double, SpyCheckin> getLatestCheckins() {
    pause = true;
    Map<Double, SpyCheckin> temp = checkins;
    checkins = Collections.synchronizedMap(new HashMap<>());
    pause = false;
    return temp;
  }

  /**
   * Calls the getLatestCheckins function and returns the data returned
   * by this function in a format compatible with our front-end.
   *
   * @return a map referring to the latest checkin data with the key checkin
   */
  public Map<String, Object> getLatestCheckinsGui() {
    return ImmutableMap.of(
      "checkin", getLatestCheckins(),
      "current_user_data", "",
      "error", "");
  }

  /**
   * Calls the setCurrentUserData function and returns the set currentUserData
   * in a format compatible with our front-end.
   *
   * @param id - the id of the user whose data is to be queried
   * @return a map referring to the current user data with the key current_user_data
   * @throws SQLException if no database is loaded
   */
  public Map<String, Object> setUserDataFromId(int id) throws SQLException {
    setCurrentUserData(id);
    return ImmutableMap.of(
      "checkin", "",
      "current_user_data", currentUserData,
      "error", "");
  }

  /**
   * Queries the checkin table in the current SQL database for all latitudes and
   * longitudes of all the checkins made by the user having the given id.
   * Updates currentUserData to refer to this data/
   *
   * @param id - the id of the user whose data is to be queried
   * @throws SQLException if no database is loaded
   */
  private void setCurrentUserData(int id) throws SQLException {
    Connection conn = DatabaseHandler.getConn();

    if (conn == null) {
      return;
    }
    String query = "SELECT latitude, longitude FROM checkins WHERE id == ?";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setInt(1, id);

    ResultSet rs = prep.executeQuery();

    currentUserData = new ArrayList<>();

    while (rs.next()) {
      Double[] curLatAndLong = new Double[2];
      curLatAndLong[0] = rs.getDouble("latitude");
      curLatAndLong[1] = rs.getDouble("longitude");

      currentUserData.add(curLatAndLong);
    }

    prep.close();
    rs.close();
  }

}
