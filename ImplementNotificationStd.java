import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ImplementNotificationStd extends UnicastRemoteObject implements InterfaceNotificationStd {
    protected ImplementNotificationStd() throws RemoteException {
    }

    @Override

    public void notificationStd(String msg) throws RemoteException {
        System.out.println(msg);
    }
}
