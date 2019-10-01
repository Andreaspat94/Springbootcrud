package gr.publicsoft.springbootcrud.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.util.CustomErrorType;

import gr.publicsoft.springbootcrud.model.Person;
import gr.publicsoft.springbootcrud.repository.PersonRepository;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RestController
@RequestMapping("springbootcrud/v1/admin/person")
public class PersonController {
	
	@Autowired 
	PersonRepository personRepo;
	
	public static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@GetMapping(path="/email/{email}")
	public ResponseEntity<Person> findByEmail(@PathVariable("email") String email) {
		Person person = personRepo.findByEmail(email);
		
		if (person == null) {
			 logger.error("Person with email {} not found.", email);
			 return new ResponseEntity(new CustomErrorType("Person with email " + email + " not found"), HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<Person>(person,HttpStatus.OK);
	}

	
	//Create a product
	
		@PostMapping(consumes = {"application/json"})
		public ResponseEntity<?> addPerson(@RequestBody Person person,UriComponentsBuilder ucBuilder) {
			
			logger.info("Creating person : {}", person);
			
			if (personRepo.findByEmail(person.getEmail())!= null) {
				logger.error("Unable to create. A Person with name {} already exist", person.getName());
				return new ResponseEntity(new CustomErrorType("Unable to create. A Person with name " + 
				person.getName() + " already exist."),HttpStatus.CONFLICT);
			}
			personRepo.save(person);
			
			HttpHeaders headers = new HttpHeaders();
			
			headers.setLocation(ucBuilder.path("/person/{id}").buildAndExpand(person.getId()).toUri());
			return new ResponseEntity<String>(headers, HttpStatus.CREATED);
		}
		
}
