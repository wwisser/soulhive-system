package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;

public class HeldItemNotFoundException extends CommandException {

    private static final String MESSAGE = Settings.PREFIX + "§cDu musst ein Item in deiner Hand halten.";

    public HeldItemNotFoundException() {
        super(MESSAGE);
    }

}
