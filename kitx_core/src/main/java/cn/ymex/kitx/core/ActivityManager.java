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

    private Stack<Activity> getStack() {
        return actStack;
    }


    /**
     * 获取栈顶activity
     *
     * @return Activity , when activity quene is empty return null.
     */
    public Activity getTop() {
        if (getStack().isEmpty()) {
            return null;
        }
        return getStack().peek();
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

    /**
     * 获取最后一个指定类名activity
     *
     * @param name activity class name
     * @return null or last activity
     */
    public Activity getLastItem(String name) {
        List<Activity> acts = getItems(name);
        if (acts.isEmpty()) {
            return null;
        }
        return acts.get(acts.size() - 1);
    }

    public Activity getLastItem(Class clazz) {
        return getLastItem(clazz.getName());
    }

    /**
     * 获取第一个指定类名activity
     *
     * @param name activity class name
     * @return null or first activity
     */
    public Activity getFirstItem(String name) {
        List<Activity> acts = getItems(name);
        if (acts.isEmpty()) {
            return null;
        }
        return acts.get(0);
    }


    public Activity getFirstItem(Class clazz) {
        return getFirstItem(clazz.getName());
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

    public void finish(Class ...  clazzes) {
        for (Class clazz : clazzes) {
            finish(clazz.getName());
        }
    }

    public int count() {
        return getStack().size();
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


    public Activity peek() {
        return getStack().peek();
    }

    public Activity pop() {
        checkArgument();
        return getStack().pop();
    }

    public boolean isEmpty() {
        return getStack().isEmpty();
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
     * 清空
     */
    public ActivityManager clear() {
        getStack().clear();
        return this;
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
