package cn.ymex.kitx.widget.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 管理fragment
 */
public class TabsHostFragment extends Fragment {

    private int contextId = generateViewId();
    private List<Fragment> mFragments = new ArrayList();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FrameLayout hostLayout = new FrameLayout(requireContext());
        FrameLayout contextLayout = new FrameLayout(requireContext());
        contextLayout.setId(contextId);
        hostLayout.addView(
                contextLayout,
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                )
        );
        return hostLayout;
    }


    public TabsHostFragment addFragments(Fragment... fragments) {
        if (fragments.length <= 0) {
            return this;
        }
        if (mFragments.size() > 0) {
            throw new IllegalArgumentException("Adding more than once is not allowed");
        }
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();

        for (int i = 0; i < fragments.length; i++) {
            Fragment it = fragments[i];
            Fragment tf = getChildFragmentManager().findFragmentByTag(it.getClass().getName() + ":" + i);
            if (tf != null) {
                mFragments.add(tf);
            } else {
                mFragments.add(it);
                ft.add(contextId, it, it.getClass().getName() + ":" + i);
            }
        }
        ft.commit();
        return this;
    }

    public void show(Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        for (Fragment item : mFragments) {
            ft.hide(item);
        }
        ft.show(fragment).commit();
    }

    public void autoShow() {
        show(currentShow);
    }


    public void show(int index) {
        if (mFragments.size() <= index) {
            return;
        }
        Fragment fragment = mFragments.get(index);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        for (Fragment item : mFragments) {
            ft.hide(item);
        }

        ft.show(fragment).commit();
        currentShow = index;
        if (onFragmentShowListener != null) {
            onFragmentShowListener.onFragmentShow(fragment);
        }
    }


    public Fragment getCurrentShowFragment() {
        return getIndexFragment(currentShow);
    }

    public int getCurrentShowFragmentIndex() {
        return currentShow;
    }

    public Fragment getIndexFragment(int index) {
        if (mFragments.size() <= index || index <= 0) {
            return null;
        }
        return mFragments.get(index);
    }

    public OnFragmentShowListener onFragmentShowListener;


    interface OnFragmentShowListener {
        void onFragmentShow(Fragment fragment);
    }


    public void setBottomNavigationView(BottomNavigationView navigationView, int... actionIds) {

        navigationView.setSelectedItemId(actionIds[currentShow]);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int fIndex = -1;
                for (int i = 0; i < actionIds.length; i++) {
                    if (item.getItemId() == actionIds[i]) {
                        fIndex = i;
                    }
                }
                if (fIndex >= 0) {
                    show(fIndex);
                    return true;
                }
                return false;
            }
        });
    }


    public void setBottomNavigationView(
            BottomNavigationView navigationView,
            BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
    ) {
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private static int currentShow = 0;

    public static TabsHostFragment get(FragmentManager fragmentManager, @IdRes int id) {
        return (TabsHostFragment) fragmentManager.findFragmentById(id);
    }


    private static AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
