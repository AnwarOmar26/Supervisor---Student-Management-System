import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceNotificationStd extends Remote {
    public void notificationStd(String msg) throws RemoteException;
}
