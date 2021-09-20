# location-tracker-service

 This Project is RestClient service which retrieve directions from Downstream server side api and evaluates the coordinates.
 Once Coordinates are evaluated.Its verifies with Server side api.
 
 Language - Java
 Version -  1.8
 
 Prerquisite to run on local-
 1.Java 1.8 should be installed
 2.Maven should be configured
 3.Should have any IDE( Intellij/Eclipse)
 
 Step to Execute on Local
1. Clone the repository - git clone https://github.com/utkarshagarwal1103/location-tracker-service.git
2. Import the project in IntelliJ/Eclipse.Wait untill all the dependecies are downloaded.
3. Run the Spring boot project using maven or Spring boot main Application file - LocationTrackerServiceApplication.java
4. Verify Spring boot application is started succesfully on console using port 8080, 
5. Verify using actuator service for health check on browser/Postman
HTTP Method- GET
URL- http://localhost:8080/which/location-tracker-service/actuator/health

This api will respond with below Json data
{
  "status": "UP"
}

If you are seeing above JSON Response, that means you Application is Up and Running

API Details
1. HTTP Method- POST
   URL- http://localhost:8080/which/location-tracker-service/coordinates
   Request Body - 
   {
    "emailId": <Email id>
   }
   example- 
   {
    "emailId": "test@gmail.com"
   }
  
  Response Body -
  {
    "coordinates": {
        "x": Integer_Value,
        "y": Integer_Value
    }
  }
  example- 
   {
    "coordinates": {
        "x": 5,
        "y": 2
    }
  }
  
  Error Responses -
   1. 400 Bad Request -
    {
    "timestamp": "2021-09-20T23:34:34.5371044",
    "message": [
                   "emailId: must be a well-formed email address"
              ]
    }
  
   2. 500 Internal Error
   {
    "timestamp": "2021-09-20T23:38:23.3123913",
    "message": "<ErrorCode> - Application Error"
   }
   example -
   {
    "timestamp": "2021-09-20T23:38:23.3123913",
    "message": "LTS108 - Application Error"
   }
   
  
  Technical Explaination - 
   Request flows from LocationTrackerController ->processDirection method which evaluates emailid, post validation of emailId it make connection to server to get list of
   direction(LocationTrackerService ->LocationTrackerDao)
   Once list is retieved it evaluates coordinates using utility DirectionUtil.
  

2. HTTP Method- POST
   URL - http://localhost:8080/which/location-tracker-service/validate
   Request Body - You can use coordinates from previous response as well
  {
     "emailId": <Email id>,
     "coordinates": {
        "x": <Integer Value>,
        "y": <Integer Value>
    }
   } 
   example-  
  {
     "emailId": "test@gmail.com",
     "coordinates": {
        "x": 5,
        "y": 8
    }
  }
  
   Response Body -
   {
    "status": "SUCCESS/FAILURE",
    "message": "<Message recieved from Server>"
   }
  
   Error Responses -
   1. 400 Bad Request -
    {
    "timestamp": "2021-09-20T23:34:34.5371044",
    "message": [
                   "emailId: must be a well-formed email address"
              ]
    }
  
   2. 500 Internal Error -
   {
    "timestamp": "2021-09-20T23:38:23.3123913",
    "message": "<ErrorCode> - Application Error"
   }
  
  
   Technical Explaination - 
   Request flows from LocationTrackerController ->processCoordinates method which evaluates emailid, post validation of emailId it make connection to server to validate
   coordinates provided (LocationTrackerService ->LocationTrackerDao)
   Once response is recieved it evaluates SUCCESS/FAILURE based on message.
  
  Project Structure
  
  ![image](https://user-images.githubusercontent.com/86582547/134086899-b4609211-ecb1-4d58-8790-b33ca0f092fe.png)

  
  Key Points-
   1. Test cases are written as part of Unit testing using Mockito
   2. Spring boot dependencies, lambok is utilized
   3. Logging is added for console level. Can be upgraded to file level as next action item
   4. Constants are externalized
   5. few values are set as yml file level like context path & server side url
   6. Utilities are created for email validation and getting coordinates.
   7. WebClient is Used to retrieve response from Server.
   8. Project Structure is seperated as Controller -> SERVICE -> DAO
   
  
  Next Steps -
  1. Additional test cases to capture test coverage.Jacoco can be configured.
  2. Jenkins file implementaion for CI/CD pipelines
  3. More Exception handling scenarios can be added.
  4. URL can be made secured using JWT based Authentication and Authorisation

 API Screenshots
 
 API 1- 
 
 ![image](https://user-images.githubusercontent.com/86582547/134088175-c56503d1-1924-4528-9ee7-0a51e4290efe.png)

 API 2- 
 
 ![image](https://user-images.githubusercontent.com/86582547/134088291-bf7d678f-b5e3-405a-b975-3a464fe9e290.png)

 
 
 
 

