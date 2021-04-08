package cn.ymex.kitx.core.storage;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.Set;

import cn.ymex.kitx.core.Kitx;

/**
 * SharedPreferences 帮助类
 * Created by ymexc on 2016/6/16.
 */
public final class SharedPreferences {

    private static volatile SharedPreferences instance;
    private android.content.SharedPreferences mPreferences;

    private SharedPreferences() {
        mPreferences = Kitx.getApplication().getSharedPreferences("KITX_SP_STORAGE", Context.MODE_PRIVATE);
    }

    public static SharedPreferences instance() {
        SharedPreferences sp = instance;
        if (sp == null) {
            synchronized (SharedPreferences.class) {
                sp = instance;
                if (sp == null) {
                    sp = new SharedPreferences();
                    instance = sp;
                }
            }
        }
        return sp;
    }

    public android.content.SharedPreferences preferences() {
        return mPreferences;
    }

    public static <T> void put(@NonNull String key, @NonNull T value) {
        android.content.SharedPreferences.Editor editor = instance().preferences().edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        } else {
            editor.putString(key, "");
        }
        editor.apply();
    }

    public static <E> E get(String key) {
        return get(key, null);
    }

    public static <E> E get(String key, E def) {
        Map map = instance().preferences().getAll();
        if (map.containsKey(key)) {
            return (E) map.get(key);
        }
        return def;
    }

    /**
     * Mark in the editor that a preference value should be removed
     *
     * @param key
     */
    public static void remove(String key) {
        instance().preferences().edit().remove(key).apply();
    }

    /**
     * Mark in the editor to remove <em>all</em> values from the
     * preferences.
     */

    public static void clear() {
        instance().preferences().edit().clear().apply();
    }


    /**
     * Retrieve all values from the preferences.
     *
     * <p>Note that you <em>must not</em> modify the collection returned
     * by this method, or alter any of its contents.  The consistency of your
     * stored data is not guaranteed if you do.
     *
     * @return Returns a map containing a list of pairs key/value representing
     * the preferences.
     */
    public static Map<String, ?> getAll() {
        return instance().preferences().getAll();
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    public static boolean contains(String key) {
        return instance().preferences().contains(key);
    }
}
