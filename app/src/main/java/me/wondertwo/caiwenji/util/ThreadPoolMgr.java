package me.wondertwo.caiwenji.util;

import android.util.Log;

/**
 * 线程池管理类，单例
 *
 * Created by wondertwo on 2016/12/23.
 */

public class ThreadPoolMgr {

    private static String TAG = "ThreadPoolMgr";
    private static ThreadPoolMgr mManager;
    private static ThreadPoolProxy mPool; //线程池代理对象

    public static ThreadPoolProxy getPool() {
        if (mPool == null) {
            int threadCount = Runtime.getRuntime().availableProcessors();
            Log.e(TAG, "core thread number : " + threadCount);
            mPool = new ThreadPoolProxy(threadCount, threadCount * 2, 2000L);
        }

        return mPool;
    }
}
