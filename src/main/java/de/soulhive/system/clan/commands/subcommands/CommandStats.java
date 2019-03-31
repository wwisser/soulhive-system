package de.soulhive.system.clan.commands.subcommands;

import de.soulhive.system.SoulHive;
import de.soulhive.system.clan.ClanService;
import de.soulhive.system.clan.commands.ClanCommand;
import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.user.UserService;
import lombok.NonNull;
import org.bukkit.entity.Player;

public class CommandStats implements ClanCommand {

    private static final String ARGUMENT = "stats";

    @NonNull private ClanService clanService;
    private UserService userService;

    public CommandStats(ClanService clanService) {
        this.clanService = clanService;
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @Override
    public boolean process(Player player, String[] args) {
        String uuid;

        if (args.length == 1) {
            uuid = player.getUniqueId().toString();

            Clan clan = this.clanService.getLocalClanStorage().getClan(player.getUniqueId().toString());

            if (clan == null) {
                return false;
            }

            this.sendClanStats(player, clan);
            return true;
        } else {
            this.userService.getUserByName(args[1]).getUuid()
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
