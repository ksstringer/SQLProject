import DAO.ProductDAO;
import DAO.VendorDAO;
import Exceptions.ProductException;
import Exceptions.VendorException;
import Model.Product;
import Model.Vendor;
import Service.ProductService;
import Service.VendorService;
import Util.ConnectionSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

public class ServiceTests {
    VendorDAO vendorDAO;
    ProductDAO productDAO;
    VendorService vendorService;
    ProductService productService;
    Connection conn;

    @Before
    public void setUp() {
        Connection conn = ConnectionSingleton.getConnection();
        ConnectionSingleton.resetTestDatabase();
        vendorDAO = new VendorDAO(conn);
        productDAO = new ProductDAO(conn);
        vendorService = new VendorService(vendorDAO);
        productService = new ProductService(productDAO);
    }

    @Test
    public void vendorServiceAddVendor () {
        int testVendorId = 4;
        String testVendorName = "vendor";
        Vendor v = new Vendor(testVendorId, testVendorName);
        try {
            vendorService.saveVendor(v);
        }catch(VendorException e){
            e.printStackTrace();;
            Assert.fail("Vendor exception incorrectly thrown");
        }
        List<Vendor> vendors = vendorService.getVendors();
        Assert.assertTrue(vendors.contains(v));
    }

    @Test
    public void vendorServiceAddBlankVendorName() {
        int testVendorId = 4;
        String testVendorName = "";
        Vendor v = new Vendor(testVendorId, testVendorName);
        try{
            vendorService.saveVendor(v);
            Assert.fail("Exception not thrown");
        }catch (VendorException e){

        }
    }

    @Test
    public void vendorServiceAddExistingVendor() {
        int testVendorId = 1;
        String testVendorName = "marty";
        Vendor v = new Vendor(testVendorId, testVendorName);
        try{
            vendorService.saveVendor(v);
            Assert.fail("Exception not thrown");
        }catch (VendorException e){

        }
    }

    @Test
    public void vendorServiceGetVendorById() throws VendorException {
        int testVendorId = 3;
        String testVendorName = "vendor";
        Vendor v = new Vendor(testVendorId, testVendorName);
        try {
            vendorService.saveVendor(v);
        } catch (VendorException e) {
            e.printStackTrace();
            Assert.fail("Vendor Exception incorrectly thrown");
        }
        vendorService.getVendorById(v.getVendorId());
    }

    @Test
    public void vendorServiceGetNonExistentVendor () {
        int testVendorId = 5;
        String testVendorName = "vendor";
        Vendor v = new Vendor(testVendorId, testVendorName);
        try {
            vendorService.getVendorById(7);
            Assert.fail("Exception not thrown");
        } catch (VendorException e) {
        }
    }

    @Test
    public void vendorServiceUpdateVendor() throws VendorException {
        int testVendorId = 2;
        String testVendorName = "samuel";
        Vendor v = new Vendor(testVendorId, testVendorName);
        try {
            vendorService.updateVendor(v, testVendorId);
        } catch (VendorException e) {
            e.printStackTrace();
            Assert.fail("Vendor Exception incorrectly thrown");
        }
        List<Vendor> vendors = vendorService.getVendors();
        Assert.assertTrue(vendors.contains(v));
    }

    @Test
    public void vendorServiceUpdateBlankVendor() {
        int testVendorId = 0;
        String testVendorName = "";
        Vendor v = new Vendor(testVendorId, testVendorName);
        try {
            vendorService.updateVendor(v, testVendorId);
            Assert.fail("Exception not thrown");
        } catch (VendorException e) {

        }
    }

    @Test
    public void productServiceAddProduct() {
        int testProductId = 8;
        String testProductName = "ice cream";
        double testProductPrice = 4.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.saveProduct(p);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Product Exception Incorrectly thrown");
        }
        List<Product> products = productService.getProducts();
        Assert.assertTrue(products.contains(p));
    }

    @Test
    public void productServiceAddBlankName() {
        int testProductId = 5;
        String testProductName = "";
        double testProductPrice = 4.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.saveProduct(p);
            Assert.fail("Exception not throw");
        } catch (ProductException e) {

        }
    }

    @Test
    public void productServiceAddInvalidPrice() {
        int testProductId = 5;
        String testProductName = "ice cream";
        double testProductPrice = 0.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.saveProduct(p);
            Assert.fail("Exception not throw");
        } catch (ProductException e) {

        }
    }

    @Test
    public void productServiceAddBlankVendor() {
        int testProductId = 5;
        String testProductName = "ice cream";
        double testProductPrice = 4.00;
        int testSoldBy = 0;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.saveProduct(p);
            Assert.fail("Exception not thrown");
        } catch (ProductException e) {

        }
    }

    @Test
    public void productServiceAddExistingProductID () {
        int testProductId = 1;
        String testProductName = "ice cream";
        double testProductPrice = 4.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.saveProduct(p);
            Assert.fail("Exception not thrown");
        } catch (ProductException e) {

        }
    }

    @Test
    public void productServiceGetProductById() throws ProductException {
        int testProductId = 7;
        String testProductName = "ice cream";
        double testProductPrice = 4.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.saveProduct(p);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Product Exception incorrectly thrown");
        }
        Product ps = productService.getProductById(p.getProductID());
    }

    @Test
    public void productServiceUpdateProduct() throws ProductException {
        int testProductId = 2;
        String testProductName = "ice cream";
        double testProductPrice = 3.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.updateProduct(p, testProductId);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Product Exception incorrectly thrown");
        }
        List<Product> products = productService.getProducts();
        System.out.println(p);
        Assert.assertTrue(products.contains(p));
    }

    @Test
    public void productServiceUpdateBlankProductName() {
        int testProductId = 5;
        String testProductName = "";
        double testProductPrice = 4.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.updateProduct(p, testProductId);
            Assert.fail("Exception not thrown");
        } catch (ProductException e) {
        }
    }

    @Test
    public void productServiceUpdateInvalidProductPrice() {
        int testProductId = 5;
        String testProductName = "ice cream";
        double testProductPrice = 0.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.updateProduct(p, testProductId);
            Assert.fail("Exception not thrown");
        } catch (ProductException e) {
        }
    }

    @Test
    public void productServiceUpdateNonExistentVendor() {
        int testProductId = 5;
        String testProductName = "ice cream";
        double testProductPrice = 0.00;
        int testSoldBy = 7;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.updateProduct(p, testProductId);
            Assert.fail("Exception not thrown");
        } catch (ProductException e) {
        }
    }

    @Test
    public void productServiceDeleteProduct() {
        int testProductId = 5;
        String testProductName = "ice cream";
        double testProductPrice = 4.00;
        int testSoldBy = 2;
        Product p = new Product(testProductId, testProductName, testProductPrice, testSoldBy);
        try {
            productService.saveProduct(p);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("Product Exception incorrectly thrown");
        }
        productService.deleteProduct(testProductId);
        List<Product> products = productService.getProducts();
        Assert.assertFalse(products.contains(p));
    }
}
