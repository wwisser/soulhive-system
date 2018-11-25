package de.soulhive.system.delay;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

@AllArgsConstructor
@Getter
public class DelayConfiguration {

    /**
     * Can contain a <code>%time</code> placeholder which gets replaced with a formatted version of the pending time.
     */
    @Nullable
    private String message;
    private long time;

}
