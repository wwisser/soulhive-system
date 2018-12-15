package de.soulhive.system.container;

import de.soulhive.system.container.listener.InventoryClickListener;
import de.soulhive.system.service.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ContainerService extends Service {

    private Set<Container> containers = new HashSet<>();

    @Override
    public void initialize() {
        super.registerListener(new InventoryClickListener(this));
    }

    public void registerContainer(final Container container) {
        this.containers.add(container);
    }

    public Set<Container> getContainers() {
        return Collections.unmodifiableSet(this.containers);
    }

}
