package de.soulhive.system.command.exception;

import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandException extends Exception {

    private String message;

    @Override
    public String getMessage() {
        return Settings.PREFIX + this.message;
    }

}
