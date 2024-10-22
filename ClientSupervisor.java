import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSupervisor {
    public static void main(String[] args) {
        while (true) {
            try {
                InterfaceSupervisor proxySpr = (InterfaceSupervisor) Naming.lookup("rmi://127.0.0.1:2000/supervisor");
                Scanner key = new Scanner(System.in);

                while (true) {
                    System.out.println("Choose the service by the number :\nRegister a new Student {0} \nRemove a current Student {1} \nSet or Update a Grade for a Student {2}\nAdd a new Course {3} \nRemove a current Course {4} \nSchedule Set Grade for a Student {5}");
                    String in = key.nextLine();

                    switch (in) {
                        case "0":
                            System.out.println("Student name?");
                            String name = key.nextLine();
                            try {
                                String res0 = proxySpr.registerStudent(name);
                                System.out.println(res0 + "\n");
                            }catch (RemoteException e) {
                                System.out.println("invalid name");
                            }
                            break;

                        case "1":
                            System.out.println("Student id?");
                            int StudentID = Integer.parseInt(key.nextLine());
                            try {
                                String res1 = proxySpr.removeStudent(StudentID);
                                System.out.println(res1 + "\n");
                            }catch (RemoteException e) {
                                System.out.println("Student not found");
                            }
                            break;

                        case "2":
                            System.out.println("Student id?");
                            int StudentID2 = Integer.parseInt(key.nextLine());
                            System.out.println("Course name?");
                            String CourseName = key.nextLine();
                            System.out.println("the grade?");
                            Double Grade = Double.valueOf(key.nextLine());
                            try {
                                String res2 = proxySpr.setGrades(StudentID2, CourseName, Grade);
                                System.out.println(res2 + "\n");
                            }catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "3":
                            System.out.println("Course name?");
                            String CourseName2 = key.nextLine();
                            try {
                                String res3 = proxySpr.addCourse(CourseName2);
                                System.out.println(res3 + "\n");
                            } catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "4":
                            System.out.println("Course name?");
                            String CourseName3 = key.nextLine();
                            try {
                                String res4 = proxySpr.removeCourse(CourseName3);
                                System.out.println(res4 + "\n");
                            } catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "5":
                            System.out.println("Student id?");
                            int StudentID3 = Integer.parseInt(key.nextLine());
                            System.out.println("Course name?");
                            String CourseName4 = key.nextLine();
                            System.out.println("the grade?");
                            Double Grade2 = Double.valueOf(key.nextLine());
                            System.out.println("you want the grade to be set after :");
                            long delay = Long.parseLong(key.nextLine());
                            try {
                                String res5 = proxySpr.scheduleSetGrade(StudentID3, CourseName4, Grade2, delay);
                                System.out.println(res5 + "\n");
                            }catch (RemoteException e) {
                                System.out.println(e.getMessage());
                            }
                            break;

                        case "6" :
                            System.out.println("Student name?");
                            String Studentname = key.nextLine();
                            try {
                                String res0 = proxySpr.GetStudentID(Studentname);
                                System.out.println(res0);
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
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
