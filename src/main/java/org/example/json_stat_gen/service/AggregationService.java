package org.example.json_stat_gen.service;

import org.example.json_stat_gen.dto.ItemDto;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AggregationService {

    private final FileScannerService fileScannerService;
    private final JsonParserService jsonParserService;
    private final XmlService xmlService;

    public AggregationService(FileScannerService fileScannerService,
                              JsonParserService jsonParserService,
                              XmlService xmlService) {
        this.fileScannerService = fileScannerService;
        this.jsonParserService = jsonParserService;
        this.xmlService = xmlService;
    }

    public void processingData(String folder, String attribute) {

        try {
            var start = System.currentTimeMillis();
            var paths = fileScannerService.getListOfFilePaths(Paths.get(folder));

            // Кожен файл -> CompletableFuture (асинхронно)
            List<CompletableFuture<Map<String, Integer>>> tasks = paths.stream()
                    .map(path -> CompletableFuture.supplyAsync(() -> {
                        try {
                            return jsonParserService.parseJsonFile(path, attribute);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }))
                    .toList();

            // Очікуємо всі Future та робимо reduce
            Map<String, Integer> globalMap = tasks.stream()
                    .map(CompletableFuture::join)
                    .reduce(new HashMap<>(), (acc, local) -> {
                        local.forEach((key, val) ->
                                acc.merge(key, val, Integer::sum)
                        );
                        return acc;
                    });

            // Сортуємо результати
            var items = globalMap.entrySet().stream()
                    .map(e -> new ItemDto(e.getKey(), e.getValue()))
                    .sorted((a, b) -> Integer.compare(b.getCount(), a.getCount()))
                    .toList();

            xmlService.writeXmlFile(folder, attribute, items);

            var finish = System.currentTimeMillis();
            System.out.println("Aggregation completed in " + (finish - start) + " ms was processed " + paths.size() + " files.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}