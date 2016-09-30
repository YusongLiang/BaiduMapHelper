package com.github.yusongliang.library.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.github.yusongliang.library.R;
import com.github.yusongliang.library.util.MPermissionChecker;

/**
 * 基础Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements MPermissionChecker.Callback {

    private AlertDialog.Builder mBuilder;

    /**
     * 安卓6.0以上检查权限
     *
     * @param requestCode 权限请求码
     * @param permissions 所需权限
     */
    protected void checkMPermissions(int requestCode, String[] permissions) {
        MPermissionChecker.checkActivityPermissions(this, requestCode, this, permissions);
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

    protected void showRationaleDialog(String message) {
        mBuilder = new AlertDialog.Builder(this).setMessage(message);
        showDialog();
    }

    protected void showRationaleDialog(@StringRes int messageId) {
        mBuilder = new AlertDialog.Builder(this).setMessage(messageId);
        showDialog();
    }

    private void showDialog() {
        mBuilder.setNegativeButton(R.string.negative_button_text, null)
                .setPositiveButton(R.string.positive_button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                }).show();
    }
}
