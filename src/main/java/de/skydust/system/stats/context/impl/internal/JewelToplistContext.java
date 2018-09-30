package de.skydust.system.stats.context.impl.internal;

import de.skydust.system.stats.context.ToplistContext;
import de.skydust.system.stats.label.StatsLabel;
import de.skydust.system.user.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class JewelToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.JEWELS;

    private UserRepository userRepository;

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    @Override
    public Map<String, ? super Number> getData() {
        return this.userRepository.fetchByColumn(STATS_LABEL.getDatabaseColumn());
    }

}
