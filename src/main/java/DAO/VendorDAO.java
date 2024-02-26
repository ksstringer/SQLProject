package DAO;

import Model.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendorDAO {
    Connection conn;
    public VendorDAO(Connection conn){
        this.conn = conn;
    }

    //add new vendor to table "Vendor"
    public void insertVendor(Vendor v){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into " +
                    "Vendor (vendor_id, vendor_name)" + "values (?, ?)");
            ps.setInt(1, v.getVendorId());
            ps.setString(2, v.getVendorName());
            ps.executeUpdate();
        }catch(SQLException e){
            //add better messaging
            e.printStackTrace();
        }
    }

    //get all vendors from table "Vendor"
    public List<Vendor> getVendors(){
        List<Vendor> vendorList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Vendor");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int vendorID = resultSet.getInt("vendor_id");
                String vendorName = resultSet.getString("vendor_name");
                Vendor v = new Vendor(vendorID, vendorName);
                vendorList.add(v);
            }
        }catch (SQLException e) {
            //add better messaging
            e.printStackTrace();
        }
        return vendorList;
    }

    //get vendor from table "Vendor" by vendorId
    public Vendor getVendorById(int vendorId) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Vendor where vendor_id = ?");
            ps.setInt(1, vendorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int vendorID = rs.getInt("vendor_id");
                String vendorName = rs.getString("vendor_name");
                Vendor v = new Vendor (vendorID, vendorName);
                return v;
            } else {
                return null;
            }
        } catch (SQLException e){
            //add better messaging
            e.printStackTrace();
        }
        return null;
    }

    //update/put vendor in table "Vendor" via vendorID
    public void updateVendor (Vendor v, int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("update Vendor set vendor_name=? where vendor_id=?");
            ps.setString(1, v.getVendorName());
            ps.setInt(2, id);
            ps.executeUpdate();
        }catch (SQLException e){
            //add better messaging
            e.printStackTrace();
        }
    }

    //delete vendor from table "Vendor" via vendorID (complexity: "Product" table dependency)
    //can we include a variation in "Update/Put" where we are able to remove a vendor name and have it be null instead?
}
