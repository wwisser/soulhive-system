package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;

public class PermissionException extends CommandException {

    public PermissionException() {
        super(Settings.COMMAND_NO_PERMISSION);
    }

}
