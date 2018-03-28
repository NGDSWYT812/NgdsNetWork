package ngds.app;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import cn.com.ngds.lib.network.HttpTaskClient;
import cn.com.ngds.lib.network.cache.LruFileCache;
import cn.com.ngds.lib.network.exception.BaseException;
import cn.com.ngds.lib.network.exception.NgdsHttpExceptionType;
import cn.com.ngds.lib.network.factory.RequestExecutorFactory;
import cn.com.ngds.lib.network.type.Response;
import cn.com.ngds.lib.network.util.NetUtils;
import ngds.app.retrofit.ApiRetrofit;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by wangyt on 2018/3/28.
 * : description
 */

public class App extends Application {
    Gson gson;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        gson = new Gson();
        ApiRetrofit.init(getNetworkInterceptor(), gson);
        HttpTaskClient.init(this, new LruFileCache(this), new RequestExecutorFactory());
    }

    private Interceptor getNetworkInterceptor() {
        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                //当没有网络连接时
                if (!NetUtils.isConnected(getApplicationContext())) {
                    throw BaseException.createCustomNgdsException(NgdsHttpExceptionType.NONE_NETWORK);
                }
                Request request = chain.request();
                okhttp3.Response response = chain.proceed(request);
                if (response.isSuccessful()) {
                    return response;
                }
                Response ngdsResponse;
                try {
                    ngdsResponse = gson.getAdapter(TypeToken.get(Response.class)).fromJson(response.body().charStream());
                } catch (Exception e) {
                    //此时返回无法适用通用处理的异常,一般是服务器服务挂掉的情况
                    throw BaseException.createCustomNgdsException(NgdsHttpExceptionType.REQUEST_UNKNOWN_ERROR);
                }
                if (null != ngdsResponse.getMeta()) {
                    throw new BaseException(ngdsResponse.getMeta().getCode(), ngdsResponse.getMeta().getMessage());
                }
                return response;
            }
        };
    }
}
