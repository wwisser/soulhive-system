package de.soulhive.system.vote;

import de.soulhive.system.SoulHive;
import de.soulhive.system.service.Service;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.item.ItemUtils;
import de.soulhive.system.util.item.YoloBootsItemFactory;
import de.soulhive.system.vote.commands.CommandFakevote;
import de.soulhive.system.vote.commands.CommandVote;
import de.soulhive.system.vote.listener.VotifierListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VoteService extends Service {

    private static final int VOTE_MAX = 3;

    private UserService userService;
    private boolean event = false;
    private int votedCount = 0;
    private Map<UUID, Long> voteTimeStamps = new HashMap<>();

    @Override
    public void initialize() {
        this.userService = SoulHive.getServiceManager().getService(UserService.class);

        super.registerCommand("fakevote", new CommandFakevote());
        super.registerCommand("vote", new CommandVote());
        super.registerListener(new VotifierListener(this));

        new BukkitRunnable() {
            @Override
            public void run() {
                VoteService.this.enableVoteEvent();
            }
        }.runTaskLater(SoulHive.getPlugin(), 20L * 60 * 5);
    }

    public void handleVote(final Player player) {
        final User user = this.userService.getUser(player);

        if (!this.event) {
            Bukkit.broadcastMessage("§8§m---------------------------------------------");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8➥ §7Der Spieler §9" + player.getName() + " §7hat gevotet!");
            Bukkit.broadcastMessage("§8➥ §7Seine Belohnung:");
            Bukkit.broadcastMessage("    §8- §330 Level");
            Bukkit.broadcastMessage("    §8- §33 Stunden Auto Equip Lvl. II");
            Bukkit.broadcastMessage("    §8- §d25 Juwelen");
            Bukkit.broadcastMessage("§8➥ §7Du willst auch eine Belohnung? §8§l=> §f/vote");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8§m---------------------------------------------");

            this.voteTimeStamps.put(player.getUniqueId(), System.currentTimeMillis() + (1000L * 60 * 60 * 3));
            player.setLevel(player.getLevel() + 30);
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
            user.addJewels(25);
        } else {
            this.votedCount++;
            Bukkit.broadcastMessage("§8§m---------------------------------------------");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8➥ §7Der Spieler §9" + player.getName() + " §7hat gevotet!");
            Bukkit.broadcastMessage("§8➥ §7Seine Belohnung (§6§lEVENT §6" + this.votedCount + "§8/§6" + VOTE_MAX + "§7):");
            Bukkit.broadcastMessage("    §8- §360 Level §7+ §d50 Juwelen");
            Bukkit.broadcastMessage("    §8- §36 Stunden Auto Equip Lvl. II");
            Bukkit.broadcastMessage("    §8- §61x OP-Goldapfel §7+ §bYOLO-Boots");
            Bukkit.broadcastMessage("§8➥ §7Du willst auch eine Belohnung? §8§l=> §f/vote");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8§m---------------------------------------------");

            this.voteTimeStamps.put(player.getUniqueId(), System.currentTimeMillis() + (1000L * 60 * 60 * 6));
            player.setLevel(player.getLevel() + 60);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, Float.MAX_VALUE, Float.MAX_VALUE);
            ItemUtils.addAndDropRest(player, new ItemStack(322, 1, (short) 1));
            ItemUtils.addAndDropRest(player, YoloBootsItemFactory.createYoloBootsItem());
            user.addJewels(50);

            if (this.votedCount >= VOTE_MAX) {
                this.disableVoteEvent();
            }
        }
    }

    public boolean hasEventKit(final Player player) {
        final long time = this.voteTimeStamps.getOrDefault(player.getUniqueId(), System.currentTimeMillis());

        return !(System.currentTimeMillis() >= time);
    }

    private void enableVoteEvent() {
        this.event = true;

        Bukkit.broadcastMessage("§8§m---------------------------------------------");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8➥ §9§lVote-Event §7gestartet!");
        Bukkit.broadcastMessage("§8➥ §7Die nächsten §f" + VOTE_MAX + " §7Spieler");
        Bukkit.broadcastMessage("§8➥ §7erhalten einen §fVote-Zusatz§7!");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8§m---------------------------------------------");
    }

    private void disableVoteEvent() {
        this.event = false;
        this.votedCount = 0;

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8➥ §c§lVote-Event beendet!");
        Bukkit.broadcastMessage("§8➥ §7Nächstes §fVote-Event §7in:");
        Bukkit.broadcastMessage("§8➥ §f30 Minuten");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8§m---------------------------------------------");

        new BukkitRunnable() {
            @Override
            public void run() {
                VoteService.this.enableVoteEvent();
            }
        }.runTaskLater(SoulHive.getPlugin(), 20L * 60 * 30);
    }

}
