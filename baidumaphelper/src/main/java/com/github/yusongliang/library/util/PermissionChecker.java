package com.github.yusongliang.library.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * 权限检查器
 *
 * @author Yusong.Liang
 */
public class PermissionChecker {

    /**
     * 针对安卓6.0以上检查相关权限问题，若当前所在页面为Activity则使用该方法,若当前页面为Fragment，
     * 则使用{@link #checkFragmentPermission(Fragment, String, int, RequestCallback)}
     *
     * @param context     传入当前Activity对象
     * @param permission  所需权限
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link RequestCallback}，并重写相关方法
     */
    public static void checkActivityPermission(@NonNull Activity context, @NonNull String permission, int requestCode, @NonNull RequestCallback callback) {
        checkPermission(context, null, permission, requestCode, callback);
    }

    /**
     * 针对安卓6.0以上检查相关权限问题，若当前所在页面为Fragment则使用该方法,若当前页面为Activity，
     * 则使用{@link #checkActivityPermission(Activity, String, int, RequestCallback)}
     *
     * @param fragment    传入当前Fragment对象
     * @param permission  所需权限
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link RequestCallback}，并重写相关方法
     */
    public static void checkFragmentPermission(@NonNull Fragment fragment, @NonNull String permission, int requestCode, @NonNull RequestCallback callback) {
        checkPermission(null, fragment, permission, requestCode, callback);
    }

    /**
     * 基础检查权限方法
     *
     * @param context     当前Activity对象
     * @param fragment    当前Fragment对象
     * @param permission  所需权限
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link RequestCallback}，并重写相关方法
     */
    private static void checkPermission(Activity context, Fragment fragment, @NonNull String permission, int requestCode, @NonNull RequestCallback callback) {
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

    /**
     * 权限检查结果的回调
     */
    public interface RequestCallback {

        /**
         * 已被授权
         */
        void onGranted();

        /**
         * 需要阐述权限用途
         */
        void onShouldShowRationale();
    }
}
