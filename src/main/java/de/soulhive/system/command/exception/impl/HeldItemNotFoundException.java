package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;

public class HeldItemNotFoundException extends CommandException {

    public HeldItemNotFoundException() {
        super("§cDu musst ein Item in deiner Hand halten.");
    }

}
