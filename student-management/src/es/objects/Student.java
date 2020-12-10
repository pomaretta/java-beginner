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

    private departments department;
    private boolean admitted;

    // Overloading constructor

    public Student(String firstName, String lastName, String studentMail, departments department ,boolean admitted){

        this.firstName = firstName;
        this.lastName = lastName;
        this.studentMail = studentMail;
        this.admitted = admitted;
        setDepartment(department);

        this.createNewStudent();

    }

    public Student(String firstName, String lastName, departments department ,boolean admitted){

        this.firstName = firstName;
        this.lastName = lastName;
        this.studentMail = genStudentMail(firstName,lastName);
        this.admitted = admitted;
        setDepartment(department);

        this.createNewStudent();

    }

    public Student(int id, String firstName, String lastName, String studentMail, departments department ,boolean admitted){

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentMail = studentMail;
        this.admitted = admitted;
        setDepartment(department);

        this.createNewStudent();

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

    public void setDepartment(departments department){
        this.department = department;
    }

    // Retrieving student data.

    public String getStudentMail(){
        return this.studentMail;
    }

    public departments getDepartment(){
        return this.department;
    }

    public boolean getAdmitted() { return this.admitted; }

    public String getID() { return String.valueOf(this.id); }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    // Creating and retrieving Students
    public void createNewStudent(){

        try {
            String source = String.format("INSERT INTO Students(firstName,lastName,studentDepartment,admitted,studentMail) VALUES ('%s','%s',%s,%b,'%s')",
                    this.firstName, this.lastName,this.department.name(), this.admitted,this.studentMail);
            DatabaseDriver.newUpdate(source);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStudentMail(String studentMail) {
        this.studentMail = studentMail;
    }

    public void setAdmitted(boolean admitted) {
        this.admitted = admitted;
    }

    public static ArrayList<Student> retrieveStudents(){

        ArrayList<Student> students = new ArrayList<>();
        Student student = null;

        String order = "SELECT * FROM Students";

        try {

            ResultSet dataInput = DatabaseDriver.newQuery(order);

            while(dataInput.next()){

                student = new Student(
                        dataInput.getInt("StudentID"),
                        dataInput.getString("firstName"),
                        dataInput.getString("lastName"),
                        dataInput.getString("studentMail"),
                        departments.valueOf(dataInput.getString("studentDepartment")),
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