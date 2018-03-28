package cn.com.ngds.lib.network.exception;

import android.text.TextUtils;

import cn.com.ngds.lib.network.UnMixable;

/**
 * 异常
 */
public class BaseException extends RuntimeException implements UnMixable {
    private static final String DEFAULT_MESSAGE = "操作失败，请重试";
    private static final int DEFAULT_CODE = -1;
    private int code;
    private String msg;

    public BaseException(int code, String msg) {
        this.code = code;
        if (TextUtils.isEmpty(msg)) {
            this.msg = DEFAULT_MESSAGE;
        } else {
            this.msg = msg;
        }
    }

    public BaseException(String msg) {
        this(DEFAULT_CODE, msg);
    }

    public BaseException(Throwable throwable) {
        this(DEFAULT_CODE, throwable.getMessage());
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public static BaseException createCustomNgdsException(NgdsHttpExceptionType ngdsHttpExceptionType) {
        return new BaseException(ngdsHttpExceptionType.code, ngdsHttpExceptionType.message);
    }

}
