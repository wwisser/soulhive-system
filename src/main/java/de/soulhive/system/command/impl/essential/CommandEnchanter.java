package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CommandEnchanter extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.enchanter");
        Player player = ValidateCommand.onlyPlayer(sender);

        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final ContainerEnchantTable container = new ContainerEnchantTable(
            entityPlayer.inventory,
            ((CraftWorld) Settings.WORLD_MAIN).getHandle(),
            new BlockPosition(-65, 170, -321)
        );

        container.checkReachable = false;
        container.enchantSlots.setItem(1, new ItemStack(Item.getById(351), 64, (byte) 4));
        final int containerCounter = entityPlayer.nextContainerCounter();
        entityPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(
            containerCounter,
            "minecraft:enchanting_table",
            new ChatMessage("Enchant"), 0
        ));
        entityPlayer.activeContainer = container;
        entityPlayer.activeContainer.windowId = containerCounter;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
    }

}
