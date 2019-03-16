package de.soulhive.system.clan.commands.subcommands;

import de.soulhive.system.clan.ClanService;
import de.soulhive.system.clan.commands.ClanCommand;
import de.soulhive.system.clan.models.Clan;
import de.soulhive.system.clan.models.ClanMember;
import de.soulhive.system.clan.storage.impl.LocalClanStorage;
import de.soulhive.system.setting.Settings;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

@AllArgsConstructor
public class CommandCreate implements ClanCommand {

    private static final String ARGUMENT = "create";
    private static final String TAG_CHARS = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
    private static final String NAME_CHARS = "aäAÄbBcCdDeEfFgGhHiIjJkKlLmMnNoOöÖpPqQrRsStTuUüÜvVwWxXyYzZ0123456789ß-^°";

    private ClanService clanService;

    @Override
    public boolean process(Player player, String[] args) {
        if (args.length < 3) {
            return false;
        }

        String uuid = player.getUniqueId().toString();

        if (this.clanService.getLocalClanStorage().getClan(uuid) != null) {
            player.sendMessage(Settings.PREFIX + "§cDu bist bereits in einem Clan. Verlasse ihn mit /clan leave.");
            return true;
        }

        String tag = args[1];
        String name = args[2];

        if (!this.isTagValid(tag)) {
            player.sendMessage(Settings.PREFIX + "§cDer Clantag darf mindestens 3 und maximal 5 Zeichen lang sein.");
            return true;
        }

        if (!this.isNameValid(name)) {
            player.sendMessage(Settings.PREFIX + "§cDer Clanname darf mindestens 3 und maximal 14 Zeichen lang sein.");
            return true;
        }

        Clan clan = new Clan(
            name,
            tag,
            uuid,
            new ArrayList<>(Collections.singleton(uuid)),
            0, 0, 0,
            System.currentTimeMillis()
        );
        ClanMember clanMember = new ClanMember(uuid, 0, 0, clan, System.currentTimeMillis(), false);

        ((LocalClanStorage) this.clanService.getLocalClanStorage()).addClanSet(clan, clanMember);

        player.sendMessage(Settings.PREFIX_TEAM + "Du hast den Clan '§a" + name + "§7' §8[§f" + tag + "§8] §7erfolgreich erstellt!");
        return true;
    }

    @Override
    public String getArgument() {
        return ARGUMENT;
    }

    private boolean isTagValid(String tag) {
        if (!(tag.length() >= 3 && tag.length() <= 5)) {
            return false;
        }
        for (String c : tag.split("")) {
            if (!TAG_CHARS.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNameValid(String name) {
        if (!(name.length() >= 3 && name.length() <= 14)) {
            return false;
        }
        for (String c : name.split("")) {
            if (!NAME_CHARS.contains(c)) {
                return false;
            }
        }
        return true;
    }

}