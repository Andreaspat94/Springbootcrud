package gr.publicsoft.springbootcrud.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.publicsoft.springbootcrud.model.Supplier;
import gr.publicsoft.springbootcrud.repository.SupplierRepository;

@Service("supplierService")
@Transactional
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	public Supplier getSupplierByVatNumber(String vatNumber) {
		return supplierRepo.findByVatNumber(vatNumber);
	}
	public List<Supplier> getSuppliersByCompanyName(String companyName) {
		return supplierRepo.findByCompanyName(companyName);
	}
	
	// Find all - endpoint
	public List<Supplier> getAllSuppliersOrderByCompanyName() {
		return supplierRepo.findAllByOrderByCompanyName();
	}
	public List<Supplier> getAllSuppliers() {
		return supplierRepo.findAll();
	}
	
	// ------------------------------------------------------
	
	public Supplier save(Supplier supplier) {
		return supplierRepo.save(supplier);
	}
	
	//This method is called in the update endpoint of the controller
	public void updateSupplier(Supplier supplier, Supplier currentSupplier) {
		currentSupplier.setCompanyName(supplier.getCompanyName());
		currentSupplier.setFirstName(supplier.getFirstName());
		currentSupplier.setLastName(supplier.getLastName());
		currentSupplier.setVatNumber(supplier.getVatNumber());
		currentSupplier.setIrsOffice(supplier.getIrsOffice());
		currentSupplier.setAddress(supplier.getAddress());
		currentSupplier.setZipCode(supplier.getZipCode());
		currentSupplier.setCity(supplier.getCity());
		currentSupplier.setCountry(supplier.getCountry());
		
		supplierRepo.save(currentSupplier);
	}
	
	public void delete(Supplier supplier) {
		 supplierRepo.delete(supplier);
	}
	
	//This method is used for retrieving the appropriate data for the "getSuppliers" entity
		public List<Supplier> getSuppliers(String param) {
			String value = param;
			switch(value) {
				case "companyname":
					return getAllSuppliersOrderByCompanyName();
				case "id":
					return getAllSuppliers();
				default:
					return null;
					
			}
			
		}
		
	
	
}
