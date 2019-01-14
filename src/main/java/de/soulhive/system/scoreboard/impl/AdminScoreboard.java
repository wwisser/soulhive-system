package de.soulhive.system.scoreboard.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.scoreboard.DynamicScoreboard;
import de.soulhive.system.vanish.VanishService;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class AdminScoreboard extends DynamicScoreboard {

    public AdminScoreboard() {
        super(" §9SoulHive ");

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
        super.updateLine("tps", "  §f" + MinecraftServer.getServer().recentTps[0]);
        super.updateLine("ping", "  §f" + ping);
    }

}
