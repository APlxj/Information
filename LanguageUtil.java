package com.pos.kmretailpos.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import com.pos.kmretailpos.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 类描述：应用语言
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public class LanguageUtil {

    public static String DATA = "KM_Language";

    /**
     * 应用语言初始化
     *
     * @param context
     */
    public static void init(Context context) {
        String language = SharedPreferencesUtil.getString(context, DATA);
        if ("null".equals(language)) language = Locale.CHINESE.getLanguage();
        updateConfiguration(context, getLocale(context, language));
    }

    /**
     * 更改应用语言
     *
     * @param context
     * @param persistence 是否持久化
     */
    public static void changeAppLanguage(Context context, Class<?> aClass, String str, boolean persistence) {
        Locale locale = getLocale(context, str);
        updateConfiguration(context, locale);
        if (persistence) SharedPreferencesUtil.put(context, DATA, locale.getLanguage());
        Intent intent = new Intent(context, aClass);
        //开始新的activity同时移除之前所有的activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    /**
     * 修改本地语言
     *
     * @param context
     * @param locale
     */
    private static void updateConfiguration(Context context, Locale locale) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            configuration.setLocale(locale);
        else
            configuration.locale = locale;
        resources.updateConfiguration(configuration, metrics);
    }

    /**
     * 获取本地语言
     *
     * @param context
     * @param str
     * @return
     */
    private static Locale getLocale(Context context, String str) {
        if (context.getResources().getString(R.string.zh).equals(str) || "zh".equals(str))
            return Locale.CHINESE;
        else if (context.getResources().getString(R.string.en).equals(str) || "en".equals(str))
            return Locale.US;
        return Locale.CHINESE;
    }

    /**
     * 获取本地语言名称
     *
     * @param context
     * @return
     */
    public static String getLocaleName(Context context) {
        String str = SharedPreferencesUtil.getString(context, LanguageUtil.DATA);
        if ("null".equals(str)) str = Locale.CHINESE.getLanguage();
        if ("zh".equals(str))
            return context.getResources().getString(R.string.zh);
        else if ("en".equals(str))
            return context.getResources().getString(R.string.en);
        return context.getResources().getString(R.string.zh);
    }

    /**
     * 获取语言list
     *
     * @param context
     * @return
     */
    public static List<String> getLanguages(Context context) {
        List<String> languages = new ArrayList<>();
        languages.add(context.getResources().getString(R.string.zh));
        languages.add(context.getResources().getString(R.string.en));
        return languages;
    }
}
