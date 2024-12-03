package com.fleetmanagement.controllers;

import java.sql.*;
import java.util.Scanner;

class Main {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/fleet_db";
    private static final String USER = "root";
    private static final String PASSWORD = "nick..";

    public static void main(String[] args) {
        try {
             // Load MySQL JDBC Driver 
             Class.forName("com.mysql.cj.jdbc.Driver");

             try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) { createTables(conn); 
                Scanner scanner = new Scanner(System.in); 
                while (true){
                     System.out.println("\nAutonomous Fleet Management System"); 
                     System.out.println("1. Add Vehicle"); 
                     System.out.println("2. View All Vehicles"); 
                     System.out.println("3. Update Vehicle Status"); 
                     System.out.println("4. View Vehicle Maintenance History"); 
                     System.out.println("5. Schedule Maintenance"); 
                     System.out.println("6. Exit"); 
                     System.out.print("Choose an option: ");
                     
                     int choice = scanner.nextInt(); 
                     switch (choice){ 
                        case 1: addVehicle(conn, scanner); 
                        break;
                        case 2: viewAllVehicles(conn); 
                        break; 
                        case 3: updateVehicleStatus(conn, scanner);
                        break; 
                        case 4: viewMaintenanceHistory(conn, scanner); 
                        break; 
                        case 5: scheduleMaintenance(conn, scanner); 
                        break; 
                        case 6: System.exit(0);

                        default: System.out.println("Invalid choice!");
                    }
                }
            }
        }catch (SQLException | ClassNotFoundException e) { 
            System.out.println("Database connection failed: " + e.getMessage()); 
            }
        }


    private static void createTables(Connection conn) throws SQLException {
        // Create Vehicles table
        String createVehiclesTable = "CREATE TABLE IF NOT EXISTS Vehicles ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "vehicle_number VARCHAR(20) UNIQUE, "
                + "model VARCHAR(50), "
                + "manufacturing_year INT, "
                + "battery_capacity DOUBLE, "
                + "current_status ENUM('AVAILABLE', 'IN_SERVICE', 'CHARGING', 'MAINTENANCE', 'OUT_OF_SERVICE'), "
                + "current_location VARCHAR(100), "
                + "last_maintenance_date DATE)";

        // Create Maintenance table
        String createMaintenanceTable = "CREATE TABLE IF NOT EXISTS Maintenance ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "vehicle_id INT, "
                + "maintenance_date DATE, "
                + "maintenance_type VARCHAR(50), "
                + "description TEXT, "
                + "cost DOUBLE, "
                + "FOREIGN KEY (vehicle_id) REFERENCES Vehicles(id))";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createVehiclesTable);
            stmt.execute(createMaintenanceTable);
            System.out.println("Database tables are ready.");
        }
    }

    private static void addVehicle(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter vehicle number: ");
        String vehicleNumber = scanner.next();
        System.out.print("Enter model: ");
        String model = scanner.next();
        System.out.print("Enter manufacturing year: ");
        int year = scanner.nextInt();
        System.out.print("Enter battery capacity (kWh): ");
        double batteryCapacity = scanner.nextDouble();
        System.out.print("Enter current location: ");
        String location = scanner.next();

        String insertSQL = "INSERT INTO Vehicles (vehicle_number, model, manufacturing_year, "
                + "battery_capacity, current_status, current_location, last_maintenance_date) "
                + "VALUES (?, ?, ?, ?, 'AVAILABLE', ?, CURDATE())";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, vehicleNumber);
            pstmt.setString(2, model);
            pstmt.setInt(3, year);
            pstmt.setDouble(4, batteryCapacity);
            pstmt.setString(5, location);
            pstmt.executeUpdate();
            System.out.println("Vehicle added successfully.");
        }
    }

    private static void viewAllVehicles(Connection conn) throws SQLException {
        String selectSQL = "SELECT * FROM Vehicles";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.println("\nCurrent Fleet Status:");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.printf("Vehicle Number: %s\n", rs.getString("vehicle_number"));
                System.out.printf("Model: %s\n", rs.getString("model"));
                System.out.printf("Status: %s\n", rs.getString("current_status"));
                System.out.printf("Location: %s\n", rs.getString("current_location"));
                System.out.printf("Battery Capacity: %.1f kWh\n", rs.getDouble("battery_capacity"));
                System.out.printf("Last Maintenance: %s\n", rs.getDate("last_maintenance_date"));
                System.out.println("----------------------------------------");
            }
        }
    }

    private static void updateVehicleStatus(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter vehicle number: ");
        String vehicleNumber = scanner.next();
        System.out.println("Select new status:");
        System.out.println("1. AVAILABLE");
        System.out.println("2. IN_SERVICE");
        System.out.println("3. CHARGING");
        System.out.println("4. MAINTENANCE");
        System.out.println("5. OUT_OF_SERVICE");
        int statusChoice = scanner.nextInt();

        String status = switch (statusChoice) {
            case 1 -> "AVAILABLE";
            case 2 -> "IN_SERVICE";
            case 3 -> "CHARGING";
            case 4 -> "MAINTENANCE";
            case 5 -> "OUT_OF_SERVICE";
            default -> throw new IllegalArgumentException("Invalid status choice");
        };

        String updateSQL = "UPDATE Vehicles SET current_status = ? WHERE vehicle_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, status);
            pstmt.setString(2, vehicleNumber);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vehicle status updated successfully.");
            } else {
                System.out.println("Vehicle not found.");
            }
        }
    }

    private static void viewMaintenanceHistory(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter vehicle number: ");
        String vehicleNumber = scanner.next();

        String selectSQL = "SELECT m.* FROM Maintenance m "
                + "JOIN Vehicles v ON m.vehicle_id = v.id "
                + "WHERE v.vehicle_number = ? "
                + "ORDER BY m.maintenance_date DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, vehicleNumber);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nMaintenance History:");
            System.out.println("----------------------------------------");
            while (rs.next()) {
                System.out.printf("Date: %s\n", rs.getDate("maintenance_date"));
                System.out.printf("Type: %s\n", rs.getString("maintenance_type"));
                System.out.printf("Description: %s\n", rs.getString("description"));
                System.out.printf("Cost: $%.2f\n", rs.getDouble("cost"));
                System.out.println("----------------------------------------");
            }
        }
    }

    private static void scheduleMaintenance(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter vehicle number: ");
        String vehicleNumber = scanner.next();
        System.out.print("Enter maintenance type: ");
        String maintenanceType = scanner.next();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter estimated cost: ");
        double cost = scanner.nextDouble();

        // First get vehicle ID
        String getVehicleIdSQL = "SELECT id FROM Vehicles WHERE vehicle_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(getVehicleIdSQL)) {
            pstmt.setString(1, vehicleNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int vehicleId = rs.getInt("id");

                // Insert maintenance record
                String insertSQL = "INSERT INTO Maintenance (vehicle_id, maintenance_date, "
                        + "maintenance_type, description, cost) VALUES (?, CURDATE(), ?, ?, ?)";
                try (PreparedStatement insertPstmt = conn.prepareStatement(insertSQL)) {
                    insertPstmt.setInt(1, vehicleId);
                    insertPstmt.setString(2, maintenanceType);
                    insertPstmt.setString(3, description);
                    insertPstmt.setDouble(4, cost);
                    insertPstmt.executeUpdate();

                    // Update vehicle status and maintenance date
                    String updateSQL = "UPDATE Vehicles SET current_status = 'MAINTENANCE', "
                            + "last_maintenance_date = CURDATE() WHERE id = ?";
                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateSQL)) {
                        updatePstmt.setInt(1, vehicleId);
                        updatePstmt.executeUpdate();
                        System.out.println("Maintenance scheduled successfully.");
                    }
                }
            } else {
                System.out.println("Vehicle not found.");
            }
        }
    }
}
