package de.soulhive.system.thread;

import de.soulhive.system.task.impl.PlannedShutdownTask;
import de.soulhive.system.util.SlackUtils;
import lombok.AllArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class ShutdownHookThread extends Thread {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.YY HH:mm:ss 'Uhr'");

    private PlannedShutdownTask plannedShutdownTask;

    @Override
    public void run() {
        if (!this.plannedShutdownTask.isRegularShutdown()) {
            SlackUtils.postMessage(
                "Unregulärer Serverstopp: " + DATE_FORMAT.format(new Date()),
                "exclamation",
                "server-status"
            );
        }
    }

}
