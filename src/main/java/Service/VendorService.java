package Service;

import DAO.VendorDAO;
import Exceptions.VendorException;
import Model.Vendor;

import java.util.ArrayList;
import java.util.List;

public class VendorService {
    VendorDAO vendorDAO;
    public VendorService(VendorDAO vendorDAO){
        this.vendorDAO = vendorDAO;
    }

    List<Vendor> vendors;
    public VendorService() {
        vendors = new ArrayList<>();
    }

    //add new vendor
    public void saveVendor(Vendor v) throws VendorException {
        if (v.getVendorName().isEmpty()) {
            throw new VendorException("Vendor Name cannot be null");
        } else if (vendorDAO.getVendors().contains(v)){  //update to be only name and not both params
            throw new VendorException("Vendor already exists");
        }
        vendorDAO.insertVendor(v);
    }

    //get all vendors
    public List<Vendor> getVendors() {
        return vendorDAO.getVendors();
    }

    //get vendor by vendorID
    public Vendor getVendorById(int vendorId) throws VendorException {
        Vendor v = vendorDAO.getVendorById(vendorId);
        if (v == null) {
            throw new VendorException("No such vendor ID found");
        } else {
            return v;
        }
    }

    //update/put vendor by vendorID
    public void updateVendor (Vendor v, int id) throws VendorException {
        if (v.getVendorName().isEmpty()) {
            throw new VendorException("Vendor Name cannot by blank");
        }
        vendorDAO.updateVendor(v, id);
    }

    //delete vendor (see DAO notes)

}
