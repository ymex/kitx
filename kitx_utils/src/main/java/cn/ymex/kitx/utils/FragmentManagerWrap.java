package cn.ymex.kitx.utils;


import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.LinkedList;
import java.util.List;

/**
 * simple fragment manager
 */
public class FragmentManagerWrap {

    private LinkedList<Fragment> mFragments;
    private FragmentManager supportManager;
    private int containerViewId = 0;
    private boolean lazyInit = false;//lazy init fragment
    private Fragment currentShowFragment;

    private FragmentManagerWrap(FragmentManager manager) {
        this.supportManager = manager;
    }

    public static FragmentManagerWrap build(FragmentManager manager) {
        return new FragmentManagerWrap(manager);
    }

    /**
     * Optional identifier of the container this fragment is
     * to be placed in.
     *
     * @param containerViewId id
     * @return this
     */
    public FragmentManagerWrap setContainerViewId(@IdRes int containerViewId) {
        this.containerViewId = containerViewId;
        return this;
    }

    public <T extends Fragment> T getLastFragment(int index) {
        return getFragment(getFragments().size() - 1);
    }

    public <T extends Fragment> T getFirstFragment(int index) {
        return getFragment(0);
    }

    public <T extends Fragment> T getFragment(int index) {
        if (getFragments().size() < 0 || index >= getFragments().size()) {
            return null;
        }
        return (T) getFragments().get(index);
    }


    private List<Fragment> getFragments() {
        return mFragments == null ? mFragments = new LinkedList<>() : mFragments;
    }

    public FragmentManager getFragmentManager() {
        return supportManager;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public FragmentManagerWrap setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
        return this;
    }

    /**
     * show fragment
     *
     * @param index fragment index
     */
    public void showFragment(int index) {
        this.showFragment(index, COMMIT);
    }


    /**
     * show fragment
     *
     * @param index fragment index
     * @param type  type
     */
    public void showFragment(int index, int type) {
        this.showFragment(getFragment(index), type);
    }


    /**
     * show fragment
     *
     * @param fragment fragment
     */
    public void showFragment(Fragment fragment) {
        showFragment(fragment, COMMIT);
    }

    /**
     * show fragment
     *
     * @param fragment   fragment
     * @param commiTtype commit type
     */
    public void showFragment(Fragment fragment, int commiTtype) {
        FragmentTransaction transaction = supportManager.beginTransaction();
        for (Fragment fg : getFragments()) {
            if (fg == fragment) {
                if (fg.isAdded()) {
                    transaction.show(fg);
                } else {
                    transaction.add(containerViewId, fg, getFragmentTag(fg));
                }
            } else {
                if (fg.isAdded()) {
                    transaction.hide(fg);
                }
            }
        }
        transaction.show(fragment);
        switch (commiTtype) {
            case COMMIT:
                transaction.commit();
                break;
            case COMMIT_NOW:
                transaction.commitNow();
                break;
            case COMMIT_NOW_ALLOWING_STATE_LOSS:
                transaction.commitNowAllowingStateLoss();
                break;
            case COMMIT_ALLOWING_STATE_LOSS:
                transaction.commitAllowingStateLoss();
                break;
            default:
                transaction.commit();
                break;
        }
        currentShowFragment = fragment;
    }

    public static final int COMMIT = 0x0;
    public static final int COMMIT_NOW = 0x1;
    public static final int COMMIT_NOW_ALLOWING_STATE_LOSS = 0x2;
    public static final int COMMIT_ALLOWING_STATE_LOSS = 0x3;


    /**
     * instance in Activity.onCreate() function
     *
     * @param fragments fragments
     * @return this
     */
    public FragmentManagerWrap attach(Fragment... fragments) {
        if (containerViewId <= 0) {
            throw new IllegalArgumentException("Fragment attach id is null");
        }
        if (supportManager == null) {
            throw new IllegalArgumentException("supportManager is null");
        }
        getFragments().clear();

        for (Fragment f : fragments) {
            Fragment fragment = supportManager.findFragmentByTag(getFragmentTag(f));
            getFragments().add(fragment == null ? f : fragment);
        }
        if (!lazyInit) {
            FragmentTransaction transaction = supportManager.beginTransaction();
            for (Fragment fg : getFragments()) {
                if (!fg.isAdded()) {
                    transaction.add(containerViewId, fg, getFragmentTag(fg)).hide(fg);
                }
            }
            transaction.commitNow();
        }
        return this;
    }


    public String getFragmentTag(Fragment fragment) {
        String tag = fragment.getClass().getName();
        if (fragment instanceof Alias) {
            return tag + ((Alias) fragment).getAlias();
        }
        return tag;
    }

    public interface Alias {
        String getAlias();
    }

    public Fragment getCurrentShowFragment() {
        return currentShowFragment;
    }
}
