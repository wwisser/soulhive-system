package de.soulhive.system.scoreboard.command;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.scoreboard.ScoreboardService;
import de.soulhive.system.scoreboard.ScoreboardType;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.item.ItemBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class CommandScoreboard extends CommandExecutorWrapper {

    @NonNull private ScoreboardService scoreboardService;
    private ContainerService containerService;

    @Override
    public void initialize() {
        this.containerService = SoulHive.getServiceManager().getService(ContainerService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.onlyPlayer(sender);

        final ScoreboardType[] values = ScoreboardType.values();
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lWähle dein Scoreboard")
            .setSize(9)
            .setStorageLevel(ContainerStorageLevel.NEW)
            .setDestroyOnClose(true);

        int count = 3;
        for (ScoreboardType type : values) {
            if (this.scoreboardService.hasSelected(player, type)) {
                ItemStack itemStack = new ItemBuilder(type.getMaterial())
                    .name(type.getName())
                    .modifyLore()
                    .add("§r")
                    .add("§7Eigenschaften:")
                    .add(type.getProperties())
                    .add("§r")
                    .add("§cBereits ausgewählt.")
                    .finish()
                    .build();

                builder.addAction(
                    count,
                    itemStack,
                    clicker -> clicker.playSound(clicker.getLocation(), Sound.NOTE_STICKS, 1, 1)
                );
            } else if (player.hasPermission(type.getPermission())) {
                ItemStack itemStack = new ItemBuilder(type.getMaterial())
                    .name(type.getName())
                    .modifyLore()
                    .add("§r")
                    .add("§7Eigenschaften:")
                    .add(type.getProperties())
                    .add("§r")
                    .add("§7Klicke, um dieses Scoreboard auszuwählen.")
                    .finish()
                    .build();

                builder.addAction(count, itemStack, clicker -> {
                    this.scoreboardService.updateSelectedType(clicker, type);
                    clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
                    clicker.closeInventory();
                    clicker.sendMessage(
                        Settings.PREFIX + "Du hast dein Scoreboard zu '" + type.getName() + "§7' gewechselt."
                    );
                });
            } else {
                ItemStack itemStack = new ItemBuilder(Material.BARRIER)
                    .name(type.getName())
                    .modifyLore()
                    .add("§c")
                    .add("§cScoreboard im Shop kaufen: /shop")
                    .finish()
                    .build();

                builder.addAction(
                    count,
                    itemStack,
                    clicker -> clicker.playSound(clicker.getLocation(), Sound.CREEPER_HISS, 1, 1)
                );
            }
            count++;
        }

        final Container builtContainer = builder.build();

        this.containerService.registerContainer(builtContainer);
        player.openInventory(builtContainer.getInventory());
    }

}
