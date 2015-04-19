package self.mf.delay;

import self.mf.comm.CommPool;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class DelayCache<K, V> {
    private static final Logger LOG = Logger.getLogger(DelayCache.class.getName());

    private ConcurrentMap<K, V> cacheObjMap = new ConcurrentHashMap<K, V>();

    private DelayQueue<DelayItem<Pair<K, V>>> q = new DelayQueue<DelayItem<Pair<K, V>>>();

    private static DelayCache<Object,Object> delayCache = new DelayCache<Object,Object>();

    private ExecuteListener executeListener;


    private DelayCache() {

        Runnable daemonTask = new Runnable() {
            public void run() {
                daemonCheck();
            }
        };

        Thread thread = new Thread(daemonTask);
//        thread.setDaemon(true);
        thread.setName("cache monitor");
//        thread.start();
        CommPool.THREAD_POOL.execute(thread);
    }


    public static <K,V> DelayCache<K,V> getInstance(){

        return (DelayCache<K, V>) delayCache;
    }

    enum NullListener implements ExecuteListener<Object, Object> {
        INSTANCE;

        @Override
        public void toDo(Object key,Object param){};

    }


    private void daemonCheck() {

        if (LOG.isLoggable(Level.INFO))
            LOG.info("cache service started.");

        for (;;) {
            try {
                DelayItem<Pair<K, V>> delayItem = q.take();
                if (delayItem != null) {
                    // 超时对象处理
                    Pair<K, V> pair = delayItem.getItem();

                    executeListener.toDo(pair.key, pair.value);

                    cacheObjMap.remove(pair.key, pair.value); // compare and remove


                }
            } catch (InterruptedException e) {
                if (LOG.isLoggable(Level.SEVERE))
                    LOG.log(Level.SEVERE, e.getMessage(), e);
//                break;
            }
        }

//        if (LOG.isLoggable(Level.INFO))
//            LOG.info("cache service stopped.");
    }


    public DelayCache<K,V> executeListener(ExecuteListener executeListener){

        this.executeListener = executeListener == null ? NullListener.INSTANCE : executeListener;
        return this;
    }

    // 添加缓存对象
    public DelayCache<K,V> put(K key, V value, long time, TimeUnit unit) {
        V oldValue = cacheObjMap.put(key, value);
        if (oldValue != null)
            q.remove(key);

        long nanoTime = TimeUnit.NANOSECONDS.convert(time, unit);
        q.put(new DelayItem<Pair<K, V>>(new Pair<K, V>(key, value), nanoTime));

        return this;
    }

    public V get(K key) {
        return cacheObjMap.get(key);
    }

    public void remove(K key) {
        cacheObjMap.remove(key);
        q.remove(key);
    }



    class Pair<K, V> {
        public K key;

        public V value;

        public Pair() {}



        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // 测试入口函数
    public static void main(String[] args) throws Exception {
        DelayCache<String,String> delayCache = DelayCache.getInstance();
        delayCache.put("1", "aaaa", 3, TimeUnit.SECONDS);

        Thread.sleep(1000 * 2);
        {
            String str = delayCache.get("1");
            System.out.println(str);
        }

        Thread.sleep(1000 * 2);
        {
            String str = delayCache.get("1");
            System.out.println(str);
        }
    }
}