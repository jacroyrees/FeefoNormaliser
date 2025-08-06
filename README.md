# Job Title Normaliser

This project provides a Java library for normalising job titles using string similarity algorithms, such as Levenshtein distance. It is designed to map various user-input job titles to a set of canonical job titles, handling variations in case, whitespace, and spelling.

## Features
- Normalises job titles to canonical forms (e.g., "Java Engineer" → "Software Engineer")
- Handles mixed case and extra whitespace
- Returns a default message when no match is found
- Throws exceptions for blank or null input
- Includes unit tests using JUnit and Mockito

## Technologies Used
- Java 8+
- Maven
- JUnit 5
- Mockito

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven

### Build and Test
1. Clone the repository:
   ```sh
   git clone <repo-url>
   cd JobTitleNormalizer
   ```
2. Build the project:
   ```sh
   mvn clean install
   ```
3. Run tests:
   ```sh
   mvn test
   ```

## Usage
You can use the `JobTitleNormaliser` class to normalise job titles:

```java
Normaliser normaliser = new JobTitleNormaliser(new LevenshteinDistanceCalculator());
String result = normaliser.normalise("Java Engineer"); // Returns "Software Engineer"
```

## Project Structure
- `src/main/java/org/example/` - Main source code
- `src/test/java/` - Unit tests
- `pom.xml` - Maven build configuration

## Example Test Cases
- "Java Engineer" → "Software Engineer"
- "C# Engineer" → "Software Engineer"
- "Accountant" → "Accountant"
- "Chief Accountant" → "Accountant"
- "King of Spain" → "No matching job title has been found"
- Handles mixed case and extra whitespace
- Throws `IllegalArgumentException` for blank or null input

# FeefoNormaliser
