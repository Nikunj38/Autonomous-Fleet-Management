package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Employee employee = new Employee(1, 123456, "Sam", "Spade", 3);
        //System.out.println(employee);
        Connection con = null;
        try {
            con = Database.getConnection();
            if (con!=null){
                System.out.println("Database Connected");
            }
            String Query1="INSERT INTO `test`.`users`(`id`,`password`,`amount`,`loan_amount`,`firstName`,`lastName`) VALUES (111,'pass',0,0,'test','test');";

            con.createStatement();
            Statement query = con.createStatement();
            query.execute(Query1);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
