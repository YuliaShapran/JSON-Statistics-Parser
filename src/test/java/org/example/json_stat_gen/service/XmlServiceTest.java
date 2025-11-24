package org.example.json_stat_gen.service;

import org.example.json_stat_gen.dto.ItemDto;
import org.example.json_stat_gen.dto.StatisticsNodeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XmlServiceTest {

    private final XmlService xmlService = new XmlService();

    @Test
    void testWriteXmlFileCreatesFileWithCorrectContent(@TempDir Path tempDir) throws Exception {
        List<ItemDto> items = List.of(
                new ItemDto("Romance", 2),
                new ItemDto("Dystopian", 1)
        );

        String attribute = "genre";
        xmlService.writeXmlFile(tempDir.toString(), attribute, items);

        File xmlFile = tempDir.resolve("statistics_by_" + attribute + ".xml").toFile();
        assertTrue(xmlFile.exists());
        assertTrue(xmlFile.length() > 0);

        JAXBContext context = JAXBContext.newInstance(StatisticsNodeDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StatisticsNodeDto readStatistics = (StatisticsNodeDto) unmarshaller.unmarshal(xmlFile);

        assertNotNull(readStatistics);
        assertEquals(2, readStatistics.getItems().size());

        assertEquals("Romance", readStatistics.getItems().get(0).getValue());
        assertEquals(2, readStatistics.getItems().get(0).getCount());

        assertEquals("Dystopian", readStatistics.getItems().get(1).getValue());
        assertEquals(1, readStatistics.getItems().get(1).getCount());
    }
}
