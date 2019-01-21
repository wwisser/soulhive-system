package de.soulhive.system.particle.command;

import de.soulhive.system.SoulHive;
import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.particle.Particle;
import de.soulhive.system.particle.ParticleService;
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
public class CommandParticle extends CommandExecutorWrapper {

    @NonNull private ParticleService particleService;
    private ContainerService containerService;

    @Override
    public void initialize() {
        this.containerService = SoulHive.getServiceManager().getService(ContainerService.class);
    }

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.onlyPlayer(sender);

        final Particle[] values = Particle.values();
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lWähle deinen Partikel")
            .setSize(9)
            .setStorageLevel(ContainerStorageLevel.NEW)
            .setDestroyOnClose(true);

        int count = 0;
        for (Particle type : values) {
            if (this.particleService.hasParticle(player, type)) {
                ItemStack itemStack = new ItemBuilder(type.getMaterial())
                    .name(type.getName())
                    .modifyLore()
                    .add("§c")
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
                    .add("§c")
                    .add("§7Klicke, um diesen Partikel auszuwählen.")
                    .finish()
                    .build();

                builder.addAction(count, itemStack, clicker -> {
                    this.particleService.setSelectedParticle(clicker, type);
                    clicker.playSound(clicker.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
                    clicker.closeInventory();
                    clicker.sendMessage(Settings.PREFIX + "Dein neuer Partikel: " + type.getName());
                });
            } else {
                ItemStack itemStack = new ItemBuilder(Material.BARRIER)
                    .name(type.getName())
                    .modifyLore()
                    .add("§c")
                    .add("§cPartikel im Shop kaufen: /shop")
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
