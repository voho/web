package cz.voho.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorProvider {
    public static final ExecutorService IMAGE_GENERATOR_EXECUTOR = Executors.newScheduledThreadPool(4);
    public static final ScheduledExecutorService INSTAGRAM_UPDATER_EXECUTOR = Executors.newScheduledThreadPool(1);

    @Override
    protected void finalize() throws Throwable {
        IMAGE_GENERATOR_EXECUTOR.shutdown();
        INSTAGRAM_UPDATER_EXECUTOR.shutdown();
    }
}
