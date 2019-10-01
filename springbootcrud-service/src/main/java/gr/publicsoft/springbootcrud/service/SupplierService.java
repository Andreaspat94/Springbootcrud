package gr.publicsoft.springbootcrud.service;

import java.util.List;

import gr.publicsoft.springbootcrud.model.Supplier;


public interface SupplierService {

	Supplier getSupplierByVatNumber(String vatNumber);
	
	List<Supplier> getSuppliersByCompanyName(String companyName);
	
	Supplier save(Supplier supplier);
	
	void delete(Supplier supplier);
	
	List<Supplier> getAllSuppliers();
	
	List<Supplier> getAllSuppliersOrderByCompanyName();
	
	List<Supplier> getSuppliers(String param);
	
}
