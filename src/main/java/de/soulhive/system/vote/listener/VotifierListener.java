package de.soulhive.system.vote.listener;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import de.soulhive.system.vote.VoteService;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class VotifierListener implements Listener {

    private VoteService voteService;

    @EventHandler
    public void onVote(VotifierEvent event) {
        final Vote vote = event.getVote();
        final Player player = Bukkit.getPlayer(vote.getUsername());

        if (player != null && player.isOnline()) {
            this.voteService.handleVote(player);
        }
    }

}
