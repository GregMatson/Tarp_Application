---------------------------------------------------------------------------
HOW TO ACCESS THE SWAGGER-UI FROM YOUR LOCAL COMPUTER OR REMOTE SERVER:   |
---------------------------------------------------------------------------

In order to access the swagger-ui, you will need to have the Backend code downloaded onto your computer.
Then, depending on if you want to access the swagger-ui from your local computer or remote server, some lines in the
application.properties file will need to be changed. The following details what exactly to change:

You can find the file in Backend/src/main/resources

-------------------
LOCAL COMPUTER:   |
-------------------

Set the following:

spring.datasource.url=jdbc:mysql://127.0.0.1:3306/coms309_Tarp
spring.datasource.username=root
spring.datasource.password=		(this is intentionally left blank)

--------------------
REMOTE SERVER:     |
--------------------

Set the following:

spring.datasource.url=jdbc:mysql://coms-309-036.cs.iastate.edu:3306/coms309_Tarp
spring.datasource.username=root
spring.datasource.password=e9d210cda90af122