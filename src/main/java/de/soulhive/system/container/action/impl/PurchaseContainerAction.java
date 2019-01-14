package de.soulhive.system.container.action.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class PurchaseContainerAction implements ContainerAction {

    @NonNull private Consumer<Player> buyAction;
    @NonNull private int costs;

    private UserService userService;

    @Override
    public void process(Player player) {
        if (this.userService == null) {
            this.userService = SoulHive.getServiceManager().getService(UserService.class);
        }

        final User user = this.userService.getUser(player);

        if (user.getJewels() >= this.costs) {
            user.removeJewels(this.costs);
            this.buyAction.accept(player);
        } else {
            player.sendMessage(
                Settings.PREFIX
                    + "§cDir fehlen noch "
                    + (this.costs - user.getJewels())
                    + " Juwelen."
            );
            player.sendMessage(Settings.PREFIX + "Jetzt §d§lJuwelen §7kaufen §8> §f/buy");
        }

        player.closeInventory();
    }

}
