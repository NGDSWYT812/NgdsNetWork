package cn.com.ngds.lib.network.util;

/**
 * Created by wangyt on 2018/2/7.
 * : 单例包装类
 */

public abstract class Singleton<T> {
    private T mInstance;

    protected abstract T create();

    public final T get() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }
            return mInstance;
        }
    }
}
