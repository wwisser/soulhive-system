package de.soulhive.system.command.impl;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;

public class CommandTeamspeak extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        sender.sendMessage(Settings.PREFIX + "TeamSpeak-IP: §fts.soulhive.de");
        sender.sendMessage(Settings.PREFIX + "Discord-Server: §fhttps://discord.gg/mxBaDKj");
    }

}
