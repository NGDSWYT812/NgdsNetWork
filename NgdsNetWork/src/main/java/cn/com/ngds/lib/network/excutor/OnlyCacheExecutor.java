package cn.com.ngds.lib.network.excutor;

import android.content.Context;
import android.text.TextUtils;

import cn.com.ngds.lib.network.cache.ICache;
import cn.com.ngds.lib.network.exception.BaseException;
import cn.com.ngds.lib.network.type.Response;
import cn.com.ngds.lib.network.call.NgdsCall;
import cn.com.ngds.lib.network.gson.GsonFactory;
import cn.com.ngds.lib.network.util.TaskUtil;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by wangyt on 2018/2/2.
 * : 只读缓存
 */

public class OnlyCacheExecutor implements IExecutor {

    @Override
    public <T> void executeAsync(Object env, Context context, ICache cache, NgdsCall<Response<T>> call, Action0 onStart, Action1<T> onNext, Action1<Throwable> onError) {
        Observable<T> observable = Observable.create(subscriber -> {
            String content = cache.getCache(context, call.getRequestUrl());
            if (!TextUtils.isEmpty(content)) {
                Response<T> data = GsonFactory.getGson().fromJson(content, call.getType());
                subscriber.onNext(data.getData());
            } else {
                subscriber.onError(new BaseException("缓存过期"));
            }
            subscriber.onCompleted();
        });
        TaskUtil.subscribe(env, observable, onStart, onNext, onError);
    }
}
