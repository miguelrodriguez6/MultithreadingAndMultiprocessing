package org.multi.multipleprocess;

import org.multi.common.Message;
import org.multi.common.Player;

import java.io.*;
import java.net.*;

/**
 * MultiProcessPlayer represents a player in the communication system.
 * It uses sockets to send and receive messages between two players.
 */
public class MultiProcessPlayer extends Player {
    private int messageCount = 0;
    private final boolean isInitiator;
    private final int port;
    private final String opponentHost;
    private final int opponentPort;
    private static final String TERMINATION_MESSAGE = "END_COMMUNICATION";

    /**
     * Constructs a MultiProcessPlayer instance.
     *
     * @param name         The name of the player.
     * @param isInitiator  Indicates if the player starts the communication.
     * @param port         The port on which this player listens for messages.
     * @param opponentHost The hostname or IP address of the opponent.
     * @param opponentPort The port on which the opponent listens for messages.
     */
    public MultiProcessPlayer(String name, boolean isInitiator, int port, String opponentHost, int opponentPort) {
        super(name);
        this.isInitiator = isInitiator;
        this.port = port;
        this.opponentHost = opponentHost;
        this.opponentPort = opponentPort;
    }

    /**
     * Starts the player process.
     * It launches the server thread and, if the player is the initiator,
     * sends the first message.
     */
    public void start() {
        new Thread(this::startServer).start();
        if (isInitiator) {
            try {
                // Allow time for the server to start
                Thread.sleep(1000);
                messageCount++;
                sendMessage(new Message("Hello world!", messageCount));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the server that listens for incoming messages.
     */
    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(getName() + " listening on port " + port);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());  // Primero OutputStream
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                    Message receivedMessage = (Message) in.readObject();
                    System.out.println(getName() + " received: " + receivedMessage.getText());

                    // Check for termination message
                    if (receivedMessage.getText().contains(TERMINATION_MESSAGE)) {
                        System.out.println(getName() + " shutting down.");
                        break;
                    }

                    // If initiator and message count reaches 10, send termination message
                    if (isInitiator && messageCount >= 10) {
                        sendMessage(new Message(TERMINATION_MESSAGE, null));
                        System.out.println(getName() + " shutting down.");
                        break;
                    }

                    messageCount++;
                    String newMessage = receivedMessage + " " + receivedMessage.getCounter();
                    sendMessage(new Message(newMessage, messageCount));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a message to the opponent.
     *
     * @param message The message object to send.
     */
    private void sendMessage(Message message) {
        try (Socket socket = new Socket(opponentHost, opponentPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(getName() + " failed to send message: " + e.getMessage());
        }
    }

    /**
     * Main method to initialize and start the player.
     *
     * @param args Command-line arguments: <name> <isInitiator> <port> <opponentHost> <opponentPort>
     */
    public static void main(String[] args) {
        if (args.length != 5) {
            System.err.println("Usage: java Player <name> <isInitiator> <port> <opponentHost> <opponentPort>");
            return;
        }

        String name = args[0];
        boolean isInitiator = Boolean.parseBoolean(args[1]);
        int port = Integer.parseInt(args[2]);
        String opponentHost = args[3];
        int opponentPort = Integer.parseInt(args[4]);

        MultiProcessPlayer player = new MultiProcessPlayer(name, isInitiator, port, opponentHost, opponentPort);
        player.start();
    }
}

