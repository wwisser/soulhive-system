package de.soulhive.system.peace;

import de.soulhive.system.peace.command.CommandPeace;
import de.soulhive.system.peace.listener.EntityDamageByEntityListener;
import de.soulhive.system.service.FeatureService;
import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FeatureService
public class PeaceService extends Service {

    private Config database;
    private Map<String, List<String>> invites;

    @Override
    public void initialize() {
        this.database = new Config(Settings.CONFIG_PATH, "peace.yml");
        this.invites = new HashMap<>();

        super.registerCommand("peace", new CommandPeace(this));
        super.registerListener(new EntityDamageByEntityListener(this));
    }

    @Override
    public void disable() {
        this.database.saveFile();
    }

    public void setPeace(final String uuid1, final String uuid2) {
        final List<String> peaceList1 = this.getPeaceList(uuid1);
        final List<String> peaceList2 = this.getPeaceList(uuid2);

        peaceList1.add(uuid2);
        peaceList2.add(uuid1);

        this.database.set(uuid1, peaceList1);
        this.database.set(uuid2, peaceList2);
    }

    public void removePeace(final String uuid1, final String uuid2) {
        final List<String> peaceList1 = this.getPeaceList(uuid1);
        final List<String> peaceList2 = this.getPeaceList(uuid2);

        peaceList1.remove(uuid2);
        peaceList2.remove(uuid1);

        this.database.set(uuid1, peaceList1);
        this.database.set(uuid2, peaceList2);
    }

    public void setInvite(final String sender, String target) {
        final List<String> invites = this.invites.getOrDefault(sender, new ArrayList<>());

        invites.add(target);
        this.invites.put(sender, invites);
    }

    public void removeInvite(final String sender, String target) {
        final List<String> invites = this.invites.getOrDefault(sender, new ArrayList<>());

        invites.remove(target);
        this.invites.put(sender, invites);
    }

    public boolean hasInvite(final String acceptor, String target) {
        return this.invites.getOrDefault(acceptor, new ArrayList<>()).contains(target);
    }

    public boolean hasPeace(final String target, final String toCheck) {
        return this.getPeaceList(target).contains(toCheck);
    }

    public List<String> getPeaceList(final String uuid) {
        List<String> peaceList;

        if (!this.database.contains(uuid)) {
            peaceList = new ArrayList<>();
        } else {
            peaceList = this.database.getStringList(uuid);
        }

        return peaceList;
    }

}
