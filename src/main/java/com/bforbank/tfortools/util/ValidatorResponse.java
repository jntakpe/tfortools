package com.bforbank.tfortools.util;

/**
 * Réponse au validator JavaScript
 *
 * @author jntakpe
 */
public class ValidatorResponse {

    private final boolean valid;

    public ValidatorResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return "ValidatorResponse{" +
                "valid=" + valid +
                '}';
    }
}
