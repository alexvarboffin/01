package com.walhalla.sportlib.activity;

import static com.android.lib.CustomTabsBroadcastReceiver.ACTION_COPY_URL;

import static org.apache.mvp.presenter.MainPresenter.NONENONE;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.ExtendedWebView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsService;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.lib.CustomTabsBroadcastReceiver;
import com.android.lib.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.my.tracker.MyTracker;
import com.walhalla.sportlib.AboutBox;
import com.walhalla.sportlib.R;
import com.walhalla.sportlib.view.SportMainView;
import com.walhalla.ui.DLog;
import com.walhalla.ui.Module_U;

import org.apache.P;
import org.apache.Utils;
import org.apache.cordova.ChromeView;
import org.apache.cordova.GConfig;
import org.apache.cordova.ScreenType;
import org.apache.cordova.domen.BodyClass;
import org.apache.cordova.domen.Dataset;
import org.apache.cordova.enumer.UrlSaver;
import org.apache.cordova.fragment.WebFragment;
import org.apache.cordova.repository.AbstractDatasetRepository;
import org.apache.cordova.repository.impl.FirebaseRepository;
import org.apache.cordova.v70.app.MyJavascriptInterface;
import org.apache.mvp.presenter.MainPresenter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseAbstractActivity
        extends AppCompatActivity
        implements org.apache.cordova.fragment.WebFragment.Lecallback,
        SportMainView, SwipeRefreshLayout.OnRefreshListener,
        org.apache.mvp.MainView {

    private boolean doubleBackToExitPressedOnce;
    protected GConfig aaa;

    //Views//@@@ addFragment(R.id.container, F_PagerContainer.newInstance("1", "2"));

    public SwipeRefreshLayout swipeRefreshLayout;
    protected ExtendedWebView __mView;
    protected RelativeLayout main;
    protected FrameLayout clazz1;
    protected ProgressBar pb;


    private boolean rotated0 = false;

    private MainPresenter presenter;
    private Navigation navigation;
    private DrawerLayout drawerLayout;
    protected Toolbar toolbar;

    protected ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private Thread mThread;
    private Handler mHandler;
    private NavigationView navigationView;


    @Override
    public void hiDeRefreshLayout() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private static final int PURCHASE_REQUEST_CODE = 1001;
    private CustomTabsIntent customTabsIntent;


    private static final boolean unlocked = true; //Unlocked Offer www btn
    private AlertDialog dialog;

    public void trackLevel(View ignored) {
        //{} MyTracker.trackLevelEvent();
        Toast.makeText(this, "Tracking level", Toast.LENGTH_SHORT).show();
    }

    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleDepplink(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PURCHASE_REQUEST_CODE == requestCode) {
            //{} MyTracker.onActivityResult(resultCode, data);
        }
    }


    @SuppressLint({"WakelockTimeout", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        mHandler = new Handler(Looper.getMainLooper());
        //mHandler = new Handler();

        aaa = GCfc();
        navigation = new Navigation(this);
        presenter = new MainPresenter(this, this, aaa, mHandler, makeTracker());


        clazz1 = findViewById(R.id.browser);
        main = findViewById(R.id.main);
        pb = findViewById(R.id.progressBar1);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (aaa.TOOLBAR_ENABLED) {
//            toolbar.setSubtitle(Util.getAppVersion(this));
//            toolbar.setVisibility(View.VISIBLE);
        }
        drawer();
        DLog.d("ON_CREATE: " + this.getClass().getSimpleName() + (toolbar == null));
        if (!rotated()) {
            presenter.init(this);
        }
        //Generate Dynamic Gui
        onViewCreated(clazz1, this);

//        Uri targetUrl =
//                AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
//        if (targetUrl != null) {
//            Log.i(TAG, "App Link Target URL: " + targetUrl.toString());
//        }

//                if (rawUrl != null) {
//
//                    HttpPostRequest getRequest = new HttpPostRequest();
//                    try {
//                        String device_id = Util.phoneId(BaseActivity.this.getApplicationContext());
//
//                        //Replace deep to tracker
//                        URI uri = new URI(getString(R.string.app_url));
//
//                        String tracker =
//                                rawUrl.replace(getString(R.string.app_scheme), uri.getScheme())
//                                        .replace(getString(R.string.app_host), uri.getHost())
//                                        .replace("%26", "&")
//                                        .replace("%3D", "=")
//                                        + "&id=" + device_id;
//
////                        if (BuildConfig.DEBUG) {
////                            Log.i(TAG, "URL: " + rawUrl);
////                            Log.i(TAG, "TR: " + uri.getScheme() + "|" + uri.getHost());
////                            Log.i(TAG, "--->: " + tracker);
////
////                            //Only for Google Chrome test
////                            //
////
////                            Log.i(TAG, "CHROME: "
////                                    + ("intent://"
////                                    + getString(R.string.app_host)
////                                    + "/#Intent;scheme="
////                                    + getString(R.string.app_scheme) + ";package=" + getPackageName())
////                                    //        .replace(";",";")
////                                    + ";end"
////                            );
////                        }
//
//                        LocalStorage storage = LocalStorage.getInstance(BaseActivity.this);
//                        JSONObject parent = new JSONObject();
//                        try {
//                            parent.put("dl", rawUrl);
//                            parent.put("ref", storage.referer());
//                            getRequest.execute(parent).get();
//                        } catch (JSONException e) {
//                            DLog.handleException(e);
//                        }
//                    } catch (ExecutionException e) {
//                        DLog.handleException(e);
//                    } catch (InterruptedException e) {
//                        DLog.handleException(e);
//                    } catch (URISyntaxException e) {
//                        DLog.handleException(e);
//                    }
//                }

//        String device_id = Util.phoneId(MainActivity.getAppContext().getApplicationContext());
//        launch(webview_url + "?id=" + device_id);

//###        if (savedInstanceState != null) {
//###            return;
//###        }

        if (checkUpdate()) {
            if (toolbar != null) {
                toolbar.post(() -> Module_U.checkUpdate(this));
            }
        }


//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view ->
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).
//                        setAction("Action", null).
//
//                        show());

//        loan = new ViewModelProvider(this).get(LoanViewModel.class);
//        loan.getLoan().observe(this, new Observer<Loan>() {
//            @Override
//            public void onChanged(Loan s) {
//                DLog.d("#####################-->" + loan.toString());
//            }
//        });

//        loadUrl(this.launchUrl);


        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        navigation = new Navigation(this);

        /**
         * PagerContainerFragment
         * CategoryListFragment
         * CategoryListFragment
         * CategoryListFragment
         */
        //Home Screen
        //addFragment(R.id.container, CategoryListFragment.newInstance(0, null));

        //addFragment(R.id.container, SimpleListFragment.newInstance());

//        throw new RuntimeException("wtf");
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        filter.addAction(MyFirebaseJobService.ACTION_SHOW_MESSAGE);
//        registerReceiver(br, filter);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "myapp:htracker");
        wakeLock.acquire();

        //{} MyTracker.getTrackerConfig().setTrackingEnvironmentEnabled(true);
        handleDepplink(getIntent());

        Module_U.anomaly(this);

        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            //addFragment(R.id.container, CategoryListFragment.newInstance());
            //getSupportFragmentManager().beginTransaction().add(R.id.container, new ScreenRSSList()).commit();

            if (navigationView.getMenu().size() > 0) {
                MenuItem item = navigationView.getMenu().getItem(0);
                if (item.hasSubMenu()) {
                    onNavigationItemSelected(item.getSubMenu().getItem(0));
                } else {
                    onNavigationItemSelected(item);
                }
            }

        }
    }

