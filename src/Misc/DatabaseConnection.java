package Misc;
import java.sql.*;

public class DatabaseConnection {

    Connection con;
    Statement stmt;

    //static variables allow for the ease of access of common Strings
    //that may vary based on the current user logged in.
    private static String username;
    private static String subjectTable;
    private static String tasksTable;

    public void createConnection(){
        //responsible for creating a connection with the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //connects the program to the database
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rudysjavaiadb", "root", "root");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        //responsible for executing queries into the database
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    public void execute (String values) throws SQLException {
        //responsible for executing statements into the database, be it update, insert, or delete
        stmt = con.createStatement();
        stmt.execute(values);
    }

    public void closeStatement() throws SQLException {
        //closes any open statements
        stmt.close();
    }

    public void closeConnection() throws SQLException {
        //closes the connection to the database
        con.close();
    }

    //the following are Accessors and Mutators
    public static String getUsername(){ return username; }
    public static void setUsername(String setUsername){ username = setUsername; }
    public static String getSubjectTable(){ return subjectTable; }
    public static void setSubjectTable(String subjectTableName){ subjectTable = subjectTableName; }
    public static String getTasksTable(){ return tasksTable; }
    public static void setTasksTable(String tasksTableName){ tasksTable = tasksTableName; }
}


