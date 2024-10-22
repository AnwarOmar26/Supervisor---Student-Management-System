import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceSupervisor extends Remote {
    public String registerStudent(String student) throws RemoteException;

    public String removeStudent(int studentid) throws RemoteException;

    public String setGrades(int studentID, String course, double grade) throws RemoteException;

    public String addCourse(String course) throws RemoteException;

    public String removeCourse(String course) throws RemoteException;

    public String scheduleSetGrade(int studentID, String course, double grade, long delay) throws RemoteException;

    public String GetStudentID(String studentname) throws RemoteException;
}
