package de.soulhive.system.util.nms;

import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

@UtilityClass
public class ActionBar {

    public void send(String message, CommandSender sender) {
        if (sender instanceof Player) {
            PlayerConnection playerConnection = ((CraftPlayer) sender).getHandle().playerConnection;
            IChatBaseComponent chatComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");

            playerConnection.sendPacket(new PacketPlayOutChat(chatComponent, (byte) 2));
        } else {
            sender.sendMessage(message);
        }
    }

}