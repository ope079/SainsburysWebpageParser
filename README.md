# Sainsbury's Grocery's site Scraper
---------------------------------------------------------------------------------
The SainsburysWebpageParser is a console application that scrapes the Sainsbury’s grocery site’s - Berries, Cherries, Currants page and returns a JSON array of all the products on the page. 

(Github link: https://github.com/ope079/SainsburysWebpageParser)



----------------

Instructions:

----------------

- "cd %directory%/SainsburysWebpageParser" -- to locate the project files
- "mvn compile" -- to compile the app
- "mvn exec: java" -- to execute the file
- Upon seeing the text "Input sainsbury's groceries website address:" enter the   correct url.

or

- "cd %directory%/SainsburysWebpageParser" -- to locate the project files
- "mvn package" to compile the jar
- "java -jar target/SainsburysWebpageParser-0.0.1-SNAPSHOT.jar" to run the jar file
- Upon seeing the text "Input sainsbury's groceries website address:" enter the   correct url.

then

- "mvn test" to apply the unit testing





-------------------

Dependencies

-------------------
The following dependencies are needed to compilt the project:
- jsoup 1.11.3 -- for html parsing and DOM manipulation
- jackson databind 2.11.0 -- for JSON binding / converting java pojos into json
- jUnit 4.12 -- for unit testing (test coverage is 86.5%)

---------------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.sainsburys.SainsburysWebpageParser.AppTest
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 17.912 sec

Results :

Tests run: 4, Failures: 0, Errors: 0, Skipped: 0