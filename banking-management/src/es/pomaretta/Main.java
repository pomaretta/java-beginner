package es.pomaretta;

import java.util.Scanner;
import objects.Account;

public class Main {

    public static void main(String[] args) {

        // Account creation without DB, creating account data with the init of the program.
        Account a1 = new Account("john","1234","John","Doe");

        // Vars for doing the app loop.
        boolean exit = false;
        boolean login = false;

        // Input of the user.
        Scanner customerInput = new Scanner(System.in);

        // First inputs for doing the login operation.
        String userName;
        String userPassword;

        // Main App loop.
        while(!exit){

            System.out.println("¡Bienvenido a la banca digital PomarettaBank!\n\n");

            System.out.println("¿Que deseas realizar? LOGIN - SIGN IN - EXIT");

            String order = customerInput.nextLine();

            // Using try catch for avoid app crash problems.

            try {
                switch(order.toLowerCase()){
                    case "login":
                        System.out.println("Introduce tu usuario y contraseña.");
                        System.out.println("Usuario: ");
                        userName = customerInput.next();
                        System.out.println("Contraseña: ");
                        userPassword = customerInput.next();
                        // After problem solution tries, first version of this app instantiate the logIn method out of if control.
                        boolean result = a1.logIn(a1,userName,userPassword);
                        if(result){
                            login = true;
                        } else {
                            login = false;
                            System.out.println("Login failed...");
                            exit = true;
                        }
                        break;
                    case "exit":
                        exit = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception e){
                System.out.println(e);
            }

            while(login){

                System.out.println("¡Bienvenido " + a1.getData(0) + " " + a1.getData(1) + " a tu cuenta de PomarettaBank!" );
                System.out.print("Introduce cual operación deseas realizar INGRESO-RETIRADA-BALANCE:");
                order = customerInput.next();

                switch (order.toLowerCase()){
                    case "ingreso":
                        try {
                            System.out.print("¿Que cantidad desea ingresar?: ");
                            double x = customerInput.nextDouble();
                            System.out.print("\nConfirme el ingreso de la cantidad " + x +". SI/NO: ");
                            String y = customerInput.next().toLowerCase();
                            if(y.equals("si")){
                                try{
                                    a1.Deposit(x);
                                } catch (Exception e){
                                    System.out.println(e);
                                }
                            } else {
                                exit = true;
                            }
                        } catch (Exception e){
                            System.out.println(e);
                        }
                        break;
                    case "retirada":
                        try {
                            System.out.print("¿Que cantidad desea retirar?: ");
                            double x = customerInput.nextDouble();
                            System.out.print("\nConfirme el ingreso de la cantidad " + x +". SI/NO: ");
                            String y = customerInput.next().toLowerCase();
                            if(y.equals("si")){
                                try{
                                    if((a1.getBalance() - x) <= -100){
                                        System.out.println("Balance threshold!");
                                        login = false;
                                    } else {
                                        a1.Withdraw(x);
                                    }
                                } catch (Exception e){
                                    System.out.println(e);
                                }
                            } else {
                                exit = true;
                            }
                        } catch (Exception e){
                            System.out.println(e);
                        }
                        break;
                    case "balance":
                        System.out.println("El balance de su cuenta es: " + a1.getBalance());
                        break;
                    case "final":
                        login = false;
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