//    private void loadHomeFragment(String currentTag, boolean clearStack) {
//        DLog.d("--> " + currentTag);
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        // selecting appropriate nav menu item
//        //@@@ selectNavMenu();
//
//        // set toolbar title
//        //setToolbarTitle();
//
//        // if user select the current navigation menu again, don't do anything
//        // just close the navigation drawer
//        Fragment aa = getSupportFragmentManager().findFragmentByTag(currentTag);
//        if (aa != null && aa.isVisible()) {
//            drawer.closeDrawers();
//
//            // show or hide the fab button
//            //@@@ toggleFab();
//            return;
//        }
//
//        // show or hide the fab button
//        //@@@ toggleFab();
//        //Closing drawer on item click
//        drawer.closeDrawers();
//
//        // refresh toolbar menu
//        invalidateOptionsMenu();
//
//        // Sometimes, when fragment has huge data, screen seems hanging
//        // when switching between navigation menus
//        // So using runnable, the fragment is loaded with cross fade effect
//        // This effect can be seen in GMail app
////        Runnable mPendingRunnable = new Runnable() {
////            @Override
////            public void run() {
////
////                try {
////                    TimeUnit.MILLISECONDS.sleep(7000);
////                    // update the main content by replacing fragments
////                    Fragment fragment = getHomeFragment();
////                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
////                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
////                            android.R.anim.fade_out);
////                    fragmentTransaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
////                    fragmentTransaction.commitAllowingStateLoss();
////
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////            }
////        };
////
////        // If mPendingRunnable is not null, then add to the message queue
////        if (mPendingRunnable != null) {
////            mHandler.post(mPendingRunnable);
////        }
//
//        mThread = new Thread(() -> {
//            try {
//                TimeUnit.MILLISECONDS.sleep(400);
//                mHandler.post(() -> {
//
//                    //Clear back stack
//                    //final int count = getSupportFragmentManager().getBackStackEntryCount();
//                    if (clearStack) {
//                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    }
//
//                    // update the main content by replacing fragments
//                    Fragment fragment = NavHelper.getHomeFragment(currentTag);
//                    FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
//                    fr.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//          //@@@          fr.replace(R.id.container, fragment, CURRENT_TAG);
//
//
////                    fr.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
////                    fr.replace(R.id.container, fragment);
////                    fr.addToBackStack(null);
//                    //fr._NOT_USE_commit();
//                    fr.commitAllowingStateLoss();
//                });
//                //mThread.interrupt();
//            } catch (InterruptedException e) {
////                Toast.makeText(this, e.getLocalizedMessage()
////                        + " - " + mThread.getName(), Toast.LENGTH_SHORT).show();
//            }
//        }, "my-threader");
//
//
//        if (!mThread.isAlive()) {
//            try {
//                mThread.start();
//            } catch (Exception r) {
//                DLog.handleException(r);
//            }
//        }
//    }

    protected void onViewCreated(ViewGroup view, Context context) {
        //mWebView = privacy.findViewById(R.id.web_view);
        //swipeRefreshLayout = privacy.findViewById(R.id.refresh);
        swipeRefreshLayout = new SwipeRefreshLayout(context);
        __mView = new ExtendedWebView(new ContextThemeWrapper(context, R.style.AppTheme));
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipeRefreshLayout.setLayoutParams(lp);
        __mView.setLayoutParams(lp);
        view.addView(swipeRefreshLayout);
        swipeRefreshLayout.addView(__mView);
        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener = () -> {
            swipeRefreshLayout.setEnabled(__mView.getScrollY() == 0);
        });

        swipeRefreshLayout.setOnRefreshListener(this);

        //mWebView.setVisibility(View.INVISIBLE);
        //webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);

