import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ImplementSupervisor extends UnicastRemoteObject implements InterfaceSupervisor {

    HashMap <Integer, String > listStudent = new HashMap<>();
    List<String> AvailableCourses = new ArrayList<>();
    HashMap <Integer , HashMap<String , Double>> Grades = new HashMap<>();
    HashMap <Integer , String> offlinemsgnotification = new HashMap<>();
    public ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ImplementSupervisor() throws RemoteException {
        AvailableCourses = new ArrayList<>();
    }

    @Override
    public String registerStudent(String student) throws RemoteException {
        if (!student.matches("[a-zA-Z]+")) {
            throw new RemoteException("Name can only contain alphabets: " + student);
        }

        else {
            Random random = new Random();

            // Generate a random number between 100 and 999 (inclusive)
            int studentid = 100 + random.nextInt(900);
            String res = ("the student "+student +" has been registered with the id :" +studentid);
            listStudent.put(studentid , student);
            Grades.put(studentid, new HashMap<>());
            return res;
        }
    }

    @Override
    public String removeStudent(int studentid) throws RemoteException {
        if (!listStudent.containsKey(studentid))
            throw new RemoteException("Student not found");
        else {
            listStudent.remove(studentid);
            Grades.remove(studentid);
            return "Student with ID " + studentid + " has been removed";
        }
    }

    @Override
    public String setGrades(int studentID, String course, double grade) throws RemoteException {
        if (!Grades.containsKey(studentID)) {
            throw new RemoteException("Student not found");
        }
        if (!Grades.get(studentID).containsKey(course)) {
            throw new RemoteException("Course not found");
        }
        if (grade > 100 || grade < 0) {
            throw new RemoteException("Grade must be between 0 and 100");
        }
        else {
            Grades.get(studentID).put(course , grade);
            String res = ("Grade set for student ID " + studentID + " in course " + course + ": " + grade);

            if (sublist.containsKey(studentID)) {
                InterfaceNotificationStd student = sublist.get(studentID);
                if (student != null) {
                    student.notificationStd("You have a new grade in course " + course +" ");
                }

            }
            else {
                offlinemsgnotification.put(studentID , "You have a new grade in course " + course +" ");
            }
            return res;
        }
    }

    @Override
    public String addCourse(String course) throws RemoteException {
        if (!course.matches("[a-zA-Z]+")) {
            throw new RemoteException("Course Name can only contain alphabets: " + course);
        }

        if (AvailableCourses.contains(course)) {
            throw new RemoteException("Course already exists");
        }
        AvailableCourses.add(course);
        String res = ("Course added: " + course);
        return res;
    }

    @Override
    public String removeCourse(String course) throws RemoteException {
        if (!AvailableCourses.contains(course)) {
            throw new RemoteException("Course not found");
        }
        else {
            AvailableCourses.remove(course);
            for (HashMap.Entry<Integer, HashMap<String, Double>> entry : Grades.entrySet()) {
                entry.getValue().remove(course);
            }
            return "Course " + course + " has been removed";
        }

    }

    @Override
    public String scheduleSetGrade(int studentID, String course, double grade, long delay) throws RemoteException {
        if (!listStudent.containsKey(studentID)) {
            throw new RemoteException("Student not found");
        }
        if (!AvailableCourses.contains(course)) {
            throw new RemoteException("Course not found");
        }
        scheduler.schedule(() -> {
            try {
                setGrades(studentID, course, grade);
                System.out.println("Scheduled grade set for student ID " + studentID + " in course " + course + ": " + grade);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }, delay, TimeUnit.SECONDS);
        return "Grade for student ID " + studentID + " in course " + course + " scheduled to be set after " + delay + " seconds.";
    }

    @Override
    public String GetStudentID(String studentname) throws RemoteException {
        boolean found = false;
        for (HashMap.Entry<Integer, String> entry : listStudent.entrySet()) {
            if (entry.getValue().equals(studentname)) {
                found = true;
                String res = ("Student ID: " + entry.getKey());
                return res;
            }
        }
        if (!studentname.matches("[a-zA-Z]+")) {
            throw new RemoteException("Name can only contain alphabets: " + studentname);
        }
        else {
            throw new RemoteException("Student " + studentname + " not found");
        }
    }

    HashMap<Integer, InterfaceNotificationStd> sublist = new HashMap<>();
}
