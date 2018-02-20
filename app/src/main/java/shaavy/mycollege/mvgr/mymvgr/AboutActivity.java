package shaavy.mycollege.mvgr.mymvgr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutActivity extends AppCompatActivity {

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private static final String TAG = "AboutActivity";
    private WebView webView;
    private String urlstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        urlstring = "https://www.duckduckgo.com/";
        if (getIntent() != null) {
            String temp = getIntent().getStringExtra("btnurl");
            Log.d(TAG, "onCreate: the temp is"+temp);
            if(temp!=null){
                urlstring = temp;
            }
            Log.d(TAG, "onCreate: url to be gone - " + urlstring);
        }

        webView = (WebView) findViewById(R.id.webView1);

        Log.d(TAG, "onCreate: the url is "+urlstring);
        //webView.loadUrl(urlstring);

        webView.setWebViewClient(new MyWebViewClient());
        openURL(urlstring);


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(AboutActivity.this, MainActivity.class));
        finish();

    }

    /** Opens the URL in a browser */
    private void openURL(String urlstring) {
        webView.loadUrl(urlstring);
        webView.requestFocus();
    }


}
