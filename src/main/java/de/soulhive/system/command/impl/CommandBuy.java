package de.soulhive.system.command.impl;

import de.soulhive.system.command.CommandExecutorWrapper;
import de.soulhive.system.command.exception.CommandException;
import de.soulhive.system.command.util.ValidateCommand;
import de.soulhive.system.setting.Settings;
import de.soulhive.system.util.Config;
import de.soulhive.system.util.SlackUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommandBuy extends CommandExecutorWrapper {

    private static final String USAGE = "§7Verwendung: §c/buy <PIN> <Betrag>";
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.YY HH:mm:ss 'Uhr'");

    @Override
    public void process(CommandSender sender, String label, String[] args) throws CommandException {
        final Player player = ValidateCommand.onlyPlayer(sender);

        if (args.length < 2) {
            sender.sendMessage(Settings.PREFIX + "§d§lJuwelen §7kaufen, so geht's:");
            sender.sendMessage(" §d1000 Juwelen §7sind §f1 EUR §7wert.");
            sender.sendMessage(" §7Für 10 EUR §7bekommst du §d10000 §7Juwelen, für 20 EUR §d20000§7, usw.");
            sender.sendMessage(" §7Zahlungsmittel: §b§lPaySafeCard §7PIN");
            sender.sendMessage(" " + USAGE);
            sender.sendMessage(" §7Bsp. für 5000 Juwelen und 5 EUR: §c/buy 0123634612341648 5");
            return;
        }

        String pin = args[0].replace("-", "");
        int amount = ValidateCommand.amount(args[1]);

        if (StringUtils.isNumeric(pin) && pin.length() == 16 && pin.startsWith("0") && amount > 0) {
            sender.sendMessage(Settings.PREFIX + "Vielen Dank!");
            sender.sendMessage(" §7Deine Spende wird innerhalb der nächsten 48 Stunden bearbeitet.");
            sender.sendMessage(" §7Bitte habe ein wenig Gedult, wir kümmern uns umgehend darum.");
            player.playSound(player.getLocation(), Sound.LEVEL_UP, Float.MAX_VALUE, Float.MIN_VALUE);

            final String time = DATE_FORMAT.format(new Date());
            final Config donationFile = new Config(
                Settings.CONFIG_PATH,
                time + "_" + player.getName() + "_" + UUID.randomUUID()
            );

            donationFile.set("Name", player.getName());
            donationFile.set("UUID", player.getUniqueId().toString());
            donationFile.set("Time", time);
            donationFile.set("PIN", pin);
            donationFile.set("Amount", amount);
            donationFile.saveFile();

            String slackText = "*Neue Spende von " + player.getName() + "*" +
                "\nPIN: `" + pin + "`" +
                "\nBetrag: " + amount +
                "\nGesendet: " + time +
                "\nUUID: " + player.getUniqueId();

            SlackUtils.postMessage(slackText, "geld_ausgeben", "donation");
        } else {
            sender.sendMessage(Settings.PREFIX + USAGE);
        }
    }

}