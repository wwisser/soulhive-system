package de.soulhive.system.command;

import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandExecutorWrapper implements CommandExecutor {

    private boolean initialized = false;

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!this.initialized) {
            this.initialized = true;

            try {
                this.initialize();
            } catch (Exception e) {
                System.out.println("An error occurred while initializing command: " + e.getMessage());
                e.printStackTrace();
            }
        }

        try {
            this.process(sender, label, args);
        } catch (Exception e) {
            String message = e.getMessage();

            if (e instanceof CommandException) {
                message = Settings.PREFIX + "§c" + e.getMessage();
            } else {
                message = Settings.PREFIX + "§c" + e.getClass().getSimpleName() + ": §o" + message;
            }

            sender.sendMessage(message);
        }

        return true;
    }

    public abstract void process(CommandSender sender, String label, String[] args) throws CommandException;

    protected void initialize() {
        // placeholder method, can be overwritten
    }

}
