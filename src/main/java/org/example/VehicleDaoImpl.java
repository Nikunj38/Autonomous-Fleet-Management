package com.jdbc.demo;

import java.sql.*;
import java.util.List;

public class VehicleDaoImpl implements VehiclesDao{

    public static final String URL = "jdbc:mysql://localhost:3306/fleet_db";

    public static final String  USER = "root";

    public static final String PASSWORD = "nick..";

    @Override
    public void addVehicle(Vehicles vehicles) {
        String query ="INSERT INTO vehicles (vehicle_id, status, current_location, battery_level, last_maintenance, model, \n" +
                "    manufacturing_year, total_distance) Values(? , ? , ? , ? , ? , ? , ? , ?)";
        try(
            Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, vehicles.getVehicle_id());
            preparedStatement.setString(2, vehicles.getStatus());
            preparedStatement.setString(3, vehicles.getCurrent_location());
            preparedStatement.setInt(4, vehicles.getBattery_level());
            preparedStatement.setDate(5, vehicles.getLast_maintenance());
            preparedStatement.setString(6, vehicles.getModel());
            preparedStatement.setInt(7, vehicles.getManufacturing_year());
            preparedStatement.setBigDecimal(8, vehicles.getTotal_distance());
        }catch(SQLException exception){
            System.out.println("Error while adding vehicle " + exception.getMessage());
        }
    }

    @Override
    public Vehicles getVehiclesById(int vehicle_id) {
        String query = "select * from vehicles where vehicle_id = ?";
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1,vehicle_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return new Vehicles(
                        resultSet.getInt("vehicle_id"),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getString(6),
                        resultSet.getInt(7),
                        resultSet.getBigDecimal(8)
                        );
            }
        } catch (SQLException exception) {
            System.out.println("Error while adding vehicle " + exception.getMessage());
        }
        return  null;
    }

    @Override
    public List<Vehicles> getAllVehicles() {
        return List.of();
    }

    @Override
    public void updateVehicles(Vehicles vehicles) {

    }

    @Override
    public void deleteVehicles(int id) {

    }
}
