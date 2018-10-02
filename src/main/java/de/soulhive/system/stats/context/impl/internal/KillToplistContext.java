package de.soulhive.system.stats.context.impl.internal;

import de.soulhive.system.stats.context.ToplistContext;
import de.soulhive.system.stats.label.StatsLabel;
import de.soulhive.system.user.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class KillToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.KILLS;

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
