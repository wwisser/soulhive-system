package de.soulhive.system.command;

import de.soulhive.system.command.impl.none.CommandNone;
import de.soulhive.system.service.Service;
import de.soulhive.system.util.ReflectUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.stream.Collectors;

public class CommandService extends Service {

    public static final CommandExecutor NO_COMMAND = new CommandNone();
    private static final String PACKAGE = CommandService.class.getPackage().getName();
    private static final int INDEX_SUBSTRING = "Command".length();

    private Map<String, CommandExecutor> commands;
    private JavaPlugin plugin;

    public CommandService(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void initialize() {
        this.resolveCommands();
        this.commands.forEach((name, commandExecutor) -> super.registerCommand(name, commandExecutor));
    }

    @Override
    public void disable() {
        this.commands.values().forEach(this::unregisterCommand);
    }

    private void resolveCommands() {
        this.commands = ReflectUtils.getPacketObjects(PACKAGE, CommandExecutorWrapper.class)
            .stream()
            .collect(
                Collectors.toMap(
                    commandExecutor -> commandExecutor.getClass().getSimpleName().substring(INDEX_SUBSTRING),
                    commandExecutor -> commandExecutor
                )
            );
    }

    private void unregisterCommand(CommandExecutor commandExecutor) {
        this.plugin.getCommand(
            commandExecutor.getClass().getSimpleName().substring(INDEX_SUBSTRING)
        ).setExecutor(NO_COMMAND);
    }

}
