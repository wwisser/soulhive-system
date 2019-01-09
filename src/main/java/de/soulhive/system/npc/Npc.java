package de.soulhive.system.npc;

import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public interface Npc {

    Consumer<Player> getClickAction();

    int getEntityTypeId();

    Location getLocation();

    Entity getEntity();

    String getEntityName();

}
