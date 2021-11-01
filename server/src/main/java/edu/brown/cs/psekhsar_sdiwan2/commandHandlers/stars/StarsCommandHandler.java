package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars;


import edu.brown.cs.psekhsar_sdiwan2.coordinates.Coordinate;
import edu.brown.cs.psekhsar_sdiwan2.coordinates.KdTree;
import edu.brown.cs.psekhsar_sdiwan2.csv.Csv;
import edu.brown.cs.psekhsar_sdiwan2.stars.Star;
import edu.brown.cs.psekhsar_sdiwan2.main.ErrorMessages;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/** Class that checks and executes stars, naive_neighbors and naive_radius commands
 by loading CSV files and extracting necessary data from aforementioned files.
 */
public final class StarsCommandHandler {
  private StarsCommandHandler() {
  }

  private static BiFunction<Integer, String, String> parseFunc;

  private static final Map<String, BiFunction<Integer, String, String>> VALID_PARSERS
      = new HashMap<>() {{
            put("repl", StarsCommandHandler::parseToRepl);
            put("gui", StarsCommandHandler::parseToRepl);
        }};

  private static List<Star> starsList = null;

  private static KdTree loadedTree = null;

  private static String loadedMessage = "*No Loaded File*";

  /** Return a String that represents the currently loaded CSV.
   @return List of Star.
   */
  public static String getLoadedMessage() {
    return loadedMessage;
  }

  /** Return a List of Star that represents the current state of starsList from
   the most recently loaded CSV.
   @return List of Star.
   */
  public static List<Star> getStarsList() {
    return starsList;
  }

  /** Return a Node of Coordinate of Integer, i.e., a Node of Star in this case,
   that represents the current state of the loaded KdTree from the most recently
   loaded CSV.
   @return Node of Coordinate of Integer i.e. Node of Star
   */
  public static KdTree<Integer, Coordinate<Integer>> getLoadedTree() {
    return loadedTree;
  }

  /** Sets the starsList and loadedTree to null.
   This method is only being used for testing purposes to simulate
   a CSV not being loaded before attempting to execute a valid command.
   */
  public static void clearLoadedData() {
    StarsCommandHandler.starsList = null;
    StarsCommandHandler.loadedTree = null;
  }

  /** Return a String error or computation outcome of the mock command passed
   back to the REPL.
   @param command A String representing the full command entered.
   @param parseKey A String key representing the function which should parse
   any successful output into the desired format.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  public static String starsCommand(String command, String parseKey) {
    parseFunc = VALID_PARSERS.get(parseKey);
    String[] splitCommand = command.split(" ");
    try {
      return checkStarsArgs(splitCommand);
    } catch (IllegalArgumentException e) {
      return ErrorMessages.STARS_INVALID_NUMBER_ARGUMENTS;
    }
  }

  /** Checks whether the mock command passed has valid arguments.
   @param splitCommand An Array of Strings representing each part of an entered
   command
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static String checkStarsArgs(String[] splitCommand) {
    if (splitCommand.length == 2) {
      String filename = splitCommand[1];
      return loadStarsCsv(filename);
    } else {
      throw new IllegalArgumentException();
    }
  }

  /** Handle all errors that may be thrown from calling extractFileRows when
   extracting all rows from the CSV at the passed path.
   @param filename A String containing the path to the CSV to be read.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static String loadStarsCsv(String filename) {
    try {
      return extractFileRows(filename);
    } catch (IndexOutOfBoundsException e) {
      return ErrorMessages.CSV_MISSING_COLUMNS;
    } catch (NumberFormatException e) {
      return ErrorMessages.INVALID_STAR_ID;
    } catch (EOFException e) {
      return ErrorMessages.INVALID_HEADER;
    } catch (FileNotFoundException e) {
      return ErrorMessages.invalidFilepath(filename);
    } catch (NullPointerException e) {
      return ErrorMessages.NO_CSV_SELECTED;
    }
  }

  /** Extract all rows within the CSV with the passed path, where each
   column of data is delimited by commas, then validate each column data
   in each row.
   @param filename A String containing the path to the CSV to be read.
   @throws FileNotFoundException indicating that the file does not exist at
   the passed path.
   @throws EOFException indicating that the file has a invalid/missing header.
   @return 1 String, either an ERROR, or the result of a successful computation
   which may have newlines.
   */
  static String extractFileRows(String filename)
      throws FileNotFoundException, EOFException {
    Csv starsCsv = new Csv();

    List<String[]> loadedData = starsCsv.loadCsvData(filename);
    List<Star> parsedData = new ArrayList<>();

    int id;
    String name;
    double x, y, z;

    String[] headerLine = loadedData.get(0);
    // check if header exists and is valid
    if (headerLine[0].equals("StarID")
        && headerLine[1].equals("ProperName")
        && headerLine[2].equals("X")
        && headerLine[3].equals("Y")
        && headerLine[4].equals("Z")) {
      for (String[] row : loadedData.subList(1, loadedData.size())) {
        if (row.length == 5) {
          id = Integer.parseInt(row[0]);
          if (id < 0) {
            throw new NumberFormatException();
          }
          name = row[1];
          x = Double.parseDouble(row[2]);
          y = Double.parseDouble(row[3]);
          z = Double.parseDouble(row[4]);

          parsedData.add(new Star(id, name, x, y, z));
        } else {
          throw new IndexOutOfBoundsException();
        }
      }
      starsList = parsedData;

      try {
        loadKdTree();
      } catch (IllegalStateException e) {
        System.out.println(ErrorMessages.STARS_INSUFFICIENT_DIMENSIONS);
      }

      int n = starsList.size();
      loadedMessage = parseFunc.apply(n, filename);
      return parseFunc.apply(n, filename);
    } else {
      throw new EOFException();
    }
  }

