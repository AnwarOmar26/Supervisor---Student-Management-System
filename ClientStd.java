import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientStd {

    public static void main(String[] args) {
        while (true) {
            try {


                InterfaceStd proxyStd = (InterfaceStd) Naming.lookup("rmi://127.0.0.1:2000/student");
                Scanner key = new Scanner(System.in);
                System.out.println("Student ID?");
                int StudentID = Integer.parseInt(key.nextLine());
                InterfaceNotificationStd studentnotf = new ImplementNotificationStd();
                proxyStd.connect(StudentID , studentnotf);
                outerloop:
                while (true) {
                    System.out.println("\nChoose an option:\nGet grade for a course {0}\nGet all your grades {1}\nGet your courses {2}\nGet all available courses {3}\nRegister for a course {4}\nDrop a course {5}\nGet your average grade {6}\nExit {7}");
                    String in = key.nextLine();
                    switch (in) {

                        case "0":
                            //System.out.println("Student ID?");
                            //int StudentID09 = Integer.parseInt(key.nextLine());
                            System.out.println("Course name?");
                            String CourseName = key.nextLine();
                            try {
                                String res1 = proxyStd.getGrade(StudentID, CourseName);
                                System.out.println(res1);
                            }catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "1":
                            //System.out.println("Student ID?");
                            //int StudentID2 = Integer.parseInt(key.nextLine());
                            try {
                                HashMap<String, Double> res2 = proxyStd.getAllGrades(StudentID);
                                System.out.println("here your grades : " + res2 + "\n");
                            }catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "2":
                            //System.out.println("Student ID?");
                            //int StudentID3 = Integer.parseInt(key.nextLine());
                            try {
                                List<String> res3 = proxyStd.getMyCourses(StudentID);
                                System.out.println("here your Courses : " + res3 + "\n");
                            }catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "3":
                            try {
                                String res4 = proxyStd.getAllCourses();
                                System.out.println(res4);
                            } catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "4":
                            //System.out.println("Student ID?");
                            //int StudentID4 = Integer.parseInt(key.nextLine());
                            System.out.println("Course name?");
                            String CourseName2 = key.nextLine();
                            try {
                                String res5 = proxyStd.registerForCourse(StudentID, CourseName2);
                                System.out.println(res5);
                            } catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "5":
                            //System.out.println("Student ID?");
                            //int StudentID5 = Integer.parseInt(key.nextLine());
                            System.out.println("Course name?");
                            String CourseName3 = key.nextLine();
                            try {
                                String res6 = proxyStd.dropCourse(StudentID, CourseName3);
                                System.out.println(res6);
                            } catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "6":
                            //System.out.println("Student ID?");
                            //int StudentID6 = Integer.parseInt(key.nextLine());
                            try {
                                String res7 = proxyStd.getAverage(StudentID);
                                System.out.println(res7);
                            } catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case "7" :
                            try {
                                proxyStd.disconnect(StudentID);
                                System.exit(0);
                            }catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }

                            break;

                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                }
            } catch (NotBoundException | MalformedURLException ex) {
                Logger.getLogger(ClientSupervisor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException e) {
                System.err.println("An error occurred: " + e.getMessage());
                //e.printStackTrace();
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
