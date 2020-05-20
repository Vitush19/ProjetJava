Rent Manager Project : 

Here are all the requested features that have been implemented:

 - list, add, modify and delete customers, vehicles and reservations
    - we must not encounter "bugs" blocking the site by using it "normally" -> manage and create pages/messages stating the problems with buttons to go back and/or to the main menu
- Service functions that must be executable in the CLI
- Respect for 3-layer architecture

- Compliance with the following constraints : 
        - a customer who is not 18 years old cannot be created...
        - a customer with an email address already taken cannot be created
        - a client's first and last name must be at least 3 characters long
        - a car cannot be booked twice in the same day.
        - a car cannot be booked more than 7 days in a row by the same user
        - a car cannot be booked 30 days in a row without a break 
        - If a customer or vehicle is deleted, then the associated reservations must be deleted.
        - a car must have one model and one manufacturer, its number of seats must be between 2 and 9


To execute the project, open a command terminal, 
then go to the root of the project named "RentManager" and enter the following line : 
"mvn clean install tomcat7:run"

Finally, just enter the following url to navigate on the site "http://localhost:8080/RentManager/home".