package org.multi.common;

/**
 * Represents a player in a multiprocess communication system.
 * Each player has a unique name and serves as the base class
 * for specific player implementations.
 */
public class Player {

    private final String name;

    /**
     * Constructs a new Player instance with a given name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}

