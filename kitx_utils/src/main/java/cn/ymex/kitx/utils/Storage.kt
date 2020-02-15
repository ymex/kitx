package cn.ymex.kitx.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferences 帮助类
 * Created by ymexc on 2016/6/16.
 */
object Storage {
    private lateinit var mContext: Context


    private lateinit var mPreferences: SharedPreferences


    fun create(context: Context) {
        mContext = if (context is Application) {
            context
        }else{
            context.applicationContext
        }
    }

    private fun preferences(): SharedPreferences {
        if (this::mPreferences.isInitialized) {
            return mPreferences
        }
        mPreferences = mContext.getSharedPreferences("KIT_X_SP", Context.MODE_PRIVATE)
        return mPreferences
    }


    fun <T> put(key: String, value: T) {
        val editor = preferences().edit()
        if (value is Int) {
            editor.putInt(key, (value as Int))
        } else if (value is Float) {
            editor.putFloat(key, (value as Float))
        } else if (value is String) {
            editor.putString(key, value as String)
        } else if (value is Long) {
            editor.putLong(key, (value as Long))
        } else if (value is Boolean) {
            editor.putBoolean(key, (value as Boolean))
        } else if (value is Set<*>) {
            editor.putStringSet(key, value as Set<String?>)
        } else {
            editor.putString(key, "")
        }
        editor.apply()
    }

    operator fun <E> get(key: String): E? {
        return get(key, null)
    }

    operator fun <E> get(key: String, def: E): E? {
        val map: Map<String, *> = preferences().all
        return if (map.containsKey(key)) {
            map[key] as E
        } else def
    }

    /**
     * Mark in the editor that a preference value should be removed
     *
     * @param key
     */
    fun remove(key: String) {
        preferences().edit().remove(key).apply()
    }

    /**
     * Mark in the editor to remove *all* values from the
     * preferences.
     */
    fun clear() {
        preferences().edit().clear().apply()
    }


    /**
     * Retrieve all values from the preferences.
     *
     *
     * Note that you *must not* modify the collection returned
     * by this method, or alter any of its contents.  The consistency of your
     * stored data is not guaranteed if you do.
     *
     * @return Returns a map containing a list of pairs key/value representing
     * the preferences.
     */
    fun getAll(): Map<String, *> {
        return preferences().all
    }

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    operator fun contains(key: String): Boolean {
        return preferences().contains(key)
    }
}