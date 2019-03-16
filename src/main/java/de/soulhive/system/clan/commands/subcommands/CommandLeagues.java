package de.soulhive.system.clan.commands.subcommands;

import de.soulhive.system.clan.commands.ClanCommand;
import de.soulhive.system.clan.models.ClanLeague;
import de.soulhive.system.setting.Settings;
import org.bukkit.entity.Player;

public class CommandLeagues implements ClanCommand {

    private static final String ARGUMENT = "leagues";

    @Override
    public boolean process(Player player, String[] args) {
        player.sendMessage(Settings.PREFIX + "Clanligen");

        for (ClanLeague league : ClanLeague.values()) {
            if (league == ClanLeague.NONE) {
                continue;
            }

            player.sendMessage(
                "  §8- " + league.getChatColor() + league.getName()
                    + " §8(" + league.getChatColor() + league.getLevel()[0] + " - "
                    + league.getLevel()[league.getLevel().length - 1] + " Kills§8)"
            );
        }

        return true;
    }

    @Override
    public String getArgument() {
        return ARGUMENT;
    }

}
