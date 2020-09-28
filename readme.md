# Welcome to TicTacToe!

## Project structure
This project is built using Apache Maven. To run this project, simply clone the repository and import the `pom.xml` 
file into your favorite IDE.

### Important features:
1. Stateless Authentication and Authorization - via JSON Web Tokens and Spring Secrutiy.
2. REST API documentation - accessible at [/api/v1/docs-ui.html](http://localhost:8080/api/v1/docs-ui.html) upon running the application.
3. Persistent storage of users and permissions via Spring Data JPA. The data will be stored in the `./db` directory.
It is possible to easily persist the TicTacToe game data with only minor refactoring.
4. Unit tests with JUnit5. Could be run individually or as a part of the Maven build.
5. Postman skeleton requests. The collection (v2.1) can be imported into Postman to ease end-to-end testing.
## Playing the game
In order to play the game, user X must login and start the game. The Authorization HTTP header is used to provide the JWT to the API. The already existing Postman scripts will take care of fetching the token. 

Only users with the granted authority
`START_GAME` can start a game. The state of the game is returned as the body of an HTTP response after a valid move has been made by a player. 
The API does not produce a JSON response because in this case that improves the readability.

Once the game is over, user X can start a new game. 

### Potential improvenets
1. Persist data of games into database, tie with two players, add game sessions, etc.
2. Implement scoreboards.
3. Make a "Single player vs the Computer" mode.
4. Webapp user interface.
5. Better handling of secrets. Use environment variables/injection by platform.

