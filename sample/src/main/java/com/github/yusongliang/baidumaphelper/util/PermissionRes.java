package com.github.yusongliang.baidumaphelper.util;

import android.Manifest;

public class PermissionRes {
    public static class RequestCode {
        public static final int REQUEST_PERMISSIONS_LOCATE = 0x00000001;
    }

    public static class PermissionGroup {
        public static final String[] PERMISSIONS_LOCATE = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        };
    }
}
