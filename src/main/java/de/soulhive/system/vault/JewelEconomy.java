package de.soulhive.system.vault;

import de.soulhive.system.user.User;
import de.soulhive.system.user.UserService;
import lombok.AllArgsConstructor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class JewelEconomy implements Economy {

    private UserService userService;

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "SoulHive";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return String.valueOf(v);
    }

    @Override
    public String currencyNamePlural() {
        return "Juwelen";
    }

    @Override
    public String currencyNameSingular() {
        return "Juwel";
    }

    @Override
    public boolean hasAccount(String s) {
        return this.userService.getUserByName(s) != null;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return this.userService.getUserByUuid(offlinePlayer.getUniqueId().toString()) != null;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return this.userService.getUserByName(s) != null;
    }

    @Deprecated
    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return true;
    }

    @Override
    public double getBalance(String s) {
        return this.userService.getUserByName(s).getJewels();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return this.userService.getUserByName(offlinePlayer.getUniqueId().toString()).getJewels();
    }

    @Override
    public double getBalance(String s, String s1) {
        return this.getBalance(s);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return this.getBalance(offlinePlayer);
    }

    @Override
    public boolean has(String s, double v) {
        return this.getBalance(s) >= v;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return this.has(offlinePlayer.getName(), v);
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return this.has(s, v);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return this.has(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        double balance = this.getBalance(playerName);
        int iAmount = (int) Math.ceil(amount);

        if (amount < 0) {
            return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds");
        }

        if (!this.has(playerName, iAmount)) {
            return new EconomyResponse(0, balance, EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        }

        final User user = this.userService.getUserByName(playerName);
        user.removeJewels(iAmount);
        this.userService.saveUser(user);

        return new EconomyResponse(iAmount, this.getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return this.withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        return this.withdrawPlayer(s, v);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return this.withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        int intAmount = (int) Math.floor(amount);

        if (amount < 0) {
            return new EconomyResponse(0, this.getBalance(playerName), EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        }

        final User user = this.userService.getUserByName(playerName);
        user.addJewels(intAmount);
        this.userService.saveUser(user);

        return new EconomyResponse(intAmount, this.getBalance(playerName), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return this.depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        return this.depositPlayer(s, v);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return this.depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, this.getBalance(s), EconomyResponse.ResponseType.FAILURE, "Not supported");
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return true;
    }

}
