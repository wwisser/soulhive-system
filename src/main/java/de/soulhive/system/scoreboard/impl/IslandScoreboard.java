package de.soulhive.system.scoreboard.impl;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import de.soulhive.system.scoreboard.DynamicScoreboard;
import de.soulhive.system.scoreboard.ScoreboardType;
import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Player;

import java.util.UUID;

public class IslandScoreboard extends DynamicScoreboard {

    private static ASkyBlockAPI ASKYBLOCK_API = ASkyBlockAPI.getInstance();

    public IslandScoreboard() {
        super(" " + Settings.NAME + " ");

        super.addBlankLine(7);
        super.addStaticLine(" §7Name", 6);
        super.addDynamicLine("name", "§f", "  ???", 5);
        super.addStaticLine(" §7Level", 4);
        super.addDynamicLine("level", "§f", "  ???", 3);
        super.addStaticLine(" §7Mitglieder", 2);
        super.addDynamicLine("members", "§f", "  ???", 1);
        super.addBlankLine(0);
    }

    public void update(Player player) {
        UUID uuid = player.getUniqueId();

        super.updateLine("name", "  §f" + ASKYBLOCK_API.getIslandName(uuid));
        super.updateLine("level", "  §f" + ASKYBLOCK_API.getLongIslandLevel(uuid));
        super.updateLine("members", "  §f" + ASKYBLOCK_API.getTeamMembers(uuid).size());
    }

    @Override
    public ScoreboardType getType() {
        return ScoreboardType.ISLAND;
    }

}
