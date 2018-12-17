package de.soulhive.system.supply;

import de.soulhive.system.service.Service;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.supply.command.CommandSupply;
import de.soulhive.system.util.Config;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SupplyService extends Service {

    @Getter private List<ItemStack> itemStacks = new ArrayList<>();
    @Getter private List<Location> signLocations = new ArrayList<>();
    private Config fileStorage = new Config(Settings.CONFIG_PATH, "supply_items.yml");

    @Override
    public void initialize() {
        this.loadItems();
        this.loadSigns();
        super.registerCommand("supply", new CommandSupply());
    }

    @Override
    public void disable() {
        this.saveItems();
        this.saveSigns();
    }

    public void setItems(final List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    private void loadItems() {
        final List<?> items = this.fileStorage.getList("items");

        for (Object item : items) {
            this.itemStacks.add((ItemStack) item);
        }
    }

    private void loadSigns() {
        final List<?> locations = this.fileStorage.getList("locations");

        for (Object item : locations) {
            this.signLocations.add((Location) item);
        }
    }

    private void saveItems() {
        this.fileStorage.set("items", this.itemStacks);
        this.fileStorage.saveFile();
    }

    private void saveSigns() {
        this.fileStorage.set("locations", this.signLocations);
        this.fileStorage.saveFile();
    }

}
