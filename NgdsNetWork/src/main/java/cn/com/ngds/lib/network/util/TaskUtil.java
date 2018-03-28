package cn.com.ngds.lib.network.util;


import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by wangyt on 2018/2/6.
 * : 公共代码抽离
 */

public class TaskUtil {
    public static <T> void subscribe(Object env, Observable<T> observable, Action0 onStart, Action1<T> onNext, Action1<Throwable> onError) {
        Observable.just(observable)
                .map(tObservable -> {
                    if (env instanceof ActivityLifecycleProvider) {
                        return tObservable.compose(((ActivityLifecycleProvider) env).bindToLifecycle()).compose(RxTools.applySchedulers());
                    } else if (env instanceof FragmentLifecycleProvider) {
                        return tObservable.compose(((FragmentLifecycleProvider) env).bindToLifecycle()).compose(RxTools.applySchedulers());
                    } else {
                        return tObservable.compose(RxTools.applySchedulers());
                    }
                })
                .subscribe(tObservable -> {
                            if (onStart != null) onStart.call();
                            tObservable.subscribe(t -> {
                                        if (onNext != null) onNext.call(t);
                                    },
                                    throwable -> {
                                        if (onError != null) onError.call(throwable);
                                    });
                        }
                );
    }
}
