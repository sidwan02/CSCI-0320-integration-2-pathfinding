package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.maps;

import edu.brown.cs.psekhsar_sdiwan2.database.DatabaseHandler;
import edu.brown.cs.psekhsar_sdiwan2.main.ErrorMessages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class that checks and executes delete command to erase all of a particular
 * user's data from the current SQL database.
 */
public final class DeleteCommandHandler {

  /**
   * Constructor for the DeleteCommandHandler class.
   * Private to prevent objects from being instantiated outside the class.
   */
  private DeleteCommandHandler() {
  }

  /**
   * Deletes all the data of the user represented in the given command String
   * from the given database.
   *
   * @param command - A string representing the full command entered
   * @param parseKey - A String key representing the function which should parse
   *                 any successful output into the desired format (not relevant
   *                 for the delete command but included to maintain consistency
   *                 with the REPL)
   * @return a string indicating that a successful deletion occurred, else
   * an informative error message
   */
  public static String deleteCommand(String command, String parseKey) {
    String[] splitCommand = command.split(" ");
    try {
      return checkDeleteArgs(splitCommand);
    } catch (NumberFormatException e) {
      return ErrorMessages.DELETE_NON_INT_ARGUMENT;
    } catch (IllegalArgumentException e) {
      return ErrorMessages.DELETE_INVALID_NUMBER_ARGUMENTS;
    } catch (SQLException e) {
      return ErrorMessages.INVALID_SQL_QUERY;
    } catch (NullPointerException e) {
      return ErrorMessages.DELETE_NO_DB_LOADED;
    }
  }

  /**
   * Checks if the command represented by splitCommand was entered in the
   * required format and with a user id that is an integer.
   *
   * @param splitCommand - the command entered by the user split by space
   * @return a string indicating that a successful deletion occurred, else
   * an informative error message
   * @throws SQLException
   */
  static String checkDeleteArgs(String[] splitCommand) throws SQLException {
    if (splitCommand.length == 2) {
      String idString = splitCommand[1];
      int id = Integer.parseInt(idString);
      return deleteUserData(id);
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Deletes all of the user represented by id's data from the
   * currently connected to maps database.
   *
   * @param id - user's id whose data needs to be deleted
   * @return a string indicating that a successful deletion occurred, else
   * an informative error message
   * @throws SQLException
   */
  static String deleteUserData(int id) throws SQLException {
    Connection conn = DatabaseHandler.getConn();

    String query = "DELETE FROM checkins WHERE id == ?";

    PreparedStatement prep = conn.prepareStatement(query);
    prep.setInt(1, id);

    prep.executeUpdate();
    prep.close();

    return "Deletion Successful";
  }

}
