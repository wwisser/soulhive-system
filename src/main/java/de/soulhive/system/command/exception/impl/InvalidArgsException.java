package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;

public class InvalidArgsException extends CommandException {

    public InvalidArgsException(String usage) {
        super(Settings.COMMAND_USAGE + usage);
    }

}
