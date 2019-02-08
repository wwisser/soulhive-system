package de.soulhive.system.vote.listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import de.soulhive.system.SoulHive;
import de.soulhive.system.user.UserService;
import de.soulhive.system.vote.VoteService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VotifierListener implements Listener {

    private VoteService voteService;
    private UserService userService;

    public VotifierListener(final VoteService voteService) {
        this.voteService = voteService;
        this.userService = SoulHive.getServiceManager().getService(UserService.class);
    }

    @EventHandler
    public void onVote(VotifierEvent event) {
        final Vote vote = event.getVote();
        final Player player = Bukkit.getPlayer(vote.getUsername());

        if (player != null && player.isOnline()) {
            this.voteService.handleVote(player);
            this.userService.getUser(player).addVote();
        }
    }

}