//        JavaScriptInterface jsInterface = new JavaScriptInterface(this);
//        __mView.addJavascriptInterface(jsInterface, "JSInterface");

        Utils.a123(new ChromeView() {

            @Override
            public void webClientError(int errorCode, String description, String failingUrl) {
                makeScreen(new Dataset(ScreenType.WEB_VIEW, MainPresenter.NONENONE));
                if (toolbar != null) {
                    toolbar.setSubtitle("");
                }
            }

            @Override
            public void mAcceptPressed(String url) {
            }

            @Override
            public void eventRequest(BodyClass bodyClass) {
                presenter.event(bodyClass);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String title = view.getTitle();
                if (webTitle() && !TextUtils.isEmpty(title) && toolbar != null) {
                    if (view.getUrl() != null && title.startsWith(view.getUrl())) {
                        toolbar.setSubtitle(title);
                    }
                }
                pb.setVisibility(View.GONE);

                //        ShowOrHideWebViewInitialUse = "hide";
                //        privacy.setVisibility(View.VISIBLE);

                __mView.setVisibility(View.VISIBLE);
                main = findViewById(R.id.main);
                if (main != null) {
                    main.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageStarted() {
                //@@@
                if (aaa.PROGRESSBAR_ENABLED) {
                    pb.setVisibility(View.VISIBLE);
                }
            }
        }, __mView);


        __mView.addJavascriptInterface(new MyJavascriptInterface(BaseAbstractActivity.this, __mView), "Client");
    }


    //Fabric.with(this, new Crashlytics()); //crashReport
    //setContentView(R.layout.activity_main);

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
//        if (itemId == android.R.id.home) {
//            onBackPressed();
//            return true;
//        } else

        if (itemId == R.id.action_about) {
            Module_U.aboutDialog(this);
            return true;
        } else if (itemId == R.id.action_privacy_policy) {
            Module_U.openBrowser(this, getString(R.string.url_privacy_policy));
            return true;
//            case R.id.action_rate_app:
//                Module_U.rateUs(this);
//                return true;
        } else if (itemId == R.id.action_share_app) {
            Module_U.shareThisApp(this);
            return true;
        } else if (itemId == R.id.action_privacy_policy_credit) {
            AboutBox.showPolicy(this);
            return true;
        } else if (itemId == R.id.action_discover_more_app) {
            Module_U.moreApp(this);
            return true;
//            case R.id.action_exit:
//                this.finish();
//                return true;
        } else if (itemId == R.id.action_feedback) {
            Module_U.feedback(this);
            return true;
        } else if (itemId == R.id.action_terms) {
            startActivity(new Intent(this, TermsActivity.class));
            return true;

//            case R.id.action_more_app_01:
//                Module_U.moreApp(this, "com.walhalla.ttloader");
//                return true;

//            case R.id.action_more_app_02:
//                Module_U.moreApp(this, "com.walhalla.vibro");
//                return true;
        }
        return super.onOptionsItemSelected(item);

        // User didn't trigger a refresh, let the superclass handle this action
        //return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
//            case R.id.action_refresh:
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    private JobBroadcastReceiver br = new JobBroadcastReceiver();
//
//    private class JobBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            StringBuilder sb = new StringBuilder();
//            sb.append("Action: " + intent.getAction() + "\n");
//            sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
//            String log = sb.toString();
//            Log.d(TAG, log);
//            Toast.makeText(context, log, Toast.LENGTH_LONG).show();
//
//        }
//    }


    @Override
    public Dataset data() {
        return null;// Payload
    }

    /**
     * If true активирует Web
     */
    public void makeScreen(Dataset screen) {

//        if (1 == 1) {
//            replaceFragment(WebFragment.newInstance(
//                    "https://kzmoney.web.app/cookies.html", aaa));
//            return;
//        }
//        DLog.d("dsds" + screen.toString());

        if (screen.screenType == ScreenType.WEB_VIEW) {
            //Toast.makeText(this, ""+screen.toString(), Toast.LENGTH_LONG).show();

            clazz1.setVisibility(View.GONE);
            main.setVisibility(View.VISIBLE);
            //mWebView.setVisibility((web) ? View.VISIBLE : View.GONE);
            pb.setVisibility(View.GONE);
            externalSetter(screen);
            if (enableHomeWebView()) {
                replaceFragment(WebFragment.newInstance(screen.url, aaa));
            }

        } else {
            DLog.d("[screen]: " + screen.screenType + "\t" + screen.getUrl());

            if (screen.screenType != ScreenType.WEB_VIEW) {
                if (orientation404() != null && this.getRequestedOrientation() != orientation404()) {
                    this.setRequestedOrientation(orientation404());
                }
            } else {
                if (orientationWeb() != null && this.getRequestedOrientation() != orientationWeb()) {
                    this.setRequestedOrientation(orientationWeb());
                }
                launch(screen.getUrl());
            }
            Utils.hideKeyboard(this);
            boolean web = screen.screenType == ScreenType.WEB_VIEW;
            clazz1.setVisibility((web) ? View.VISIBLE : View.GONE);
            main.setVisibility((web) ? View.GONE : View.VISIBLE);
            //mWebView.setVisibility((web) ? View.VISIBLE : View.GONE);
            pb.setVisibility((web) && aaa.PROGRESSBAR_ENABLED ? View.VISIBLE : View.GONE);
            externalSetter(screen);
        }
    }


    protected void externalSetter(Dataset screen) {
        //unlocked = true; //screen.isEnabled(); NOT USE
        webview_external = screen.webview_external;
        DLog.d("@EX@" + webview_external);
    }

    protected abstract boolean enableHomeWebView();

    @Override
    public boolean blockEmulators() {
        return false;
    }

    @Override
    public Integer orientation404() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public Integer orientationWeb() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public boolean checkUpdate() {
        return false;
    }

    @Override
    public boolean webTitle() {
        return false;
    }

    @Override
    public boolean handleDeepLink() {
        return false;
    }

    @Override
    public void PEREHOD_S_DEEPLINKOM() {

    }

    @Override
    public AbstractDatasetRepository makeTracker() {
        return new FirebaseRepository(this);
    }


    public GConfig GCfc() {
        return new GConfig(true, false, false, UrlSaver.NONE);
    }


    public boolean isWebViewExternal() {
        DLog.d("@@@@@@@@" + webview_external);
        return webview_external;
    }

    protected static boolean webview_external;


    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(getIntent());

        customTabsIntent = customWeb();
        AboutBox.privacy_dialog_request(this);

//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String muted = sharedPreferences.getString(KEY_MUTED, null);
//        if (muted == null) {
//            if (BuildConfig.DEBUG) {
//                //Toast.makeText(this, "Application first launch", Toast.LENGTH_SHORT).show();
//            }
//            //DLog.d(new GsonBuilder().create().toJson(new SystemEnvironment()));
//            Thread t = new Thread(() -> {
//                try {
//                    String am = new WebView(this).getSettings().getUserAgentString();
//                    SystemEnvironment me = new SystemEnvironment(am);
//                    DatabaseReference reference = FirebaseDatabase.getInstance()
//                            .getReference("users").child(me.GUID);
//                    reference.setValue(me);
//
//                } catch (Exception e) {
//                    DLog.handleException(e);
//                }
//            });
//            t.start();
//        }
    }


