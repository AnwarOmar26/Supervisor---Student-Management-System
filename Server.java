import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Salah
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here

            InterfaceSupervisor Supervisor =new ImplementSupervisor();
            Naming.rebind("rmi://127.0.0.1:2000/supervisor", Supervisor);
            InterfaceStd Student =new ImplementStd((ImplementSupervisor) Supervisor);
            Naming.rebind("rmi://127.0.0.1:2000/student", Student);

            System.out.println("Attente des clients");

        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}