package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;

public class InvalidSenderException extends CommandException {

    @Override
    public String getMessage() {
        return Settings.COMMAND_ONLY_PLAYERS;
    }

}
