package de.soulhive.system.command.impl;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.scheduled.AlreadyInTeleportException;
import de.soulhive.system.scheduled.ScheduledTeleport;
import de.soulhive.system.service.ServiceManager;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMenu extends CommandExecutorWrapper {

    private ContainerService containerService;
    private UserService userService;
    private ASkyBlockAPI aSkyBlockAPI;

    @Override
    public void initialize() {
        final ServiceManager serviceManager = SoulHive.getServiceManager();

        this.containerService = serviceManager.getService(ContainerService.class);
        this.userService = serviceManager.getService(UserService.class);
        this.aSkyBlockAPI = ASkyBlockAPI.getInstance();
    }

    public void process(CommandSender commandSender, String label, String[] args) throws CommandException {
        final Player sender = ValidateCommand.onlyPlayer(commandSender);

        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lMenü §0| /menu")
            .setStorageLevel(ContainerStorageLevel.STORED)
            .setDestroyOnClose(false)
            .setSize(9 * 4);

        builder.addAction(
            12,
            new ItemBuilder(Material.IRON_SWORD)
                .glow()
                .name("§b§lItemshop")
                .modifyLore()
                .add("")
                .add("§7Kaufe oder verkaufe Items.")
                .finish()
                .build(),
            clicker -> {
                if (clicker.hasPermission(Settings.PERMISSION_ADMIN)) {
                    clicker.teleport(Settings.LOCATION_ITEMSHOP);
                } else {
                    try {
                        new ScheduledTeleport(
                            Settings.LOCATION_ITEMSHOP,
                            3,
                            ScheduledTeleport.RESULT
                        ).process(clicker);
                        clicker.sendMessage(
                            Settings.PREFIX + "Du wirst in §f3 §7Sekunden teleportiert. Bewege dich nicht."
                        );
                    } catch (AlreadyInTeleportException e) {
                        clicker.sendMessage(Settings.PREFIX + "§cDu wirst bereits teleportiert.");
                    }
                }
            }
        );


        builder.addAction(
            10,
            new ItemBuilder(Material.BLAZE_POWDER)
                .glow()
                .name("§5§lPartikel §7(§5/partikel§7)")
                .modifyLore()
                .add("")
                .add("§7Wechsle deine Partikel.")
                .finish()
                .build(),
            player -> player.performCommand("partikel")
        );
        builder.addAction(
            14,
            new ItemBuilder(this.aSkyBlockAPI.hasIsland(sender.getUniqueId()) ? Material.GRASS : Material.MYCEL)
                .glow()
                .name("§e§lDeine Insel §7(§e/is§7)")
                .modifyLore()
                .add("")
                .add("§7Lagere deine Items und baue mit Freunden.")
                .add("§7Inselname: §f" + this.aSkyBlockAPI.getIslandName(sender.getUniqueId()))
                .add("§7Level: §f" + this.aSkyBlockAPI.getLongIslandLevel(sender.getUniqueId()))
                .finish()
                .build(),
            player -> player.performCommand("is")
        );

        builder.addAction(
            16,
            new ItemBuilder(Material.BOOK)
                .glow()
                .name("§a§lShop §7(§a/shop§7)")
                .modifyLore()
                .add("")
                .add("§7Kaufe dir Ränge, Rechte und vieles mehr.")
                .finish()
                .build(),
            player -> player.performCommand("shop")
        );

        builder.addAction(
            19,
            new ItemBuilder(Material.PRISMARINE_SHARD)
                .glow()
                .name("§6§lAuktionshaus §7(§6/ah§7)")
                .modifyLore()
                .add("")
                .add("§7Kaufe und verkaufe Items unter Spielern.")
                .finish()
                .build(),
            player -> player.performCommand("ah")
        );

        final User user = this.userService.getUser(sender);

        builder.addAction(
            21,
            new ItemBuilder(Material.EMERALD)
                .glow()
                .name("§d§lJuwelen §7(§d/juwelen§7)")
                .modifyLore()
                .add("")
                .add("§7Du besitzt aktuell §d" + user.getJewels() + " §7Juwelen.")
                .finish()
                .build(),
            player -> {
                player.performCommand("juwelen");
                player.closeInventory();
            }
        );

        builder.addAction(
            23,
            new ItemBuilder(Material.CHEST)
                .name("§3§lKits §7(§3/kit§7)")
                .modifyLore()
                .add("")
                .add("§7Hole dir dein tägliches Equip ab.")
                .finish()
                .build(),
            player -> player.performCommand("kit")
        );

        builder.addAction(
            25,
            new ItemBuilder(Material.SIGN)
                .glow()
                .name("§2§lScoreboards §7(§2/scoreboard§7)")
                .modifyLore()
                .add("")
                .add("§7Wechsle deine Scoreboardwerte.")
                .finish()
                .build(),
            player -> player.performCommand("scoreboard")
        );


        final Container builtContainer = builder.build();

        this.containerService.registerContainer(builtContainer);
        sender.openInventory(builtContainer.getInventory());
    }

}
