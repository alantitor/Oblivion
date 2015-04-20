package ntou.cs.lab505.oblivion.activitites;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import ntou.cs.lab505.oblivion.R;

public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String helpUrl = "file:///android_asset/help.html";
        WebView webView = (WebView) findViewById(R.id.webview_activity_info);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(helpUrl);
    }
}
