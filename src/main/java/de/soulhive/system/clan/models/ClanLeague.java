package de.soulhive.system.clan.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public enum ClanLeague {

    NONE("Keine Liga", ChatColor.WHITE, 0, 9),
    COAL("Kohle", ChatColor.DARK_GRAY, 10, 25, 50, 75, 99),
    BRONZE("Bronze", ChatColor.RED, 100, 150, 200, 250, 299),
    SILVER("Silber", ChatColor.GRAY, 300, 400, 500, 575, 599),
    TITAN("Titan", ChatColor.BLUE, 600, 700, 800, 900, 999),
    SAPPHIRE("Saphir", ChatColor.DARK_RED, 1000, 1500, 2000, 2500, 2999),
    JEWEL("Juwelen", ChatColor.LIGHT_PURPLE, 3000, 4000, 5000, 6000, 7000);

    private String name;
    private ChatColor chatColor;
    private int[] level;

    ClanLeague(String name, ChatColor chatColor, int... level) {
        this.name = name;
        this.chatColor = chatColor;
        this.level = level;
    }

    public static InfoMapper getInfo(final int kills) {
        ClanLeague maxLeague = ClanLeague.values()[ClanLeague.values().length - 1];

        if (kills >= maxLeague.getLevel()[maxLeague.getLevel().length - 1]) {
            return new InfoMapper(maxLeague, 5);
        }

        for (int i = ClanLeague.values().length - 1; i >= 0; i--) {
            ClanLeague league = ClanLeague.values()[i];

            if (kills >= league.getLevel()[0] && kills <= league.getLevel()[league.getLevel().length - 1]) {
                int count = 0;
                for (int level : league.getLevel()) {
                    if (kills > level) {
                        count++;
                    } else {
                        break;
                    }
                }

                return new InfoMapper(league, count);
            }
        }

        return null;
    }

    @AllArgsConstructor
    @Getter
    public static class InfoMapper {
        private ClanLeague league;
        private int level;
    }

}
