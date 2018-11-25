package de.soulhive.system.delay;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

@AllArgsConstructor
@Getter
public class DelayConfiguration {

    @Nullable
    private String message;
    private long time;

}
