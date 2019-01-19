package de.soulhive.system.user.helper;

import de.soulhive.system.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserHelper {

    private static UserHelper instance;

    /**
     * Handles a simple jewel transaction.
     *
     * @return false if the sender has not enough jewel balance
     */
    public boolean handleTransaction(User sender, User receiver, int amount) {
        if (amount > 0 && sender.getJewels() >= amount) {
            sender.removeJewels(amount);
            receiver.addJewels(amount);

            return true;
        } else {
            return false;
        }
    }

    public static UserHelper getInstance() {
        if (UserHelper.instance == null) {
            return (UserHelper.instance = new UserHelper());
        } else {
            return UserHelper.instance;
        }
    }

}
