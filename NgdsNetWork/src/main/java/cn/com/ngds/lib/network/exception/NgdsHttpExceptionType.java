package cn.com.ngds.lib.network.exception;


import cn.com.ngds.lib.network.UnMixable;

/**
 * NgdsHttpExceptionType
 * Description:自定义网络请求错误
 * Author:walker lx
 */
public enum NgdsHttpExceptionType implements UnMixable {
    NONE_NETWORK(-1, "请检查您的网络连接"), REQUEST_UNKNOWN_ERROR(-2, "请求发生未知错误,请稍后再试");
    public int code;
    public String message;

    NgdsHttpExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
