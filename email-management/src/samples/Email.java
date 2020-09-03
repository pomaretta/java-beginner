package samples;

import java.io.IOException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Email {

    /*

        For this project need to have this vars:
            -   First name and last name of the user. (2 Inputs)
            -   Randomize the user password
            -   Determine the email capacity of the user
            -   Create an email from the names of the user.
                Optional:
                -   Have a company domain for generate the alternative mail.
                -   Determine the department of the user (1 Input)
                -   Determine the storage capacity of th user (1 Input)

    */

    // The departments with enum statement

    public enum DEPARTMENT {
        SALES,
        WEB,
        MARKETING,
        InD
    }

    // Public data of the user. EMAIL and DEPARTMENT

    DEPARTMENT departmentName;
    public int storageCapacity;
    public String alternativeMail;
    public String companyDomain = ".pomaretta.es";

    // Private data of the user.

    final private String firstName;
    final private String lastName;
    private String password;



    public Email(String fName, String lName, DEPARTMENT dName,int sCapacity){

        // Setting private data of the user

        this.firstName = fName;
        this.lastName = lName;
        this.departmentName = dName;
        this.storageCapacity = sCapacity;

        // Generating public data.

        genMail(fName,lName,dName);
        genPass();
    }

    private String genMail(String fName, String lName, DEPARTMENT dName){

        // This is for capitalize the first letter of the last name.
        String lNameOut = lName.substring(0,1).toUpperCase() + lName.substring(1);

        alternativeMail = fName.toLowerCase() + lNameOut + "@" + dName.toString().toLowerCase() + companyDomain.toLowerCase();
        try {
            return alternativeMail;
        } catch (Exception e){
            return e.toString();
        }
    }

    private String genPass(){

        String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
        String out = "";

        for(int i = 0;i <= 8; i++){

            // This is the random number for the capitalization of the letter.
            int randomizeCapitalLetter = (int)(Math.random() * 10 + 1);

            if(randomizeCapitalLetter <= 2.5){ // The 2.5 is the % for capitalize letter in this case 25% of each letter.
                char letterToCapitalize = (char)alphabet.charAt((int)(Math.random() * 36 * 1));
                out += Character.toUpperCase(letterToCapitalize);
            } else {
                out += alphabet.charAt((int)(Math.random() * 36 * 1));
            }
        }

        this.password = out;

        return this.password;
    }

    public String getData(int order){
        switch(order){
            case 0:
                return this.firstName;
            case 1:
                return this.lastName;
            case 2:
                return this.password;
            case 3:
                return this.alternativeMail;
            case 4:
                return this.departmentName.toString();
            case 5:
                return String.valueOf(this.storageCapacity);
            default:
                return "DEFAULT";
        }
    }

    public void mailReport(Email e){
        System.out.println("\n\nEMAIL INFORMATION OF " + e.getData(0) + " " + e.getData(1) + "; COMPANY MAIL: " + e.getData(3) + " ; PASSWORD: " + e.getData(2) + " ; DEPARTMENT: " + e.getData(4) + " ; StorageCapacity: " + e.getData(5));
    }

    // Is not working because the writing system im using.
    // With the java.util.Formatter using format method replace current data.
    // Of the file and I cannot append new data.

    public boolean saveEmail(){

            try {

                String root = System.getProperty("user.dir");
                String dataDir = root + "/src/samples";
                Path dir = Paths.get(dataDir);

                File usersId = new File(dir + File.separator + "lastSeenId.txt");

                final int newId = 0;

                if(usersId.exists()){
                    createUserData();
                } else {
                    Formatter userId = new Formatter("/usersId.txt");
                    userId.format("%s\n", newId);
                    createUserData();
                }
            } catch (IOException e) {
                System.out.println(e);
            }

        return true;
    }

    private void createUserData(){
        try {
            // Instancing the object for creating new file or writing new data.
            Calendar c = new GregorianCalendar();

            String root = System.getProperty("user.dir");
            String dataDir = root + "/src/samples";
            Path dir = Paths.get(dataDir);
            File usersId = new File(dir + File.separator + "lastSeenId.txt");
            Formatter userData = new Formatter(dir + File.separator + "/userData.txt");

            // Getting the last user id for increment.
            Scanner sc = new Scanner(usersId);

            int lastSeenId = 0;
            int count = 0;

            while(sc.hasNextLine()){
                ++count;
                String lastLine = sc.nextLine();
                if(!sc.hasNextLine()){
                    lastSeenId = Integer.parseInt(lastLine);
                }
            }

            // Writing user data to db.

            String dataToWrite = "";

            for(int i  = 0;i <= 1;i++){
                dataToWrite = String.format("%d;%s-%s-%s;%s;%s;%s;%s;%d\r\n",(lastSeenId + 2),c.get(Calendar.DATE),c.get(Calendar.MONTH),c.get(Calendar.YEAR),this.firstName,this.lastName,this.alternativeMail,this.password,this.storageCapacity);
            }

            userData.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    /*
    public static Email retrieveEmail(){

    }
    */
}
