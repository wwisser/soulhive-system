package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;

public class InvalidAmountException extends CommandException {

    public InvalidAmountException(final String unparsedNumber) {
        super("§c'" + unparsedNumber + "' ist keine gültige Zahl.");
    }

}
