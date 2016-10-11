# MaterialDialog #

Customized **Material Dialog**.It is compatible with low-version-SDK。

## Screenshot ##
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-115816.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-115953.png)
![](https://github.com/JustinRoom/MaterialDialogDemo/blob/master/screenshot/device-2016-10-11-120035.png)

<br>
## Usage ##
<pre><code>
<font color="#800080">
  public void showMaterialDialog(View view) {

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.tip);

        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(this)
                .setTitle("Title")
                .setSubtitle("sub title")
                .setMessage("display message here.display message here.display message here.display message here.display message here.")
                .setPositiveButton("OK", new ConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(DialogInterface dialog, int which) {
                        
                    }
                })
                .setNegativeButton("CANCEL", new ConfirmDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(DialogInterface dialog, int which) {
                        
                    }
                })
                .setView(imageView);

        builder.show();
    }
</font>
</code></pre>
