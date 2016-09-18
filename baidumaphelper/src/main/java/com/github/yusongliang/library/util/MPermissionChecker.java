package com.github.yusongliang.library.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限检查器，针对安卓6.0以上检查相关权限问题
 *
 * @author Yusong.Liang
 */
public class MPermissionChecker {

    /**
     * 检查相关权限，若当前所在页面为Activity则使用该方法,若当前页面为Fragment，
     * 则使用{@link #checkFragmentPermissions(Fragment, int, CheckCallback, String...)}
     *
     * @param context     传入当前Activity对象
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link CheckCallback}，并重写相关方法
     * @param permissions 所需权限
     */
    public static void checkActivityPermissions(@NonNull Activity context, int requestCode,
                                                @NonNull CheckCallback callback, @NonNull String... permissions) {
        checkPermissions(context, null, requestCode, callback, permissions);
    }

    /**
     * 检查相关权限，若当前所在页面为Fragment则使用该方法,若当前页面为Activity，
     * 则使用{@link #checkActivityPermissions(Activity, int, CheckCallback, String...)}
     *
     * @param fragment    传入当前Fragment对象
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link CheckCallback}，并重写相关方法
     * @param permissions 所需权限
     */
    public static void checkFragmentPermissions(@NonNull Fragment fragment, int requestCode,
                                                @NonNull CheckCallback callback, @NonNull String... permissions) {
        checkPermissions(null, fragment, requestCode, callback, permissions);
    }

    /**
     * 基础检查权限方法
     *
     * @param context     当前Activity对象
     * @param fragment    当前Fragment对象
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link CheckCallback}，并重写相关方法
     * @param permissions 所需权限
     */
    private static void checkPermissions(Activity context, Fragment fragment, int requestCode,
                                         @NonNull CheckCallback callback, @NonNull String... permissions) {
        List<String> grantedList = new ArrayList<>();
        List<String> shouldShowRequestList = new ArrayList<>();
        List<String> requestList = new ArrayList<>();
        if (fragment != null) context = fragment.getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    grantedList.add(permission);
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                        shouldShowRequestList.add(permission);
                    } else {
                        requestList.add(permission);
                    }
                }
            }
            callback.onGranted(requestCode, grantedList.toArray(new String[grantedList.size()]));
            callback.onShouldShowRationale(requestCode, shouldShowRequestList.toArray(new String[shouldShowRequestList.size()]));
            if (requestList.size() > 0)
                requestPermissions(context, fragment, requestCode, requestList);
        } else {
            callback.onGranted(requestCode, permissions);
        }
    }

    /**
     * 申请权限
     *
     * @param context     当前Activity对象
     * @param fragment    当前Fragment对象
     * @param requestCode 权限请求码
     * @param permissions 所需权限
     */
    private static void requestPermissions(Activity context, Fragment fragment, int requestCode, @NonNull List<String> permissions) {
        String[] permissionsToRequest = permissions.toArray(new String[permissions.size()]);
        if (fragment != null) {
            fragment.requestPermissions(permissionsToRequest, requestCode);
        } else {
            ActivityCompat.requestPermissions(context, permissionsToRequest, requestCode);
        }
    }

    /**
     * 请求结果回调，在Activity的{@link Activity#onRequestPermissionsResult(int, String[], int[])}
     * 或Fragment的{@link Fragment#onRequestPermissionsResult(int, String[], int[])}中调用
     *
     * @param requestCode  请求码
     * @param permissions  请求权限
     * @param grantResults 授权结果
     * @param callback     结果回调
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults, ResultCallback callback) {
        List<String> grantedPermissions = new ArrayList<>();
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            int currResult = grantResults[i];
            String currPermission = permissions[i];
            if (currResult == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(currPermission);
            } else {
                deniedPermissions.add(currPermission);
            }
        }
        callback.onRequestPermissionsSuccess(requestCode, grantedPermissions.toArray(new String[grantedPermissions.size()]));
        callback.onRequestPermissionsFail(requestCode, deniedPermissions.toArray(new String[deniedPermissions.size()]));
    }

    /**
     * 权限检查结果的回调
     */
    public interface CheckCallback {

        /**
         * 已被授权
         */
        void onGranted(int requestCode, String[] permissions);

        /**
         * 需要阐述权限用途
         */
        void onShouldShowRationale(int requestCode, String[] permissions);
    }

    /**
     * 权限请求结果的回调
     */
    public interface ResultCallback {

        /**
         * 权限请求成功
         *
         * @param requestCode        请求码
         * @param successPermissions 请求成功的权限
         */
        void onRequestPermissionsSuccess(int requestCode, String[] successPermissions);

        /**
         * 权限请求失败
         *
         * @param requestCode     请求码
         * @param failPermissions 请求失败的权限
         */
        void onRequestPermissionsFail(int requestCode, String[] failPermissions);
    }
}
