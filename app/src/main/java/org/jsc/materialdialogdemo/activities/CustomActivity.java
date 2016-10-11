package org.jsc.materialdialogdemo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.jsc.materialdialog.ConfirmDialog;
import org.jsc.materialdialogdemo.R;

public class CustomActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
    }

    public void showMaterialDialog(View view) {

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.tip);

        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(this)
                .setTitle("Title")
                .setSubtitle("sub title")
                .setMessage("display message here.display message here.display message here.display message here.display message here.")
                .setPositiveButton("OK", null)
                .setNegativeButton("CANCEL", null)
                .setView(imageView);

        builder.show();
    }


}
