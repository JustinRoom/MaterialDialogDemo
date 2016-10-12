# MaterialDialog #

Customized **Material Dialog**.It is compatible with low-version-SDK。

## Screenshot ##
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-115816.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-115953.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-120035.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-12-115304.png)

<br>
## Usage ##
<pre><code>
<font color="#800080">
  public void showMaterialDialog(View view) {

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.tip);

        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(this)
                .setTitle("Title")
//                .setSubtitle("sub title")
//                .setMessage("display message here.display message here.display message here.display message here.display message here.")
                .setPositiveButton("OK")
                .setNegativeButton("CANCEL")
                .setOnDialogButtonClickListener(new ConfirmDialog.OnDialogButtonClickListener() {
                    @Override
                    public void onDialogButtonClick(DialogInterface dialog, Editable editable, boolean isSure) {
                        dialog.dismiss();
                        if (isSure)
                            showToast(editable.toString());
                    }
                })
                .showEditModel("", "输入内容", -1);
//                .setView(imageView);

        builder.show();
    }
</font>
</code></pre>
