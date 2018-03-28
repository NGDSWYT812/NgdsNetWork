package cn.com.ngds.lib.network.cache;

import android.content.Context;

/**
 * Created by wangyt on 2018/2/2.
 * : cache抽象
 */

public interface ICache {

    void saveCache(Context context, String index, String data);

    String getCache(Context context, String index);
}
