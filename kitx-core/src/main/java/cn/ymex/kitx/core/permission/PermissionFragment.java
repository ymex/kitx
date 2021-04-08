package cn.ymex.kitx.core.permission;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
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

    private Queue<String> permissionQueue = new LinkedList<>();
    private String[] requestPermissions;
    private String currentRequestPermission;
    private boolean isRequestEach = false;

    private static final List<String> SPECIAL_PERMISSIONS = new ArrayList<String>() {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                add(Manifest.permission.REQUEST_INSTALL_PACKAGES);
            } else {
                add("android.permission.REQUEST_INSTALL_PACKAGES");
            }
            add(Manifest.permission.SYSTEM_ALERT_WINDOW);
//            有问题：打开设置页面，就回调了，onActivityResult，暂时无法解决
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                add(Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE);
//            }
        }
    };


    private void setOnResultCallBack(PermissionRequest.OnResultCallBack onResultCallBack) {
        this.onResultCallBack = onResultCallBack;
    }


    void requestPermissions(@NonNull String[] permissions, @NonNull PermissionRequest.OnResultCallBack onResultCallBack, boolean each) {
        isRequestEach = each;
        requestPermissions = permissions;
        setOnResultCallBack(onResultCallBack);
        permissionQueue.clear();
        if (each) {
            permissionQueue.addAll(Arrays.asList(permissions));
            requestQueuePermission();
        } else {
            for (String permission : permissions) {
                if (isSpecialPermission(permission)) {
                    permissionQueue.add(permission);
                }
            }
            if (!permissionQueue.isEmpty()) {
                requestQueuePermission();
            } else {
                requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
            }

        }
    }

    private boolean isSpecialPermission(String permission) {
        return SPECIAL_PERMISSIONS.contains(permission);
    }


    private void requestQueuePermission() {
        currentRequestPermission = "";
        if (permissionQueue.isEmpty()) {
            if (!isRequestEach) {
                requestPermissions(requestPermissions, PERMISSIONS_REQUEST_CODE);
            }
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
            case Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && !notificationListenerEnable()) {
                    startNotificationAccessSetting();
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
            granteds[i] = grantResults[i] == PackageManager.PERMISSION_GRANTED || isGranted(permissions[i]);
            rationales[i] = shouldShowRequestPermissionRationale(permissions[i]);
        }
        onRequestPermissionsResult(permissions, granteds, rationales);
    }

    private void onRequestPermissionsResult(String[] permissions, boolean[] grantResults, boolean[] shouldShowRequestPermissionRationale) {
        List<Permission> pls = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {

            pls.add(new Permission(permissions[i], grantResults[i], shouldShowRequestPermissionRationale[i]));
            System.out.println("---- onRequestPermissionsResult: " + pls.get(i));
        }
        if (onResultCallBack != null) {
            boolean next = onResultCallBack.onResult(new Permission(pls));
            if (next && isRequestEach) {
                requestQueuePermission();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("----------onActivityResult permission " + currentRequestPermission);
        if (requestCode != ACTION_PERMISSIONS_REQUEST_CODE || currentRequestPermission.isEmpty()) {
            return;
        }
        if (isRequestEach) {
            onRequestPermissionsResult(new String[]{currentRequestPermission}, new boolean[]{isGranted(currentRequestPermission)}, new boolean[]{false});
        } else {
            requestQueuePermission();
        }
    }


    boolean isGranted(String permission) {
        final FragmentActivity fragmentActivity = getActivity();
        boolean granted = true;
        if (fragmentActivity == null) {
            throw new IllegalStateException("This fragment must be attached to an activity.");
        }

        switch (permission) {
            case Manifest.permission.REQUEST_INSTALL_PACKAGES:
                granted = canRequestPackageInstalls();
                break;
            case Manifest.permission.SYSTEM_ALERT_WINDOW:
                granted = canDrawOverlays();
                break;
            case Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE:
                granted = notificationListenerEnable();
                break;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    granted = fragmentActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                }
                break;
        }

        return granted;
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


    private boolean canRequestPackageInstalls() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Objects.requireNonNull(getContext()).getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }


    private boolean canDrawOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(getContext());
        }
        return true;
    }

    private boolean notificationListenerEnable() {
        boolean enable = false;
        String packageName = Objects.requireNonNull(getContext()).getPackageName();
        String flat = Settings.Secure.getString(getContext().getContentResolver(), "enabled_notification_listeners");
        if (flat != null) {
            enable = flat.contains(packageName);
        }
        return enable;
    }


    private boolean startNotificationAccessSetting() {
        try {
            Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS", packageURI);
            startActivityForResult(intent, ACTION_PERMISSIONS_REQUEST_CODE);
            return true;
        } catch (ActivityNotFoundException e) {
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.Settings$NotificationAccessSettingsActivity");
                intent.setComponent(cn);
                intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
                startActivityForResult(intent, ACTION_PERMISSIONS_REQUEST_CODE);
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
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
