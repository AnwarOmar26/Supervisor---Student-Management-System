import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImplementStd extends UnicastRemoteObject implements InterfaceStd {
    ImplementSupervisor Database = new ImplementSupervisor();
    public ImplementStd(ImplementSupervisor Database) throws RemoteException {
        this.Database = Database;
    }

    @Override
    public String getGrade(int StudentID , String course) throws RemoteException {
        if (!Database.Grades.containsKey(StudentID)) {
            throw new RemoteException("Student " + StudentID + " not found");
        }
        if(!Database.Grades.get(StudentID).containsKey(course)) {
            throw new RemoteException("Course " + course + " not founded for the student : " + StudentID);
        }
        else {
            String res = ("Student "+StudentID+" had a grade : " + Database.Grades.get(StudentID).get(course) + " in course : "+course);
            return res;
        }
    }

    @Override
    public HashMap<String, Double> getAllGrades(int StudentID) throws RemoteException {
        if (!Database.Grades.containsKey(StudentID)) {
            throw new RemoteException("Student " + StudentID + " not found");
        }
        if (Database.Grades.get(StudentID).isEmpty()) {
            throw new RemoteException("Student " + StudentID + " has not registered into a course");
        }
        else {
            HashMap<String, Double> res = ((Database.Grades.get(StudentID)));
            return res;
        }
    }

    @Override
    public List<String> getMyCourses(int StudentID) throws RemoteException {
        if (Database.Grades.get(StudentID) == null) {
            throw new RemoteException("Student " + StudentID + " has not registered into a course");
        }
        else {
            List<String> courses = new ArrayList<>(Database.Grades.get(StudentID).keySet());
            return courses;

        }
    }

    @Override
    public String getAllCourses() throws RemoteException {
        if (Database.AvailableCourses == null) {
            throw new RemoteException("no course available");
        }
        else {
            String res = ("the available courses are : "+ Database.AvailableCourses);
            return res;
        }
    }

    @Override
    public String registerForCourse(int StudentID , String course) throws RemoteException {
        if (!Database.Grades.containsKey(StudentID)) {
            throw new RemoteException("Student " + StudentID + " not found");
        }
        if (!Database.AvailableCourses.contains(course)){
            throw new RemoteException("The Course " + course + " is not available now");
        }
        else {
            Database.Grades.get(StudentID).put(course ,null);
            String res = ("Student " + StudentID + " has been registred to the course : " + course);
            return res;
        }
    }

    @Override
    public String dropCourse(int StudentID , String course) throws RemoteException {
        if (!Database.Grades.containsKey(StudentID)) {
            throw new RemoteException("Student " + StudentID + " not found");
        }
        if (!Database.Grades.get(StudentID).containsKey(course)){
            throw new RemoteException("The Student " + StudentID + " has not registred to this course before");
        }
        else {
            Database.Grades.get(StudentID).remove(course);
            String res = ("Student " + StudentID + " has dropped the course : " + course);
            return res;
        }
    }

    @Override
    public String getAverage(int StudentID) throws RemoteException {
        if (!Database.Grades.containsKey(StudentID)) {
            throw new RemoteException("Student " + StudentID + " not found");
        }
        HashMap<String, Double> studentGrades = Database.Grades.get(StudentID);
        if (studentGrades == null || studentGrades.isEmpty()) {
            throw new RemoteException("Student " + StudentID + " has not registered into any courses");
        }
        double sum = 0;
        int count = 0;
        for (Double grade : studentGrades.values()) {
            if (grade != null) {
                sum += grade;
                count++;
            }
        }
        if (count == 0) {
            throw new RemoteException("Student " + StudentID + " has not received any grades yet");
        }
        double average = sum / count;
        String res = ("Average grade for student ID " + StudentID + ": " + average);
        return res;
    }

    @Override
    public void connect(int StudentID, InterfaceNotificationStd studentnotf) throws RemoteException {
        if (!Database.listStudent.containsKey(StudentID)) {
            throw new RemoteException("Student not found");
        }
        Database.sublist.put(StudentID, studentnotf);
        if(Database.offlinemsgnotification.containsKey(StudentID)) {
            studentnotf.notificationStd(Database.offlinemsgnotification.get(StudentID));
            Database.offlinemsgnotification.remove(StudentID);
        }
    }

    @Override
    public void disconnect(int StudentID) throws RemoteException {
        Database.sublist.remove(StudentID);
    }
}
