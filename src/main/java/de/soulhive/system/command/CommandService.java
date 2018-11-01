package de.soulhive.system.command;

import de.soulhive.system.command.impl.none.CommandNone;
import de.soulhive.system.service.Service;
import de.soulhive.system.util.reflect.ReflectUtils;
import org.bukkit.command.CommandExecutor;

import java.util.Map;
import java.util.stream.Collectors;

public class CommandService extends Service {

    public static final CommandExecutor NO_COMMAND = new CommandNone();
    private static final String PACKAGE = CommandService.class.getPackage().getName();
    private static final int INDEX_SUBSTRING = "Command".length();

    @Override
    public Map<String, CommandExecutor> getCommands() {
        return ReflectUtils.getPacketObjects(PACKAGE, CommandExecutorWrapper.class)
            .stream()
            .collect(
                Collectors.toMap(
                    commandExecutor -> commandExecutor.getClass().getSimpleName().substring(INDEX_SUBSTRING),
                    commandExecutor -> commandExecutor
                )
            );
    }

}
