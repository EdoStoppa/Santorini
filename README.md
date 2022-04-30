<div align="center">
  <img src="https://github.com/EdoStoppa/EdoStoppa/blob/main/imgs/Santorini/startBackground.png?raw=true" alt="Santorini">
<div />
<div align="left"><div /><br />

This prject is the virtual adaptation of the board game called "Santorini".
The game is multiplayer (up to 3 players), and can be played using a GUI or a CLI.
The project was deisgned following the MVC architectural pattern using Java.
To run a multiplayer game a Server application was built completely from scratch.

The development team was formed by:
- Samuele Tagliabue - [samtagliabue](https://github.com/samtagliabue)
- Gianmarco Vuolo - [GiamboVuolo](https://github.com/GiamboVuolo)
- Edoardo Stoppa - [EdoStoppa](https://github.com/EdoStoppa)

  
<div align="center">
  <img src="https://github.com/EdoStoppa/EdoStoppa/blob/main/imgs/Santorini/chose_gods.png?raw=true" alt="God Choosing Screen" width="450" height="400" >
  <img src="https://github.com/EdoStoppa/EdoStoppa/blob/main/imgs/Santorini/in_game.png?raw=true" alt="Game Board" width="450" height="400" >
<div />
<div align="left"><div /><br />

## Technology Stack

- Java 14
- JavaFX 14
- JUnit library to write tests
- Maven package to manage testing, deployment, etc...

## UML

In the folder `deiverables/uml` there are 3 other foledrs:

- In `/Final UML` you can find (.png and .jpg version) the final project UML (slightly modified to enhance the readibility)
- In `/Development Snapshots UML` you can find multiple .jpg files that represent some "snapshots" about the state of developement of the project while it was
in development
- In `/Complete UML` you can find multiple UML files automatically generated b Intellij 

## JAR Generation

The JAR files generated at the end will be 3:
- "ServerApp.jar"
- "ClientCliApp.jar"
- "ClientGuiApp.jar"

Every JAR file is created using the command: `mvn package`, and will be place in the folder called `executable`. 
We have included all the JAR files needed to run a game into `deliverables/JARs`. To run a game simply use them.

## JAR Execution

### Server
To execute the server application is sufficient to run the command:
```
java -jar ServerApp.jar [port number]
```
Where `[port number]` is the port number where the server will be listening for new connection. It's an optional parameter.

### Client
Both client versions allow connections to the Server choosing the IP and port number at runtime.
The default values are `IP = "127.0.0.1"` and `port = "54321"`. If you're only interest in executing everything locally
then no other parameter is required. Let's make some examples:

EX 1:
I start the Server with the default values (port 54321) but not locally, then it will be required to insert the Sever IP and the Server port the Clients.

Ex 2:
I start the Server with a new port (55555) locally, then it will be necessary to insert manually the default Server IP ("127.0.0.1") and the new Server port in the Clients.

#### CLI
To use the CLI version of the game your terminal must support Unicode and ANSI escape characters. This is true (generally) for Linux and MacOS, but not for Windows.
(We've tested the game on Windows using [ConEMU](https://conemu.github.io/)).

The command to execute the CLI client is:
```
java -jar ClientCliApp.jar
```

#### GUI
The command to execute the GUI client is:
```
java -jar ClientGuiApp.jar
```

***N.B.:*** A Java SDK or Java JRE  wrsion 14+ is necessary to use the GUI.
(si consiglia [AdoptOpenJDK](https://adoptopenjdk.net/releases.html)) .
