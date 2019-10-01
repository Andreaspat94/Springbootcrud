# Springbootcrud
 	
This is a spring boot project using multiple maven modules.
My contribution in this project was to build the back-end part for the suppliers. In the "Functionalities" section below you can find a description of how the REST api works.

An attempt to contribute in the front end took place but with no results.


## Server Application</br>
Open Command line prompt and inside the project file where pom.xml is enter the following commands:</br>
"mvn clean install" or "mvn clean package".
(If you want to build with the regular mvn command, you will need Maven v3.5.0 or above).
A jar excecutable file will be created in target file.
Lastly, excecute that jar with this command: "java -cp target/artifactId-version-SNAPSHOT.jar"


##Client Application(springbootcrud - client)</br>
- Be sure that you are in the springbootcrud-client folder and execute the following
command: npm install
- When the installation will be finished you can run the Client with: npm run dev
- Then you can visit http://localhost:9000


## Functionalities: </br>
The application contains one controller with multiple endpoints that use "ResponseEntity" class to manipulate the HTTP response.
ResponseEntity represents the whole HTTP response: status code, headers, and body.
The controller can do the below functionalities: </br>

- Get all suppliers from the database based on supplier's ID, and Company's name: </br>
	The controller receives one of the following path variables: "companyname", "id" and sends back the appropriate data.
	If the list is empty it returns a 204 No Content Http status. </br>
 
- Get a selected supplier by entering the VAT number: </br>
	The controller is receiving a string (vatNumber) through a GET request and forms a response.
	The Response is sent back with an appropriate message in its body if it doesn't find a product with that code(HTTP_Status = 404 not found).
	If the supplier is found it responds with a HTTP_Status = 200 OK and the supplier itself. </br>
	
- Create a supplier: </br>
	A POST request is being sent in the controller with a supplier that the client wants to create.
	If the supplier code already exists it respondes with a 409 CONFLICT status and a message.
	Otherwise, the respond contains the header with the location of the new supplier and a 201 "Created" status. </br>
	
- Update a supplier by entering the vat number: </br>
	A PUT request is being sent it the controller.The client sents the vat number of the one that wants to update.
 	If it doesn't find it it returns a 404 status not found and an appropriate message.
	Otherwise, it checks if the new vat number is already occupied. If yes, it responds with a 404 status not found and an appropriate message, as well.
	If not, it returns a status 200 OK and the updated supplier. </br>
	
- Delete a supplier by entering the vat number: </br>
	The controller receives a DELETE request and vat number of the supplier that wants to delete.
	If the controller does not found the supplier it responds with a message and a 404 Not Found Status.
	If the supplier is found it gets erased and the controller sents back just the httpStatus of 204 No content.


