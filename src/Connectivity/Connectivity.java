package Connectivity;

import java.sql.*;

public class Connectivity {

    private static final String userName = "manthika";
    private static final String password = "0724mantika";
    private static final String url = "jdbc:mysql://localhost/jfs";


    public static Connection getConnection(){

        Connection connec = null;


        try {
            connec = DriverManager.getConnection(url,userName,password);


        } catch (SQLException e) {
            System.err.println(e);
        }

        return connec;
    }
}
