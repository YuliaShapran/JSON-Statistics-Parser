package org.example.json_stat_gen.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileScannerService {

    public List<Path> getListOfFilePaths(Path folder) throws IOException {
        try (var stream = Files.list(folder)) {
            return stream.filter(p -> p.toString().toLowerCase().endsWith(".json"))
                    .collect(Collectors.toList());
        }
    }
}
