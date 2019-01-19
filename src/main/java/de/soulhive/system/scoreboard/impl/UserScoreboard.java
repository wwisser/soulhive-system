package de.soulhive.system.scoreboard.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.scoreboard.DynamicScoreboard;
import de.soulhive.system.scoreboard.ScoreboardType;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.vanish.VanishService;
import org.bukkit.Bukkit;

public class UserScoreboard extends DynamicScoreboard {

    private static final ScoreboardType SCOREBOARD_TYPE = ScoreboardType.USER;


    public UserScoreboard() {
        super(" " + Settings.NAME + " ");


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
        VanishService vanishService = SoulHive.getServiceManager().getService(VanishService.class);

        super.updateLine("players", "  §f" + vanishService.getOnlinePlayersDiff());
        super.updateLine("kills", "  §f" + user.getKills());
        super.updateLine("jewels", "  §f" + user.getJewels());
    }

    @Override
    public ScoreboardType getType() {
        return SCOREBOARD_TYPE;
    }

}
