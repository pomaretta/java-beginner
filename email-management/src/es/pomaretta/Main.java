package es.pomaretta;

import samples.Email;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner userInput = new Scanner(System.in);
        String order;

        boolean exit = false;

        String fName, lName;
        int sC;
        Email.DEPARTMENT d1 = Email.DEPARTMENT.WEB;

        // App interface loop
        while(!exit){

            System.out.println("Welcome to Pomaretta Company mail generator!\n");
            System.out.println("Avaiable orders: CREATE - EXIT\n");
            System.out.print("What do you want to do: ");

            order = userInput.next();

            try {
                switch (order.toLowerCase()){
                    case "create":

                        System.out.print("\nFirst name: ");
                        fName = userInput.next();
                        System.out.print("\nLast name: ");
                        lName = userInput.next();
                        System.out.print("\nStorage capacity: ");
                        sC = userInput.nextInt();

                        System.out.print("\nIntroduce number of the department 0(SALES) - 1(WEB) - 2(MARKETING) - 3(InD): ");
                        int department = userInput.nextInt();
                        switch (department){
                            case 0:
                                d1 = Email.DEPARTMENT.SALES;
                                break;
                            case 1:
                                d1 = Email.DEPARTMENT.WEB;
                                break;
                            case 2:
                                d1 = Email.DEPARTMENT.MARKETING;
                                break;
                            case 3:
                                d1 = Email.DEPARTMENT.InD;
                                break;
                            default:
                                break;
                        }

                        Email e = new Email(fName,lName,d1,sC);

                        e.mailReport(e);

                        userInput.next();

                        break;
                    default:
                        break;
                }
            } catch (Exception e){
                System.out.println(e);
            }

            if(order.toLowerCase().equals("exit")){
                exit = true;
            }
        }
    }
}
