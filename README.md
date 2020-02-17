# simple-order-service
Simple Order Service enables customers to order and cancel an order via a REST API 


Steps to follow to run the application: 

1. Run DB Script in MySQL. DB file available in src/main/resources

2. Install Gradle to manage all the dependencies for building the application which is built from build.gradle 

3. Steps to run and test this application using docker. 
   - Run the docker-compose command on docker-compose.yml file available in the root folder of the project repository. 
   
   - Hit http://localhost:8083/swagger-ui.html to find the Rest API Documentation 

4. Available Endpoints are 

![Alt text](./simple-order-service/simpleorderservice/images/endpoints.PNG?raw=true "Optional Title")

5. Inventory Model 
![Alt text](./simple-order-service/simpleorderservice/images/inventoryModel.PNG?raw=true "Optional Title")

6. Order Model
![Alt text](./simple-order-service/simpleorderservice/images/ordermodel.PNG?raw=true "Optional Title")
