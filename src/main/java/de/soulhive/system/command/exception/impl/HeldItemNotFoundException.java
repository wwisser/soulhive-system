package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;

public class HeldItemNotFoundException extends CommandException {

    public HeldItemNotFoundException() {
        super("§cDu musst ein Item in deiner Hand halten.");
    }

}
