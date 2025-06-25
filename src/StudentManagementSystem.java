import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

// Student class with encapsulation
class Student {
    private int id;
    private String name;
    private double marks;
    private static int nextId = 1; // Static variable for auto-incrementing ID

    public Student() {
        this.id = nextId++;
        this.name = "";
        this.marks = 0.0;
    }

    public Student(String name, double marks) {
        this.id = nextId++;
        this.name = name;
        this.marks = marks;
    }

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        // Update nextId if provided id is greater
        if (id >= nextId) {
            nextId = id + 1;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    // Method to get grade based on marks
    public String getGrade() {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 50) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %-20s | Marks: %.2f | Grade: %s",
                id, name, marks, getGrade());
    }
}

// Main class for Student Management System
public class StudentManagementSystem {
    private static ArrayList<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== STUDENT RECORD MANAGEMENT SYSTEM ===");
        System.out.println("Welcome to the Student Management System!");

        // Add some sample data
        addSampleData();

        int choice;
        do {
            displayMenu();
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        viewStudentById();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        deleteStudent();
                        break;
                    case 6:
                        sortStudents();
                        break;
                    case 7:
                        searchStudent();
                        break;
                    case 8:
                        displayStatistics();
                        break;
                    case 0:
                        System.out.println("Thank you for using Student Management System!");
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Please enter a number between 0-8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.");
                choice = -1; // Continue the loop
            }

