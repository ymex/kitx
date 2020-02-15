package cn.ymex.kitx.core.permission;


import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class PermissionRequest {
    static final String TAG = PermissionRequest.class.getSimpleName();
    Lazy<PermissionFragment> fragmentLazy;


    public PermissionRequest(@NonNull final FragmentActivity activity) {
        fragmentLazy = getLazySingleton(activity.getSupportFragmentManager());
    }

    public PermissionRequest(@NonNull final Fragment fragment) {
        fragmentLazy = getLazySingleton(fragment.getChildFragmentManager());
    }

    @NonNull
    private Lazy<PermissionFragment> getLazySingleton(@NonNull final FragmentManager fragmentManager) {
        return new Lazy<PermissionFragment>() {

            private PermissionFragment fragment;

            @Override
            public synchronized PermissionFragment get() {
                if (fragment == null) {
                    fragment = getPermissionsFragment(fragmentManager);
                }
                return fragment;
            }

        };
    }


    private PermissionFragment getPermissionsFragment(@NonNull final FragmentManager fragmentManager) {
        PermissionFragment fragment = findPermissionsFragment(fragmentManager);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new PermissionFragment();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG)
                    .commitNow();
        }
        return fragment;
    }

    private PermissionFragment findPermissionsFragment(@NonNull final FragmentManager fragmentManager) {
        return (PermissionFragment) fragmentManager.findFragmentByTag(TAG);
    }


    public void requestEach(String[] permissions, OnResultCallBack onResult) {
        fragmentLazy.get().requestPermissions(permissions,onResult,true);
    }

    public void request(String[] permissions,OnResultCallBack onResult) {
        fragmentLazy.get().requestPermissions(permissions,onResult,false);
    }



    /**
     * Returns true if the permission is already granted.
     * <p>
     * Always true if SDK &lt; 23.
     */
    @SuppressWarnings("WeakerAccess")
    public boolean isGranted(String permission) {
        return !isMarshmallow() || fragmentLazy.get().isGranted(permission);
    }

    /**
     * Returns true if the permission has been revoked by a policy.
     * <p>
     * Always false if SDK &lt; 23.
     */
    @SuppressWarnings("WeakerAccess")
    public boolean isRevoked(String permission) {
        return isMarshmallow() && fragmentLazy.get().isRevoked(permission);
    }

    boolean isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }



    @FunctionalInterface
    public interface Lazy<V> {
        V get();
    }


    @FunctionalInterface
    public interface OnResultCallBack {
        /**
         *
         * @param permission
         * @return is true will continue request next permission
         */
        boolean onResult(Permission permission);
    }
}
