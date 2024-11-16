package com.fleetmanagement.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        Connection con = null;
        try {
            con = Database.getConnection();
            if (con != null) {
                System.out.println("Database Connected");
            }
            
            String insertVehicleQuery = "INSERT INTO vehicles (status, current_location, battery_level, last_maintenance, model, manufacturing_year, total_distance) " +
                                        "VALUES ('Active', 'Zone A-1', 85, '2024-03-15', 'Tesla Model 3', 2023, 1000.00);";

            Statement stmt = con.createStatement();
            stmt.executeUpdate(insertVehicleQuery);
            System.out.println("Record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
