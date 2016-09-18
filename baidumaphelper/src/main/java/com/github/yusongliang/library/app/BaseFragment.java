package com.github.yusongliang.library.app;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.github.yusongliang.library.util.MPermissionChecker;

/**
 * 基础Fragment
 */
public abstract class BaseFragment extends Fragment implements MPermissionChecker.CheckCallback, MPermissionChecker.ResultCallback {

    /**
     * 安卓6.0以上检查权限
     *
     * @param requestCode 权限请求码
     * @param permissions 所需权限
     */
    public void checkPermissions(int requestCode, String[] permissions) {
        MPermissionChecker.checkActivityPermissions(getActivity(), requestCode, this, permissions);
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
    }

    @Override
    public void onShouldShowRationale(int requestCode, String[] permissions) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onRequestPermissionsSuccess(int requestCode, String[] permissions) {
    }

    @Override
    public void onRequestPermissionsFail(int requestCode, String[] permissions) {
    }
}
