package cn.com.ngds.lib.network.call;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * Created by wangyt on 2018/2/9.
 * : call转换
 */

public class NgdsCallAdapter implements CallAdapter<NgdsCall<?>> {

    private final Type responseType;

    public NgdsCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public <R> NgdsCall<R> adapt(Call<R> call) {
        return new NgdsCall<>(call, responseType);
    }
}
