package com.github.yusongliang.library.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.github.yusongliang.library.R;
import com.github.yusongliang.library.util.MPermissionChecker;

/**
 * 基础Fragment,含检查权限功能
 */
public abstract class BaseFragment extends Fragment implements MPermissionChecker.Callback {

    private AlertDialog mDialog;

    /**
     * 安卓6.0以上检查权限
     *
     * @param requestCode 权限请求码
     * @param permissions 所需权限
     */
    public void checkMPermissions(int requestCode, String[] permissions) {
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

    /**
     * 弹出权限说明Dialog
     *
     * @param message 权限说明文字
     */
    protected void showRationaleDialog(String message) {
        showRationaleDialog(message, 0);
    }

    /**
     * 弹出权限说明Dialog
     *
     * @param messageId 权限说明文字资源id
     */
    protected void showRationaleDialog(@StringRes int messageId) {
        showRationaleDialog(null, messageId);
    }

    /**
     * 弹出权限说明Dialog
     *
     * @param message   权限说明文字
     * @param messageId 权限说明文字资源id
     */
    private void showRationaleDialog(String message, @StringRes int messageId) {
        if (mDialog == null || !mDialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setNegativeButton(R.string.negative_button_text, null)
                    .setPositiveButton(R.string.positive_button_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
                            startActivity(intent);
                        }
                    });
            if (messageId == 0) builder.setMessage(message);
            else builder.setMessage(messageId);
            mDialog = builder.show();
        }
    }
}
