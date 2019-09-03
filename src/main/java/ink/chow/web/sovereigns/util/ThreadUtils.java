package ink.chow.web.sovereigns.util;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static ExecutorService one = Executors.newSingleThreadExecutor();

    public static void submit(Runnable runnable){
        executorService.submit(runnable);
    }

    public static void submitByOneThread(Runnable runnable){
        one.submit(runnable);
    }
}
