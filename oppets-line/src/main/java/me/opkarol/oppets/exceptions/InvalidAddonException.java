package me.opkarol.oppets.exceptions;

public class InvalidAddonException extends RuntimeException {

    public InvalidAddonException(String errorMessage) {
        super(errorMessage);
    }
}
