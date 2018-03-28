package cn.com.ngds.lib.network.excutor;

import android.content.Context;

import cn.com.ngds.lib.network.cache.ICache;
import cn.com.ngds.lib.network.type.Response;
import cn.com.ngds.lib.network.call.NgdsCall;
import cn.com.ngds.lib.network.gson.GsonFactory;
import cn.com.ngds.lib.network.util.TaskUtil;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by wangyt on 2018/2/2.
 * : 优先网络
 */

public class NetFirstExecutor implements IExecutor {

    @Override
    public <T> void executeAsync(Object env, Context context, ICache cache, NgdsCall<Response<T>> call, Action0 onStart, Action1<T> onNext, Action1<Throwable> onError) {
        Observable<T> observable = Observable.create(subscriber -> {
            try {
                Response<T> data = call.execute();
                subscriber.onNext(data.getData());
                cache.saveCache(context, call.getRequestUrl(), GsonFactory.getGson().toJson(data));
            } catch (Exception e) {
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        });
        TaskUtil.subscribe(env, observable, onStart, onNext, onError);

    }
}
