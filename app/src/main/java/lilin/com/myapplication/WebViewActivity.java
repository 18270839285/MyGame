package lilin.com.myapplication;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webView);
        button = findViewById(R.id.button);
        initWeb();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initWeb(){
        WebSettings webSettings = webView.getSettings();
        //设置为可调用js方法*
        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl("file:///android_asset/test.html");
//        webView.loadUrl("JavaScript:show()");
//        webView.loadUrl("https://www.baidu.com");
//        webView.loadUrl("https://www.zhibohome.net/zbzj/index.html");
        webView.loadUrl("http://www.zhibohome.net/headlines/#/?token=WTC0SIvJ19W7Se1m1S1S&article_id=143&show=true");
        webView.addJavascriptInterface(new JsInteface(),"$bridge");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:ReplyContent");
//                webView.evaluateJavascript("sum(2,3)", new ValueCallback<String>() {
//                    @Override
//                    public void onReceiveValue(String value) {
//                        Log.e("onReceiveValue", "onReceiveValue: value = "+value );
//                    }
//                });
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("setWebViewClient", "shouldOverrideUrlLoading: url = "+url );
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("setWebViewClient", "onPageFinished: url = "+url );
                webView.loadUrl("javascript:ReplyContent");
                super.onPageFinished(view, url);
            }
        });

    }

    public class JsInteface{
        @JavascriptInterface
        public String sendDicToWebpage(){
            return "success";
        }
        @JavascriptInterface
        public String back(){
            return "success_back";
        }
    }
}
