package com.stdu.x5;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.stdu.x5.utils.WebViewJavaScriptFunction;
import com.stdu.x5.utils.X5WebView;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MainActivity extends AppCompatActivity {
 private X5WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView= findViewById(R.id.myweb);
       // TbsVideo.openVideo(getApplicationContext(),"https://zy.kakazy-yun.com/ppvod/FCDA325C3F63777D3DA9DDED7C2A7E9C.m3u8");
      webView.loadUrl("file:///android_asset/webpage/fullscreenVideo.html");
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        webView.setDrawingCacheEnabled(true);
        //enableX5FullscreenFunc();
         webView.addJavascriptInterface(new WebViewJavaScriptFunction() {
    @Override
    public void onJsFunctionCalled(String tag) {

    }
             @JavascriptInterface
             public void onX5ButtonClicked() {
                 MainActivity.this.enableX5FullscreenFunc();
             }

             @JavascriptInterface
             public void onCustomButtonClicked() {
                 MainActivity.this.disableX5FullscreenFunc();
             }

             @JavascriptInterface
             public void onLiteWndButtonClicked() {
                 MainActivity.this.enableLiteWndFunc();
             }

             @JavascriptInterface
             public void onPageVideoClicked() {
                 MainActivity.this.enablePageVideoFunc();
             }

},"Android");

    }
private  void preinitX5WebCore() {
    if (!QbSdk.isTbsCoreInited())
    {Toast.makeText(this, "x5加载失败", Toast.LENGTH_LONG).show();
        QbSdk.initX5Environment(this, null);}
    Toast.makeText(this, "x5加载OK", Toast.LENGTH_LONG).show();
}
    private void enableX5FullscreenFunc() {

        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }else{
            Toast.makeText(this, "开启X5全屏播放模式失败view为空", Toast.LENGTH_LONG).show();
        }
    }

    private void disableX5FullscreenFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enableLiteWndFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enablePageVideoFunc() {
        if (webView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
    @Override
public  void onBackPressed()
{
if(webView.canGoBack())
{webView.goBack();

}else
{
    finish();
}

}

}
