package edu.brown.cs.ta.obstacle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * thread that continuously communicates with obstacle server.
 */
public final class ObstacleThread extends Thread {
  private long last = 0;
  private NavigableMap<Double, List<Obstacle>> obstaclePositions;
  private boolean pause = false;
  static final long MSCONVERSION = 1000;

  /**
   * Constructs a ObstacleThread object.
   */
  public ObstacleThread() {
    obstaclePositions = new TreeMap<>();
  }

  /**
   * runs the thread by querying the url for information on obstacle positions.
   */
  public synchronized void run() {
    System.out.println("RUNNING");

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

          List<Obstacle> obstaclePositionsArr = new ArrayList<>();

          double mostRecentTimestamp = 0;

          for (List<String> el : updates) {
            double timestamp = Double.parseDouble(el.get(0));

            if (timestamp > mostRecentTimestamp) {
              mostRecentTimestamp = timestamp;
            }

            int id = Integer.parseInt(el.get(1));
            String name = el.get(2);
            double lat = Double.parseDouble(el.get(3));
            double lon = Double.parseDouble(el.get(4));

            // put in concurrent hashmap
            Obstacle obsPos = new Obstacle(id, name, timestamp, lat, lon);
            obstaclePositionsArr.add(obsPos);
          }

          obstaclePositions.put(mostRecentTimestamp, obstaclePositionsArr);
        }
        lastSec = sec;
      }
    }
  }

  /**
   * Queries and returns updated data from the obstacle server.
   *
   * @return a list of string lists representing the updated data
   * queried from the obstacle server
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
        String timestamp = matcher.group(1);
        String parsedTimestamp = timestamp.replace("[", "").replace("]", "");
        data.add(parsedTimestamp);
        data.add(matcher.group(2));
        data.add(matcher.group(3));
        data.add(matcher.group(4));
        data.add(matcher.group(5));
        output.add(data);
      }
    }

    if (output.size() > 5){
      // this means that it is getting the data from multiple updates from startup
      return new ArrayList<>();
    } else {
      return output;
    }
  }

  /**
   * gets the latest checkin updates. Refreshes hashmap so only new
   * checkin updates are returned next time.
   *
   * @return map entry containing the most recent obstacle positions
   */
  public Map.Entry<Double, List<Obstacle>> getLatestObstaclePositions() {
    pause = true;
    Map.Entry<Double, List<Obstacle>> temp = obstaclePositions.lastEntry();
    obstaclePositions = new TreeMap<>();
    pause = false;
    return temp;
  }
}
