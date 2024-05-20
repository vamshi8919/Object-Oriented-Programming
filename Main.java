import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Basic Person class to inherit from
class Person {
    private String name;
    private int age;
    private String id;

    public Person(String name, int age, String id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

// Student class inheriting from Person
class Student extends Person {
    private List<Course> courses;

    public Student(String name, int age, String id) {
        super(name, age, id);
        this.courses = new ArrayList<>();
    }

    public void enrollCourse(Course course) {
        this.courses.add(course);
        course.addStudent(this);
    }

    public void dropCourse(Course course) {
        this.courses.remove(course);
        course.removeStudent(this);
    }

    public List<Course> getCourses() {
        return courses;
    }
}

// Teacher class inheriting from Person
class Teacher extends Person {
    private List<Course> courses;

    public Teacher(String name, int age, String id) {
        super(name, age, id);
        this.courses = new ArrayList<>();
    }

    public void assignCourse(Course course) {
        this.courses.add(course);
        course.setTeacher(this);
    }

    public void unassignCourse(Course course) {
        this.courses.remove(course);
        course.removeTeacher();
    }

    public List<Course> getCourses() {
        return courses;
    }
}

// Course class
class Course {
    private String courseName;
    private String courseId;
    private Teacher teacher;
    private List<Student> students;
    private Map<Student, String> grades;

    public Course(String courseName, String courseId) {
        this.courseName = courseName;
        this.courseId = courseId;
        this.students = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        grades.remove(student);
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void removeTeacher() {
        this.teacher = null;
    }

    public void assignGrade(Student student, String grade) {
        if (students.contains(student)) {
            grades.put(student, grade);
        } else {
            System.out.println("Student not enrolled in the course");
        }
    }

    public String getGrade(Student student) {
        return grades.get(student);
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public List<Student> getStudents() {
        return students;
    }
}

// Management system class
class StudentManagementSystem {
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Course> courses;

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
        this.teachers = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    // Add a student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Delete a student
    public void deleteStudent(String studentId) {
        Student student = getStudent(studentId);
        if (student != null) {
            for (Course course : student.getCourses()) {
                course.removeStudent(student);
            }
            students.remove(student);
        }
    }

    // Update a student
    public void updateStudent(String studentId, String newName, int newAge) {
        Student student = getStudent(studentId);
        if (student != null) {
            student.setName(newName);
            student.setAge(newAge);
        }
    }

    // Add a teacher
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    // Delete a teacher
    public void deleteTeacher(String teacherId) {
        Teacher teacher = getTeacher(teacherId);
        if (teacher != null) {
            for (Course course : teacher.getCourses()) {
                course.removeTeacher();
            }
            teachers.remove(teacher);
        }
    }

    // Update a teacher
    public void updateTeacher(String teacherId, String newName, int newAge) {
        Teacher teacher = getTeacher(teacherId);
        if (teacher != null) {
            teacher.setName(newName);
            teacher.setAge(newAge);
        }
    }

    // Add a course
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Delete a course
    public void deleteCourse(String courseId) {
        Course course = getCourse(courseId);
        if (course != null) {
            for (Student student : course.getStudents()) {
                student.dropCourse(course);
            }
            if (course.getTeacher() != null) {
                course.getTeacher().unassignCourse(course);
            }
            courses.remove(course);
        }
    }

    // Enroll a student in a course
    public void enrollStudentInCourse(String studentId, String courseId) {
        Student student = getStudent(studentId);
        Course course = getCourse(courseId);
        if (student != null && course != null) {
            student.enrollCourse(course);
        }
    }

    // Assign a teacher to a course
    public void assignTeacherToCourse(String teacherId, String courseId) {
        Teacher teacher = getTeacher(teacherId);
        Course course = getCourse(courseId);
        if (teacher != null && course != null) {
            teacher.assignCourse(course);
        }
    }

    // Assign grade to a student
    public void assignGradeToStudent(String studentId, String courseId, String grade) {
        Student student = getStudent(studentId);
        Course course = getCourse(courseId);
        if (student != null && course != null) {
            course.assignGrade(student, grade);
        }
    }

    // Get student information
    public Student getStudent(String studentId) {
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    // Get teacher information
    public Teacher getTeacher(String teacherId) {
        for (Teacher teacher : teachers) {
            if (teacher.getId().equals(teacherId)) {
                return teacher;
            }
        }
        return null;
    }

    // Get course information
    public Course getCourse(String courseId) {
        for (Course course : courses) {
            if (course.getCourseId().equals(courseId)) {
                return course;
            }
        }
        return null;
    }

    // Get all students
    public List<Student> getAllStudents() {
        return students;
    }
}

// Main class to demonstrate functionality
public class Main {
    private static StudentManagementSystem sms = new StudentManagementSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    addTeacher();
                    break;
                case 5:
                    updateTeacher();
                    break;
                case 6:
                    deleteTeacher();
                    break;
                case 7:
                    addCourse();
                    break;
                case 8:
                    deleteCourse();
                    break;
                case 9:
                    enrollStudentInCourse();
                    break;
                case 10:
                    assignTeacherToCourse();
                    break;
                case 11:
                    assignGradeToStudent();
                    break;
                case 12:
                    showStudentInfo();
                    break;
                case 13:
                    showTeacherInfo();
                    break;
                case 14:
                    showCourseInfo();
                    break;
                case 15:
                    showAllStudents();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nStudent Management System");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");
        System.out.println("4. Add Teacher");
        System.out.println("5. Update Teacher");
        System.out.println("6. Delete Teacher");
        System.out.println("7. Add Course");
        System.out.println("8. Delete Course");
        System.out.println("9. Enroll Student in Course");
        System.out.println("10. Assign Teacher to Course");
        System.out.println("11. Assign Grade to Student");
        System.out.println("12. Show Student Information");
        System.out.println("13. Show Teacher Information");
        System.out.println("14. Show Course Information");
        System.out.println("15. Show All Students");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = new Student(name, age, id);
        sms.addStudent(student);
        System.out.println("Student added successfully.");
    }

    private static void updateStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        sms.updateStudent(id, name, age);
        System.out.println("Student updated successfully.");
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        sms.deleteStudent(id);
        System.out.println("Student deleted successfully.");
    }

    private static void addTeacher() {
        System.out.print("Enter teacher name: ");
        String name = scanner.nextLine();
        System.out.print("Enter teacher age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter teacher ID: ");
        String id = scanner.nextLine();
        Teacher teacher = new Teacher(name, age, id);
        sms.addTeacher(teacher);
        System.out.println("Teacher added successfully.");
    }

    private static void updateTeacher() {
        System.out.print("Enter teacher ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline
        sms.updateTeacher(id, name, age);
        System.out.println("Teacher updated successfully.");
    }

    private static void deleteTeacher() {
        System.out.print("Enter teacher ID: ");
        String id = scanner.nextLine();
        sms.deleteTeacher(id);
        System.out.println("Teacher deleted successfully.");
    }

    private static void addCourse() {
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter course ID: ");
        String id = scanner.nextLine();
        Course course = new Course(name, id);
        sms.addCourse(course);
        System.out.println("Course added successfully.");
    }

    private static void deleteCourse() {
        System.out.print("Enter course ID: ");
        String id = scanner.nextLine();
        sms.deleteCourse(id);
        System.out.println("Course deleted successfully.");
    }

    private static void enrollStudentInCourse() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();
        sms.enrollStudentInCourse(studentId, courseId);
        System.out.println("Student enrolled in course successfully.");
    }

    private static void assignTeacherToCourse() {
        System.out.print("Enter teacher ID: ");
        String teacherId = scanner.nextLine();
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();
        sms.assignTeacherToCourse(teacherId, courseId);
        System.out.println("Teacher assigned to course successfully.");
    }

    private static void assignGradeToStudent() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();
        sms.assignGradeToStudent(studentId, courseId, grade);
        System.out.println("Grade assigned to student successfully.");
    }

    private static void showStudentInfo() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();
        Student student = sms.getStudent(studentId);
        if (student != null) {
            System.out.println("Name: " + student.getName());
            System.out.println("Age: " + student.getAge());
            System.out.println("ID: " + student.getId());
            System.out.println("Courses: ");
            for (Course course : student.getCourses()) {
                System.out.println(course.getCourseName() + " (ID: " + course.getCourseId() + ")");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void showTeacherInfo() {
        System.out.print("Enter teacher ID: ");
        String teacherId = scanner.nextLine();
        Teacher teacher = sms.getTeacher(teacherId);
        if (teacher != null) {
            System.out.println("Name: " + teacher.getName());
            System.out.println("Age: " + teacher.getAge());
            System.out.println("ID: " + teacher.getId());
            System.out.println("Courses: ");
            for (Course course : teacher.getCourses()) {
                System.out.println(course.getCourseName() + " (ID: " + course.getCourseId() + ")");
            }
        } else {
            System.out.println("Teacher not found.");
        }
    }

    private static void showCourseInfo() {
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();
        Course course = sms.getCourse(courseId);
        if (course != null) {
            System.out.println("Course Name: " + course.getCourseName());
            System.out.println("Course ID: " + course.getCourseId());
            Teacher teacher = course.getTeacher();
            if (teacher != null) {
                System.out.println("Teacher: " + teacher.getName());
            } else {
                System.out.println("No teacher assigned.");
            }
            System.out.println("Students: ");
            for (Student student : course.getStudents()) {
                System.out.println(student.getName() + " (ID: " + student.getId() + ")");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    private static void showAllStudents() {
        List<Student> students = sms.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student student : students) {
                System.out.println("Name: " + student.getName());
                System.out.println("Age: " + student.getAge());
                System.out.println("ID: " + student.getId());
                System.out.println("Courses: ");
                for (Course course : student.getCourses()) {
                    System.out.println("  - " + course.getCourseName() + " (ID: " + course.getCourseId() + ")");
                }
                System.out.println();
            }
        }
    }
}
