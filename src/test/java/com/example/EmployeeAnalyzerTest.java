package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

class EmployeeAnalyzerTest {

    private EmployeeAnalyzer analyzer;

    @BeforeEach
    void setup() {
        analyzer = new EmployeeAnalyzer();
    }

    @Test
    void testLoadEmployees_ValidFile() {
        analyzer.loadEmployees("src/main/resources/employees_valid.csv");
        assertFalse(analyzer.getEmployees().isEmpty(), "Employees list should not be empty");
    }

    @Test
    void testLoadEmployees_EmptyFile() {
        
        analyzer.loadEmployees("src/main/resources/employees_empty.csv");
        assertTrue(analyzer.getEmployees().isEmpty(), "Employees list should be empty");
    }

    @Test
    void testLoadEmployees_InvalidData() {
        assertThrows(NumberFormatException.class, () -> {
            analyzer.loadEmployees("src/main/resources/employees_invalid.csv");
        }, "Should throw NumberFormatException for invalid salary");
    }

    @Test
    void testBuildHierarchy() throws IOException {
        analyzer.loadEmployees("src/main/resources/employees_valid.csv");
        assertNotNull(analyzer.getCeo(), "CEO should be identified");
        assertEquals(2, analyzer.getCeo().getSubordinates().size(), "CEO should have 2 direct reports");
    }

    @Test
    void testAnalyzeSalaries() throws IOException {
        analyzer.loadEmployees("src/main/resources/employees_valid.csv");
        analyzer.analyzeSalaries();
    }

    @Test
    void testAnalyzeReportingDepth() throws IOException {
        analyzer.loadEmployees("src/main/resources/employees_valid.csv");
        analyzer.analyzeReportingDepth();
    }

    @Test
    void testGetHierarchy() throws IOException {
        analyzer.loadEmployees("src/main/resources/employees_valid.csv");
        List<String> hierarchy = analyzer.getHierarchy(4);
        assertEquals(List.of("John Doe", "Jane Smith", "Lily Evans"), hierarchy, "Hierarchy should be correct");
    }

    @Test
    void testPrintHierarchy() throws IOException {
        analyzer.loadEmployees("src/main/resources/employees_valid.csv");
        analyzer.printHierarchy();
    }
}
