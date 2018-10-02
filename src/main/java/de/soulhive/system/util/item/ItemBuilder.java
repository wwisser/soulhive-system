package de.soulhive.system.util.item;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemBuilder {

    private Material material;
    private byte damage = 0;
    private int amount = 1;
    private String name;
    private String owner;
    private ItemLore itemLore;
    private Map<Enchantment, Integer> enchantments;
    private Map<Enchantment, Integer> enchantmentStorage;
    private boolean glow = false;
    private Color color;

    public ItemBuilder(ItemStack item) {
        this.material = item.getType();
        this.damage = item.getData().getData();
        this.amount = item.getAmount();

        if (item.getItemMeta().hasDisplayName()) {
            this.name = item.getItemMeta().getDisplayName();
        }
        if (item.getItemMeta().hasLore()) {
            this.itemLore = new ItemLore(this, item.getItemMeta().getLore());
        }
        if (item.getItemMeta().hasEnchants()) {
            this.enchantments = item.getItemMeta().getEnchants();
        }
    }

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder(int id) {
        this.material = Material.getMaterial(id);
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder id(int id) {
        this.material = Material.getMaterial(id);
        return this;
    }

    public ItemBuilder data(byte data) {
        this.damage = data;
        return this;
    }

    public ItemBuilder amount(int amount) {
        if (amount > 64) {
            amount = 64;
        }

        this.amount = amount;
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemLore modifyLore() {
        return new ItemLore(this);
    }

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        if (this.enchantments == null) {
            this.enchantments = new HashMap<>();
        }

        this.enchantments.put(enchantment, level);
        return this;
    }

    public ItemBuilder enchantStorage(Enchantment enchantment, int level) {
        if (this.enchantmentStorage == null) {
            this.enchantmentStorage = new HashMap<>();
        }

        this.enchantmentStorage.put(enchantment, level);
        return this;
    }

    public ItemBuilder glow() {
        this.glow = true;
        return this;
    }

    public ItemBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public ItemBuilder owner(String owner) {
        this.owner = owner;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.amount, this.damage);

        if (this.name != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(this.name);
            item.setItemMeta(meta);
        }
        if (this.itemLore != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setLore(this.itemLore.toArray());
            item.setItemMeta(meta);
        }
        if (this.enchantments != null) {
            this.enchantments.forEach(item::addUnsafeEnchantment);
        } else {
            if (this.glow) {
                net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
                NBTTagCompound tag = null;

                if (!nmsStack.hasTag()) {
                    tag = new NBTTagCompound();

                    nmsStack.setTag(tag);
                }
                if (tag == null) {
                    tag = nmsStack.getTag();
                }

                NBTTagList ench = new NBTTagList();

                tag.set("ench", ench);
                nmsStack.setTag(tag);

                item = CraftItemStack.asCraftMirror(nmsStack);
            }
        }
        if (this.enchantmentStorage != null) {
            if (item.getType().equals(Material.ENCHANTED_BOOK)) {
                EnchantmentStorageMeta storageMeta = (EnchantmentStorageMeta) item.getItemMeta();
                this.enchantmentStorage.forEach((enchantment, level)
                        -> storageMeta.addStoredEnchant(enchantment, level, true));
                item.setItemMeta(storageMeta);
            }
        }
        if (this.color != null) {
            if (this.material.toString().startsWith("LEATHER_")) {
                LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

                meta.setColor(this.color);
                item.setItemMeta(meta);
            }
        }
        if (this.owner != null) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();

            meta.setOwner(this.owner);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void setItemLore(ItemLore itemLore) {
        this.itemLore = itemLore;
    }

    public class ItemLore {

        private ItemBuilder itemBuilder;
        private List<String> lore;

        public ItemLore(ItemBuilder itemBuilder) {
            this.itemBuilder = itemBuilder;
            this.lore = new ArrayList<>();
        }

        public ItemLore(ItemBuilder itemBuilder, List<String> lore) {
            this.itemBuilder = itemBuilder;
            this.lore = lore;
        }

        public ItemLore clear() {
            this.lore.clear();
            return this;
        }

        public ItemLore add(String line) {
            this.lore.add("§r" + line);
            return this;
        }

        public ItemLore set(String[] lines) {
            this.clear();

            for (String line : lines) {
                add(line);
            }

            return this;
        }

        public List<String> toArray() {
            return this.lore;
        }

        public ItemBuilder finish() {
            this.itemBuilder.setItemLore(this);
            return this.itemBuilder;
        }

    }

}
