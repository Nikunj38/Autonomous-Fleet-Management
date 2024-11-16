package com.jdbc.demo;

import java.util.List;

public interface VehiclesDao {

    //CRUD --> create, read, update, delete

    void addVehicle(Vehicles vehicles);

    Vehicles getVehiclesById(int vehicle_id);

    List<Vehicles> getAllVehicles();

    void updateVehicles(Vehicles vehicles);

    void deleteVehicles(int id);
}