//    private void privacy_dialog_request(Context context) {
////        dialog = new AlertDialog.Builder(context)
////                .setTitle(R.string.app_name)
////                .setMessage(spannable)
////                .setIcon(R.mipmap.ic_launcher)
////                .setCancelable(false)
////                .setNegativeButton("No",
////                        (dialog, id) -> {
////                            dialog.cancel();
////                            finish();
////                        })
////                .setPositiveButton(R.string.action_continue,
////                        (dialog, id) -> dialog.cancel())
////                .create();
////
////        TextView aa = ((TextView) dialog.findViewById(android.R.id.message));
////        if (aa != null) {
////            aa.setMovementMethod(LinkMovementMethod.getInstance());
////            aa.setHighlightColor(Color.BLACK);// background color when pressed
////            aa.setLinkTextColor(Color.RED);
////        }
////        if (dialog != null && !dialog.isShowing()) {
////            dialog.show();
////        }
//    }


    public void replaceFragment(Fragment fragment) {
        navigation.replaceFragment(fragment, R.id.container);
    }

    @Override
    public void setActionBarTitle(String title) {

    }

    @Override
    public boolean isUnlocked() {
        DLog.d("hidden functionality unlocked? -->" + unlocked + " " + webview_external);
        return unlocked;
    }


    private void handleDepplink(Intent intent) {
        //Toast.makeText(this, "Handling deeplink", Toast.LENGTH_SHORT).show();
        String deeplink = MyTracker.handleDeeplink(intent);
        if (deeplink != null && !deeplink.isEmpty()) {
            return;
        }
        //Toast.makeText(this, "Deeplink tracked: " + deeplink, Toast.LENGTH_SHORT).show();
    }

    public void trackLogin(View ignored) {
        // you can add custom params you want to track to all events
        // can be omitted or null
        Map<String, String> eventCustomParams = new HashMap<>();
        eventCustomParams.put("someParamKey", "someParamValue");

        //{} MyTracker.trackLoginEvent("custom_user_id", "vk_connect_id", eventCustomParams);
        Toast.makeText(this, "Tracking login", Toast.LENGTH_SHORT).show();
    }

    public void trackInvite(View ignored) {
        //{} MyTracker.trackInviteEvent();
        Toast.makeText(this, "Tracking invite", Toast.LENGTH_SHORT).show();
    }

    public void trackRegistration(View ignored) {
        //{} MyTracker.trackRegistrationEvent("custom_user_id", "vk_connect_id");
        Toast.makeText(this, "Tracking registration", Toast.LENGTH_SHORT).show();
    }


    public void trackPurchase(View ignored) {
        // Create buy bundle
        // Bundle buyIntentBundle = callMethodToReceiveBundle();
        // Extracting pending intent from bundle
        // PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");
        // Starting intent sender
        // startIntentSenderForResult(pendingIntent.getIntentSender(),
        //							  PURCHASE_REQUEST_CODE,
        //							  new Intent(),
        //							  0,
        //							  0,
        //							  0);
    }

    //"https://kzmoney.web.app/cookies.html"

