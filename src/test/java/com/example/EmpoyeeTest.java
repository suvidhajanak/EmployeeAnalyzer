package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void testGetFullName() {
        Employee emp = new Employee(1, "John", "Doe", 50000, null);
        assertEquals("John Doe", emp.getFullName(), "Full name should be 'John Doe'");
    }
}
