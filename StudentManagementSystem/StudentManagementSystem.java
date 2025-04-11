import java.io.*;
import java.util.*;

class Student implements Serializable {
    int rollNo;
    String name;
    String department;

    Student(int rollNo, String name, String department) {
        this.rollNo = rollNo;
        this.name = name;
        this.department = department;
    }

    public String toString() {
        return "Roll No: " + rollNo + ", Name: " + name + ", Department: " + department;
    }
}

public class StudentManagementSystem {
    static final String FILE_NAME = "students.dat";
    static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        loadStudents();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Add Student\n2. View Students\n3. Update Student\n4. Delete Student\n5. Exit\nChoose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> addStudent(scanner);
                case 2 -> viewStudents();
                case 3 -> updateStudent(scanner);
                case 4 -> deleteStudent(scanner);
                case 5 -> { saveStudents(); return; }
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    static void addStudent(Scanner scanner) {
        System.out.print("Enter Roll No: ");
        int roll = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String dept = scanner.nextLine();
        students.add(new Student(roll, name, dept));
        System.out.println("Student added successfully.");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.forEach(System.out::println);
    }

    static void updateStudent(Scanner scanner) {
        System.out.print("Enter Roll No of student to update: ");
        int roll = scanner.nextInt();
        scanner.nextLine();
        for (Student s : students) {
            if (s.rollNo == roll) {
                System.out.print("Enter new Name: ");
                s.name = scanner.nextLine();
                System.out.print("Enter new Department: ");
                s.department = scanner.nextLine();
                System.out.println("Student updated successfully.");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    static void deleteStudent(Scanner scanner) {
        System.out.print("Enter Roll No of student to delete: ");
        int roll = scanner.nextInt();
        students.removeIf(s -> s.rollNo == roll);
        System.out.println("Student deleted if existed.");
    }

    static void saveStudents() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    static void loadStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            students = (List<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }
}