//    @Override
//    public void openOfferRequest(Category ka) {
//        if (isUnlocked() || ka.detail == null) {
//            openBrowser(ka);
//            //"EVENT_EXTERNAL"
//        } else {
//            replaceFragment(OfferDescriptionFragment.newInstance(-1, ka));
//            //"EVENT_INTERNAL"
//        }
//
////        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
////        Bundle bundle = new Bundle();
////        bundle.putString("WEB_OPEN", "" + isUnlocked());
////        mFirebaseAnalytics.logEvent("in_web_open_click", bundle);
//
//        offerPresetEvent(ka);
//        trackClickOffer(ka);
//    }

//    public void trackClickOffer(Category category) {
//        WebView mView = new WebView(this);
//        String tmp = mView.getSettings().getUserAgentString();
//        tmp = tmp.replace("; wv)", ")");
//
//        Map<String, String> params = new HashMap<>();
//        params.put("user_agent", tmp);
//        params.put("model", Build.MODEL);
//        params.put("board", Build.BOARD);
//        params.put(FirebaseAnalytics.Param.ITEM_ID, "[*]" + category._id);
//        params.put(FirebaseAnalytics.Param.ITEM_NAME, "[*]" + category.name);
//        params.put(FirebaseAnalytics.Param.CONTENT_TYPE, "CREDIT_OFFER");
//        //{} MyTracker.trackEvent("click_offer", params);
//    }

