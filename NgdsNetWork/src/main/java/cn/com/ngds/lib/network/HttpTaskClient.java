package cn.com.ngds.lib.network;

import android.annotation.SuppressLint;
import android.content.Context;

import cn.com.ngds.lib.network.cache.ICache;
import cn.com.ngds.lib.network.call.NgdsCall;
import cn.com.ngds.lib.network.factory.IExecutorFactory;
import cn.com.ngds.lib.network.type.Response;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by wangyt on 2018/2/2.
 * : 任务封装
 */

public class HttpTaskClient {
    private Context mContext;
    private ICache mCache;
    private IExecutorFactory mFactory;

    private HttpTaskClient() {
    }

    public static HttpTaskClient get() {
        return InstanceContainer.instance;
    }

    private static class InstanceContainer {
        @SuppressLint("StaticFieldLeak")
        private final static HttpTaskClient instance = new HttpTaskClient();
    }

    public static void init(Context context, ICache cache, IExecutorFactory factory) {
        get().setContext(context);
        get().setCache(cache);
        get().setFactory(factory);
    }

    public static <T> Request<T> request(Object env, NgdsCall<Response<T>> call) {
        return get().new Request<>(env, call);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setCache(ICache cache) {
        this.mCache = cache;
    }

    public void setFactory(IExecutorFactory factory) {
        this.mFactory = factory;
    }


    public class Request<T> {
        Object env;

        NgdsCall<Response<T>> call;

        @Const.CacheType
        int type;

        Action0 onStart;

        Action1<T> onNext;

        Action1<Throwable> onError;

        Request(Object env, NgdsCall<Response<T>> call) {
            this.call = call;
            this.env = env;
        }

        public Request<T> type(@Const.CacheType int type) {
            this.type = type;
            return this;
        }

        public Request<T> onStart(Action0 onStart) {
            this.onStart = onStart;
            return this;
        }

        public Request<T> onSuc(Action1<T> onNext) {
            this.onNext = onNext;
            return this;
        }

        public Request<T> onFail(Action1<Throwable> onError) {
            this.onError = onError;
            return this;
        }

        public void call() {
            mFactory.getExecutor(type)
                    .executeAsync(env, mContext, mCache, call, onStart, onNext, onError);
        }
    }
}
