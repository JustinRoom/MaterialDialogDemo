package org.jsc.materialdialogdemo.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jsc.materialdialog.MaterialDialog;
import org.jsc.materialdialogdemo.R;

public class CustomActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
    }

    public void showMaterialDialog(View view) {

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .setTitle("Title")
                .setSubtitle("sub title")
                .setMessage("display message here.display message here.display message here.display message here.display message here.")
                .setPositiveButton("OK")
                .setNegativeButton("CANCEL")
                .setOnDialogButtonClickListener(new MaterialDialog.OnDialogButtonClickListener() {
                    @Override
                    public void onDialogButtonClick(DialogInterface dialog, Editable editable, boolean isSure) {

                    }
                })
                .setCustomView(imageView, params);
//                .setEditModelEnable(true)
//                .showEditModel("", "输入内容", -1);
//                .setCustomView(imageView);

        builder.show();
    }


}
