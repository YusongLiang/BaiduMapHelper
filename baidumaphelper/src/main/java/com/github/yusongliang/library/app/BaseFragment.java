package com.github.yusongliang.library.app;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.github.yusongliang.library.util.MPermissionChecker;

/**
 * 基础Fragment,含检查权限功能
 */
public abstract class BaseFragment extends Fragment implements MPermissionChecker.Callback {

    /**
     * 安卓6.0以上检查权限
     *
     * @param requestCode 权限请求码
     * @param permissions 所需权限
     */
    public void checkPermissions(int requestCode, String[] permissions) {
        MPermissionChecker.checkFragmentPermissions(this, requestCode, this, permissions);
    }

    @Override
    public void onRequestPermissionsSuccess(int requestCode, String[] successPermissions) {
    }

    @Override
    public void onRequestPermissionsFail(int requestCode, String[] failPermissions) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
