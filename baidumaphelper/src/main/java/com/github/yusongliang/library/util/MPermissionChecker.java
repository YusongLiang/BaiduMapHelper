package com.github.yusongliang.library.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
     * 已授予权限集合
     */
    private static List<String> mGrantedList;

    /**
     * 检查结果的回调，{@link MPermissionChecker.Callback}的实现类对象
     */
    private static Callback mCallback;
    private static List<String> mDeniedList;

    /**
     * 检查相关权限，若当前所在页面为{@link Activity}则使用该方法,若当前页面为{@link Fragment}，
     * 则使用{@link #checkFragmentPermissions(Fragment, int, Callback, String...)}
     *
     * @param context     传入当前{@link Activity}对象
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link MPermissionChecker.Callback}
     * @param permissions 需检查的权限
     */
    public static void checkActivityPermissions(@NonNull Activity context, int requestCode,
                                                @NonNull Callback callback, @NonNull String... permissions) {
        checkPermissions(context, null, requestCode, callback, permissions);
    }

    /**
     * 检查相关权限，若当前所在页面为{@link Fragment}则使用该方法,若当前页面为{@link Activity}，
     * 则使用{@link #checkActivityPermissions(Activity, int, Callback, String...)}
     *
     * @param fragment    传入当前{@link Fragment}对象
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，需实现{@link MPermissionChecker.Callback}
     * @param permissions 需检查的权限
     */
    public static void checkFragmentPermissions(@NonNull Fragment fragment, int requestCode,
                                                @NonNull Callback callback, @NonNull String... permissions) {
        checkPermissions(null, fragment, requestCode, callback, permissions);
    }

    /**
     * 基础检查权限方法
     *
     * @param context     当前{@link Activity}对象
     * @param fragment    当前{@link Fragment}对象
     * @param requestCode 权限请求码
     * @param callback    检查结果的回调，{@link MPermissionChecker.Callback}的实现类对象
     * @param permissions 需检查的权限
     */
    private static void checkPermissions(Activity context, Fragment fragment, int requestCode,
                                         @NonNull Callback callback, @NonNull String... permissions) {
        mCallback = callback;
        mGrantedList = new ArrayList<>();
        mDeniedList = new ArrayList<>();
        List<String> requestList = new ArrayList<>();
        if (fragment != null) context = fragment.getActivity();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//版本为安卓6.0以上
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {//当前已被授权
                    mGrantedList.add(permission);//当前权限添加到已授权集合
                } else {//当前尚未授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, permission)) {//曾被拒绝，需要进一步解释该权限
                        mDeniedList.add(permission);
                    } else {//权限首次请求
                        requestList.add(permission);
                    }
                }
            }
            if (requestList.size() > 0) {//有需要请求的权限
                requestPermissions(context, fragment, requestCode, requestList);
            } else {
                if (mGrantedList.size() > 0) {
                    mCallback.onRequestPermissionsSuccess(requestCode, mGrantedList.toArray(new String[mGrantedList.size()]));
                }
                if (mDeniedList.size() > 0) {
                    mCallback.onRequestPermissionsFail(requestCode, mDeniedList.toArray(new String[mDeniedList.size()]));
                }
            }
        } else {//版本为安卓6.0以下
            mCallback.onRequestPermissionsSuccess(requestCode, permissions);
        }
    }

    /**
     * 请求权限
     *
     * @param context     传入的{@link android.content.Context}
     * @param fragment    传入的{@link Fragment}
     * @param requestCode 权限请求码
     * @param permissions 请求的权限
     */
    private static void requestPermissions(Activity context, @Nullable Fragment fragment, int requestCode, @NonNull List<String> permissions) {
        String[] permissionsToRequest = permissions.toArray(new String[permissions.size()]);
        if (fragment != null) {
            fragment.requestPermissions(permissionsToRequest, requestCode);
        } else {
            ActivityCompat.requestPermissions(context, permissionsToRequest, requestCode);
        }
    }

    /**
     * 处理权限请求结果，需在{@link Activity#onRequestPermissionsResult(int, String[], int[])}方法中，
     * 或{@link Fragment#onRequestPermissionsResult(int, String[], int[])}方法中调用
     *
     * @param requestCode  权限请求码
     * @param permissions  所请求的权限
     * @param grantResults 权限的授权结果
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            int currResult = grantResults[i];
            String currPermission = permissions[i];
            if (currResult == PackageManager.PERMISSION_GRANTED) {
                mGrantedList.add(currPermission);
            } else {
                mDeniedList.add(currPermission);
            }
        }
        if (mGrantedList.size() > 0) {
            mCallback.onRequestPermissionsSuccess(requestCode, mGrantedList.toArray(new String[mGrantedList.size()]));
        }
        if (mDeniedList.size() > 0) {
            mCallback.onRequestPermissionsFail(requestCode, mDeniedList.toArray(new String[mDeniedList.size()]));
        }
    }

    /**
     * 权限请求结果的回调
     */
    public interface Callback {

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
