package cn.com.ngds.lib.network.excutor;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;

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
 * : 优先缓存
 */

public class CacheFirstExecutor implements IExecutor {

    @Override
    public <T> void executeAsync(Object env, Context context, ICache cache, NgdsCall<Response<T>> call, Action0 onStart, Action1<T> onNext, Action1<Throwable> onError) {
        Observable<T> observable = Observable.create(subscriber -> {
            String content = cache.getCache(context, call.getRequestUrl());
            if (!TextUtils.isEmpty(content)) {
                Response<T> data = GsonFactory.getGson().fromJson(content, call.getType());
                subscriber.onNext(data.getData());
            } else {
                subscriber.onNext(null);
            }

            try {
                Response<T> rep = call.execute();
                subscriber.onNext(rep.getData());
                cache.saveCache(context, call.getRequestUrl(), GsonFactory.getGson().toJson(rep));
            } catch (IOException e) {
                e.printStackTrace();
            }
            subscriber.onCompleted();

        });
        TaskUtil.subscribe(env, observable, onStart, onNext, onError);

    }
}
