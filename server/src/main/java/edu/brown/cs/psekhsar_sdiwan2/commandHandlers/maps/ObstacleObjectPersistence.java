package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import edu.brown.cs.psekhsar_sdiwan2.traffic.ObstacleThread;

/**
 * Class to store reference to currently running ObstacleThread.
 */
public final class ObstacleObjectPersistence {

  /**
   * Constructs an object of type CheckinObjectPersistence.
   *
   * Private to prevent objects from being instantiated outside the class
   */
  private ObstacleObjectPersistence() {
  }

  private static ObstacleThread curObstacleObj;

  /**
   * Getter method for curCheckinObj.
   *
   * @return the current value of curCheckinObj
   */
  public static ObstacleThread getObstacleObj() {
    return curObstacleObj;
  }

  /**
   * Setter method for curCheckinObj.
   *
   * @param obstTh - ObstacleThread object to set curCheckinObj with
   */
  public static void setObstacleObj(ObstacleThread obstTh) {
    System.out.println("obstTh: " + obstTh);
    ObstacleObjectPersistence.curObstacleObj = obstTh;
  }
}
