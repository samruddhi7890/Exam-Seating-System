import java.util.*;

interface ExamSeating {
    void assignSeat();
}

class Student {
    String name, subject;
    int rollNumber;

    Student(String name, int rollNumber, String subject) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return name + " (Roll No: " + rollNumber + ", Subject: " + subject + ")";
    }
}

class SeatingArrangement implements ExamSeating {
    private List<Student> students = new ArrayList<>();
    private List<ArrayList<Student>> rooms = new ArrayList<>();
    private int maxPerRoom;

    SeatingArrangement(int maxPerRoom) {
        this.maxPerRoom = maxPerRoom;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    @Override
    public void assignSeat() {

        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(i).rollNumber > students.get(j).rollNumber) {

                    Student temp = students.get(i);
                    students.set(i, students.get(j));
                    students.set(j, temp);
                }
            }
        }

        List<Student> arrangedSeating = new ArrayList<>();

        while (!students.isEmpty()) {
            Student current = students.remove(0);

            boolean isFirstStudent = arrangedSeating.isEmpty();
            boolean isDifferentSubject = true;

            if (!isFirstStudent) {
                Student lastStudent = arrangedSeating.get(arrangedSeating.size() - 1);
                isDifferentSubject = lastStudent.subject != null && current.subject != null
                        && !lastStudent.subject.equalsIgnoreCase(current.subject);
            }

            if (isFirstStudent || isDifferentSubject) {
                arrangedSeating.add(current);
            } else {
                students.add(current);
                if (students.size() == 1) {
                    arrangedSeating.add(students.remove(0));
                }
            }
        }

        for (int i = 0; i < arrangedSeating.size(); i += maxPerRoom) {
            int endIndex = Math.min(i + maxPerRoom, arrangedSeating.size());
            ArrayList<Student> newRoom = new ArrayList<>(arrangedSeating.subList(i, endIndex));
            rooms.add(newRoom);
        }

        System.out.println("\n--- Exam Seating Arrangement ---");
        for (int i = 0; i < rooms.size(); i++) {
            System.out.println("\nRoom " + (i + 1) + ":");
            for (Student s : rooms.get(i)) {
                System.out.println(s);
            }
        }
    }
}

public class ExamSeatingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the maximum number of students per room: ");
        int maxPerRoom = scanner.nextInt();
        scanner.nextLine();

        SeatingArrangement arrangement = new SeatingArrangement(maxPerRoom);

        System.out.print("Enter number of students: ");
        int studentCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < studentCount; i++) {
            System.out.println("\nEnter details for Student " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Roll Number: ");
            int rollNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Subject: ");
            String subject = scanner.nextLine();

            arrangement.addStudent(new Student(name, rollNumber, subject));
        }

        arrangement.assignSeat();
        scanner.close();
    }
}
