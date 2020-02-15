package cn.ymex.kitx.core.permission;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class PermissionFragment extends Fragment {

    private boolean logging = true;
    private PermissionRequest.OnResultCallBack onResultCallBack;
    private static final int PERMISSIONS_REQUEST_CODE = 0x0811;
    private static final int ACTION_PERMISSIONS_REQUEST_CODE = 0x0215;
    private String[] requestPermissions = new String[]{};
    private Queue<String> permissionQueue = new LinkedList<>();
    private String currentRequestPermission;

    private static final List<String> SPECIAL_PERMISSIONS = new ArrayList<String>() {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                add(Manifest.permission.REQUEST_INSTALL_PACKAGES);
            } else {
                add("android.permission.REQUEST_INSTALL_PACKAGES");
            }
            add(Manifest.permission.SYSTEM_ALERT_WINDOW);
        }
    };


    private void setOnResultCallBack(PermissionRequest.OnResultCallBack onResultCallBack) {
        this.onResultCallBack = onResultCallBack;
    }


    void requestPermissions(@NonNull String[] permissions, @NonNull PermissionRequest.OnResultCallBack onResultCallBack, boolean each) {
        this.requestPermissions = permissions;
        setOnResultCallBack(onResultCallBack);
        if (each) {
            permissionQueue.clear();
            permissionQueue.addAll(Arrays.asList(permissions));
            requestQueuePermission();
        } else {
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
        }
    }

    boolean isSpecialPermission(String permission) {
        return SPECIAL_PERMISSIONS.contains(permission);
    }

    boolean containsSpecialPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (SPECIAL_PERMISSIONS.contains(permission)) {
                return true;
            }
        }
        return false;
    }

    private void requestQueuePermission() {
        currentRequestPermission = "";
        if (permissionQueue.isEmpty()) {
            return;
        }
        String permission = permissionQueue.poll();
        currentRequestPermission = permission;
        if (TextUtils.isEmpty(permission)) {
            throw new IllegalArgumentException("permission con`t be null or empty!");
        }
        switch (permission) {
            case Manifest.permission.REQUEST_INSTALL_PACKAGES:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !canRequestPackageInstalls()) {
                    startInstallPermissionSettingActivity();
                } else {
                    onActivityResult(ACTION_PERMISSIONS_REQUEST_CODE, 0, null);
                }
                break;
            case Manifest.permission.SYSTEM_ALERT_WINDOW:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !canDrawOverlays()) {
                    startCanDrawOverlaysPermissionSettingActivity();
                } else {
                    onActivityResult(ACTION_PERMISSIONS_REQUEST_CODE, 0, null);
                }
                break;
            default:
                requestPermissions(new String[]{permission}, PERMISSIONS_REQUEST_CODE);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != PERMISSIONS_REQUEST_CODE) return;
        boolean[] rationales = new boolean[permissions.length];
        boolean[] granteds = new boolean[permissions.length];


        for (int i = 0; i < permissions.length; i++) {
            granteds[i] = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            rationales[i] = shouldShowRequestPermissionRationale(permissions[i]);
        }
        onRequestPermissionsResult(permissions, granteds, rationales);
    }

    private void onRequestPermissionsResult(String[] permissions, boolean[] grantResults, boolean[] shouldShowRequestPermissionRationale) {

        List<Permission> pls = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            pls.add(new Permission(permissions[i], grantResults[i], shouldShowRequestPermissionRationale[i]));
            log("onRequestPermissionsResult " + pls.get(i));
        }
        if (onResultCallBack != null) {
            boolean next = onResultCallBack.onResult(new Permission(pls));
            if (next) {
                requestQueuePermission();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != ACTION_PERMISSIONS_REQUEST_CODE || currentRequestPermission.isEmpty()) {
            return;
        }
        boolean granted = true;
        switch (currentRequestPermission) {
            case Manifest.permission.REQUEST_INSTALL_PACKAGES:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    granted = canRequestPackageInstalls();
                }
                break;
            case Manifest.permission.SYSTEM_ALERT_WINDOW:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    granted = canDrawOverlays();
                }
                break;
        }
        onRequestPermissionsResult(new String[]{currentRequestPermission}, new boolean[]{granted}, new boolean[]{false});

    }

    @TargetApi(Build.VERSION_CODES.M)
    boolean isGranted(String permission) {
        final FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity == null) {
            throw new IllegalStateException("This fragment must be attached to an activity.");
        }
        return fragmentActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    boolean isRevoked(String permission) {
        final FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity == null) {
            throw new IllegalStateException("This fragment must be attached to an activity.");
        }
        return fragmentActivity.getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startCanDrawOverlaysPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, packageURI);
        startActivityForResult(intent, ACTION_PERMISSIONS_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, ACTION_PERMISSIONS_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean canRequestPackageInstalls() {
        return Objects.requireNonNull(getContext()).getPackageManager().canRequestPackageInstalls();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean canDrawOverlays() {
        return Settings.canDrawOverlays(getContext());
    }

    void log(String message) {
        if (logging) {
            Log.d(PermissionRequest.TAG, message);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
