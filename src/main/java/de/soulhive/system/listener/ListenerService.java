package de.soulhive.system.listener;

import de.soulhive.system.service.Service;
import de.soulhive.system.util.reflect.ReflectUtils;
import org.bukkit.event.Listener;

import java.util.Set;

public class ListenerService extends Service {

    private static final String PACKAGE = ListenerService.class.getPackage().getName();

    @Override
    public Set<Listener> getListeners() {
        return ReflectUtils.getPacketObjects(PACKAGE, Listener.class);
    }

}
