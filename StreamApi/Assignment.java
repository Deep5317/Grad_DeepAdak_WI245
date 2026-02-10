package StreamApi;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Assignment {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Amit", 25, "Male", 30000, "Developer", "IT"));
        employees.add(new Employee("Sneha", 28, "Female", 35000, "Tester", "QA"));
        employees.add(new Employee("Rahul", 32, "Male", 50000, "Manager", "HR"));
        employees.add(new Employee("Priya", 26, "Female", 32000, "Analyst", "Finance"));
        employees.add(new Employee("Vikram", 35, "Male", 60000, "Team Lead", "IT"));
        employees.add(new Employee("Neha", 24, "Female", 28000, "Support Exec", "Support"));
        employees.add(new Employee("Arjun", 29, "Male", 40000, "Developer", "IT"));
        employees.add(new Employee("Kavya", 31, "Female", 45000, "Designer", "UI/UX"));
        employees.add(new Employee("Rohan", 27, "Male", 37000, "Tester", "QA"));
        employees.add(new Employee("Pooja", 30, "Female", 42000, "HR Exec", "HR"));

        employees.add(new Employee("Karan", 34, "Male", 52000, "Architect", "IT"));
        employees.add(new Employee("Anjali", 29, "Female", 39000, "Accountant", "Finance"));
        employees.add(new Employee("Deepak", 41, "Male", 70000, "Senior Manager", "Operations"));
        employees.add(new Employee("Meera", 23, "Female", 26000, "Intern", "IT"));
        employees.add(new Employee("Suresh", 38, "Male", 58000, "Admin", "Admin"));
        employees.add(new Employee("Nisha", 27, "Female", 36000, "Recruiter", "HR"));
        employees.add(new Employee("Varun", 33, "Male", 48000, "Consultant", "Finance"));
        employees.add(new Employee("Isha", 25, "Female", 31000, "Coordinator", "Operations"));
        employees.add(new Employee("Manoj", 45, "Male", 75000, "Director", "Management"));
        employees.add(new Employee("Ritu", 28, "Female", 34000, "Content Writer", "Marketing"));
        System.out.println("All employees in the company:");
       employees.forEach(System.out::println);

        Employee highestPaidEmployee=employees.stream().max(Comparator.comparingInt(e->e.salary)).orElse(null);
        System.out.println("Higest paid employee:");
        System.out.println(highestPaidEmployee);

        Map<String, Long> genderCountMap=employees.stream().collect(Collectors.groupingBy(e->e.gender,Collectors.counting()));
        System.out.println("Number of employees by gender:");
        System.out.println(genderCountMap);

        Map<String, Integer> departmentExpenseMap=employees.stream().collect(Collectors.groupingBy(e->e.department,Collectors.summingInt(e->e.salary)));
        System.out.println("Total expense by department:");
        System.out.println(departmentExpenseMap);

        Map<String, List<Employee>> managers=employees.stream().filter(e->e.designation.toLowerCase().contains("manager")).collect(Collectors.groupingBy(e->e.name));
        System.out.println("Names of managers:");
        System.out.println(managers);
        
        employees.stream().filter(e->!e.designation.toLowerCase().contains("manager")).map(e->e.salary*1.2);
        employees.forEach(System.out::println);
        
        System.out.println("top 5 senior employees in the company");
        employees.stream().sorted((e1,e2)->Integer.compare(e2.age, e1.age)).limit(5).forEach(System.out::println);
        System.out.println("Total Employee:");
        System.out.println(employees.size());
    }
}

class Employee {
    public String name;
    public int age;
    public String gender;
    public int salary;
    public String designation;
    public String department;

    Employee(String name, int age, String gender, int salary, String designation, String department) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.designation = designation;
        this.department = department;
    }
    public String toString() {
        return "Employee{name='" + name + "', age=" + age + ", gender='" + gender + "', salary=" + salary + ", designation='" + designation + "', department='" + department + "'}";
    }

}

// Employee
// ----------
// name
// age
// gender
// salary
// designation
// department

// * Find the highest salary paid employee
// * Find how many male & female employees working in company (numbers)
// * Total expense for the company department wise
// * Who is the top 5 senior employees in the company
// * Find only the names who all are managers
// * Hike the salary by 20% for everyone except manager
// * Find the total number of employees