//    private void offerPresetEvent(Category category) {
////        Bundle bundle = new Bundle();
////        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, /*id*/"id");
////        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, /**/"name");
////        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
////        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//
////        Bundle bundle = new Bundle();
////        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(0));
////        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "init");
////        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
////        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//
//        Bundle params = new Bundle();
//        params.putString(FirebaseAnalytics.Param.ITEM_ID, "[*]" + category._id);
//        params.putString(FirebaseAnalytics.Param.ITEM_NAME, "[*]" + category.name);
//        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "CREDIT_OFFER");
//        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
//    }


//    public void openBrowser(Category category) {
//        if (isWebViewExternal()) {
//            String url = category.url;
//            if (!url.startsWith("http://") && !url.startsWith("https://")) {
//                url = "http://" + url;
//            }
//            DLog.d("@" + category.url);
//            ArrayList<ResolveInfo> list = getCustomTabsPackages(this);
//            //for (ResolveInfo info : list) {
//            //24 ResolveInfo{5f59c04 com.android.chrome/com.google.android.apps.chrome.Main m=0x208000}
//            //   ResolveInfo{a8a75dc com.android.chrome/com.google.android.apps.chrome.IntentDispatcher m=0x208000}
//            //DLog.d("@" + info.toString());
//            //}
//            if (list.size() > 0) {
//                customTabsIntent.launchUrl(this, Uri.parse(url));
//            } else {
//                Helpers.openExternalBrowser(this, category);
//            }
//        } else {
//            replaceFragment(WebFragment.newInstance(category.url, aaa));
//        }
//    }

    private CustomTabsIntent customWeb() {
        //Configure the color of the address bar
        CustomTabColorSchemeParams defaultColors = new CustomTabColorSchemeParams.Builder().setToolbarColor(getResources().getColor(R.color.colorPrimaryDark))
                //.setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark))
                .setNavigationBarDividerColor(Color.BLACK).setSecondaryToolbarColor(Color.BLACK).build();

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setDefaultColorSchemeParams(defaultColors);

        //Configure a custom action button
        //24dp
        //@PandingIntent pandingIntent = new PendingI
        //@builder.setActionButton(icon, description, pendingIntent, tint);

        //Configure a custom menu
        //builder.addMenuItem(menuItemTitle, menuItemPendingIntent);

        //Android 12
        final int flag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE : PendingIntent.FLAG_UPDATE_CURRENT;


        Intent intent = new Intent(this, CustomTabsBroadcastReceiver.class);
        intent.setAction(CustomTabsBroadcastReceiver.ACTION_COPY_URL);
        String label = "Copy link";
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, flag);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            //PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
//            pendingIntent = PendingIntent.getBroadcast(
//                    this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        } else {
//            //PendingIntent.FLAG_UPDATE_CURRENT
//            pendingIntent = PendingIntent.getBroadcast(
//                    this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        }
        builder.addMenuItem(label, pendingIntent).build();
