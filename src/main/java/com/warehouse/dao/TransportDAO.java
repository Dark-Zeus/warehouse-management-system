package com.warehouse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.warehouse.model.Transport;
import com.warehouse.util.SQLConnector;

public class TransportDAO {
    Connection conn = null;

    public TransportDAO() {
        conn = SQLConnector.getConnection("warehouse");
    }

    public Transport getTransportById(int transportId) {
        String query = "SELECT * FROM `transport` WHERE transport_id = ?";
        Transport transport = new Transport();

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, transportId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                transport.setTransport_id(rs.getInt("transport_id"));
                transport.setStart_location(rs.getString("start_location"));
                transport.setDestination(rs.getString("destination"));
                transport.setVehicle_type(rs.getString("vehicle_type"));
                transport.setVehicle_number(rs.getString("vehicle_number"));
                transport.setContact_number(rs.getString("contact_number"));
                transport.setStatus(rs.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transport;
    }

    public List<Transport> getAllTransports() {
        List<Transport> transports = new ArrayList<>();
        String query = "SELECT * FROM `transport`";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transport transport = new Transport();
                transport.setTransport_id(rs.getInt("transport_id"));
                transport.setStart_location(rs.getString("start_location"));
                transport.setDestination(rs.getString("destination"));
                transport.setVehicle_type(rs.getString("vehicle_type"));
                transport.setVehicle_number(rs.getString("vehicle_number"));
                transport.setContact_number(rs.getString("contact_number"));
                transport.setStatus(rs.getString("status"));

                transports.add(transport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transports;
    }

    public boolean addTransport(Transport transport) {
        String query = "INSERT INTO `transport` (start_location, destination, vehicle_type, vehicle_number, contact_number, status) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, transport.getStart_location());
            ps.setString(2, transport.getDestination());
            ps.setString(3, transport.getVehicle_type());
            ps.setString(4, transport.getVehicle_number());
            ps.setString(5, transport.getContact_number());
            ps.setString(6, transport.getStatus());
            

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTransport(Transport transport) {
        String query = "UPDATE `transport` SET start_location = ?, destination = ?, vehicle_type = ?, vehicle_number = ?, contact_number = ?, status = ? WHERE transport_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, transport.getStart_location());
            ps.setString(2, transport.getDestination());
            ps.setString(3, transport.getVehicle_type());
            ps.setString(4, transport.getVehicle_number());
            ps.setString(5, transport.getContact_number());
            ps.setString(6, transport.getStatus());
            ps.setInt(7, transport.getTransport_id());
           


            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTransport(int transportId) {
        String query = "DELETE FROM `transport` WHERE transport_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, transportId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
