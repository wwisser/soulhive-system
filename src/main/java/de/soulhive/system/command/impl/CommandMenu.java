package de.soulhive.system.command.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.scheduled.AlreadyInTeleportException;
import de.soulhive.system.scheduled.ScheduledTeleport;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMenu extends CommandExecutorWrapper {

    private Container container;

    @Override
    public void initialize() {
        final ContainerService service = SoulHive.getServiceManager().getService(ContainerService.class);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lMenü §0| /menu")
            .setStorageLevel(ContainerStorageLevel.STORED)
            .setDestroyOnClose(false)
            .setSize(9 * 4);

        builder.addAction(
            12,
            new ItemBuilder(Material.IRON_SWORD).glow().name("§b§lItemshop").build(),
            player -> {
                if (player.hasPermission(Settings.PERMISSION_ADMIN)) {
                    player.teleport(Settings.LOCATION_ITEMSHOP);
                } else {
                    try {
                        new ScheduledTeleport(
                            Settings.LOCATION_ITEMSHOP,
                            3,
                            ScheduledTeleport.RESULT
                        ).process(player);
                        player.sendMessage(
                            Settings.PREFIX + "Du wirst in §f3 §7Sekunden teleportiert. Bewege dich nicht."
                        );
                    } catch (AlreadyInTeleportException e) {
                        player.sendMessage(Settings.PREFIX + "§cDu wirst bereits teleportiert.");
                    }
                }
            }
        );


        builder.addAction(
            10,
            new ItemBuilder(Material.BLAZE_POWDER).glow().name("§5§lPartikel").build(),
            player -> player.performCommand("scoreboards")
        );
        builder.addAction(
            14,
            new ItemBuilder(Material.GRASS).glow().name("§e§lDeine Insel").build(),
            player -> player.performCommand("is go")
        );

        builder.addAction(
            16,
            new ItemBuilder(Material.BOOK).glow().name("§a§lShop").build(),
            player -> player.performCommand("shop")
        );

        builder.addAction(
            19,
            new ItemBuilder(Material.PRISMARINE_SHARD).glow().name("§6§lAuktionshaus").build(),
            player -> player.performCommand("ah")
        );

        builder.addAction(
            21,
            new ItemBuilder(Material.EMERALD).glow().name("§d§lJuwelen").build(),
            player -> player.performCommand("juwelen")
        );

        builder.addAction(
            23,
            new ItemBuilder(Material.CHEST).glow().name("§3§lKits").build(),
            player -> player.performCommand("kit")
        );

        builder.addAction(
            25,
            new ItemBuilder(Material.SIGN).glow().name("§2§lScoreboards").build(),
            player -> player.performCommand("scoreboards")
        );


        final Container builtContainer = builder.build();

        service.registerContainer(builtContainer);
        this.container = builtContainer;
    }

    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.onlyPlayer(sender);

        player.openInventory(this.container.getInventory());
    }

}
