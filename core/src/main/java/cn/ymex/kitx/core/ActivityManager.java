package cn.ymex.kitx.core;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Activity 堆栈管理。
 */
public final class ActivityManager implements Application.ActivityLifecycleCallbacks {

    private static ActivityManager activityManager;
    private Stack<Activity> actStack = new Stack<>();
    /**
     * 是否自动管理activity,默认为true ，若设置为false 则需要自己手动处理activity 出入栈。
     */
    private boolean autoManage = true;


    private ActivityManager(boolean auto) {
        this.autoManage = auto;
    }

    private ActivityManager() {
        this(true);
    }

    private static synchronized ActivityManager instance(boolean auto) {
        if (activityManager == null) {
            activityManager = new ActivityManager(auto);
        }
        return activityManager;
    }

    public static ActivityManager get() {
        return instance(true);
    }


    public void registerActivityLifecycleCallbacks(Application context) {
        context.registerActivityLifecycleCallbacks(this);
    }

    /**
     * 设置是否自动管理activity
     *
     * @param auto
     */
    public ActivityManager autoManage(boolean auto) {
        autoManage = auto;
        return this;
    }

    public Stack<Activity> getStack() {
        return actStack;
    }


    /**
     * 通过类名获取指定activitys
     *
     * @param name activity class name
     * @return 所有同名activity list
     */
    public List<Activity> getItems(String name) {
        List<Activity> acts = new ArrayList<>();
        for (Activity act : getStack()) {
            if (act.getClass().getName().equals(name)) {
                acts.add(act);
            }
        }
        return acts;
    }

    public List<Activity> getItems(Class clazz) {
        return getItems(clazz.getName());
    }

    public void finishOthers(Activity activity) {
        while (getStack().size() > 0) {
            Activity act = getStack().pop();
            if (act != activity) {
                finish(act);
            }
        }
        getStack().push(activity);
    }

    /**
     * 结束指定类名的activity
     *
     * @param name activity class name
     */
    public void finish(String name) {
        List<Activity> acts = getItems(name);
        if (acts.isEmpty()) {
            return;
        }
        for (Activity act : acts) {
            finish(act);
        }
    }


    public void finish(String... names) {
        for (String name : names) {
            finish(name);
        }
    }


    /**
     * 结束 指定 activity
     *
     * @param activity 入参activity
     */
    public void finish(Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()) {
                return;
            }
        }
        activity.finish();
    }


    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Activity act : getStack()) {
            builder.append(" {");
            builder.append(act.getClass().getName());
            builder.append(" }");
        }
        if (getStack().isEmpty()) {
            builder.append("activity stack is empty!");
        }
        return builder.toString();
    }


    /**
     * 删除
     *
     * @param activity
     */
    public void remove(Activity activity) {
        checkArgument();
        _remove(activity);
    }

    /**
     * 入栈activity
     *
     * @param activity
     */
    public void push(Activity activity) {
        checkArgument();
        _push(activity);
    }


    private void checkArgument() {
        if (autoManage) {
            throw new IllegalArgumentException("if autoManage is true , ActivityStack is not allow opt!");
        }
    }


    private void _remove(Activity activity) {
        actStack.remove(activity);
    }


    private void _push(Activity activity) {
        if (!getStack().contains(activity)) {
            getStack().push(activity);
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        if (autoManage) {
            _push(activity);
        }

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (autoManage) {
            _remove(activity);
        }
    }
}
