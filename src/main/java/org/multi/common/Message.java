package org.multi.common;

import java.io.Serializable;

/**
 * Represents a message exchanged between players.
 * Each message contains a text payload and an optional counter
 * that tracks the number of messages exchanged.
 * This class implements {@link Serializable} to allow messages
 * to be sent over network streams.
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String text;
    private final Integer counter;

    /**
     * Constructs a new Message instance.
     *
     * @param text    The text content of the message.
     * @param counter The counter associated with the message, can be null.
     */
    public Message(String text, Integer counter) {
        this.text = text;
        this.counter = counter;
    }

    public String getText(){
        return text;
    }

    public Integer getCounter(){
        return counter;
    }

    /**
     * Returns a string representation of the message.
     * This overrides the default {@code toString()} method
     *
     * @return The message text.
     */
    @Override
    public String toString() {
        return text;
    }
}
