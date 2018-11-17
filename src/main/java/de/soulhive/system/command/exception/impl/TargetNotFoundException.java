package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TargetNotFoundException extends CommandException {

    private String target;

    @Override
    public String getMessage() {
        return Settings.PREFIX + "§cZiel '" + target + "' nicht gefunden.";
    }

}
