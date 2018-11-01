package de.soulhive.system.command.impl;

import de.skycade.system.setting.Message;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVote implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            TextComponent base = new TextComponent();
            base.setText(Message.PREFIX + "Klicke §3hier§7, um für tolle Vorteile zu voten.");
            base.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{
                    new TextComponent("§aKlicke, um zu voten")}));
            base.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.serverliste.net/vote/1173/" + player.getName()));
            player.spigot().sendMessage(base);
        } else {
            commandSender.sendMessage("Dieser Command ist nur für Spieler.");
        }

        return true;
    }

}