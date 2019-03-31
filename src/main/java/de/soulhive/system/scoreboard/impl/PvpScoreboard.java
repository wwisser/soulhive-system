package de.soulhive.system.scoreboard.impl;

import de.soulhive.system.scoreboard.DynamicScoreboard;
import de.soulhive.system.scoreboard.ScoreboardType;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;

public class PvpScoreboard extends DynamicScoreboard {

    public PvpScoreboard() {
        super(" " + Settings.NAME + " ");

        super.addBlankLine(7);
        super.addStaticLine(" §7Kills", 6);
        super.addDynamicLine("kills", "§f", "  ???", 5);
        super.addStaticLine(" §7Deaths", 4);
        super.addDynamicLine("deaths", "§f", "  ???", 3);
        super.addStaticLine(" §7KD/r", 2);
        super.addDynamicLine("kdr", "§f", "  ???", 1);
        super.addBlankLine(0);
    }

    public void update(User user) {
        super.updateLine("kills", "  §f" + user.getKills());
        super.updateLine("deaths", "  §f" + user.getDeaths());
        super.updateLine("kdr", "  §f" + user.getKdr());
    }

    @Override
    public ScoreboardType getType() {
        return ScoreboardType.PVP;
    }
}
