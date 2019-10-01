package gr.publicsoft.springbootcrud.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.demo.util.CustomErrorType;

import gr.publicsoft.springbootcrud.model.Supplier;
import gr.publicsoft.springbootcrud.repository.SupplierRepository;
import gr.publicsoft.springbootcrud.service.SupplierServiceImpl;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("springbootcrud/v1/admin/supplier")
public class SupplierController {

	@Autowired
	SupplierServiceImpl supplierService;
	
	public static final Logger logger = LoggerFactory.getLogger(SupplierController.class);
	
	
	// Get all suppliers ordered by supplier's "companyname"/"id".
	@GetMapping(path = "/{param}")
	public ResponseEntity<List<Supplier>> getAllSuppliers(@PathVariable(name="param") String param) {

		logger.info("Fetching the suppliers ordered by: {}", param);
		List<Supplier> suppliers = supplierService.getSuppliers(param);
			if (suppliers == null) {
				logger.error("Suppliers {} not found.");
		        return new ResponseEntity<List<Supplier>>(HttpStatus.NO_CONTENT);
		    }
	    return new ResponseEntity<List<Supplier>>(suppliers, HttpStatus.OK);
	}
	
	// Get suppliers by Vat Number
		@GetMapping(path = "/vatnumber/{vatNumber}")
		public ResponseEntity<Supplier> getSupplierByVatNumber(@PathVariable(name="vatNumber") String vatNumber) {

			logger.info("Fetching the supplier with Vat number: {}", vatNumber);
			Supplier supplier = supplierService.getSupplierByVatNumber(vatNumber);

			if (supplier == null) {
				 logger.error("Supplier with Vat number {} not found.", vatNumber);
				 return new ResponseEntity(new CustomErrorType("Supplier with Vat number " + vatNumber + " not found"), HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<Supplier>(supplier,HttpStatus.OK);
		
		}

//	 Insert a supplier
	@PostMapping(consumes = { "application/json" })
	public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier, UriComponentsBuilder ucBuilder) {

		logger.info("Creating supplier : {}", supplier.toString());

		if (supplierService.getSupplierByVatNumber(supplier.getVatNumber()) != null) {
			logger.error("Unable to create. A Supplier with Vat Number {} already exist", supplier.getVatNumber());
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A Supplier with Vat Number " + supplier.getVatNumber() + " already exist."),
					HttpStatus.CONFLICT);
		}
		supplierService.save(supplier);

		HttpHeaders headers = new HttpHeaders();

		headers.setLocation(ucBuilder.path("/supplier/{id}").buildAndExpand(supplier.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	 //Update a supplier 
		@RequestMapping(value="/{vatNumber}", consumes = {"application/json"}, method = {RequestMethod.GET, RequestMethod.PUT})
		public ResponseEntity<Supplier> updateSupplier(@RequestBody Supplier supplier,@PathVariable("vatNumber") String vatNumber) {		
			logger.info("Updating supplier with Vat number {}", vatNumber);
			
			//Searching for the supplier with the Vat Number given by the client
			Supplier currentSupplier = supplierService.getSupplierByVatNumber(vatNumber);
			
			if (currentSupplier == null) {
				logger.error("Unable to update. Supplier with product_code {} not found.", vatNumber);
				return new ResponseEntity(new CustomErrorType("Unable to upate. Supplier with Vat number: " + vatNumber + " not found."),
						HttpStatus.NOT_FOUND);
			}		
		
			//checks if the new code is occupied by another product
			Supplier existingSupplier = supplierService.getSupplierByVatNumber(supplier.getVatNumber());
			if(existingSupplier != null) {
				logger.error("Unable to update. Supplier with the same Vat number {} has been found.", existingSupplier.getVatNumber());
				return new ResponseEntity(new CustomErrorType("Unable to upate. Supplier with the same Vat number: " + existingSupplier.getVatNumber() + " has been found."),
						HttpStatus.CONFLICT);
			}
			supplierService.updateSupplier(supplier, currentSupplier);
			
			return new ResponseEntity<Supplier>(currentSupplier, HttpStatus.OK);		
		}
		
		//Delete a supplier 
		@DeleteMapping(path="/{vatNumber}")
		public ResponseEntity<?> deleteProduct(@PathVariable("vatNumber") String vatNumber) {
			logger.info("Fetching & Deleting supplier with Vat number {}", vatNumber);
			Supplier supplier = supplierService.getSupplierByVatNumber(vatNumber);
			
			if (supplier == null) {
				logger.error("Unable to delete. Supplier with Vat number {} not found.", vatNumber);
				return new ResponseEntity(new CustomErrorType("Unable to delete. Supplier with Vat number " + vatNumber + " not found."),
						HttpStatus.NOT_FOUND);
			}
			supplierService.delete(supplier);
			
			return new ResponseEntity<Supplier>(HttpStatus.NO_CONTENT);
		}
}
