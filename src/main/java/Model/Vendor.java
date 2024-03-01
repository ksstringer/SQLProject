package Model;

import java.util.Objects;

public class Vendor {
    // We have getters and setter so that we can control how these fields are accessed. Making them
    // private forces consumers to use these methods instead of accessing the fields directly.
    private int vendorId;
    private String vendorName;

    public Vendor(){

    }

    public Vendor(int vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendor vendor = (Vendor) o;
        return vendorId == vendor.vendorId && Objects.equals(vendorName, vendor.vendorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorId, vendorName);
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId=" + vendorId +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }
}
