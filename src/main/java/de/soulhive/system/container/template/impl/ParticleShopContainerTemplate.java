package de.soulhive.system.container.template.impl;

import de.soulhive.system.SoulHive;
import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.container.action.impl.PurchaseContainerAction;
import de.soulhive.system.container.template.ContainerTemplate;
import de.soulhive.system.particle.Particle;
import de.soulhive.system.particle.ParticleService;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.PermissionUtils;
import de.soulhive.system.util.item.ItemBuilder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ParticleShopContainerTemplate extends ContainerTemplate {

    private Map<Particle, ContainerAction> purchaseActions = new HashMap<>();
    private ShopContainerTemplate shopContainerTemplate;

    ParticleShopContainerTemplate(
        final ContainerService containerService,
        final ShopContainerTemplate shopContainerTemplate
    ) {
        super(containerService);
        this.shopContainerTemplate = shopContainerTemplate;
        final ParticleService particleService = SoulHive.getServiceManager().getService(ParticleService.class);

        for (Particle type : Particle.values()) {
            this.purchaseActions.put(
                type,
                new PurchaseContainerAction(player -> {
                    PermissionUtils.setRank(player.getName(), type.getPermission());
                    player.sendMessage(Settings.PREFIX + "Du hast dir den Partikel " + type.getName() + " §7gekauft und ausgewählt.");
                    particleService.setSelectedParticle(player, type);
                }, type.getCosts())
            );
        }
    }

    @Override
    protected void openContainer(final Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lPartikel");

        builder.addAction(26, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer)
            .setStorageLevel(ContainerStorageLevel.STORED);

        int count = 9;
        for (Particle type : Particle.values()) {
            final boolean hasPermission = player.hasPermission(type.getPermission());

            final ItemStack item = new ItemBuilder(type.getMaterial()).name(type.getName()).modifyLore()
                .add("§r")
                .add(hasPermission ? "§aBereits gekauft" : "§7Preis: §d" + type.getCosts() + " Juwelen")
                .finish()
                .build();

            builder.addAction(
                count++,
                item,
                hasPermission
                    ? clicker -> clicker.playSound(clicker.getLocation(), Sound.CREEPER_HISS, 1, 1)
                    : this.purchaseActions.get(type)
            );
        }

        final Container container = builder.build();

        super.containerService.registerContainer(container);
        player.openInventory(container.getInventory());
    }

}
