package de.skydust.system.stats.label;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatsLabel {

    PLAYTIME("Spielzeit", "Stunden"),
    KILLS("Kills", "Kills"),
    DEATHS("Tode", "Tode"),
    JEWELS("Juwelen", "Juwelen"),
    ISLAND_LEVEL("IS-Level", "Level");

    private String displayName;
    private String additive;

}
