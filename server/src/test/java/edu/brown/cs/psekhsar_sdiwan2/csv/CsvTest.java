package edu.brown.cs.psekhsar_sdiwan2.csv;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class CsvTest {
  @Test
  public void testLoadCsvData() throws FileNotFoundException {
    Csv csv = new Csv();
    List<String[]> splitData = csv.loadCsvData("data/stars/stardata.csv");

    assertEquals(splitData.size(), 119617 + 1);
  }

  @Test
  public void testLoadCsvDataException() {
    assertThrows(FileNotFoundException.class, () -> {
      Csv csv = new Csv();
      csv.loadCsvData("");
    });
  }
}
