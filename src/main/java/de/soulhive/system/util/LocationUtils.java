package de.soulhive.system.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

@UtilityClass
public class LocationUtils {

    public Block getTargetBlock(final Player player, final int range) {
        BlockIterator blockIterator = new BlockIterator(player, range);
        Block lastBlock = blockIterator.next();

        while (blockIterator.hasNext()) {
            lastBlock = blockIterator.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }

        return lastBlock;
    }

}
