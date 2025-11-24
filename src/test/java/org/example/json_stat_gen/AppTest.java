package org.example.json_stat_gen;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AppTest {

  @Test
  void testMainWithInsufficientArgsPrintsUsage() {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    try {
      App.main(new String[]{}); // без аргументів
      String output = outContent.toString();
      assertTrue(output.contains("Usage: java -jar target/JsonStatGen-1.0-SNAPSHOT-jar-with-dependencies.jar"));
    } finally {
      System.setOut(originalOut);
    }
  }

}
