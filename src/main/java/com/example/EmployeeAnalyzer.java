package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class to analyze employee data, including hierarchy and salary structure.
 */
public class EmployeeAnalyzer {
    private Map<Integer, Employee> employees = new HashMap<>();
    private Employee ceo;

    // Getter for employees
    public Map<Integer, Employee> getEmployees() {
        return employees;
    }

    // Getter for CEO
    public Employee getCeo() {
        return ceo;
    }

    // Setter for CEO
    public void setCeo(Employee ceo) {
        this.ceo = ceo;
    }

    /**
     * Loads employee data from a CSV file and builds the hierarchy.
     * Handles file-related exceptions and invalid data gracefully.
     */
    public void loadEmployees(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            if (lines.size() < 2) {
                System.err.println("Error: CSV file is empty or contains only headers.");
                return;
            }

            for (String line : lines.subList(1, lines.size())) {
                try {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0]);
                    String firstName = parts[1];
                    String lastName = parts[2];
                    double salary=0.0;
                    if (parts.length > 3 && !parts[3].trim().isEmpty()) {
                        //System.out.println("Printing parts 3: " +parts[3]);
                        salary = Double.parseDouble(parts[3]);
                        //System.out.println("Salary : " + salary);
                    }
                    Integer managerId = (parts.length > 4 && !parts[4].isEmpty()) ? Integer.parseInt(parts[4]) : null;
                    Employee employee = new Employee(id, firstName, lastName, salary, managerId);
                    employees.put(id, employee);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping row due to invalid number format: " + line);
                    throw e;
                }
            }
            //System.out.println("Total employees loaded: " + employees.size());

            // Build the reporting hierarchy
            buildHierarchy();
        } catch (IOException e) {
            System.err.println("Error: Could not read the file - " + filePath);
        }
    }

    /**
     * Checks for circular references in employee hierarchy.
     */
   private boolean hasCircularReference(int empId) {
        Set<Integer> visited = new HashSet<>();
        Integer current = empId;
        while (current != null) {
            if (visited.contains(current)) {
                return true; // Circular reference detected
            }
            visited.add(current);
            Employee employee = employees.get(current);
            current = (employee != null) ? employee.getManagerId() : null;
        }
        return false;
    }

    /**
     * Builds the reporting hierarchy by linking employees to their managers.
     */
    private void buildHierarchy() {
        for (Employee emp : employees.values()) {
            if (emp.getManagerId() == null) {
                setCeo(emp);
                System.out.println("CEO Identified: " + emp.getFullName());
            } else {
                if (hasCircularReference(emp.getId())) {
                    System.err.println("Error: Circular reporting detected for employee " + emp.getId());
                    continue;
                }
                Employee manager = employees.get(emp.getManagerId());
                if (manager != null) {
                    manager.getSubordinates().add(emp);
                } else {
                    System.err.println("Warning: Manager ID " + emp.getManagerId() + " for employee " + emp.getId() + " not found.");
                }
            }
        }

        if (getCeo() == null) {
            System.err.println("Error: No CEO found. Printing all employees for debugging:");
            for (Employee emp : employees.values()) {
                System.err.println("Employee ID: " + emp.getId() + ", Manager ID: " + emp.getManagerId());
            }
        }
    }

    /**
     * Analyzes salaries and checks if managers' salaries are within the allowed range.
     */
    public void analyzeSalaries() {
        for (Employee manager : employees.values()) {
            if (!manager.getSubordinates().isEmpty()) {
                double avgSubordinateSalary = manager.getSubordinates().stream()
                        .mapToDouble(Employee::getSalary).average().orElse(0);

                double minAllowed = avgSubordinateSalary * 1.2;
                double maxAllowed = avgSubordinateSalary * 1.5;

                if (manager.getSalary() < minAllowed) {
                    System.out.printf("Manager %s earns %.2f less than allowed.%n", manager.getFirstName(), minAllowed - manager.getSalary());
                } else if (manager.getSalary() > maxAllowed) {
                    System.out.printf("Manager %s earns %.2f more than allowed.%n", manager.getFirstName(), manager.getSalary() - maxAllowed);
                }
            }
        }
    }

    /**
     * Analyzes the reporting depth and warns if it exceeds 4 levels.
     */
    public void analyzeReportingDepth() {
        for (Employee emp : employees.values()) {
            int depth = getReportingDepth(emp);
            if (depth > 4) {
                System.out.printf("Employee %s has %d managers above them, exceeding the limit by %d.%n",
                        emp.getFirstName(), depth, depth - 4);
            }
        }
    }

    /**
     * Calculates the reporting depth of an employee.
     */
    private int getReportingDepth(Employee emp) {
        int depth = 0;
        while (emp.getManagerId() != null) {
            if (!employees.containsKey(emp.getManagerId())) {
                System.err.println("Error: Manager ID " + emp.getManagerId() + " not found.");
                return -1;
            }
            emp = employees.get(emp.getManagerId());
            depth++;
        }
        return depth;
    }

    /**
     * Returns the hierarchy path from an employee to the CEO.
     */
    public List<String> getHierarchy(int employeeId) {
        List<String> hierarchy = new LinkedList<>();
        Employee current = employees.get(employeeId);

        while (current != null) {
            hierarchy.add(0, current.getFullName());
            current = current.getManagerId() != null ? employees.get(current.getManagerId()) : null;
        }

        return hierarchy;
    }

    /**
     * Prints the reporting hierarchy for each employee.
     */
    public void printHierarchy() {
        System.out.println("Hierarchy from each employee to the CEO:\n");
        for (Employee emp : employees.values()) {
            List<String> hierarchy = getHierarchy(emp.getId());
            System.out.println(emp.getFullName() + "'s hierarchy: " + String.join(" -> ", hierarchy));
        }
    }

    /**
     * Main method to run the EmployeeAnalyzer program.
     */
    public static void main(String[] args) {
        try {
            EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
            analyzer.loadEmployees("src/main/resources/employees1.csv");
            analyzer.analyzeSalaries();
            analyzer.analyzeReportingDepth();
            analyzer.printHierarchy();
        } catch (Exception e) {
            e.printStackTrace(); // Print full error stack trace
        }
    }
}
