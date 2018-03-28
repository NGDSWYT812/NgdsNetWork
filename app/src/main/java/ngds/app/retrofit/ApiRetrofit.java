package ngds.app.retrofit;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.com.ngds.lib.network.call.NgdsTypeAdapterFactory;
import cn.com.ngds.lib.network.gson.GsonConverterFactory;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by tsung on 4/23/14.
 */
public class ApiRetrofit {
    private static TestApi testApi;

    public static void init(Interceptor networkInterceptor, Gson gson) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(networkInterceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                builder.addHeader("Device-id", "asdsagsgsdgdsgdsgag");
                builder.addHeader("Platform", "1");
                builder.addHeader("version", "1.0.0");
                builder.addHeader("channel-id", "gf");
                builder.addHeader("app-id", "5");
                Request request = builder.method(original.method(), original.body()).build();
                return chain.proceed(request);
            }
        });
        httpClient.connectTimeout(15, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        //if(!env.equals(RequestUrl.ENV_OUT_VALUE)) {
        HttpLoggingInterceptor loggingBodysInterceptor = new HttpLoggingInterceptor();
        loggingBodysInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingBodysInterceptor);
        //}

        OkHttpClient client = httpClient.build();


        Retrofit test = new Retrofit.Builder()
                .baseUrl("http://luck.api.newgamepad.com/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(NgdsTypeAdapterFactory.INSTANCE)
                .client(client)
                .build();
        testApi = test.create(TestApi.class);
    }


    public static TestApi getTest() {
        return testApi;
    }
}
