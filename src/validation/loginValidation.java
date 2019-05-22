package validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginValidation {

    public boolean CusLoginValidate(String userName, String password, Connection connection){
        boolean b = false;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT email, password, access FROM users WHERE email=? AND password=? AND access=?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,"Customer");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                b = true;
                System.out.println("Customer logged in");
            } else {
                b = false;
                System.out.println("login error");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return b;
    }


    public boolean StaffLoginValidate(String userName, String password, Connection connection){
        boolean b = false;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT email, password, access FROM users WHERE email=? AND password=? AND access=?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,"Staff");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                b = true;
                System.out.println("Staff member logged in");
            } else {
                b = false;
                System.out.println("login error");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return b;
    }



    public boolean OwnerLoginValidate(String userName, String password, Connection connection){
        boolean b = false;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT email, password, access FROM users WHERE email=? AND password=? AND access=?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,password);
            preparedStatement.setString(3,"Owner");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                b = true;
                System.out.println("Owner logged in");
            } else {
                b = false;
                System.out.println("login error");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return b;
    }



}
