package es.objects;

import es.objects.Department;
import es.objects.DatabaseDriver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class Student {

    // Student name related data

    private int id;
    private String firstName;
    private String lastName;
    private String studentMail;

    // Student economic related data

    private double balance;
    private double fee;

    // Student academic related data

    private Department department;
    private boolean admitted;

    // Overloading constructor

    public Student(String firstName, String lastName, String studentMail, String department ,boolean admitted){

        this.firstName = firstName;
        this.lastName = lastName;
        this.studentMail = studentMail;
        this.admitted = admitted;
        setDepartment(department);

    }

    public Student(String firstName, String lastName, String department ,boolean admitted){

        this.firstName = firstName;
        this.lastName = lastName;
        this.studentMail = genStudentMail(firstName,lastName);
        this.admitted = admitted;
        setDepartment(department);

    }

    public Student(int id, String firstName, String lastName, String studentMail, String department ,boolean admitted){

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentMail = studentMail;
        this.admitted = admitted;
        setDepartment(department);

    }

    // Student instancing process methods.

    private String genStudentMail(String firstName, String lastName){

        final String company = "pomaretta.es";
        final String FQDN = ".edu";
        String result = "";

        try {
            result = firstName.substring(0,1).toLowerCase() + lastName.toLowerCase() + "@" + company + FQDN;
        } catch (Exception e){
            System.out.println(e);
        }

        return result;
    }

    private void setDepartment(String department){

        this.department = new Department(department);

    }

    // Retrieving student data.

    public String getStudentMail(){
        return this.studentMail;
    }

    public String getDepartment(){
        return this.department.getName();
    }

    public String getAdmitted() { if(this.admitted) { return "YES"; } else { return "NO"; } }

    public String getID() { return String.valueOf(this.id); }

    public String getNames(int x){

        String output = "";

        switch (x){
            case 0:
                output = this.firstName;
                break;
            case 1:
                output = this.lastName;
                break;
        }
        return output;
    }

    // Creating and retrieving Students

    public void createNewStudent(){

        try {

            int department = 0;

            switch (this.department.getName()){
                case "MATH":
                    department = 0;
                    break;
                case "SCIENCE":
                    department = 1;
                    break;
                case "CS":
                    department = 2;
                    break;
                case "LITERATURE":
                    department = 3;
                    break;
            }

            String source = String.format("INSERT INTO Students(firstName,lastName,studentDepartment,admitted,studentMail) VALUES ('%s','%s',%d,%s,'%s')", this.firstName, this.lastName,department, this.admitted,this.studentMail);
            DatabaseDriver.newUpdate(source);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Collection<Student> retrieveStudents(){

        Collection<Student> students = new ArrayList<Student>();
        Student student = null;

        String order = "SELECT * FROM Students";

        try {

            ResultSet dataInput = DatabaseDriver.newQuery(order);

            while(dataInput.next()){

                String department = "";

                switch (dataInput.getInt("studentDepartment")){
                    case 0:
                        department = "MATH";
                        break;
                    case 1:
                        department = "SCIENCE";
                        break;
                    case 2:
                        department = "CS";
                        break;
                    case 3:
                        department = "LITERATURE";
                        break;
                }

                student = new Student(
                        dataInput.getInt("StudentID"),
                        dataInput.getString("firstName"),
                        dataInput.getString("lastName"),
                        dataInput.getString("studentMail"),
                        department,
                        dataInput.getBoolean("admitted")
                        );

                students.add(student);

            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return students;
    }

}