package de.soulhive.system.util;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class MillisecondsConverter {

    /**
     * Converts the given amount of millis into a formatted String (days, hours, minutes, seconds)
     */
    public String convertToString(long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        List<String> results = new LinkedList<>();

        if (days != 0) {
            results.add(days + "d");
        }

        if (hours != 0) {
            results.add(hours + "h");
        }

        if (minutes != 0) {
            results.add(minutes + "m");
        }

        if (seconds != 0) {
            results.add(seconds + "s");
        }

        if (results.isEmpty()) {
            return "1ms";
        }

        return String.join(", ", results);
    }

}
