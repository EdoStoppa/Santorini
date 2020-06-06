package it.polimi.ingsw;

import it.polimi.ingsw.Server.Server;
import java.io.IOException;

public class ServerApp {
    public static void main( String[] args )
    {
        Server server;
        try {
            if(args.length == 0)
                server = new Server();
            else if(args.length == 1){
                int port = Integer.parseInt(args[0]);
                if(port<0 || port>65535)
                    throw new IllegalArgumentException("the port number is wrong");

                server = new Server(port);
            } else
                throw new IllegalArgumentException("too many arguments");

            server.run();
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Impossible to initialize the server: " + e.getMessage() + "!");
        }
    }
}
