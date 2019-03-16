package de.soulhive.system.stats.label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatsLabel {

    KILLS("Kills", "Kills", 2),
    DEATHS("Tode", "Tode", 3),
    VOTES("Votes", "Votes", 4),
    PLAYTIME("Spielzeit", "Stunden", 5),
    JEWELS("Juwelen", "Juwelen", 6),
    ISLAND_LEVEL("IS-Level", "Level", -1),
    CLAN_KILLS("Clans", "Kills", -1);

    private String displayName;
    private String additive;
    private int databaseColumn;

}
