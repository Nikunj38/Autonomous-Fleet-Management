package com.jdbc.demo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Objects;

public class Vehicles {

    private int vehicle_id;

    private String status;

    private String current_location;

    private int battery_level;

    private Date last_maintenance;

    private String model;

    private int manufacturing_year;

    private BigDecimal total_distance;

    public Vehicles(int vehicle_id, String status, String current_location, int battery_level, Date last_maintenance, String model, int manufacturing_year, BigDecimal total_distance) {
        this.vehicle_id = vehicle_id;
        this.status = status;
        this.current_location = current_location;
        this.battery_level = battery_level;
        this.last_maintenance = last_maintenance;
        this.model = model;
        this.manufacturing_year = manufacturing_year;
        this.total_distance = total_distance;
    }

    public Vehicles(int vehicleId, String active, String zoneA1, int batteryLevel, String date, String mercedes, int manufacturingYear, double v) {
    }


    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public void setCurrent_location(String current_location) {
        this.current_location = current_location;
    }

    public int getBattery_level() {
        return battery_level;
    }

    public void setBattery_level(int battery_level) {
        this.battery_level = battery_level;
    }

    public java.sql.Date getLast_maintenance() {
        return (java.sql.Date) last_maintenance;
    }

    public void setLast_maintenance(Date last_maintenance) {
        this.last_maintenance = last_maintenance;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufacturing_year() {
        return manufacturing_year;
    }

    public void setManufacturing_year(int manufacturing_year) {
        this.manufacturing_year = manufacturing_year;
    }

    public BigDecimal getTotal_distance() {
        return total_distance;
    }

    public void setTotal_distance(BigDecimal total_distance) {
        this.total_distance = total_distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicles vehicles = (Vehicles) o;
        return vehicle_id == vehicles.vehicle_id && battery_level == vehicles.battery_level && manufacturing_year == vehicles.manufacturing_year && Objects.equals(status, vehicles.status) && Objects.equals(current_location, vehicles.current_location) && Objects.equals(last_maintenance, vehicles.last_maintenance) && Objects.equals(model, vehicles.model) && Objects.equals(total_distance, vehicles.total_distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle_id, status, current_location, battery_level, last_maintenance, model, manufacturing_year, total_distance);
    }

    @Override
    public String toString() {
        return "Vehicles{" +
                "vehicle_id=" + vehicle_id +
                ", status='" + status + '\'' +
                ", current_location='" + current_location + '\'' +
                ", battery_level=" + battery_level +
                ", last_maintenance=" + last_maintenance +
                ", model='" + model + '\'' +
                ", manufacturing_year=" + manufacturing_year +
                ", total_distance=" + total_distance +
                '}';
    }
}

