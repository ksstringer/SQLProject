package Model;

import java.util.Objects;

public class Product {
    public int productID;
    public String productName;
    public double productPrice;
    public int soldBy;

    public Product() {

    }

    public Product(int productID, String productName, double productPrice, int soldBy) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.soldBy = soldBy;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(int soldBy) {
        this.soldBy = soldBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productID == product.productID && Double.compare(productPrice, product.productPrice) == 0 && soldBy == product.soldBy && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID, productName, productPrice, soldBy);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", soldBy=" + soldBy +
                '}';
    }
}
