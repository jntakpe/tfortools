package com.sopra.tfortools.util;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Classe utilitaire pour Spring security
 *
 * @author jntakpe
 */
public final class SecurityUtils {

    public static TftUser getCurrentUser() {
        return (TftUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
