package de.soulhive.system.util.item;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class YoloBootsItemFactory {

    public ItemStack createYoloBootsItem() {
        return new ItemBuilder(Material.LEATHER_BOOTS)
            .name(ChatColor.AQUA + "YOLO-Boots")
            .modifyLore()
            .add("§7Anziehen, um die Schuhe zu aktivieren.")
            .finish()
            .build();
    }

}
