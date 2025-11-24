package org.example.json_stat_gen.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonParserService {

    public Map<String, Integer> parseJsonFile(Path file, String attribute) throws IOException {

        Map<String, Integer> localMap = new HashMap<>();

        JsonFactory factory = new JsonFactory();

        try (InputStream in = Files.newInputStream(file);
             JsonParser jp = factory.createParser(in)) {

            String currentField = null;

            while (jp.nextToken() != null) {

                JsonToken token = jp.currentToken();

                if (token == JsonToken.FIELD_NAME) {
                    currentField = jp.getCurrentName();
                }
                else if (token == JsonToken.VALUE_STRING) {
                    if (attribute.equals(currentField)) {
                        countStringValue(jp.getText(), localMap);
                    }
                }
                else if (token == JsonToken.VALUE_NUMBER_INT || token == JsonToken.VALUE_NUMBER_FLOAT) {
                    if (attribute.equals(currentField)) {
                        countSingleValue(jp.getText(), localMap);
                    }
                }
            }
        }

        return localMap;
    }

    private void countStringValue(String raw, Map<String, Integer> map) {
        if (raw == null || raw.isEmpty()) return;

        String[] parts = raw.split(",");
        for (String part : parts) {
            String value = part.trim();
            if (!value.isEmpty()) {
                map.merge(value, 1, Integer::sum);
            }
        }
    }

    private void countSingleValue(String value, Map<String, Integer> map) {
        if (value != null && !value.isEmpty()) {
            map.merge(value, 1, Integer::sum);
        }
    }
}