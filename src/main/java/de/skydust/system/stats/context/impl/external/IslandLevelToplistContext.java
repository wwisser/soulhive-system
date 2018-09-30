package de.skydust.system.stats.context.impl.external;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import de.skydust.system.stats.context.ToplistContext;
import de.skydust.system.stats.label.StatsLabel;
import de.skydust.system.user.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class IslandLevelToplistContext implements ToplistContext {

    private static final StatsLabel STATS_LABEL = StatsLabel.ISLAND_LEVEL;

    private UserRepository userRepository;
    private ASkyBlockAPI aSkyBlockApi;

    @Override
    public StatsLabel getLabel() {
        return STATS_LABEL;
    }

    @Override
    public Map<String, ? super Number> getData() {
        Map<String, ? super Number> result = new HashMap<>();

        this.aSkyBlockApi.getLongTopTen().forEach((uuid, level) -> {
            String name = this.userRepository.fetchNameByUuid(uuid.toString());
            result.put(name, level);
        });

        return result;
    }

}
