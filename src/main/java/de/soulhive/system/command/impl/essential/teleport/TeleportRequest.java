package de.soulhive.system.command.impl.essential.teleport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
class TeleportRequest {

    @Getter private Player sender;
    @Getter private Player target;
    @Getter private RequestType requestType;

    public enum RequestType {
        TPA,
        TPAHERE
    }

}
