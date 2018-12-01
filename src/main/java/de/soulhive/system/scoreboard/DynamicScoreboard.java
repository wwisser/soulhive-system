package de.soulhive.system.scoreboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Getter
public abstract class DynamicScoreboard {

    private static final String[] PLACEHOLDERS = {
        "§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9",
        "§a", "§b", "§c", "§d", "§e", "§f", "§l", "§m", "§n", "§o"
    };

    private Scoreboard scoreboard;
    private Objective objective;
    private int teams;

    public DynamicScoreboard(String title) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("dyn_scoreboard", "dummy");
        this.teams = 0;

        this.objective.setDisplayName(title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    protected void addBlankLine(int height) {
        this.objective.getScore(PLACEHOLDERS[this.teams++]).setScore(height);
    }

    protected void addStaticLine(String value, int height) {
        this.objective.getScore(value).setScore(height);
    }

    protected void addDynamicLine(String name, String prefix, String value, int height) {
        Team team = this.scoreboard.registerNewTeam(name);

        team.setPrefix(prefix);
        team.addEntry(PLACEHOLDERS[this.teams++]);
        team.setSuffix(prefix + value);
        this.objective.getScore(PLACEHOLDERS[this.teams - 1]).setScore(height);
    }

    public void updateLine(String name, String value) {
        this.scoreboard.getTeam(name).setSuffix(value);
    }

    void show(Player player) {
        player.setScoreboard(this.scoreboard);
    }

}
