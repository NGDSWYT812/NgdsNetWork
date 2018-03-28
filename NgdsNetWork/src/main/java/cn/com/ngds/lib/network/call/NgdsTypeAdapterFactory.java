package cn.com.ngds.lib.network.call;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by wangyt on 2018/2/9.
 * : call中范型type转换
 */

public class NgdsTypeAdapterFactory extends CallAdapter.Factory {
    public static final NgdsTypeAdapterFactory INSTANCE = new NgdsTypeAdapterFactory();

    /**
     * Returns a call adapter for interface methods that return {@code returnType}, or null if it
     * cannot be handled by this factory.
     *
     * @param returnType
     * @param annotations
     * @param retrofit
     */
    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        // 获取原始类型
        Class<?> rawType = getRawType(returnType);
        // 返回值必须是NgdsCall并且带有泛型
        if (rawType == NgdsCall.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new NgdsCallAdapter(callReturnType);
        }
        return null;
    }
}
