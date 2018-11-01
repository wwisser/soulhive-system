package de.soulhive.system.command.exception.impl;

import de.soulhive.system.command.exception.CommandException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TargetNotFoundException extends CommandException {

    private String target;

}
