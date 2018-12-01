package de.soulhive.system.scoreboard.impl;

import de.soulhive.system.scoreboard.DynamicScoreboard;
import de.soulhive.system.user.User;
import org.bukkit.Bukkit;

public class UserScoreboard extends DynamicScoreboard {

    public UserScoreboard() {
        super(" §9§lSoulHive ");

        super.addBlankLine(7);
        super.addStaticLine(" §7Spieler", 6);
        super.addDynamicLine("players", "§f", "  ???", 5);
        super.addStaticLine(" §7Kills", 4);
        super.addDynamicLine("kills", "§f", "  ???", 3);
        super.addStaticLine(" §dJuwelen", 2);
        super.addDynamicLine("jewels", "§f", "  ???", 1);
        super.addBlankLine(0);
    }

    public void update(User user) {
        super.updateLine("players", "  §f" + Bukkit.getOnlinePlayers().size());
        super.updateLine("kills", "  §f" + user.getKills());
        super.updateLine("jewels", "  §f" + user.getJewels());
    }

}
