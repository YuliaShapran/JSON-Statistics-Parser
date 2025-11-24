# JsonStatGen

**JsonStatGen** is a Java-based tool that scans directories, parses JSON files, aggregates statistics by a selected attribute, and exports the results to an XML file.
The project is optimized for processing many large files using asynchronous operations via `CompletableFuture`.

## Features

- Recursive directory scan for `.json` files
- Streaming JSON parsing with minimal memory usage
- Aggregation by any JSON attribute
- Export of sorted results to XML
- Multi-threaded asynchronous processing

## Project Structure

```
src/
 ├── main/java/org/example/json_stat_gen/
 │    ├── dto/               # Data-transfer objects
 │    ├── service/           # Core services: scanning, parsing, XML writing, aggregation
 │    └── App.java           # Application entry point
 ├── main/resources/         # Sample JSON files, output XML files
 └── test/java/...           # Unit tests
```

## Core Components

### **FileScannerService**
Returns all JSON file paths found in a directory.

### **JsonParserService**
Streams JSON using Jackson `JsonParser`, extracts values, returns:

```
Map<String, Integer>
```

### **AggregationService**
Processes each file asynchronously, merges results, sorts, and exports XML.

### **XmlService**
Generates the output XML file based on aggregated statistics.

### **ItemDto**
Represents a value and its aggregated count.

## Installation

1. Install **JDK 21**
2. Clone the repository
3. Install dependencies with Maven:
   ```bash
   mvn clean install
   ```

## Running the Application

Run the main JAR:

```bash
java -jar target/JsonStatGen-1.0-SNAPSHOT-jar-with-dependencies.jar <folderPath> <attribute>
```

## Example Input JSON Files

`movie1.json`
```json
{
  "title": "Matrix",
  "genre": "Sci-Fi",
  "year": 1999
}
```

`movie2.json`
```json
{
  "title": "Avatar",
  "genre": "Sci-Fi"
}
```

`movie3.json`
```json
{
  "title": "Titanic",
  "genre": "Drama"
}
```

## Example Output XML

```xml
<statistics attribute="genre">
    <item>
        <value>Sci-Fi</value>
        <count>2</count>
    </item>
    <item>
        <value>Drama</value>
        <count>1</count>
    </item>
</statistics>
```

## ⚡ Performance Experiments (8 JSON files)

The following results were obtained using different thread counts while processing 8 JSON files:

| Threads | Time (ms) | Observation |
|---------|-----------|-------------|
| **1**   | 257 ms    | Sequential, lowest overhead |
| **2**   | 261 ms    | Slight improvement |
| **4**   | 280 ms    | Optimal for 8 files |
| **8**   | 295 ms    | No gain, more overhead |

### **Conclusion**
- 4 threads provide optimal performance
- Increasing threads beyond available work produces diminishing returns
- JSON streaming ensures excellent memory efficiency

## Tests

Includes tests for:

- JSON parsing
- File scanning
- Aggregation logic (no mocks)
- XML generation

Run tests:

```bash
mvn test
```

