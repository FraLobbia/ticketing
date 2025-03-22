package com.authentication.models.enums;

import java.util.Arrays;

public enum AuthExceptionEnum {
    
    // Errori di login
    INVALID_CREDENTIALS("AUTH001", "Credenziali non valide."),
    ACCOUNT_LOCKED("AUTH002", "Account bloccato."),
    ACCOUNT_DISABLED("AUTH003", "Account disabilitato."),
    EMAIL_NOT_FOUND("AUTH004", "Email non trovata."),
    INVALID_PASSWORD("AUTH005", "Password non valida."),

    // Errori di registrazione
    EMAIL_ALREADY_EXISTS("AUTH006", "Email già in uso."),
    WEAK_PASSWORD("AUTH007", "Password debole."),
    INVALID_EMAIL_FORMAT("AUTH008", "Formato email non valido."),
    REGISTRATION_FAILED("AUTH009", "Registrazione fallita."),

    // Altri errori di autenticazione
    UNAUTHORIZED_ACCESS("AUTH010", "Accesso non autorizzato."),
    TOKEN_EXPIRED("AUTH011", "Token scaduto."),
    INVALID_TOKEN("AUTH012", "Token non valido."),
    GENERIC_ERROR("AUTH013", "Errore generico.");

    private final String exceptionCode;
    private final String message;

    AuthExceptionEnum(String code, String message) {
        this.exceptionCode = code;
        this.message = message;
    }

    public String getCode() {
        return exceptionCode;
    }

    public String getMessage() {
        return message;
    }
    
    public static AuthExceptionEnum getEnumByCode(String code) {
        return Arrays.stream(AuthExceptionEnum.values())
                .filter(e -> e.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null); 
    }
}