  /** Use the current state of starsList to populate a KdTree for the Stars loaded from
   the most recently loaded CSV.
   */
  static void loadKdTree() throws IllegalStateException {

    ArrayList<Coordinate<Integer>> coordinates = new ArrayList<>(starsList);

    loadedTree = new KdTree<>(3, coordinates);

    loadedTree.buildTree();
  }

  /** Parses the passed Coordinates into a display String using ID.
   @param n An integer representing the number of loaded Stars from the loaded CSV.
   @param filename A String representing the path to the loaded CSV.
   @return A String concatenating the passed parameters into a meaningful CSV load message.
   */
  static String parseToRepl(int n, String filename) {
    return "Read " + n + " stars from " + filename;
  }
}


// package edu.brown.cs.psekhsar_sdiwan2.commandHandlers.stars;


// import edu.brown.cs.psekhsar_sdiwan2.coordinates.Coordinate;
// import edu.brown.cs.psekhsar_sdiwan2.coordinates.KdTree;
// import edu.brown.cs.psekhsar_sdiwan2.csv.Csv;
// import edu.brown.cs.psekhsar_sdiwan2.stars.Star;

// import java.io.EOFException;
// import java.io.FileNotFoundException;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.function.BiFunction;

// /** Class that checks and executes stars, naive_neighbors and naive_radius commands
//  by loading CSV files and extracting necessary data from aforementioned files.
//  */
// public final class StarsCommandHandler {
//   private StarsCommandHandler() {
//   }

//   private static BiFunction<Integer, String, String> parseFunc;

//   private static final Map<String, BiFunction<Integer, String, String>> VALID_PARSERS
//       = new HashMap<>() {{
//             put("repl", StarsCommandHandler::parseToRepl);
//             put("gui", StarsCommandHandler::parseToRepl);
//         }};

//   private static List<Star> starsList = null;

//   //private static KdTree<Integer, Star> loadedTree = null;
//   private static KdTree<Integer, Coordinate<Integer>> loadedTree = null;

//   private static String loadedMessage = "*No Loaded File*";

//   /** Return a String that represents the currently loaded CSV.
//    @return List of Star.
//    */
//   public static String getLoadedMessage() {
//     return loadedMessage;
//   }

//   /** Return a List of Star that represents the current state of starsList from
//    the most recently loaded CSV.
//    @return List of Star.
//    */
//   public static List<Star> getStarsList() {
//     return starsList;
//   }

//   /** Return a Node of Coordinate of Integer, i.e., a Node of Star in this case,
//    that represents the current state of the loaded KdTree from the most recently
//    loaded CSV.
//    @return Node of Coordinate of Integer i.e. Node of Star
//    */
//   public static KdTree<Integer, Coordinate<Integer>> getLoadedTree() {
//   // public static KdTree<Integer, Star> getLoadedTree() {
//     return loadedTree;
//   }

//   /** Sets the starsList and loadedTree to null.
//    This method is only being used for testing purposes to simulate
//    a CSV not being loaded before attempting to execute a valid command.
//    */
//   public static void clearLoadedData() {
//     StarsCommandHandler.starsList = null;
//     StarsCommandHandler.loadedTree = null;
//   }

