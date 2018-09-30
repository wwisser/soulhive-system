package de.skydust.system.stats.context;

import de.skydust.system.stats.label.StatsLabel;

import java.util.Map;

public interface ToplistContext {

    StatsLabel getLabel();

    Map<String, ? super Number> getData();

}
