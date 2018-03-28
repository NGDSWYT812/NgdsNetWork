package cn.com.ngds.lib.network.type;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by ytzyg on 15/1/6.
 * :响应封装
 */
public class Response<T> extends BaseType {

    public static final Type type = new TypeToken<Response>() {
    }.getType();

    private T data;
    private Meta meta;
    private Pagination pagination;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /**
     * 异常信息
     * Created by ytzyg on 15/1/6.
     */
    public static class Meta extends BaseType {

        private int code;

        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    /**
     * 页码
     * Created by ytzyg on 15/1/6.
     */
    public static class Pagination extends BaseType {
        @SerializedName("rows_found")
        private int rowsFound;
        private int offset;
        private long since;
        private long until;
        private int limit;

        public int getRowsFound() {
            return rowsFound;
        }

        public void setRowsFound(int rowsFound) {
            this.rowsFound = rowsFound;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public long getSince() {
            return since;
        }

        public void setSince(long since) {
            this.since = since;
        }

        public long getUntil() {
            return until;
        }

        public void setUntil(long until) {
            this.until = until;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public boolean hasMore() {
            return rowsFound - offset > 0;
        }
    }
}
