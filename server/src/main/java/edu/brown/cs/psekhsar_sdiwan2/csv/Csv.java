package edu.brown.cs.psekhsar_sdiwan2.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/** Class to parse any CSV at a given path location.
 */
public class Csv {

  /** Load the CSV with the filename specified.
   @param filename A String that represents the path to the desired file.
   @throws FileNotFoundException indicating that the file does not exist at
   the passed path.
   @return An ArrayList of Arrays of Strings, where each Array represents the
   column values, and each ArrayList index represents each row in the CSV
   */
  public List<String[]> loadCsvData(String filename) throws FileNotFoundException {
    try {
      BufferedReader in
          = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8));

      Stream<String> allRows = in.lines();

      List<String[]> splitData = new ArrayList<>();

      for (String row : (Iterable<String>) allRows::iterator) {
        String[] splitRow = row.split(",");

        splitData.add(splitRow);
      }
      in.close();
      return splitData;
    } catch (IOException e) {
      throw new FileNotFoundException();
    }
  }
}
