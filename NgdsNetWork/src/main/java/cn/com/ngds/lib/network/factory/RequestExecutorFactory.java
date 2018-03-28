package cn.com.ngds.lib.network.factory;

import android.util.SparseArray;

import java.lang.ref.SoftReference;

import cn.com.ngds.lib.network.Const;
import cn.com.ngds.lib.network.excutor.IExecutor;
import cn.com.ngds.lib.network.excutor.CacheFirstExecutor;
import cn.com.ngds.lib.network.excutor.NetFirstExecutor;
import cn.com.ngds.lib.network.excutor.OnlyNetExecutor;
import cn.com.ngds.lib.network.excutor.OnlyCacheExecutor;


/**
 * Created by wangyt on 2018/2/2.
 * : 请求流程工厂
 */

public class RequestExecutorFactory implements IExecutorFactory {
    private SparseArray<SoftReference<IExecutor>> mExecutors;

    public RequestExecutorFactory() {
        mExecutors = new SparseArray<>();
    }

    @Override
    public IExecutor getExecutor(@Const.CacheType int cacheType) {
        IExecutor executor = null;
        SoftReference<IExecutor> softReference = mExecutors.get(cacheType);
        if (softReference != null) {
            executor = softReference.get();
        }
        if (executor != null) {
            return executor;
        }
        switch (cacheType) {
            case Const.ONLY_NET:
                executor = new OnlyNetExecutor();
                break;
            case Const.ONLY_CACHE:
                executor = new OnlyCacheExecutor();
                break;
            case Const.CACHE_FIRST:
                executor = new CacheFirstExecutor();
                break;
            case Const.NET_FIRST:
                executor = new NetFirstExecutor();
                break;
            default:
                executor = new CacheFirstExecutor();
                break;
        }
        mExecutors.put(cacheType, new SoftReference<>(executor));
        return executor;
    }
}
