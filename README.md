# **EmployeeAnalyzer: Java-Based Organizational Analysis Exercise**  

## **📌 Overview**  
This exercise is designed to help you understand **organizational hierarchy analysis** using **Java, Maven, and GitHub**. The project reads employee data from a **CSV file**, builds a **hierarchy**, checks for **salary compliance**, and ensures **reporting depth limits** are followed.  

By following this exercise, you will:  
✅ Learn how to **process CSV data in Java**.  
✅ Understand **employee-manager relationships** in an organization.  
✅ Implement **salary validation logic** using Java collections.  
✅ Use **JUnit to write test cases**.  

---

## **🛠️ What You Need to Try This Yourself**  
### **1️⃣ Prerequisites**  
Make sure you have the following installed on your system:  
- **Java Development Kit (JDK 17 or later)** → [Download Here](https://adoptopenjdk.net/)  
- **Apache Maven** → [Download Here](https://maven.apache.org/download.cgi)  
- **Git (for version control)** → [Download Here](https://git-scm.com/downloads)  
- **VS Code (or any Java IDE)** → [Download Here](https://code.visualstudio.com/)  
- **JUnit for Testing** (included in Maven dependencies)  

---

## **🚀 How to Set Up and Run the Project**  
### **1️⃣ Clone the Repository from GitHub**  
Open **VS Code** and run the following commands in the terminal:  
```sh
git clone https://github.com/YOUR_USERNAME/EmployeeAnalyzer.git
cd EmployeeAnalyzer
```

### **2️⃣ Build the Project with Maven**  
```sh
mvn clean install
```
This compiles the code and downloads dependencies.

### **3️⃣ Run the Employee Analysis Program**  
```sh
mvn exec:java -Dexec.mainClass="com.example.EmployeeAnalyzer"
```
The program will:  
- Load employee data from `employees.csv`.  
- Validate **manager salary rules** (20%-50% salary difference).  
- Check if **any employees have more than 4 managers**.  
- Print the **hierarchical structure** from employees to the CEO.  

---

## **📝 How to Modify the Employee Data**  
You can edit the **`employees.csv`** file in `src/main/resources/` to test different cases:  

| Id  | First Name | Last Name | Salary | Manager Id |
|-----|-----------|----------|--------|-----------|
| 1   | Alice     | Johnson  | 200000 | (CEO)    |
| 2   | Bob       | Smith    | 120000 | 1         |
| 3   | Charlie   | Brown    | 100000 | 2         |
| 4   | David     | Lee      | 90000  | 3         |
| 5   | Eva       | Garcia   | 80000  | 4         |

Then rerun the program to see how it **analyzes salaries and hierarchy**.

---

## **🛠️ Running the Tests**  
The project includes **JUnit test cases** in `EmployeeAnalyzerTest.java`.  
To run the tests, use:
```sh
mvn test
```
This will validate:  
✅ **CSV file loading**  
✅ **Hierarchy construction**  
✅ **Salary validation rules**  
✅ **Reporting depth calculations**  

---

💡 **Need Help?** Let me know if you have questions! 🚀

