package com.android.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.datingforall.anonymousOnlineDating.BuildConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

public class ExtendedWebView extends WebView {

    private static final String TAG = "@@@";

    public ExtendedWebView(Context context) {
        //super(context);
        super(context.getApplicationContext());
        enablePlugins(false);
    }

    public ExtendedWebView(Context context, AttributeSet attrs) {
        //super(context, attrs);
        super(context.getApplicationContext(), attrs);
        enablePlugins(false);
    }

    public ExtendedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        enablePlugins(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExtendedWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    /** UTF-8 Charset */
    public static final String CHARSET_UTF8 = "UTF-8";
    /** Path to webview html template */
    public static final String HTML_TEMPLATE_ERROR = "html/template_webview_error.html";
    /** substring where the error message will be placed */
    public static final String HTML_TEMPLATE_CONTENT = "##CONTENT##";


    /**
     * Loads an error page with the <var>error message</var>
     *
     * @param error message
     */
    public void loadErrorPage(String error) {
        try {
            // Load HTML template from assets
            StringBuilder htmlContent = new StringBuilder();
            InputStream is = getContext().getAssets().open(HTML_TEMPLATE_ERROR);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                htmlContent.append(line);
            }
            br.close();

            htmlContent = new StringBuilder(htmlContent.toString().replace(HTML_TEMPLATE_CONTENT, error));

            loadData(htmlContent.toString(), "text/html; charset=" + CHARSET_UTF8, CHARSET_UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // Workaround for problems in Android 4.2.x
        // You can't type properly in input texts and nor textareas until you
        // touch the webview
        // You can't change the font size until you touch the webview
        // http://stackoverflow.com/questions/15127762/webview-fails-to-render-until-touched-android-4-2-2
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            invalidate();
        }
        super.onDraw(canvas);
    }


    @SuppressLint("ObsoleteSdkInt")
    protected void enablePlugins(final boolean enabled) {
        // Android 4.3 and above has no concept of plugin states
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            // Note: this is needed to compile against api level 18.
            try {
                Class<Enum> pluginStateClass = (Class<Enum>) Class
                        .forName("android.webkit.WebSettings$PluginState");

                Class<?>[] parameters = {pluginStateClass};
                Method method = getSettings().getClass()
                        .getDeclaredMethod("setPluginState", parameters);

                Object pluginState = Enum.valueOf(pluginStateClass, enabled ? "ON" : "OFF");
                method.invoke(getSettings(), pluginState);
            } catch (Exception e) {
                Log.i(TAG,"Unable to modify WebView plugin state.");
            }
        } else {
            try {
                Method method = Class.forName("android.webkit.WebSettings")
                        .getDeclaredMethod("setPluginsEnabled", boolean.class);
                method.invoke(getSettings(), enabled);
            } catch (Exception e) {
                Log.i(TAG, "Unable to " + (enabled ? "enable" : "disable")
                        + "WebSettings plugins for BaseWebView.");
            }
        }
    }
}
