package cn.com.ngds.lib.network.excutor;

import android.content.Context;

import cn.com.ngds.lib.network.cache.ICache;
import cn.com.ngds.lib.network.type.Response;
import cn.com.ngds.lib.network.call.NgdsCall;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by wangyt on 2018/2/2.
 * : 执行的流程
 */

public interface IExecutor {
    //异步实现
    <T> void executeAsync(Object env, Context context, ICache cache, NgdsCall<Response<T>> call, Action0 onStart, Action1<T> onNext, Action1<Throwable> onError);
}
