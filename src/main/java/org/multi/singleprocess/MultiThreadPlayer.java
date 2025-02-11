package org.multi.singleprocess;

import org.multi.common.Message;
import org.multi.common.Player;

import java.util.concurrent.BlockingQueue;

/**
 * Represents a Player in the communication system.
 * Each Player runs in a separate thread and exchanges messages with another Player
 * using blocking queues.
 */
public class MultiThreadPlayer extends Player implements Runnable{
    private final BlockingQueue<Message> myQueue;
    private final BlockingQueue<Message> otherQueue;
    private final boolean isInitiator;
    private int messageCounter = 0;
    private static final String TERMINATION_MESSAGE = "END_COMMUNICATION";

    /**
     * Constructs a Player instance.
     *
     * @param name         The name of the player.
     * @param myQueue      The queue from which this player receives messages.
     * @param otherQueue   The queue to which this player sends messages.
     * @param isInitiator  Determines if this player starts the communication.
     */
    public MultiThreadPlayer(String name, BlockingQueue<Message> myQueue, BlockingQueue<Message> otherQueue, boolean isInitiator) {
        super(name);
        this.myQueue = myQueue;
        this.otherQueue = otherQueue;
        this.isInitiator = isInitiator;
    }

    /**
     * Executes the player's messaging behavior.
     * The initiator starts by sending a message, and both players continue exchanging
     * messages until the initiator reaches the limit of 10 messages.
     */
    @Override
    public void run() {
        try {
            if (isInitiator) {
                messageCounter++;
                Message message = new Message("Hello world!", messageCounter);
                otherQueue.put(message);
            }
            while (true) {
                Message receivedMessage = myQueue.take();
                System.out.println(getName() + " received: " + receivedMessage.getText());

                if (receivedMessage.getText().equals(TERMINATION_MESSAGE)) {
                    break;
                }

                if (isInitiator && messageCounter >= 10) {
                    Message terminationMessage = new Message(TERMINATION_MESSAGE, null);
                    otherQueue.put(terminationMessage);
                    break;
                }

                messageCounter++;
                Message newMessage = new Message(receivedMessage.getText() + " " + receivedMessage.getCounter(), messageCounter);
                otherQueue.put(newMessage);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}