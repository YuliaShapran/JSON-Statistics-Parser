package org.example.json_stat_gen;

import org.example.json_stat_gen.service.AggregationService;
import org.example.json_stat_gen.service.FileScannerService;
import org.example.json_stat_gen.service.JsonParserService;
import org.example.json_stat_gen.service.XmlService;

public class App {
  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: java -jar target/JsonStatGen-1.0-SNAPSHOT-jar-with-dependencies.jar <folderPath> <attribute> [threads]");
      return;
    }

    String folder = args[0];
    String attribute = args[1];

    AggregationService aggregationService = new AggregationService(new FileScannerService(),
            new JsonParserService(),
            new XmlService());

    aggregationService.processingData(folder, attribute);

  }
}
