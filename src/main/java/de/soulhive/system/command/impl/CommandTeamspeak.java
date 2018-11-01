package de.soulhive.system.command.impl;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public class CommandTeamspeak extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(Settings.PREFIX + "Unser TeamSpeak-Server: §fts.soulhive.de");
    }

}