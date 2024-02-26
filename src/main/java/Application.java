import Controller.Controller;
import DAO.ProductDAO;
import DAO.VendorDAO;
import Service.ProductService;
import Service.VendorService;
import Util.ConnectionSingleton;
import io.javalin.Javalin;

import java.sql.Connection;

public class Application {
    public static void main(String[] args) {
        Connection conn = ConnectionSingleton.getConnection();

        VendorDAO vendorDAO = new VendorDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn);
        VendorService vendorService = new VendorService(vendorDAO);
        ProductService productService = new ProductService(productDAO);
        Controller Controller = new Controller(vendorService, productService);

        Javalin api = Controller.getAPI();
        api.start(9003);
    }
}