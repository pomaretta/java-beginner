package es.Env;

public class Env {
    public static String database = "DATABASENAME";
    public static String username = "USERNAME";
    public static String password = "PASSWORD";
    public static String url = "jdbc:mysql://localhost:3306/" + database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
}
