package org.multi.multipleprocess;

import java.io.IOException;

/**
 * The main class that initiates the player communication system.
 * It creates two players, assigns them their respective queues,
 * and starts them in separate process to exchange messages.
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Create a process for Player 1 (Initiator)
            // Arguments: Role ("Initiator"), flag ("true"), port (5000), host ("localhost"), opponent's port (5001)
            ProcessBuilder player1 = new ProcessBuilder("java", "-cp", "target/classes/", "org.assessment.multiple_processes.MultiProcessPlayer", "Initiator", "true", "5000", "localhost", "5001");

            // Create a process for Player 2 (Opponent)
            // Arguments: Role ("Opponent"), flag ("false"), port (5001), host ("localhost"), opponent's port (5000)
            ProcessBuilder player2 = new ProcessBuilder("java", "-cp", "target/classes/", "org.assessment.multiple_processes.MultiProcessPlayer", "Opponent", "false", "5001", "localhost", "5000");

            // Ensure both processes inherit the I/O streams of the main process
            player1.inheritIO();
            player2.inheritIO();

            // Start both processes
            Process p1 = player1.start();
            Process p2 = player2.start();

            // Wait for both processes to complete execution before proceeding
            p1.waitFor();
            p2.waitFor();

            System.out.println("Exit program.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
