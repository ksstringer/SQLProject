package Controller;

import Exceptions.ProductException;
import Exceptions.VendorException;
import Model.Product;
import Model.Vendor;
import Service.ProductService;
import Service.VendorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class Controller {

    VendorService vendorService;
    ProductService productService;

    public Controller(VendorService vendorService, ProductService productService){
        this.vendorService = vendorService;
        this.productService = productService;
    }


    public Javalin getAPI() {
        Javalin app = Javalin.create();

        //VENDORS
        app.post("vendor", context -> {
            try {
                ObjectMapper om = new ObjectMapper();
                Vendor v = om.readValue(context.body(), Vendor.class);
                vendorService.saveVendor(v);
                context.status(201);
                context.json(v);
            } catch (JsonProcessingException e) {
                context.result(e.getMessage());
                context.status(400);
            }
        });

        app.get("vendor", context -> {
            List<Vendor> vendors = vendorService.getVendors();
            context.json(vendors);
        });

        app.get("vendor/{vendor_id}", context -> {
            int vendorID = Integer.parseInt(context.pathParam("vendor_id"));
            try{
                Vendor v = vendorService.getVendorById(vendorID);
                context.json(v);
            }catch (VendorException e){
                context.status(404);
                context.result(e.getMessage());
            }
        });

        app.put("vendor/{vendor_id}", context -> {
            ObjectMapper om = new ObjectMapper();
            Vendor v = om.readValue(context.body(), Vendor.class);
            int id = Integer.parseInt(context.pathParam("vendor_id"));
            try{
                vendorService.updateVendor(v, id);
                context.json(v);
            }catch (VendorException e){
                context.status(400);
                context.result(e.getMessage());
            }
        });


        //PRODUCTS
        app.post("product", context -> {
            ObjectMapper om = new ObjectMapper();
            Product p = om.readValue(context.body(), Product.class);
            try{
                productService.saveProduct(p);
                context.status(201);
                context.json(p);
            }catch(ProductException e){
                context.status(400);
                context.result(e.getMessage());
            }
        });

        app.get("product", context -> {
            List<Product> products = productService.getProducts();
            context.json(products);
        });

        app.get("product/{product_id}", context -> {
            int productID = Integer.parseInt(context.pathParam("product_id"));
            try{
                Product p = productService.getProductById(productID);
                context.json(p);
            }catch (ProductException e){
                context.status(400);
                context.result(e.getMessage());
            }
        });

        ///RETURNING ALL PRODUCTS
        app.get("product?soldBy={sold_by}", context -> {
            int soldBy = Integer.parseInt(context.pathParam("sold_by"));
            try{
                List<Product> p = productService.getProductByVendor(soldBy);
                context.json(p);
            }catch (ProductException e){
                context.status(400);
                context.result(e.getMessage());
            }
        });

        app.put("product/{product_id}", context -> {
            ObjectMapper om = new ObjectMapper();
            Product p = om.readValue(context.body(), Product.class);
            int id = Integer.parseInt(context.pathParam("product_id"));
            try{
                productService.updateProduct(p, id);
                context.json(p);
            }catch (ProductException e){
                context.status(400);
                context.result(e.getMessage());
            }
        });

        app.delete("product/{product_id}", context -> {
            int productID = Integer.parseInt(context.pathParam("product_id"));
            productService.deleteProduct(productID);
            context.status(200);
        });


        return app;
    }
}
