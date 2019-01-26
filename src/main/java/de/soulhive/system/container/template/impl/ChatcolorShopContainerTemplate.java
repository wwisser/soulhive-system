package de.soulhive.system.container.template.impl;

import de.soulhive.system.container.Container;
import de.soulhive.system.container.ContainerService;
import de.soulhive.system.container.ContainerStorageLevel;
import de.soulhive.system.container.action.ContainerAction;
import de.soulhive.system.container.action.impl.PurchaseContainerAction;
import de.soulhive.system.container.template.ContainerTemplate;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.PermissionUtils;
import de.soulhive.system.util.item.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ChatcolorShopContainerTemplate extends ContainerTemplate {

    private Map<ChatcolorShopContainerTemplate.ShopChatcolor, ContainerAction> purchaseActions = new HashMap<>();

    private ShopContainerTemplate shopContainerTemplate;

    ChatcolorShopContainerTemplate(final ContainerService containerService, final ShopContainerTemplate shopTemplate) {
        super(containerService);

        this.shopContainerTemplate = shopTemplate;

        for (ChatcolorShopContainerTemplate.ShopChatcolor chatColor : ChatcolorShopContainerTemplate.ShopChatcolor.values()) {
            this.purchaseActions.put(
                chatColor,
                new PurchaseContainerAction(
                    player -> {
                        PermissionUtils.addPermission(player.getName(), chatColor.getPermission());
                        Bukkit.broadcastMessage(
                            Settings.PREFIX
                                + "§f" + player.getName()
                                + " hat sich die Chatfarbe " + chatColor.getChatColor() + chatColor.getName() + " §7gekauft."
                                + " §8=> &f/shop"
                        );
                    },
                    chatColor.getCosts()
                )
            );
        }
    }

    @Override
    protected void openContainer(final Player player) {
        final Container.ContainerBuilder builder = new Container.ContainerBuilder("§0§lShop §0> §0§lChatfarben")
            .addAction(26, ShopContainerTemplate.ITEM_BACK, this.shopContainerTemplate::openContainer)
            .setStorageLevel(ContainerStorageLevel.STORED);

        int count = 0;
        for (ChatcolorShopContainerTemplate.ShopChatcolor chatColor : ChatcolorShopContainerTemplate.ShopChatcolor.values()) {
            final boolean permission = player.hasPermission(chatColor.getPermission());

            ContainerAction action = permission
                ? ContainerAction.NONE
                : this.purchaseActions.get(chatColor);

            ItemStack itemStack = new ItemBuilder(chatColor.getItemStack())
                .name(chatColor.getChatColor() + chatColor.getName())
                .modifyLore()
                .add("")
                .add("§7Verwendung: §r&" + chatColor.getChatColor().getChar() + " <Nachricht>")
                .add("")
                .add(
                    permission
                        ? "§aBereits gekauft"
                        : "§7Preis: §d" + chatColor.getCosts() + " Juwelen"
                )
                .finish()
                .build();

            builder.addAction(count++, itemStack, action);
        }

        final Container builtContainer = builder.build();
        this.containerService.registerContainer(builtContainer);

        player.openInventory(builtContainer.getInventory());
    }

    @AllArgsConstructor
    @Getter
    private enum ShopChatcolor {
        DARK_GREEN("§2Dunkelgrün", ChatColor.DARK_GREEN, new ItemStack(351, 1, (short) 2), 300),
        GREEN("§aGrün", ChatColor.GREEN, new ItemStack(351, 1, (short) 10), 300),
        DARK_AQUA("§3Dunkelaqua", ChatColor.DARK_AQUA, new ItemStack(351, 1, (short) 6), 300),
        AQUA("§bAqua", ChatColor.AQUA, new ItemStack(Material.WATER_BUCKET), 300),
        DARK_RED("§4Dunkelrot", ChatColor.DARK_RED, new ItemStack(Material.REDSTONE), 300),
        RED("§cRot", ChatColor.RED, new ItemStack(351, 1, (short) 1), 300),
        DARK_PURPLE("§5Lila", ChatColor.DARK_PURPLE, new ItemStack(351, 1, (short) 5), 300),
        LIGHT_PURPLE("§dPink", ChatColor.LIGHT_PURPLE, new ItemStack(351, 1, (short) 13), 300),
        GOLD("§6Gold", ChatColor.GOLD, new ItemStack(351, 1, (short) 14), 300),
        YELLOW("§eGelb", ChatColor.YELLOW, new ItemStack(351, 1, (short) 11), 300),
        DARK_GRAY("§8Dunkelgrau", ChatColor.DARK_GRAY, new ItemStack(351, 1, (short) 8), 300),
        GRAY("§7Grau", ChatColor.GRAY, new ItemStack(351, 1, (short) 7), 300),
        DARK_BLUE("§1Dunkelblau", ChatColor.DARK_BLUE, new ItemStack(351, 1, (short) 4), 300),
        BLUE("§9Blau", ChatColor.BLUE, new ItemStack(351, 1, (short) 12), 300);

        private String name;
        private ChatColor chatColor;
        private ItemStack itemStack;
        private int costs;

        public String getPermission() {
            return "soulhive.chatcolor." + this.chatColor.toString().toLowerCase();
        }

    }

}
