package edu.brown.cs.psekhsar_sdiwan2.traffic;

/**
 * Class for passing user checkin data.
 */
public class Obstacle {

  private int id;
  private String name;
  private double ts;
  private double lat;
  private double lon;

  /**
   * Constructs an object of type User Checkin.
   *
   * @param obstacleId - unique integer ID number for each user
   * @param obstacleName - name of the given obstacle
   * @param timestamp - timestamp of the obstacle's checkin
   * @param latitude - latitude of the obstacle's checkin position
   * @param longitude - longitude of the obstacle's checkin position
   */
  public Obstacle(
    int obstacleId,
    String obstacleName,
    double timestamp,
    double latitude,
    double longitude) {
    id = obstacleId;
    name = obstacleName;
    ts = timestamp;
    lat = latitude;
    lon = longitude;
  }

  /**
   * Getter for the unique id field.
   * @return the calling UserCheckins's id
   */
  public int getId() {
    return id;
  }

  /**
   * Getter for the name field.
   * @return the calling UserCheckins's name
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for the timestamp field.
   * @return the calling UserCheckins's timestamp
   */
  public double getTimestamp() {
    return ts;
  }

  /**
   * Getter for the latitude field.
   * @return the calling UserCheckins's latitude
   */
  public double getLat() {
    return lat;
  }

  /**
   * Getter for the longitude field.
   * @return the calling UserCheckins's longitude
   */
  public double getLon() {
    return lon;
  }
}
