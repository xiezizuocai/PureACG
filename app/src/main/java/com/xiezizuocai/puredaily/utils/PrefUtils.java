package com.xiezizuocai.puredaily.utils;

import android.content.Context;

import com.xiezizuocai.puredaily.constant.PrefConstants;


// 参数   工具类
public class PrefUtils {

    /**
     * 从当前Context获取SharedPreferences，将key,value以键值对存到SharedPreferences中。
     *
     * @param context 当前上下文
     * @param key  键
     * @param value  值
     */
    public static void putString(Context context, String key, String value) {
        // PrefConstants.CONFIG_SP_FILE_NAME，  指定SharedPreferences的名称
        // Context.MODE_PRIVATE， 操作模式（只允许当前应用程序对SharedPreferences进行读写）

        // 1.Context.getSharedPreferences获取SharedPreferences对象
        // 2.SharedPreferences.edit()获取SharedPreferences.Editor对象
        // 3.向SharedPreferences.Editor对象中添加String数据
        // 4.调用commit()方法将添加的数据提交，完成数据存储
        context.getSharedPreferences(PrefConstants.CONFIG_SP_FILE_NAME, Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    /**
     * 获取存储在SharedPreferences中的以PrefConstants.CONFIG_SP_FILE_NAME为键名的String值，如果不存在该键，则返回默认值defValue。
     * @param context  当前上下文
     * @param key  键
     * @param defValue  默认值
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        return context.getSharedPreferences(PrefConstants.CONFIG_SP_FILE_NAME, Context.MODE_PRIVATE).getString(key, defValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        context.getSharedPreferences(PrefConstants.CONFIG_SP_FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return context.getSharedPreferences(PrefConstants.CONFIG_SP_FILE_NAME, Context.MODE_PRIVATE).getBoolean(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        context.getSharedPreferences(PrefConstants.CONFIG_SP_FILE_NAME, Context.MODE_PRIVATE).edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        return context.getSharedPreferences(PrefConstants.CONFIG_SP_FILE_NAME, Context.MODE_PRIVATE).getInt(key, defValue);
    }

}
