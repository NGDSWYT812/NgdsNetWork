package cn.com.ngds.lib.network;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wangyt on 2018/2/2.
 * : 常量
 */

public class Const {
    public static final int ONLY_NET = 1;//不读缓存
    public static final int ONLY_CACHE = 2;//只读缓存
    public static final int CACHE_FIRST = 3;//优先缓存
    public static final int NET_FIRST = 4;//优先网络

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ONLY_NET, ONLY_CACHE, CACHE_FIRST, NET_FIRST})
    public @interface CacheType {
    }

}