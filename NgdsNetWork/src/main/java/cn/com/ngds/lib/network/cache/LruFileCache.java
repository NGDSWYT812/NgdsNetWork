package cn.com.ngds.lib.network.cache;

import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;


/**
 * Created by wangyt on 2018/2/9.
 * : 文件缓存实现
 */

public final class LruFileCache implements ICache {
    private static final int FILE_INDEX = 0;
    private String mPath;
    private int mSize = 10 * 1024 * 1024;//缓存大小，默认10mb
    private DiskLruCache mDiskLruCache;

    public LruFileCache(Context context) {
        context = context.getApplicationContext();
        mPath = context.getCacheDir().getAbsolutePath();
        init(mPath, mSize);
    }

    public LruFileCache(Context context, int size) {
        context = context.getApplicationContext();
        mPath = context.getCacheDir().getAbsolutePath();
        mSize = size;
        init(mPath, mSize);
    }

    public LruFileCache(String path, int size) {
        mPath = path;
        mSize = size;
        init(mPath, mSize);
    }

    private void init(String dir, long maxSize) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        try {
            mDiskLruCache = DiskLruCache.open(dirFile, 1, 1, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void saveCacheInternal(String cacheKey, String jsonStr) {
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(String.valueOf(cacheKey.hashCode()));
            editor.set(FILE_INDEX, jsonStr);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (editor != null) {
                try {
                    editor.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private synchronized String getCacheInternal(String cacheKey) {
        DiskLruCache.Snapshot snapshot;
        try {
            snapshot = mDiskLruCache.get(String.valueOf(cacheKey.hashCode()));
            if (snapshot != null) {
                return snapshot.getString(FILE_INDEX);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 验证缓存是否过期
     *
     * @param cacheKey     缓存key
     * @param cacheSeconds 过期时间 传-1表示最大过期时间,如果lru删除缓存文件则过期;如果lru未删除缓存文件则不过期;
     * @return true:过期;false:未过期;
     */
    public synchronized boolean isTimeOut(String cacheKey, long cacheSeconds) {
        final File f = new File(mDiskLruCache.getDirectory(), String.valueOf(cacheKey.hashCode()) + "." + FILE_INDEX);
        if (cacheSeconds <= -1) {
            return !f.exists();
        } else {
            if (f.exists()) {
                long time = f.lastModified();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(time);
                cal.add(Calendar.SECOND, (int) cacheSeconds);
                return cal.before(Calendar.getInstance());
            }
        }
        return true;
    }

    /**
     * 将缓存记录同步到journal文件中。
     */
    public void flush() {
        try {
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭
     */
    public void close() {
        try {
            mDiskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空缓存
     */
    public void clear() {
        try {
            mDiskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把数据写入文件缓存
     */
    @Override
    public  void saveCache(Context context, String index, String data) {
        saveCacheInternal(index, data);
    }

    /**
     * 获取缓存数据，如果缓存过期，则返回null，不过期则读取缓存信息
     */
    @Override
    public  String getCache(Context context, String index) {
        return getCacheInternal(index);
    }
}

