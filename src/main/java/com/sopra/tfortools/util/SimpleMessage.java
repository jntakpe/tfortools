package com.sopra.tfortools.util;

/**
 * Objet définissant un message basique renvoyé à une vue
 *
 * @author jntakpe
 */
public class SimpleMessage {


    private final boolean success;

    private final String message;

    private SimpleMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static SimpleMessage success(String message) {
        return new SimpleMessage(true, message);
    }

    public static SimpleMessage error(String message) {
        return new SimpleMessage(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
