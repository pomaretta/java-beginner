package services;

import java.sql.*;

public class Database {

    final private String host = "HOSTNAME";
    final private String port = "PORT";
    final private String database = "DATABASE";
    final private String username = "USERNAME";
    final private String password = "PASSWORD";
    final private String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

    private Connection con = null;
    private Statement stmt = null;

    public Database(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con = DriverManager.getConnection(this.url,this.username,this.password);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet newQuery(String statement){

        ResultSet rs = null;

        try {

            stmt = con.createStatement();
            rs = stmt.executeQuery(statement);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return rs;

    }

    public boolean newUpdate(String statement){

        Boolean rs = null;

        try {

            stmt = con.createStatement();
            rs = stmt.execute(statement);

        } catch (SQLException e){
            e.printStackTrace();
        }

        return rs;

    }

}
