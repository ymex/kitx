/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 * <p>
 * Email:ymex@foxmail.com  (www.ymex.cn)
 * @author ymex
 * date: 16/4/21
 */
package cn.ymex.kitx.core;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Kitx {
    private static Context application;
    public static boolean log = true;

    private Kitx() {
        throw new RuntimeException("Kitx not allow instance");
    }

    public static void init(Context context) {
        if (context == null) {
            return;
        }
        if (application != null) {
            return;
        }

        if (context instanceof Application) {
            application = context;
        } else {
            application = context.getApplicationContext();
        }
    }

    public static Context getApplication() {
        return application;
    }

    public static void err(String text) {
        if (!log) {
            return;
        }
        Log.e("Kitx.E", text);
    }

    public static void err(Throwable throwable) {
        if (!log) {
            return;
        }
        if (throwable == null) {
            Log.e("Kitx.E", "log error: throwable is null");
            return;
        }
        Log.e("Kitx.E", throwable.getMessage(), throwable);
    }

    public static void log(String text) {
        if (!log) {
            return;
        }
        Log.i("Kitx.I", text);
    }

    public static class Initializer extends ContentProvider {


        @Override
        public boolean onCreate() {
            if (Kitx.getApplication() == null) {
                Kitx.init(getContext().getApplicationContext());
            }
            return true;
        }

        @Nullable
        @Override
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
            return null;
        }

        @Nullable
        @Override
        public String getType(@NonNull Uri uri) {
            return null;
        }

        @Nullable
        @Override
        public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
            return null;
        }

        @Override
        public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
            return 0;
        }

        @Override
        public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
            return 0;
        }
    }
}
