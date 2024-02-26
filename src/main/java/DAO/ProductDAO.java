package DAO;

import Model.Product;
import org.h2.command.Prepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    Connection conn;
    public ProductDAO(Connection conn) {
        this.conn=conn;
    }

    //add new product to table "Product"
    public void insertProduct (Product p){
        try{
            PreparedStatement ps = conn.prepareStatement("insert into Product" +
                    " (product_id, product_name, product_price, sold_by) " +
                    "values (?, ?, ?, ?)");
            ps.setInt(1, p.getProductID());
            ps.setString(2, p.getProductName());
            ps.setDouble(3, p.getProductPrice());;
            ps.setInt(4, p.getSoldBy());
            ps.executeUpdate();
        }catch(SQLException e){
            //add better messaging
            e.printStackTrace();
        }
    }

    //get all products from table "Product"
    public List<Product> getProducts(){
        List<Product> productList = new ArrayList<>();
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Product");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                double productPrice = rs.getDouble("product_price");
                int soldBy = rs.getInt("sold_by");
                Product p = new Product(productId, productName, productPrice, soldBy);
                productList.add(p);
            }
        }catch(SQLException e){
            //add better messaging
            e.printStackTrace();
        }
        return productList;
    }

    //get product from table "Product" via productId
    public Product getProductById(int id){
        try{
            PreparedStatement ps = conn.prepareStatement("select * from Product where product_id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                double productPrice = rs.getDouble("product_price");
                int soldBy = rs.getInt("sold_by");
                Product p = new Product(productId, productName, productPrice, soldBy);
                return p;
            }
        }catch(SQLException e){
            //add better messaging
            e.printStackTrace();
        }
        return null;
    }

    //get products from table "Product" via vendorId
    public List<Product> getProductByVendor (int id){
        List<Product> productList = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Product where sold_by = ?");
            ps.setInt(4, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                double productPrice = rs.getDouble("product_price");
                int soldBy = rs.getInt("sold_by");
                Product p = new Product(productId, productName, productPrice, soldBy);
                productList.add(p);
            }
        }catch (SQLException e){
            //add better messaging
            e.printStackTrace();
        }
        return productList;
    }


    //update/put product from table "Product" via productID
    public void updateProduct (Product p, int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("update Product set product_name=?, product_price=?, sold_by=?" +
                    "where product_id = ?");
            ps.setString(1, p.getProductName());
            ps.setDouble(2, p.getProductPrice());;
            ps.setInt(3, p.getSoldBy());
            ps.setInt(4, id);
            ps.executeUpdate();
        }catch(SQLException e){
            //add better messaging
            e.printStackTrace();
        }
    }

    //delete product from table "Product" via productId
    public void deleteProduct (int id){
        try {
            PreparedStatement ps = conn.prepareStatement("delete from Product where product_id = ?");
            ps.setInt(1, id);
            ps.execute();
        }catch (SQLException e){
            //add better messaging
            e.printStackTrace();
        }
    }

    //delete all products from table "Product" via vendorID
}
