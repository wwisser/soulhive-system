package de.soulhive.system.clan.commands.subcommands;

import de.soulhive.system.clan.ClanService;
import de.soulhive.system.clan.commands.ClanCommand;
import de.soulhive.system.clan.models.Clan;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandStats implements ClanCommand {

    private static final String ARGUMENT = "stats";

    private ClanService clanService;

    @Override
    public boolean process(Player player, String[] args) {
        if (args.length == 1) {
            Clan clan = this.clanService.getLocalClanStorage().getClan(player.getUniqueId().toString());

            if (clan == null) {
                return false;
            }

            this.sendClanStats(player, clan);
            return true;
        }
        return true;
    }

    @Override
    public String getArgument() {
        return ARGUMENT;
    }

    private void sendClanStats(Player player, Clan clan) {
        //player.sen
    }

}
