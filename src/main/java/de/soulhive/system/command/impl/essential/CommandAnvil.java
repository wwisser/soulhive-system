package de.soulhive.system.command.impl.essential;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CommandAnvil extends CommandExecutorWrapper {

    @Override
    public void process(CommandSender sender, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.anvil");
        Player player = ValidateCommand.onlyPlayer(sender);

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        MockAnvilContainer container = new MockAnvilContainer(entityPlayer);

        int c = entityPlayer.nextContainerCounter();
        entityPlayer.playerConnection.sendPacket(
            new PacketPlayOutOpenWindow(
                c,
                "minecraft:anvil",
                new ChatMessage("Repairing"),
                0
            )
        );

        entityPlayer.activeContainer = container;
        entityPlayer.activeContainer.windowId = c;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
        entityPlayer.activeContainer = container;
    }

    private class MockAnvilContainer extends ContainerAnvil {

        MockAnvilContainer(EntityHuman entity) {
            super(
                entity.inventory,
                entity.world,
                new BlockPosition(entity.locX, entity.locY, entity.locZ),
                entity
            );
        }

        @Override
        public boolean a(EntityHuman entityhuman) {
            return true;
        }

    }

}
