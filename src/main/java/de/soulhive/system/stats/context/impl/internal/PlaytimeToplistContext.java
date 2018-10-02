package de.soulhive.system.stats.context.impl.internal;

import de.soulhive.system.stats.context.ToplistContext;
import de.soulhive.system.stats.label.StatsLabel;
import de.soulhive.system.user.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class PlaytimeToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.PLAYTIME;

    private UserRepository userRepository;

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    @Override
    public Map<String, ? super Number> getData() {
        Map<String, ? super Number> fetched = this.userRepository.fetchByColumn(STATS_LABEL.getDatabaseColumn());
        Map<String, ? super Number> converted = new HashMap<>();

        fetched.forEach((name, minutes) -> converted.put(name, ((Number) minutes).longValue() / 60));

        return converted;
    }

}
