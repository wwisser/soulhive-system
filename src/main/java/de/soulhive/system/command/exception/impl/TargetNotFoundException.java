package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class TargetNotFoundException extends CommandException {

    public TargetNotFoundException(String target) {
        super("§cZiel '" + target + "' nicht gefunden.");
    }

}
