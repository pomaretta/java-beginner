package es.objects;

import es.objects.Student;
import es.Env.Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseDriver {

    public static ResultSet newQuery(String statement){

        // Query executor for get data from DB

        // Initial vars
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;

        try {

            // Getting the user data from an user env class
            String username = Env.username;
            String password = Env.password;
            String url = Env.url;
            // Instantiating the connection
            con = DriverManager.getConnection(url,username,password);

            try {

                // Creating the statement and executing
                stm = con.createStatement();
                rs = stm.executeQuery(statement);

            } catch (SQLException e1){
                e1.printStackTrace();
            }

        } catch (Exception e){
            System.out.print(e);
        }

        return rs;

    }

    public static boolean newUpdate(String statement){

        // Query executor for update data of DB, I could use one method instead of two but I had this way to do it before, for next projects

        Connection con = null;
        Statement stm = null;
        boolean rs = false;

        try {

            String username = Env.username;
            String password = Env.password;
            String url = Env.url;
            con = DriverManager.getConnection(url,username,password);

            try {

                stm = con.createStatement();
                rs = stm.execute(statement);

            } catch (SQLException e1){
                e1.printStackTrace();
            }

        } catch (Exception e){
            System.out.print(e);
        }

        return rs;

    }

}
