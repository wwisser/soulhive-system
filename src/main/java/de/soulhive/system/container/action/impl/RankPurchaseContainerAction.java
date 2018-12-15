package de.soulhive.system.container.action.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class RankPurchaseContainerAction implements ContainerAction {

    @NonNull private String rank;
    @NonNull private int costs;

    private UserService userService;
    private Chat vaultChat = Bukkit.getServer().getServicesManager().getRegistration(Chat.class).getProvider();

    @Override
    public void process(Player player) {
        if (this.userService == null) {
            this.userService = SoulHive.getServiceManager().getService(UserService.class);
        }

        final User user = this.userService.getUser(player);

        if (user.getJewels() >= this.costs) {
            user.removeJewels(this.costs);
            Bukkit.dispatchCommand(
                Bukkit.getConsoleSender(),
                "pex user " + player.getName() + " group set " + this.rank
            );

            player.sendMessage(
                Settings.PREFIX
                    + "Du hast dir den Rang "
                    + this.vaultChat.getGroupPrefix(player.getWorld(), this.rank)
                    + " §7für §d"
                    + this.costs
                    + " Juwelen §7gekauft!"
            );
        } else {
            player.sendMessage(
                Settings.PREFIX
                    + "§cDir fehlen noch "
                    + (this.costs - user.getJewels())
                    + " Juwelen, um diesen Rang kaufen zu können."
            );
        }
    }

}
