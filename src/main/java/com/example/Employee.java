package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an Employee with attributes and subordinates list.
 */
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private double salary;
    private Integer managerId;
    private List<Employee> subordinates;

    /**
     * Constructor to initialize an Employee object.
     */
    public Employee(int id, String firstName, String lastName, double salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
        this.subordinates = new ArrayList<>();
    }

    // Getter methods
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }
    public Integer getManagerId() { return managerId; }
    public List<Employee> getSubordinates() { return subordinates; }

    // Setter methods
    public void setSalary(double salary) { this.salary = salary; }
    public void addSubordinate(Employee employee) { subordinates.add(employee); }
    public void setManagerId(Integer managerId) {
        if (managerId != null && managerId.equals(this.id)) {
            throw new IllegalArgumentException("Employee cannot be their own manager.");
        }
        this.managerId = managerId;
    }

    /**
     * Returns full name of the employee.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

}
