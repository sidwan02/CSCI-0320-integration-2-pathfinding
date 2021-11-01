package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import edu.brown.cs.psekhsar_sdiwan2.checkin.CheckinThread;

/**
 * Class to store reference to currently running ObstacleThread.
 */
public final class CheckinObjectPersistence {

  /**
   * Constructs an object of type CheckinObjectPersistence.
   *
   * Private to prevent objects from being instantiated outside the class
   */
  private CheckinObjectPersistence() {
  }

  private static CheckinThread curCheckinObj;

  /**
   * Getter method for curCheckinObj.
   *
   * @return the current value of curCheckinObj
   */
  public static CheckinThread getCheckinObj() {
    return curCheckinObj;
  }

  /**
   * Setter method for curCheckinObj.
   *
   * @param checkTh - ObstacleThread object to set curCheckinObj with
   */
  public static void setCheckinObj(CheckinThread checkTh) {
    CheckinObjectPersistence.curCheckinObj = checkTh;
  }
}
