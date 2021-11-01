package edu.brown.cs.psekhsar_sdiwan2.checkin;

/**
 * Class for passing user checkin data.
 * (Stencil code provided in handout)
 */
public class SpyCheckin {

  private int id;
  private String name;
  private double ts;
  private double lat;
  private double lon;

  /**
   * Constructs an object of type Spy Checkin.
   *
   * @param userId - unique integer ID number for each spy
   * @param username - name of the given spy
   * @param timestamp - timestamp of the spy's checkin
   * @param latitude - latitude of the spy's checkin position
   * @param longitude - longitude of the spy's checkin position
   */
  public SpyCheckin(
          int userId,
          String username,
          double timestamp,
          double latitude,
          double longitude) {
    id = userId;
    name = username;
    ts = timestamp;
    lat = latitude;
    lon = longitude;
  }

  /**
   * Getter for the unique id field.
   * @return the calling SpyCheckins's id
   */
  public int getId() {
    return id;
  }

  /**
   * Getter for the name field.
   * @return the calling SpyCheckins's name
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for the timestamp field.
   * @return the calling SpyCheckins's timestamp
   */
  public double getTimestamp() {
    return ts;
  }

  /**
   * Getter for the latitude field.
   * @return the calling SpyCheckins's latitude
   */
  public double getLat() {
    return lat;
  }

  /**
   * Getter for the longitude field.
   * @return the calling SpyCheckins's longitude
   */
  public double getLon() {
    return lon;
  }
}
