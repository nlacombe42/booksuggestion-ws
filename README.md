# Booksuggestion Web Service

This project is the back-end of my booksuggestion repo.

## Run

First setup a MySQL DB on localhost port 3306 (default) with DB name book_suggestion, username book_suggestion and password book_suggestion.
Ex: `docker run -d -p "3306:3306"  -e MYSQL_USER=book_suggestion -e MYSQL_PASSWORD=book_suggestion -e MYSQL_DATABASE=book_suggestion centos/mysql-56-centos7`

To Build and run with maven: `mvn clean install && mvn -pl booksuggestion-ws spring-boot:run`
Otherwise juste starting the project as a spring boot project will work in most IDE.
