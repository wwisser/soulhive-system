package de.soulhive.system.clan.commands.subcommands;

import de.soulhive.system.clan.ClanService;
import de.soulhive.system.clan.commands.ClanCommand;
import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@AllArgsConstructor
public class CommandDisband implements ClanCommand {

    private static final String ARGUMENT = "disband";

    private ClanService clanService;

    @Override
    public boolean process(Player player, String[] args) {
        String uuid = player.getUniqueId().toString();
        Clan clan = this.clanService.getLocalClanStorage().getClan(uuid);

        if (clan == null) {
            player.sendMessage(Settings.PREFIX + "§cDu bist in keinem Clan");
        } else if (clan.getOwner().equals(uuid)) {
            player.sendMessage(Settings.PREFIX + "§cDer Clan " + clan.getName() + " gehört dir nicht.");
        } else {
            for (String member : clan.getMembers()) {
                Player memberPlayer = Bukkit.getPlayer(UUID.fromString(member));
                if (memberPlayer != null && memberPlayer.isOnline()) {
                    memberPlayer.sendMessage(Settings.PREFIX + "§cDein Clan wurde aufgelöst.");
                }
            }

            this.clanService.getLocalClanStorage().removeClan(clan);
            this.clanService.getDatabaseClanStorage().deleteClan(clan);
        }

        return true;
    }

    @Override
    public String getArgument() {
        return ARGUMENT;
    }

}
