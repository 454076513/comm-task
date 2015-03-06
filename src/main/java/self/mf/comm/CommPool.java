package self.mf.comm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommPool {
	 /**
     * 固定线程池
     */
    public final static ExecutorService THREAD_POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
}
