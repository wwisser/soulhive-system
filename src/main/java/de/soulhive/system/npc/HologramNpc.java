package de.soulhive.system.npc;

public interface HologramNpc extends Npc {

    String getHologramName();

    /**
     * @return the Y distance between the mob and the Hologram
     */
    double getHeight();

}
