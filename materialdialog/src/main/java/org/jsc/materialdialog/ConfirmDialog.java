package org.jsc.materialdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConfirmDialog extends Dialog {

    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvMessage;
    private TextView btnNegative;
    private TextView btnPositive;
    private LinearLayout customViewContainer;

    private View customView;

    private OnDialogClickListener positiveListener;
    private OnDialogClickListener negativeListener;

    public ConfirmDialog(Context context) {
        super(context, R.style.MyDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        init(context);
    }

    public ConfirmDialog(Context context, int attrs) {
        super(context, attrs);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.confirm_dialog_layout);

        tvTitle = (TextView) findViewById(R.id.title);
        tvSubTitle = (TextView) findViewById(R.id.sub_title);
        tvMessage = (TextView) findViewById(R.id.message);
        btnNegative = (TextView) findViewById(R.id.btn_negative);
        btnPositive = (TextView) findViewById(R.id.btn_positive);
        customViewContainer = (LinearLayout) findViewById(R.id.custom_view_container);

        tvTitle.setVisibility(View.GONE);
        tvSubTitle.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        btnNegative.setVisibility(View.GONE);
        btnPositive.setVisibility(View.GONE);

        btnPositive.setOnClickListener(listener);
        btnNegative.setOnClickListener(listener);
    }

    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title))
            return;
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
    }

    public void setSubTitle(CharSequence subTitle){
        if (TextUtils.isEmpty(subTitle))
            return;
        tvSubTitle.setVisibility(View.VISIBLE);
        tvSubTitle.setText(subTitle);
    }

    public void setMessage(CharSequence message){
        if (TextUtils.isEmpty(message))
            return;
        tvMessage.setVisibility(View.VISIBLE);
        tvMessage.setText(message);
    }

    public void setCustomView(View customView) {
        customViewContainer.removeAllViews();
        this.customView = customView;
        if (customView == null)
            return;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        customViewContainer.addView(this.customView);
    }

    public void setPositiveButton(String positive, OnDialogClickListener positiveListener){
        if (TextUtils.isEmpty(positive))
            return;
        btnPositive.setVisibility(View.VISIBLE);
        btnPositive.setText(positive);
        this.positiveListener = positiveListener;
    }

    public void setNegativeButton(String negative, OnDialogClickListener negativeListener){
        if (TextUtils.isEmpty(negative))
            return;
        btnNegative.setVisibility(View.VISIBLE);
        btnNegative.setText(negative);
        this.negativeListener = negativeListener;
    }

    public View getCustomView() {
        return customView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            if (v == btnPositive){
                if (positiveListener != null)
                    positiveListener.onDialogClick(ConfirmDialog.this, 0);
            }

            else if (v == btnNegative){
                if (negativeListener != null)
                    negativeListener.onDialogClick(ConfirmDialog.this, 1);
            }
        }
    };

    public interface OnDialogClickListener{
        public void onDialogClick(DialogInterface dialog, int which);
    }

    public static class Builder{
        Context context;
        String title;
        String subtitle;
        String message;
        String positive;
        String negative;
        OnDialogClickListener positiveListener;
        OnDialogClickListener negativeListener;
        boolean cancelable = true;
        boolean cancelableOnTouchOutside = true;
        View view;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButton(String positive, OnDialogClickListener positiveListener){
            this.positive = positive;
            this.positiveListener = positiveListener;
            return this;
        }

        public Builder setNegativeButton(String negative, OnDialogClickListener negativeListener){
            this.negative = negative;
            this.negativeListener = negativeListener;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCancelableOnTouchOutside(boolean cancelableOnTouchOutside) {
            this.cancelableOnTouchOutside = cancelableOnTouchOutside;
            return this;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        public ConfirmDialog create(){
            ConfirmDialog dialog = new ConfirmDialog(context);
            dialog.setTitle(title);
            dialog.setSubTitle(subtitle);
            dialog.setMessage(message);
            dialog.setNegativeButton(negative, negativeListener);
            dialog.setPositiveButton(positive, positiveListener);
            dialog.setCustomView(view);

            return dialog;
        }

        public void show(){
            create().show();
        }
    }
}
