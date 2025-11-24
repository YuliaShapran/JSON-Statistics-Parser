package org.example.json_stat_gen.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileScannerServiceTest {

    private final FileScannerService service = new FileScannerService();

    @Test
    void testReturnsOnlyJsonFiles(@TempDir Path tempDir) throws IOException {
        // створюємо тестові файли
        Path file1 = Files.createFile(tempDir.resolve("a.json"));
        Path file2 = Files.createFile(tempDir.resolve("b.JSON"));
        Path file3 = Files.createFile(tempDir.resolve("c.txt"));
        Path file4 = Files.createFile(tempDir.resolve("d.csv"));

        List<Path> result = service.getListOfFilePaths(tempDir);

        assertTrue(result.contains(file1));
        assertTrue(result.contains(file2));
        assertFalse(result.contains(file3));
        assertFalse(result.contains(file4));
        assertEquals(2, result.size());
    }

    @Test
    void testEmptyDirectory(@TempDir Path tempDir) throws IOException {
        List<Path> result = service.getListOfFilePaths(tempDir);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testIgnoresSubdirectories(@TempDir Path tempDir) throws IOException {
        Path subdir = Files.createDirectory(tempDir.resolve("subdir"));
        Path file1 = Files.createFile(tempDir.resolve("file.json"));

        List<Path> result = service.getListOfFilePaths(tempDir);

        assertTrue(result.contains(file1));
        assertFalse(result.contains(subdir)); // директорія не включається
        assertEquals(1, result.size());
    }

    @Test
    void testHandlesNonexistentFolder() {
        Path nonExistent = Path.of("folder_does_not_exist");

        IOException exception = assertThrows(IOException.class, () ->
                service.getListOfFilePaths(nonExistent)
        );

        assertTrue(exception.getMessage().contains("not_exist"));
    }
}
