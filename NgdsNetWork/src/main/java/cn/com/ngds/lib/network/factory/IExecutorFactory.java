package cn.com.ngds.lib.network.factory;


import cn.com.ngds.lib.network.Const;
import cn.com.ngds.lib.network.excutor.IExecutor;

/**
 * Created by wangyt on 2018/2/2.
 * : 工厂接口
 */

public interface IExecutorFactory {
    IExecutor getExecutor(@Const.CacheType int cacheType);
}
