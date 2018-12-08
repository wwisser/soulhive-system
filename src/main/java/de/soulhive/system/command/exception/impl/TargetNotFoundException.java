package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;

public class TargetNotFoundException extends CommandException {

    public TargetNotFoundException(String target) {
        super("§cZiel '" + target + "' nicht gefunden.");
    }

}
