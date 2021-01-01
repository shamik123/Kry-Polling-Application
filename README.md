# Kry-Polling-Application

Build the application with mvn:spring-boot:run.

The application will start on port 8090.

The below url will provide an UI to add new services and list services.

http://localhost:8090/TestApp

The application needs a MySql Database as a persistent storage.

Below are the steps for the same (MySQl server should be running on the same system on which Spring boot application is installed.)

From mysql client login to the mysql server :

mysql -u root -p
<Enter root password>

mysql> CREATE DATABASE testdb
mysql> CREATE USER <username>@localhost IDENTIFIED BY 'password';
mysql> GRANT ALL ON testdb.* TO <username>@localhost;

Modify the below properties in application.properties

spring.datasource.username=<username>
spring.datasource.password=<password>


The below url will be used to populate Services and URL's using REST APIS

http://localhost:8090/urlService/Service

It takes input in the below format :

{ "url":"http://localhost:8091", "userId":"User123", "service":"Service1" }

It supports GET, POST and DELETE requests.

The sample application to be monitored is available in the git repo https://github.com/shamik123/PolledApplication.git. The sample monitored application will be run on port 8091.

.

