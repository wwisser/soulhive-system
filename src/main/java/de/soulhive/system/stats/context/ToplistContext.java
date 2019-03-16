package de.soulhive.system.stats.context;

import de.soulhive.system.stats.label.StatsLabel;

import java.util.Map;

public interface ToplistContext {

    StatsLabel getLabel();

    Map<String, ? super Number> getData();

    default String getSkullOwnerByKey(String key) {
        return key;
    }

}
