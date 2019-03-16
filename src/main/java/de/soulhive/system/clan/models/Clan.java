package de.soulhive.system.clan.models;


import java.util.List;

public class Clan {

    private String name;
    private String tag;
    private String owner;
    private List<String> members;
    private int kills;
    private int deaths;
    private int bankJewels;
    private long created;

    public Clan(
        String name,
        String tag,
        String owner,
        List<String> members,
        int kills,
        int deaths,
        int bankJewels,
        long created
    ) {
        this.name = name;
        this.tag = tag;
        this.owner = owner;
        this.members = members;
        this.kills = kills;
        this.deaths = deaths;
        this.bankJewels = bankJewels;
        this.created = created;
    }

    public String getName() {
        return this.name;
    }

    public String getTag() {
        return this.tag;
    }

    public String getOwner() {
        return this.owner;
    }

    public List<String> getMembers() {
        return this.members;
    }

    public int getKills() {
        return this.kills;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getBankJewels() {
        return this.bankJewels;
    }

    public long getCreated() {
        return this.created;
    }

}
