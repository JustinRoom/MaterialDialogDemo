# MaterialDialog #

Customized **Material Dialog**.It is compatible with low-version-SDK。

## Screenshot ##
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-115816.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-115953.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-120035.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-12-115304.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-12-181215.png)

<br>
## Usage ##
<pre><code>
<font color="#800080">
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
</font>
</code></pre>
