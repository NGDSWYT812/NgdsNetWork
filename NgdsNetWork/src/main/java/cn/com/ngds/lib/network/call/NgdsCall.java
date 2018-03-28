package cn.com.ngds.lib.network.call;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit2.Call;

/**
 * Created by wangyt on 2018/2/9.
 * : call包装
 */

public class NgdsCall<R> {

    public final Call<R> call;
    private Type mType;

    public NgdsCall(Call<R> call, Type type) {
        this.call = call;
        mType = type;
    }

    public String getRequestUrl() {
        return call.request().url().toString();
    }

    public R execute() throws IOException {
        return call.execute().body();
    }

    public Type getType() {
        return mType;
    }
}

