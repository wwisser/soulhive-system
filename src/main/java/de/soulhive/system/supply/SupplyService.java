package de.soulhive.system.supply;

import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.supply.command.CommandSupply;
import de.soulhive.system.util.Config;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SupplyService extends Service {

    @Getter private List<ItemStack> itemStacks = new ArrayList<>();
    private Config itemFile = new Config(Settings.CONFIG_PATH, "supply_items.yml");

    @Override
    public void initialize() {
        this.loadItems();
        super.registerCommand("supply", new CommandSupply());
    }

    @Override
    public void disable() {
        this.saveItems();
    }

    public void setItems(final List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    private void loadItems() {
        final List<?> items = this.itemFile.getList("items");

        for (Object item : items) {
            this.itemStacks.add((ItemStack) item);
        }
    }

    private void saveItems() {
        this.itemFile.set("items", this.itemStacks);
        this.itemFile.saveFile();
    }

}
