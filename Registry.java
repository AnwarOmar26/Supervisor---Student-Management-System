import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Registry {
    public static void main(String[] args) throws RemoteException {
        try {
            // TODO code application logic here
            int port = 2000;

            LocateRegistry.createRegistry(port);

            Scanner key = new Scanner(System.in);
            key.nextLine();
        } catch (RemoteException ex) {
            Logger.getLogger(Registry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
