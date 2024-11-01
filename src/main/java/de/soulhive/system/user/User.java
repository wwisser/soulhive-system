package de.soulhive.system.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
public class User {

    private String name;
    private String uuid;
    private long firstSeen;
    private long lastSeen;
    private int kills;
    private int deaths;
    private int votes;
    /**
     * Holds playtime in minutes.
     */
    private int playtime;
    private int jewels;

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }

    public void addVote() {
        this.votes++;
    }

    public void addPlaytime() {
        this.playtime++;
    }

    public void addJewels(int amount) {
        this.jewels += amount;
    }

    public void removeJewels(int amount) {
        this.jewels -= amount;
    }

    public double getKdr() {
        if (this.kills != 0 && this.deaths != 0) {
            return Math.round(((double) this.kills / (double) this.deaths) * 100.0) / 100.0;
        }
        if (this.kills != 0) {
            return this.kills;
        }

        return Double.NaN;
    }

}
