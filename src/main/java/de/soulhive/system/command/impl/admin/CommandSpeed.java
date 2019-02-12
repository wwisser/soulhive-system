package de.soulhive.system.command.impl.admin;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpeed extends CommandExecutorWrapper {

    private static final String USAGE = "/speed <value>";

    private static final float SPEED_BASE_FLYING = 0.1F;
    private static final float SPEED_BASE_WALKING = 0.2F;

    private static final float SPEED_MAX = 10.0F;
    private static final float SPEED_MIN = 1.0E-004F;

    private static final float SPEED_RATIO_MAX = 1.0F;
    private static final float SPEED_RATIO = 9.0F;

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        ValidateCommand.permission(sender, "soulhive.speed");
        Player player = ValidateCommand.onlyPlayer(sender);

        ValidateCommand.minArgs(1, args, USAGE);
        float userSpeed = Float.parseFloat(args[0]);

        if (userSpeed > SPEED_MAX) {
            userSpeed = SPEED_MAX;
        } else if (userSpeed < SPEED_MIN) {
            userSpeed = SPEED_MIN;
        }

        float base = player.isFlying() ? SPEED_BASE_FLYING : SPEED_BASE_WALKING;
        float ratio = (userSpeed - SPEED_RATIO_MAX) / SPEED_RATIO * (SPEED_RATIO_MAX - base);
        float speed = ratio + base;
        String mode;

        if (player.isFlying()) {
            player.setFlySpeed(speed);
            mode = "Fly";
        } else {
            player.setWalkSpeed(speed);
            mode = "Walk";
        }

        player.sendMessage(Settings.PREFIX + "Dein §f" + mode + "§7-Speed beträgt jetzt §f" + userSpeed + "§7.");
    }

}
