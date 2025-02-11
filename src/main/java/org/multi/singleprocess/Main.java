package org.multi.singleprocess;

import org.multi.common.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The main class that initiates the player communication system.
 * It creates two players, assigns them their respective queues,
 * and starts them in separate threads to exchange messages.
 */
public class Main {
    public static void main(String[] args) {
        BlockingQueue<Message> queue1 = new LinkedBlockingQueue<>();
        BlockingQueue<Message> queue2 = new LinkedBlockingQueue<>();

        // Create players with assigned queues
        MultiThreadPlayer initiator = new MultiThreadPlayer("Initiator", queue1, queue2, true);
        MultiThreadPlayer opponent = new MultiThreadPlayer("Opponent ", queue2, queue1, false);

        // Create a thread for each player
        Thread initiatorThread = new Thread(initiator);
        Thread opponentThread = new Thread(opponent);

        // Start both player threads
        initiatorThread.start();
        opponentThread.start();

        // Wait for both threads to complete execution
        try {
            initiatorThread.join();
            opponentThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("All messages sent and received. Program terminating.");
    }
}