//        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle bundle = new Bundle();
//        bundle.putString("goz_ad_click", "1");
//        mFirebaseAnalytics.logEvent("in_offer_open_click", bundle);
        return builder.build();
    }

    public static ArrayList<ResolveInfo> getCustomTabsPackages(Context context) {
        PackageManager pm = context.getPackageManager();
        // Get default VIEW intent handler.
        Intent activityIntent = new Intent().setAction(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE).setData(Uri.fromParts("http", "", null));

        // Get all apps that can handle VIEW intents.
        List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
        ArrayList<ResolveInfo> packagesSupportingCustomTabs = new ArrayList<>();
        for (ResolveInfo info : resolvedActivityList) {
            Intent serviceIntent = new Intent();
            serviceIntent.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
            serviceIntent.setPackage(info.activityInfo.packageName);
            // Check if this package also resolves the Custom Tabs service.
            if (pm.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info);
            }
        }
        return packagesSupportingCustomTabs;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        if (__mView.canGoBack()) {
            __mView.goBack();
        } else {


            //Log.d(TAG, "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());


            //Pressed back => return to home screen
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(count > 0);
            }
            if (count > 0) {
                getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } else {//count == 0

//                Dialog
//                new AlertDialog.Builder(this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Leaving this App?")
//                        .setMessage("Are you sure you want to close this application?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                            }
//
//                        })
//                        .setNegativeButton("No", null)
//                        .show();
                //super.onBackPressed();


                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    try {
                        this.finish();
                    } catch (Exception ignored) {
                    }
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 800);

            }


            /*
            //Next/Prev Navigation
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Leaving this App?")
                        .setMessage("Are you sure you want to close this application?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            else
            {
            super.onBackPressed();
            }
            */

        }
    }

    public void launch(String url) {
        DLog.d("00000000000000000000000000000000000000000");
        __mView.post(() -> __mView.loadUrl(url));
    }


    @Override
    public void dappend(String var0) {
        DLog.d("@@@@@@@@@@@" + var0 + "\n");
    }

    protected void actionRefresh() {
        String url = __mView.getUrl();
        if (url != null) {
            __mView.reload();
            //getContent(url);
            Snackbar.make(getWindow().getDecorView(), /*url*/"Data updated.", Snackbar.LENGTH_SHORT).setAction(android.R.string.ok, null).show();
        }
    }

    private void drawer() {
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(item -> {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            onNavigationItemSelected(item);
            DrawerLayout drawer0 = findViewById(R.id.drawer_layout);
            drawer0.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void onNavigationItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.nav_send) {
//            Intent i = new Intent(this, FeedPreferancesActivity.class);
//            startActivity(i);
//            return;
//        }
        Fragment fragment;
        Class<? extends Fragment> fragmentClass = null;
        int itemId = item.getItemId();
//        if (itemId == R.id.nav_rss_news_main) {
//            fragmentClass = ScreenRSSList.class;
//        } else
//            if (itemId == R.id.nav_gallery) {
//            fragmentClass = F_PagerContainer.class;
//        } else {
//            fragmentClass = FragmentFeedList.class;
//        }
        try {
            fragment = fragmentClass.newInstance();
            setTitle(item.getTitle());
            navigation.replaceFragment(fragment, R.id.container);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void wrapContentRequest() {
    }


    @Override
    public void onRefresh() {
        __mView.reload();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean rotated() {
        return rotated0;
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        outState.putBoolean(P.KEY_ROTATED, rotated0);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rotated0 = savedInstanceState.getBoolean(P.KEY_ROTATED, false);
    }

    @Override
    protected void onStop() {
        swipeRefreshLayout.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    //@Override
    public void fullNews(String link, String title) {
//        ScreenFullNews fullNews = ScreenFullNews.newInstance(link, title);
//        navigation.replaceFragment(fullNews, R.id.container);
    }
}
