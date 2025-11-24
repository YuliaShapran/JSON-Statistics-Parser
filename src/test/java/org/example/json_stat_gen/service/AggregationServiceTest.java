package org.example.json_stat_gen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class AggregationServiceTest {

    private FileScannerService fileScannerService;
    private JsonParserService jsonParserService;
    private XmlService xmlService;

    private AggregationService aggregationService;

    private final Path testFolder = Paths.get("src/test/resources/testFiles");
    private final Path emptyFolder = Paths.get("src/test/resources/emptyFolder");

    @BeforeEach
    void setUp() throws Exception {
        fileScannerService = new FileScannerService();
        jsonParserService = new JsonParserService();
        xmlService = new XmlService();

        aggregationService = new AggregationService(fileScannerService, jsonParserService, xmlService);

    }

    @Test
    void testProcessingDataAggregatesCorrectly() throws Exception {
        aggregationService.processingData(testFolder.toString(), "Java");

        Path xmlFile = testFolder.resolve("statistics_by_Java.xml");

        String xmlContent = Files.readString(xmlFile);
        assertNotNull(xmlContent);
    }

    @Test
    void testEmptyFolder() throws Exception {
        Path emptyFolder = Paths.get("src/test/resources/emptyFolder");
        if (!Files.exists(emptyFolder)) {
            Files.createDirectories(emptyFolder);
        }

        aggregationService.processingData(emptyFolder.toString(), "language");

        Path xmlFile = emptyFolder.resolve("language.xml");
        assertFalse(Files.exists(xmlFile));
    }
}
