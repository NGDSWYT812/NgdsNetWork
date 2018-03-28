package cn.com.ngds.lib.network.util;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wangyt on 2018/1/13.
 * : rxjava工具，址在提高rxjava的易用性
 */

public class RxTools {

    /**
     * 线程切换 用compose调用
     *
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) transformer;
    }

    /**
     * 重试 用retryWhen调用
     *
     * @param maxRetries       重试次数
     * @param retryDelayMillis 重试间隔
     * @return
     */
    public static RetryWithDelay retryObservable(int maxRetries, int retryDelayMillis) {
        return new RetryWithDelay(maxRetries, retryDelayMillis);
    }

    /**
     * 切换线程实现
     */
    private static final Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 重试实现
     */
    private static class RetryWithDelay implements
            Func1<Observable<? extends Throwable>, Observable<?>> {

        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        RetryWithDelay(int maxRetries, int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
        }

        @Override
        public Observable<?> call(Observable<? extends Throwable> errObservable) {
            return errObservable.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
                if (++retryCount <= maxRetries) {
                    return Observable.timer(retryDelayMillis,
                            TimeUnit.MILLISECONDS);
                }
                return Observable.error(throwable);
            });
        }
    }
}
