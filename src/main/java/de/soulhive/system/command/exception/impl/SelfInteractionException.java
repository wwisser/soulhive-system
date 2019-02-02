package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;

public class SelfInteractionException extends CommandException {

    public SelfInteractionException() {
        super("§cDu darfst nicht mit dir selbst interagieren!");
    }

}
