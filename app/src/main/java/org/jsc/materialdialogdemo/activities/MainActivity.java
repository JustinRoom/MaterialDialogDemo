package org.jsc.materialdialogdemo.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import org.jsc.materialdialogdemo.R;
import org.jsc.materialdialogdemo.customui.DataModel;
import org.jsc.materialdialogdemo.customui.MyChartView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends CommonActivity {

    private MyChartView myChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myChartView = (MyChartView) findViewById(R.id.chart_view);

        List<DataModel> models = new ArrayList<>();
        models.add(new DataModel(60, "100"));
        models.add(new DataModel(20, "101"));
        models.add(new DataModel(58, "102"));
        models.add(new DataModel(60, "103"));
        models.add(new DataModel(45.5f, "104"));
        models.add(new DataModel(80, "105"));
        models.add(new DataModel(60, "106"));
        models.add(new DataModel(99, "107"));
        models.add(new DataModel(60, "108"));
        models.add(new DataModel(75, "109"));
        models.add(new DataModel(60, "110"));
        models.add(new DataModel(15, "111"));

        myChartView.setLineStyle(MyChartView.LineStyle.LINE);
        myChartView.setDataModels(models);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void openCustom(View view){
        startActivity(new Intent(this, CustomActivity.class));
    }

    /**
     * 权限兼容。SDK23及以上的权限,除了在Manifest文件里做相应的配置外，还需要主动申请。
     * @param view
     */
    public void requestPermission(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            checkPermission(Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            checkPermission(Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onCheckOrRequestPermissionResult(boolean isRequestResult, String permission, int grantResult) {
        if (!isRequestResult){
            showToast("无需请求权限，您想干嘛就干嘛！");
            return;
        }

        if (grantResult == PackageManager.PERMISSION_GRANTED){
            showToast("您已经请求了\"" + permission + "\"权限。");
        } else if (grantResult == PackageManager.PERMISSION_DENIED){
            showToast("您已经拒绝了\"" + permission + "\"权限。");
        } else {
            showToast("这是什么鬼！");
        }
    }
}
