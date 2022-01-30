package cz.voho.common.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorProvider {
    public static final ExecutorService IMAGE_GENERATOR_EXECUTOR = Executors.newFixedThreadPool(4);
    public static final ScheduledExecutorService RECENT_ITEMS_UPDATER_EXECUTOR = Executors.newScheduledThreadPool(1);

    @Override
    protected void finalize() {
        IMAGE_GENERATOR_EXECUTOR.shutdown();
        RECENT_ITEMS_UPDATER_EXECUTOR.shutdown();
    }
}
