package es.App;

import es.objects.Student;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

public class App {

    public void Start(){

        // Initial variables to set
        boolean loop = true; // For the main loop of the application
        Scanner userInput = new Scanner(System.in); // For get the user input
        String appOrder; // For the initial order of the user

        // Along the app I use the try catch block for avoiding app crashes
        try {

            while(loop){

                // Initial messages of the app
                Messages.printMessage("welcome");
                Messages.printMessage("options");
                Messages.printMessage("order");

                appOrder = userInput.nextLine();

                // Main switch for setting the order of the user
                switch (appOrder.toLowerCase()){
                    case "create":
                        Messages.printMessage("create");

                        try {

                            // Variables for setting the inputs of the user and passing to the Student constructor
                            String fName = "", lName = "", dName = "", aD1 = "", confirm = "";
                            boolean aD = false;

                            Messages.printMessage("firstname");
                            fName = String.valueOf(userInput.next());
                            Messages.printMessage("lastname");
                            lName = String.valueOf(userInput.next());
                            Messages.printMessage("department");
                            dName = String.valueOf(userInput.next()).toUpperCase();

                            boolean admittedLoop = true;

                            do {
                                Messages.printMessage("admitted");
                                aD1 = userInput.next();
                                if(aD1.toLowerCase().equals("yes")){
                                    aD = true;
                                    admittedLoop = false;
                                } else if(aD1.toLowerCase().equals("no")) {
                                    aD = false;
                                    admittedLoop = false;
                                }
                            } while (admittedLoop);

                            // Creating the new Student object
                            Student e = new Student(fName,lName,dName,aD);
                            Messages.printMessage("user",e); // Printing the actual user data passed

                            // Simple confirm window for avoiding human errors
                            System.out.print("\n\n<----- DO YOU CONFIRM: ");
                            confirm = String.valueOf(userInput.next());

                            if(confirm.toLowerCase().equals("yes")){
                                try {
                                    // Creating a new entry for the DB
                                    e.createNewStudent();
                                } catch (Exception e2) { e2.printStackTrace(); }
                            }

                        } catch (Exception e){
                            e.printStackTrace();
                        }

                        Messages.printMessage("endcreate");
                        break;
                    case "retrieve":
                        Messages.printMessage("retrieve");

                        // Getting the user data from database with the retrieveStudents method
                        Collection<Student> students = Student.retrieveStudents();

                        try {

                            // Printing the user data from DB
                            for(Student e: students){

                                Messages.printMessage("user", e);

                            }

                        } catch (Exception retrieveE) { retrieveE.printStackTrace(); }

                        Messages.printMessage("endretrieve");
                        break;
                    case "exit":
                        Messages.printMessage("bye");
                        loop = false;
                        break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

// An App message class for getting the messages instead writing once

class Messages {

    public static void printMessage(String messageToPrint){

        switch (messageToPrint.toLowerCase()){

            case "welcome":
                System.out.println("<---------- WELCOME TO STUDENT MANAGEMENT APP V1 BY CARLOS POMARES g/pomaretta ---------->\n\n");
                break;
            case "bye":
                System.out.println("\n\n<---------- BYE ---------->");
                break;
            case "order":
                System.out.print("---------> Order: ");
                break;
            case "options":
                System.out.println("<--------- OPTIONS --> CREATE ; --> RETRIEVE ; --> PAY ; --> EXIT <---------\n");
                break;
            case "create":
                System.out.print("\n\n<--------- CREATE MODULE --------->\n\n");
                break;
            case "endcreate":
                System.out.print("\n\n<--------- END CREATE MODULE --------->\n\n");
                break;
            case "retrieve":
                System.out.print("\n\n<--------- RETRIEVE MODULE --------->\n\n");
                break;
            case "endretrieve":
                System.out.print("\n\n<--------- END RETRIEVE MODULE --------->\n\n");
                break;
            case "confirm":
                System.out.print("\n\n<--------- CONFIRM MODULE --------->\n\n");
                break;
            case "endconfirm":
                System.out.print("\n\n<--------- END CONFIRM MODULE --------->\n\n");
                break;
            case "firstname":
                System.out.print("---------> Firstname: ");
                break;
            case "lastname":
                System.out.print("---------> Lastname: ");
                break;
            case "department":
                System.out.print("---------> Department: ");
                break;
            case "admitted":
                System.out.print("---------> Admitted: ");
                break;
            case "success":
                System.out.println("SUCCESS");
                break;
            case "error":
                System.out.println("ERROR");
                break;

        }

    }

    // Overloading method for passing a message with object data
    public static void printMessage(String messageToPrint, Student user){

        switch (messageToPrint.toLowerCase()){

            case "user":
                System.out.print("\n\n<----- USER --> Firstname: " + user.getNames(0) + " ; --> Lastname: " + user.getNames(2) + " ; --> Email: " + user.getStudentMail() + " ; --> Department: " + user.getDepartment() + " ; --> Fees balance:  ; " + " --> Admitted: " + user.getAdmitted() + " ; --> ID: " + user.getID() + "\n\n");

        }

    }

    // Tried to clear console without results, F
    public static void clearConsole(){

        try {

            if(System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }

        } catch (IOException | InterruptedException ex){
            ex.printStackTrace();
        }
    }

    // Tried to passing the confirm process as method but I realised that is better setting this in a class
    public static boolean confirmProcess(String x){

        boolean out = false;

        Messages.printMessage("confirm");

        System.out.print("-----> DO YOU CONFIRM: ");

        if(x.toLowerCase().equals("yes")){
            out = true;
        } else if (x.toLowerCase().equals("no")){
            out = false;
        }

        Messages.printMessage("endconfirm");

        return out;
    }

}
