package cz.voho.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorProvider {
    private static final ScheduledExecutorService SHARED_EXECUTOR = Executors.newScheduledThreadPool(4);
    public static final ExecutorService IMAGE_GENERATOR_EXECUTOR = SHARED_EXECUTOR;
    public static final ScheduledExecutorService INSTAGRAM_UPDATER_EXECUTOR = SHARED_EXECUTOR;

    @Override
    protected void finalize() throws Throwable {
        SHARED_EXECUTOR.shutdown();
    }
}
