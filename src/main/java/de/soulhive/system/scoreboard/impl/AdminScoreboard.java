package de.soulhive.system.scoreboard.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.scoreboard.DynamicScoreboard;
import de.soulhive.system.scoreboard.ScoreboardType;
import de.soulhive.system.vanish.VanishService;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class AdminScoreboard extends DynamicScoreboard {

    private static final ScoreboardType SCOREBOARD_TYPE = ScoreboardType.ADMIN;

    public AdminScoreboard() {
        super(" §9§lSoulHive ");

        super.addBlankLine(7);
        super.addStaticLine(" §7Spieler", 6);
        super.addDynamicLine("players", "§f", "  ???", 5);
        super.addStaticLine(" §7TPS", 4);
        super.addDynamicLine("tps", "§f", "  ???", 3);
        super.addStaticLine(" §7Ping", 2);
        super.addDynamicLine("ping", "§f", "  ???", 1);
        super.addBlankLine(0);
    }

    public void update(final Player player) {
        VanishService vanishService = SoulHive.getServiceManager().getService(VanishService.class);
        int ping = ((CraftPlayer) player).getHandle().ping;

        if (ping > 999 || ping < 1) {
            ping = 999;
        }

        super.updateLine("players", "  §f" + vanishService.getOnlinePlayersDiff());
        super.updateLine("tps", "  §f" + this.formatTps(MinecraftServer.getServer().recentTps[0]));
        super.updateLine("ping", "  §f" + ping);
    }

    private String formatTps(double tps) {
        return (tps > 18.0D ? ChatColor.GREEN : (tps > 16.0D ? ChatColor.YELLOW : ChatColor.RED)).toString()
            + (tps > 20.0D ? "*" : "")
            + Math.min((double) Math.round(tps * 100.0D) / 100.0D, 20.0D);
    }

    @Override
    public ScoreboardType getType() {
        return SCOREBOARD_TYPE;
    }

}