//   /** Return a String error or computation outcome of the mock command passed
//    back to the REPL.
//    @param command A String representing the full command entered.
//    @param parseKey A String key representing the function which should parse
//    any successful output into the desired format.
//    @return 1 String, either an ERROR, or the result of a successful computation
//    which may have newlines.
//    */
//   public static String starsCommand(String command, String parseKey) {
//     parseFunc = VALID_PARSERS.get(parseKey);
//     String[] splitCommand = command.split(" ");
//     try {
//       return checkStarsArgs(splitCommand);
//     } catch (IllegalArgumentException e) {
//       return "ERROR: Invalid no.of arguments passed for stars";
//     }
//   }

//   /** Checks whether the mock command passed has valid arguments.
//    @param splitCommand An Array of Strings representing each part of an entered
//    command
//    @return 1 String, either an ERROR, or the result of a successful computation
//    which may have newlines.
//    */
//   static String checkStarsArgs(String[] splitCommand) {
//     if (splitCommand.length == 2) {
//       String filename = splitCommand[1];
//       return loadStarsCsv(filename);
//     } else {
//       throw new IllegalArgumentException();
//     }
//   }

//   /** Handle all errors that may be thrown from calling extractFileRows when
//    extracting all rows from the CSV at the passed path.
//    @param filename A String containing the path to the CSV to be read.
//    @return 1 String, either an ERROR, or the result of a successful computation
//    which may have newlines.
//    */
//   static String loadStarsCsv(String filename) {
//     try {
//       return extractFileRows(filename);
//     } catch (IndexOutOfBoundsException e) {
//       return "ERROR: Missing columns in csv";
//     } catch (NumberFormatException e) {
//       return "ERROR: Star ID must be a non-negative integer";
//     } catch (EOFException e) {
//       return "ERROR: File does not contain header/invalid header found";
//     } catch (FileNotFoundException e) {
//       return "ERROR: File not found";
//     } catch (NullPointerException e) {
//       return "ERROR: No CSV selected";
//     }
//   }

//   /** Extract all rows within the CSV with the passed path, where each
//    column of data is delimited by commas, then validate each column data
//    in each row.
//    @param filename A String containing the path to the CSV to be read.
//    @throws FileNotFoundException indicating that the file does not exist at
//    the passed path.
//    @throws EOFException indicating that the file has a invalid/missing header.
//    @return 1 String, either an ERROR, or the result of a successful computation
//    which may have newlines.
//    */
//   static String extractFileRows(String filename) throws FileNotFoundException, EOFException {
//     Csv starsCsv = new Csv();

//     List<String[]> loadedData = starsCsv.loadCsvData(filename);
//     List<Star> parsedData = new ArrayList<>();

//     int id;
//     String name;
//     double x, y, z;

//     String[] headerLine = loadedData.get(0);
//     // check if header exists and is valid
//     if (headerLine[0].equals("StarID")
//         && headerLine[1].equals("ProperName")
//         && headerLine[2].equals("X")
//         && headerLine[3].equals("Y")
//         && headerLine[4].equals("Z")) {
//       for (String[] row : loadedData.subList(1, loadedData.size())) {
//         if (row.length == 5) {
//           id = Integer.parseInt(row[0]);
//           if (id < 0) {
//             throw new NumberFormatException();
//           }
//           name = row[1];
//           x = Double.parseDouble(row[2]);
//           y = Double.parseDouble(row[3]);
//           z = Double.parseDouble(row[4]);

//           parsedData.add(new Star(id, name, x, y, z));
//         } else {
//           throw new IndexOutOfBoundsException();
//         }
//       }
//       starsList = parsedData;

//       loadKdTree();

//       int n = starsList.size();
//       loadedMessage = parseFunc.apply(n, filename);
//       return parseFunc.apply(n, filename);
//     } else {
//       throw new EOFException();
//     }
//   }

//   /** Use the current state of starsList to populate a KdTree for the Stars loaded from
//    the most recently loaded CSV.
//    */
//   static void loadKdTree() {

//     ArrayList<Star> coordinates = new ArrayList<>(starsList);

//     loadedTree = new KdTree<>(3, coordinates);

//     loadedTree.buildTree();
//   }

//   /** Parses the passed Coordinates into a display String using ID.
//    @param n An integer representing the number of loaded Stars from the loaded CSV.
//    @param filename A String representing the path to the loaded CSV.
//    @return A String concatenating the passed parameters into a meaningful CSV load message.
//    */
//   static String parseToRepl(int n, String filename) {
//     return "Read " + n + " stars from " + filename;
//   }
// }
