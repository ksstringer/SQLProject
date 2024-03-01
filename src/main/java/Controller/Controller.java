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
import io.javalin.http.HttpStatus;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class Controller {

    // Constants are useful when reusing strings so you only have to modify in one play when changing.
    // This can go in a dedicated constants file if desired.
    public static final String VENDOR_ID_QUERY_PARAM = "vendor_id";
    private VendorService vendorService;
    private ProductService productService;
    private ObjectMapper objectMapper;

    public Controller(VendorService vendorService, ProductService productService) {
        this.vendorService = vendorService;
        this.productService = productService;
        // Moving the initialization of object mapper to the constructor makes it get called once, instead of
        // once per API request. More efficient code.
        objectMapper = new ObjectMapper();
    }


    public Javalin getAPI() {
        Javalin app = Javalin.create();

        //VENDORS
        app.post("vendor", context -> {
            try {
                Vendor vendor = objectMapper.readValue(context.body(), Vendor.class);
                vendorService.saveVendor(vendor);
                // Using constants can help with clarity
                context.status(HttpStatus.CREATED);
                context.json(vendor);
            } catch (JsonProcessingException e) {
                context.result(e.getMessage());
                context.status(HttpStatus.BAD_REQUEST);
            }
        });

        app.get("vendor", context -> {
            List<Vendor> vendors = vendorService.getVendors();
            context.json(vendors);
        });

        app.get("vendor/{vendor_id}", context -> {
            int vendorID = Integer.parseInt(context.pathParam(VENDOR_ID_QUERY_PARAM));
            try {
                Vendor v = vendorService.getVendorById(vendorID);
                context.json(v);
            } catch (VendorException e) {
                context.status(404);
                context.result(e.getMessage());
            }
        });

        app.put("vendor/{vendor_id}", context -> {
            Vendor v = objectMapper.readValue(context.body(), Vendor.class);
            int id = Integer.parseInt(context.pathParam(VENDOR_ID_QUERY_PARAM));
            try {
                vendorService.updateVendor(v, id);
                context.json(v);
            } catch (VendorException e) {
                context.status(400);
                context.result(e.getMessage());
            }
        });


        // Separating out logically separate code can help organize and enhance readability
        initializeProductEndpoints(app);

        return app;
    }

    private void initializeProductEndpoints(Javalin app) {
        app.post("product", context -> {
            Product p = objectMapper.readValue(context.body(), Product.class);
            try {
                productService.saveProduct(p);
                context.status(201);
                context.json(p);
            } catch (ProductException e) {
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
            try {
                Product p = productService.getProductById(productID);
                context.json(p);
            } catch (ProductException e) {
                context.status(400);
                context.result(e.getMessage());
            }
        });

        ///RETURNING ALL PRODUCTS
        app.get("product?soldBy={sold_by}", context -> {
            int soldBy = Integer.parseInt(context.pathParam("sold_by"));
            try {
                List<Product> p = productService.getProductByVendor(soldBy);
                context.json(p);
            } catch (ProductException e) {
                context.status(400);
                context.result(e.getMessage());
            }
        });

        app.put("product/{product_id}", context -> {
            Product p = objectMapper.readValue(context.body(), Product.class);
            int id = Integer.parseInt(context.pathParam("product_id"));
            try {
                productService.updateProduct(p, id);
                context.json(p);
            } catch (ProductException e) {
                context.status(400);
                context.result(e.getMessage());
            }
        });

        app.delete("product/{product_id}", context -> {
            int productID = Integer.parseInt(context.pathParam("product_id"));
            productService.deleteProduct(productID);
            context.status(200);
        });
    }
}
