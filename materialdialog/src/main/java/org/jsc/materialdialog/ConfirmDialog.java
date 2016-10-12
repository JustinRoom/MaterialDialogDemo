package org.jsc.materialdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ConfirmDialog extends Dialog {

    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvMessage;
    private EditText etInput;
    private TextView btnNegative;
    private TextView btnPositive;
    private LinearLayout customViewContainer;

    private View customView;

    private OnDialogButtonClickListener clickListener;
    //EditText's attr

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
        etInput = (EditText) findViewById(R.id.input);
        btnNegative = (TextView) findViewById(R.id.btn_negative);
        btnPositive = (TextView) findViewById(R.id.btn_positive);
        customViewContainer = (LinearLayout) findViewById(R.id.custom_view_container);

        tvTitle.setVisibility(View.GONE);
        tvSubTitle.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);
        etInput.setVisibility(View.GONE);
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
        customViewContainer.addView(this.customView, params);
    }

    public void setPositiveButton(CharSequence positive){
        if (TextUtils.isEmpty(positive))
            return;
        btnPositive.setVisibility(View.VISIBLE);
        btnPositive.setText(positive);
    }

    public void setNegativeButton(CharSequence negative){
        if (TextUtils.isEmpty(negative))
            return;
        btnNegative.setVisibility(View.VISIBLE);
        btnNegative.setText(negative);
    }

    public void setClickListener(OnDialogButtonClickListener clickListener) {
        this.clickListener = clickListener;
    }

    private void setEditModelEnable(boolean editModelEnable) {
        if (editModelEnable)
            etInput.setVisibility(View.VISIBLE);
        else
            etInput.setVisibility(View.GONE);
    }

    public void showEditModel(CharSequence txt, CharSequence hint, int inputType){
        setEditModelEnable(true);
        etInput.setText(txt);
        etInput.setHint(hint);
        etInput.setInputType(inputType);
    }

    public View getCustomView() {
        return customView;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            boolean isPositiveButtonClick = false;
            if (v == btnPositive){
                isPositiveButtonClick = true;

            }
            if (clickListener != null)
                clickListener.onDialogButtonClick(ConfirmDialog.this, etInput.getText(), isPositiveButtonClick);

            //in editable model
            if (etInput.getVisibility() != View.VISIBLE)
                dismiss();
        }
    };

    public interface OnDialogButtonClickListener {
        /**
         * The click onDialogButtonClickListener of dialog's buttons.
         * @param dialog
         * @param editable
         *         Input value from EditText in edit model.
         * @param isSure
         *         It indicates that positive button was clicked if true, negative button false.
         */
        void onDialogButtonClick(DialogInterface dialog, Editable editable, boolean isSure);
    }

    public static class Builder{
        Context context;
        CharSequence title;
        CharSequence subtitle;
        CharSequence message;
        CharSequence positive;
        CharSequence negative;
        OnDialogButtonClickListener onDialogButtonClickListener;
        boolean cancelable = true;
        boolean cancelableOnTouchOutside = true;
        View view;

        //EditText's attr
        boolean editModelEnable = false;
        CharSequence txt;
        CharSequence hint;
        int inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setSubtitle(CharSequence subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        public Builder setPositiveButton(CharSequence positive){
            this.positive = positive;
            return this;
        }

        public Builder setNegativeButton(CharSequence negative){
            this.negative = negative;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setOnDialogButtonClickListener(OnDialogButtonClickListener onDialogButtonClickListener) {
            this.onDialogButtonClickListener = onDialogButtonClickListener;
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

        /**
         * Whether the editable dialog can be shown or not.
         * See Method {@link #showEditModel(CharSequence, CharSequence, int)}.
         * @param editModelEnable
         *        Show editable model if true.
         * @return
         */
        public Builder setEditModelEnable(boolean editModelEnable) {
            this.editModelEnable = editModelEnable;
            return this;
        }

        /**
         * Init EditText's attr in editable model.
         * @param txt
         * @param hint
         * @param inputType
         *          The default input type is InputType.TYPE_TEXT_FLAG_MULTI_LINE
         *          It will use default input type if inputType's value is -1.
         * @return
         */
        public Builder showEditModel(CharSequence txt, CharSequence hint, int inputType){
            this.editModelEnable = true;
            this.txt = txt;
            this.hint = hint;
            this.inputType = inputType;
            return this;
        }

        public ConfirmDialog create(){
            ConfirmDialog dialog = new ConfirmDialog(context);
            dialog.setTitle(title);
            dialog.setSubTitle(subtitle);
            dialog.setMessage(message);
            dialog.setNegativeButton(negative);
            dialog.setPositiveButton(positive);
            dialog.setClickListener(onDialogButtonClickListener);
            dialog.setCustomView(view);
            dialog.showEditModel(txt, hint, inputType);

            return dialog;
        }

        public void show(){
            create().show();
        }
    }
}
