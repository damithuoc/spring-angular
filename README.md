Spring+AngularJs
=================
How to run this project
01) Requirements
JDK 1.8.x.x
MySQL 5.6
Maven 3.0

02) Create a database "VRIGOR" and give username as "root" and password
as "root" or change springWebSecurity.xml file for your preference.

03) Run maven command: "mvn clean package install -DskipTests"
It will automatically generate database tabels for this project.

04) Run unit test in for create sample users(see UserServiceTest in test source)

05) Now you can login to the demo application using "localhost:8000/"

Basic Coverages
===============
* Spring RestFull webservices for API Management
* Spring JDBC
* Spring MVC
* Spring Security(Basic Level)
* AngualrJs(Factory, RouterProver, WebServices)
* JAVA 8 new features(basic Stream API and lamda)
