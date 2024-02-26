package Service;

import DAO.ProductDAO;
import Exceptions.ProductException;
import Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    List<Product> products;

    public ProductService() { products = new ArrayList<>(); }

    //add new product
    public void saveProduct(Product p) throws ProductException {
        if (p.getProductName().isEmpty()){
            throw new ProductException("Product Name cannot be blank");
        } else if (p.getProductPrice() <= 0){
            throw new ProductException("Product Price must be greater than 0");
        } else if (p.getSoldBy() == 0) {
            throw new ProductException("Vendor cannot be blank and must already exist to add a product");
        }
        int productId = p.getProductID();
        if(productDAO.getProductById(productId) == null) {
            productDAO.insertProduct(p);
        }else{
            throw new ProductException("Product with id "+productId+"already exists");
        }
    }

    //get all products
    public List<Product> getProducts() {
        return productDAO.getProducts();
    }

    //get product by productID
    public Product getProductById (int productId) throws ProductException {
        Product p = productDAO.getProductById(productId);
        if(p == null){
            throw new ProductException("No such product ID found");
        }else{
            return p;
        }
    }

    //get product by vendorId
    public List<Product> getProductByVendor (int soldBy) throws ProductException {
        List<Product> vp = productDAO.getProductByVendor(soldBy);
        if (vp == null){
            throw new ProductException("No such vendor ID found");
        }else {
            return vp;
        }
    }

    //update/put product by product ID
    public void updateProduct (Product p, int id) throws ProductException {
        if (p.getProductName().isEmpty()){
            throw new ProductException("Product Name cannot be blank");
        } else if (p.getProductPrice() <= 0){
            throw new ProductException("Product Price must be greater than 0");
        } else if (p.getSoldBy() == 0) {
            throw new ProductException("Vendor cannot be blank and must already exist to add a product");
        }
        productDAO.updateProduct(p, id);
    }


    //delete product by product ID
    public void deleteProduct (int productID) {
        productDAO.deleteProduct(productID);
    }

    //delete all products by vendorID
}
