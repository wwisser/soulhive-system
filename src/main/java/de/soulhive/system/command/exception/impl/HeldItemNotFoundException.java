package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;

public class HeldItemNotFoundException extends CommandException {

    private static final String MESSAGE = "§cDu musst ein Item in deiner Hand halten.";

    @Override
    public String getMessage() {
        return Settings.PREFIX + MESSAGE;
    }

}
