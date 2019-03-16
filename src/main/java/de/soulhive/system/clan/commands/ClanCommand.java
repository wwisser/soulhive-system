package de.soulhive.system.clan.commands;

import org.bukkit.entity.Player;

public interface ClanCommand {

    boolean process(Player player, String[] args);

    String getArgument();

}
