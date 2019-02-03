package de.soulhive.system.kit.command;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.kit.KitService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.item.ItemBuilder;
import de.soulhive.system.util.item.ItemUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@RequiredArgsConstructor
public class CommandKit extends CommandExecutorWrapper {

    @NonNull private KitService kitService;
    private ContainerService containerService;

    @Override
    public void initialize() {
        this.containerService = SoulHive.getServiceManager().getService(ContainerService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.onlyPlayer(sender);
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lKits §0| §0/kit")
            .setSize(3 * 9)
            .setDestroyOnClose(true)
            .setStorageLevel(ContainerStorageLevel.NEW);

        ItemBuilder itemBuilder;
        List<ItemStack> kitList;

        if (player.hasPermission("soulhive.obsidian")) {
            kitList = Settings.KIT_OBSIDIAN;
            itemBuilder = new ItemBuilder(Material.OBSIDIAN).name("§7Kit §5§lObsidian");
        } else if (player.hasPermission("soulhive.emerald")) {
            kitList = Settings.KIT_EMERALD;
            itemBuilder = new ItemBuilder(Material.EMERALD).name("§7Kit §a§lEmerald");
        } else if (player.hasPermission("soulhive.diamond")) {
            kitList = Settings.KIT_DIAMOND;
            itemBuilder = new ItemBuilder(Material.DIAMOND).name("§7Kit §b§lDiamond");
        } else if (player.hasPermission("soulhive.gold")) {
            kitList = Settings.KIT_GOLD;
            itemBuilder = new ItemBuilder(Material.GOLD_INGOT).name("§7Kit §6§lGold");
        } else {
            kitList = Settings.KIT_PLAYER;
            itemBuilder = new ItemBuilder(Material.GRASS).name("§7Kit §9§lSpieler");
        }

        if (this.kitService.hasRecieved(player)) {
            itemBuilder = itemBuilder
                .modifyLore()
                .add("")
                .add("§7Zeit bis zur nächsten Abholung:")
                .add("§c" + this.kitService.getTimeRemaining(player))
                .finish();
        } else {
            itemBuilder = itemBuilder
                .modifyLore()
                .add("")
                .add("§7Klicke, um dein Kit abzuholen")
                .finish()
                .glow();
        }

        builder.addAction(13, itemBuilder.build(), clicker -> {
            if (!this.kitService.hasRecieved(player)) {
                player.closeInventory();
                kitList.forEach(itemStack -> ItemUtils.addAndDropRest(player, itemStack));
                player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
                this.kitService.setRecieved(player);
            } else {
               player.playSound(player.getLocation(), Sound.CREEPER_HISS, 1, 1);
            }
        });

        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);
        player.openInventory(builtContainer.getInventory());
    }

}
