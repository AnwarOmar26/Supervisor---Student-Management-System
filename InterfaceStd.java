import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface InterfaceStd extends Remote {

  //  public String getID(String student) throws RemoteException;

    public String getGrade(int StudentID , String course) throws RemoteException;

    public HashMap<String, Double> getAllGrades(int StudentID) throws RemoteException;

    public List<String> getMyCourses(int StudentID) throws RemoteException;

    public String getAllCourses() throws RemoteException;

    public String registerForCourse(int StudentID , String course) throws RemoteException;

    public String dropCourse(int StudentID , String course) throws RemoteException;

    public String getAverage(int StudentID) throws RemoteException;

    public void connect (int StudentID , InterfaceNotificationStd studentnotf) throws RemoteException;

    public void disconnect (int StudentID) throws RemoteException;
}

