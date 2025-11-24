package org.example.json_stat_gen.service;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserServiceTest {

    private final JsonParserService service = new JsonParserService();

    @Test
    void testParseArrayOfObjectsWithTextValues() throws Exception {
        Path temp = Files.createTempFile("test", ".json");

        String json = """
                [
                  { "genre": "Action, Drama" },
                  { "genre": "Drama" },
                  { "genre": "Comedy, Action" }
                ]
                """;

        Files.writeString(temp, json);

        Map<String, Integer> result = service.parseJsonFile(temp, "genre");

        assertEquals(3, result.size());
        assertEquals(2, result.get("Action"));
        assertEquals(2, result.get("Drama"));
        assertEquals(1, result.get("Comedy"));

        Files.deleteIfExists(temp);
    }

    @Test
    void testParseSingleObjectWithNumberValue() throws Exception {
        Path temp = Files.createTempFile("test", ".json");

        String json = """
                { "year": 1999 }
                """;

        Files.writeString(temp, json);

        Map<String, Integer> result = service.parseJsonFile(temp, "year");

        assertEquals(1, result.size());
        assertEquals(1, result.get("1999"));

        Files.deleteIfExists(temp);
    }

    @Test
    void testMissingAttributeReturnsEmptyMap() throws Exception {
        Path temp = Files.createTempFile("test", ".json");

        String json = """
                [
                  { "title": "A" },
                  { "title": "B" }
                ]
                """;

        Files.writeString(temp, json);

        Map<String, Integer> result = service.parseJsonFile(temp, "genre");

        assertTrue(result.isEmpty());

        Files.deleteIfExists(temp);
    }

    @Test
    void testEmptyStringValue() throws Exception {
        Path temp = Files.createTempFile("test", ".json");

        String json = """
                [
                  { "genre": "" },
                  { "genre": "Action" }
                ]
                """;

        Files.writeString(temp, json);

        Map<String, Integer> result = service.parseJsonFile(temp, "genre");

        assertEquals(1, result.size());
        assertEquals(1, result.get("Action"));

        Files.deleteIfExists(temp);
    }

    @Test
    void testMultipleCommaSeparatedValues() throws Exception {
        Path temp = Files.createTempFile("test", ".json");

        String json = """
                {
                  "genre": "Fantasy, Adventure, Fantasy"
                }
                """;

        Files.writeString(temp, json);

        Map<String, Integer> result = service.parseJsonFile(temp, "genre");

        assertEquals(2, result.get("Fantasy"));
        assertEquals(1, result.get("Adventure"));

        Files.deleteIfExists(temp);
    }
}
