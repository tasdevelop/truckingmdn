package com.transporindo.truckingmdn;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient; //memasukan class WebViewClient
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView view;
    private ProgressDialog progressDialog;
    private BroadcastReceiver mNetworkReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        if (!DetectConnection.checkInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_SHORT).show();
        } else {

            view = (WebView) this.findViewById(R.id.webview);

            view.setWebViewClient(new MyBrowser());
            view.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= 19) {
                view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
            view.loadUrl("http://202.162.198.51:8888/ci-android-mdn/");
        }



    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            progressDialog.show();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            if (progressDialog.isShowing() ) {
                progressDialog.dismiss();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(MainActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();

        }


    }
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //ketika disentuh tombol back
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && view.canGoBack()) {
//            view.goBack(); //method goback(),untuk kembali ke halaman sebelumnya
//            return true;
//        }else{
//            finish();
//            System.exit(0);
//        }
//        // Jika tidak ada halaman yang pernah dibuka
//        // maka akan keluar dari activity (tutup aplikasi)
//        return super.onKeyDown(keyCode, event);
//    }
    @Override
    public void onBackPressed() {
        if (view.isFocused() && view.canGoBack()) {
            view.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
