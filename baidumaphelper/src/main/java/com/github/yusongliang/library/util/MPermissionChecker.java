package com.github.yusongliang.library.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * 权限检查器，针对安卓6.0以上检查相关权限问题
 *
 * @author Yusong.Liang
 */
public class MPermissionChecker {

    /**
     * 检查相关权限，若当前所在页面为Activity则使用该方法,若当前页面为Fragment，
     * 则使用{@link #checkFragmentPermission(Fragment, String, int, CheckCallback)}
     *
     * @param context     传入当前Activity对象
     * @param permission  所需权限
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link CheckCallback}，并重写相关方法
     */
    public static void checkActivityPermission(@NonNull Activity context, @NonNull String permission,
                                               int requestCode, @NonNull CheckCallback callback) {
        checkPermission(context, null, permission, requestCode, callback);
    }

    /**
     * 检查相关权限，若当前所在页面为Fragment则使用该方法,若当前页面为Activity，
     * 则使用{@link #checkActivityPermission(Activity, String, int, CheckCallback)}
     *
     * @param fragment    传入当前Fragment对象
     * @param permission  所需权限
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link CheckCallback}，并重写相关方法
     */
    public static void checkFragmentPermission(@NonNull Fragment fragment, @NonNull String permission,
                                               int requestCode, @NonNull CheckCallback callback) {
        checkPermission(null, fragment, permission, requestCode, callback);
    }

    /**
     * 基础检查权限方法
     *
     * @param context     当前Activity对象
     * @param fragment    当前Fragment对象
     * @param permission  所需权限
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link CheckCallback}，并重写相关方法
     */
    private static void checkPermission(Activity context, Fragment fragment, @NonNull String permission,
                                        int requestCode, @NonNull CheckCallback callback) {
        if (fragment != null) context = fragment.getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                callback.onGranted();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {
                    callback.onShouldShowRationale();
                } else {
                    if (fragment != null) {
                        fragment.requestPermissions(new String[]{permission}, requestCode);
                    } else {
                        ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
                    }
                }
            }
        }
    }

    public static void onRequestPermissionsResult(int requestCode, int[] grantResults, int[] requestCodes, ResultCallback callback) {
        for (int request : requestCodes) {
            if (requestCode == request) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callback.onSuccess();
                } else {
                    callback.onFail();
                }
            }
        }
    }

    /**
     * 权限检查结果的回调
     */
    public interface CheckCallback {

        /**
         * 已被授权
         */
        void onGranted();

        /**
         * 需要阐述权限用途
         */
        void onShouldShowRationale();
    }

    /**
     * 权限请求结果的回调
     */
    public interface ResultCallback {

        /**
         * 权限请求成功
         */
        void onSuccess();

        /**
         * 权限请求失败
         */
        void onFail();
    }
}
