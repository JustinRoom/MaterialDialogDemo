package org.jsc.materialdialogdemo.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsc on 2016/10/9.
 */

public abstract class CommonActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    protected final int PERMISSION_REQUEST_CODE = 0x8888;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE)
            onCheckOrRequestPermissionResult(true, permissions[0], grantResults[0]);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    /**
     * 请求权限
     * @param permissions
     */
    protected void checkPermission(String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            onCheckOrRequestPermissionResult(false, "", 0);
            return;
        }

        List<String> unGrantedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            int checkResult = ActivityCompat.checkSelfPermission(this, permission);
            if (checkResult != PackageManager.PERMISSION_GRANTED) {
                unGrantedPermissions.add(permission);
            }

            int size = unGrantedPermissions.size();
            if (size > 0) {
                String[] needRequestPermissions = unGrantedPermissions.toArray(new String[size]);
                ActivityCompat.requestPermissions(this, needRequestPermissions, PERMISSION_REQUEST_CODE);
            } else {
                onCheckOrRequestPermissionResult(false, "", 0);
            }
        }
    }

    /**
     * 请求权限结果
     * @param isRequestResult
     *      false不需要请求;true请求后返回结果
     * @param permission
     * @param grantResult
     */
    protected void onCheckOrRequestPermissionResult(boolean isRequestResult, String permission, int grantResult){
        //do nothing
    }


    protected void showToast(String txt){
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }
}
