package de.skydust.system.stats.context;

import de.skydust.system.stats.label.StatsLabel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ToplistContext {

    private Map<String, ? super Number> data;
    private StatsLabel label;

}
