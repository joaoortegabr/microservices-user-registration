# MICROSERVICES

### A simple study about microservices

User registration triggers a confirmation email to the new user.
This study intended to integrate two different services (USER and EMAIL) in a real situation.
Email service is listening to User service to send email every time a new user is registered. 
Emails are enqueued to be sent. Both are stored in database.

### Technologies
The following techs were used to accomplish this lesson:

* Java 17
* Springboot 3.1.8
* Maven
* RabbitMQ 
* PostgreSQL
