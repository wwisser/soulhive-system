package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidArgsException extends CommandException {

    private String message;

    @Override
    public String getMessage() {
        return Settings.PREFIX + "§c" + message;
    }

}