            if (choice != 0) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            STUDENT MANAGEMENT MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. ➕ Add New Student");
        System.out.println("2. 📋 View All Students");
        System.out.println("3. 🔍 View Student by ID");
        System.out.println("4. ✏️  Update Student");
        System.out.println("5. 🗑️  Delete Student");
        System.out.println("6. 📊 Sort Students");
        System.out.println("7. 🔎 Search Student");
        System.out.println("8. 📈 Display Statistics");
        System.out.println("0. 🚪 Exit");
        System.out.println("=".repeat(50));
    }

    private static void addStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");

        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("❌ Name cannot be empty!");
                return;
            }

            System.out.print("Enter marks (0-100): ");
            double marks = Double.parseDouble(scanner.nextLine());

            if (marks < 0 || marks > 100) {
                System.out.println("❌ Marks must be between 0 and 100!");
                return;
            }

            Student student = new Student(name, marks);
            students.add(student);

            System.out.println("✅ Student added successfully!");
            System.out.println("Student Details: " + student);

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid marks! Please enter a valid number.");
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n--- ALL STUDENTS ---");

        if (students.isEmpty()) {
            System.out.println("❌ No students found in the system.");
            return;
        }

        System.out.println("Total Students: " + students.size());
        System.out.println("-".repeat(70));

        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("-".repeat(70));
    }

    private static void viewStudentById() {
        System.out.println("\n--- VIEW STUDENT BY ID ---");

        try {
            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student student = findStudentById(id);
            if (student != null) {
                System.out.println("✅ Student found:");
                System.out.println("-".repeat(70));
                System.out.println(student);
                System.out.println("-".repeat(70));
            } else {
                System.out.println("❌ Student with ID " + id + " not found!");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID! Please enter a valid number.");
        }
    }

    private static void updateStudent() {
        System.out.println("\n--- UPDATE STUDENT ---");

        try {
            System.out.print("Enter student ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student student = findStudentById(id);
            if (student == null) {
                System.out.println("❌ Student with ID " + id + " not found!");
                return;
            }

            System.out.println("Current Details: " + student);
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Marks");
            System.out.println("3. Both");
            System.out.print("Enter choice: ");

            int updateChoice = Integer.parseInt(scanner.nextLine());

            switch (updateChoice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine().trim();
                    if (!newName.isEmpty()) {
                        student.setName(newName);
                        System.out.println("✅ Name updated successfully!");
                    } else {
                        System.out.println("❌ Name cannot be empty!");
                    }
                    break;

                case 2:
                    System.out.print("Enter new marks (0-100): ");
                    double newMarks = Double.parseDouble(scanner.nextLine());
                    if (newMarks >= 0 && newMarks <= 100) {
                        student.setMarks(newMarks);
                        System.out.println("✅ Marks updated successfully!");
                    } else {
                        System.out.println("❌ Marks must be between 0 and 100!");
                    }
                    break;

                case 3:
                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter new marks (0-100): ");
                    double marks = Double.parseDouble(scanner.nextLine());

                    if (!name.isEmpty() && marks >= 0 && marks <= 100) {
                        student.setName(name);
                        student.setMarks(marks);
                        System.out.println("✅ Student details updated successfully!");
                    } else {
                        System.out.println("❌ Invalid input! Name cannot be empty and marks must be 0-100.");
                    }
                    break;

                default:
                    System.out.println("❌ Invalid choice!");
                    return;
            }

            System.out.println("Updated Details: " + student);

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter valid numbers.");
        }
    }

    private static void deleteStudent() {
        System.out.println("\n--- DELETE STUDENT ---");

        try {
            System.out.print("Enter student ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            Student student = findStudentById(id);
            if (student == null) {
                System.out.println("❌ Student with ID " + id + " not found!");
                return;
            }

            System.out.println("Student to delete: " + student);
            System.out.print("Are you sure you want to delete this student? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("y") || confirm.equals("yes")) {
                students.remove(student);
                System.out.println("✅ Student deleted successfully!");
            } else {
                System.out.println("❌ Deletion cancelled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID! Please enter a valid number.");
        }
    }

    private static void sortStudents() {
        System.out.println("\n--- SORT STUDENTS ---");

        if (students.isEmpty()) {
            System.out.println("❌ No students to sort!");
            return;
        }

        System.out.println("Sort by:");
        System.out.println("1. ID (Ascending)");
        System.out.println("2. Name (A-Z)");
        System.out.println("3. Marks (Highest to Lowest)");
        System.out.println("4. Marks (Lowest to Highest)");
        System.out.print("Enter choice: ");

        try {
            int sortChoice = Integer.parseInt(scanner.nextLine());

            switch (sortChoice) {
                case 1:
                    Collections.sort(students, Comparator.comparingInt(Student::getId));
                    System.out.println("✅ Students sorted by ID (Ascending)");
                    break;
                case 2:
                    Collections.sort(students, Comparator.comparing(Student::getName));
                    System.out.println("✅ Students sorted by Name (A-Z)");
                    break;
                case 3:
                    Collections.sort(students, Comparator.comparingDouble(Student::getMarks).reversed());
                    System.out.println("✅ Students sorted by Marks (Highest to Lowest)");
                    break;
                case 4:
                    Collections.sort(students, Comparator.comparingDouble(Student::getMarks));
                    System.out.println("✅ Students sorted by Marks (Lowest to Highest)");
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
                    return;
            }

            viewAllStudents();

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a valid number.");
        }
    }

    private static void searchStudent() {
        System.out.println("\n--- SEARCH STUDENT ---");

        if (students.isEmpty()) {
            System.out.println("❌ No students to search!");
            return;
        }

        System.out.println("Search by:");
        System.out.println("1. Name");
        System.out.println("2. Grade");
        System.out.print("Enter choice: ");

        try {
            int searchChoice = Integer.parseInt(scanner.nextLine());

            switch (searchChoice) {
                case 1:
                    System.out.print("Enter name to search: ");
                    String searchName = scanner.nextLine().trim().toLowerCase();
                    searchByName(searchName);
                    break;
                case 2:
                    System.out.print("Enter grade to search (A+, A, B, C, D, F): ");
                    String searchGrade = scanner.nextLine().trim().toUpperCase();
                    searchByGrade(searchGrade);
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a valid number.");
        }
    }

    private static void searchByName(String searchName) {
        ArrayList<Student> foundStudents = new ArrayList<>();

        for (Student student : students) {
            if (student.getName().toLowerCase().contains(searchName)) {
                foundStudents.add(student);
            }
        }

        if (foundStudents.isEmpty()) {
            System.out.println("❌ No students found with name containing: " + searchName);
        } else {
            System.out.println("✅ Found " + foundStudents.size() + " student(s):");
            System.out.println("-".repeat(70));
            for (Student student : foundStudents) {
                System.out.println(student);
            }
            System.out.println("-".repeat(70));
        }
    }

    private static void searchByGrade(String searchGrade) {
        ArrayList<Student> foundStudents = new ArrayList<>();

        for (Student student : students) {
            if (student.getGrade().equals(searchGrade)) {
                foundStudents.add(student);
            }
        }

        if (foundStudents.isEmpty()) {
            System.out.println("❌ No students found with grade: " + searchGrade);
        } else {
            System.out.println("✅ Found " + foundStudents.size() + " student(s) with grade " + searchGrade + ":");
            System.out.println("-".repeat(70));
            for (Student student : foundStudents) {
                System.out.println(student);
            }
            System.out.println("-".repeat(70));
        }
    }

    private static void displayStatistics() {
        System.out.println("\n--- STUDENT STATISTICS ---");

        if (students.isEmpty()) {
            System.out.println("❌ No students to analyze!");
            return;
        }

        double totalMarks = 0;
        double highestMarks = students.get(0).getMarks();
        double lowestMarks = students.get(0).getMarks();
        Student topStudent = students.get(0);

        // Count grades
        int[] gradeCount = new int[6]; // A+, A, B, C, D, F

        for (Student student : students) {
            double marks = student.getMarks();
            totalMarks += marks;

            if (marks > highestMarks) {
                highestMarks = marks;
                topStudent = student;
            }
            if (marks < lowestMarks) {
                lowestMarks = marks;
            }

            // Count grades
            String grade = student.getGrade();
            switch (grade) {
                case "A+": gradeCount[0]++; break;
                case "A": gradeCount[1]++; break;
                case "B": gradeCount[2]++; break;
                case "C": gradeCount[3]++; break;
                case "D": gradeCount[4]++; break;
                case "F": gradeCount[5]++; break;
            }
        }

        double averageMarks = totalMarks / students.size();

        System.out.println("=".repeat(50));
        System.out.println("Total Students: " + students.size());
        System.out.println("Average Marks: " + String.format("%.2f", averageMarks));
        System.out.println("Highest Marks: " + String.format("%.2f", highestMarks));
        System.out.println("Lowest Marks: " + String.format("%.2f", lowestMarks));
        System.out.println("Top Student: " + topStudent.getName() + " (ID: " + topStudent.getId() + ")");

        System.out.println("\nGrade Distribution:");
        String[] gradeLabels = {"A+", "A", "B", "C", "D", "F"};
        for (int i = 0; i < gradeCount.length; i++) {
            if (gradeCount[i] > 0) {
                double percentage = (gradeCount[i] * 100.0) / students.size();
                System.out.println(gradeLabels[i] + ": " + gradeCount[i] +
                        " students (" + String.format("%.1f", percentage) + "%)");
            }
        }
        System.out.println("=".repeat(50));
    }

    private static Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private static void addSampleData() {
        // Adding some sample students for demonstration
        students.add(new Student("Alice Johnson", 95.5));
        students.add(new Student("Bob Smith", 78.0));
        students.add(new Student("Charlie Brown", 82.5));
        students.add(new Student("Diana Prince", 91.0));
        students.add(new Student("Edward Norton", 67.5));
    }
}