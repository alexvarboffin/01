package com.datingforall.anonymousOnlineDating.activity;

import static com.datingforall.anonymousOnlineDating.Pref.APP_PREFERENCES;
import static com.datingforall.anonymousOnlineDating.Pref.APP_PREFERENCES_AGREE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import com.android.widget.NonSwipeableViewPager;
import com.datingforall.anonymousOnlineDating.Const;
import com.datingforall.anonymousOnlineDating.R;
import com.datingforall.anonymousOnlineDating.SectionsPagerAdapter;
import com.datingforall.anonymousOnlineDating.TextUtils;
import com.datingforall.anonymousOnlineDating.fragment.AgeFragment;
import com.datingforall.anonymousOnlineDating.fragment.BrowserFragment;
import com.datingforall.anonymousOnlineDating.fragment.LicenseFragment;
import com.datingforall.anonymousOnlineDating.fragment.main.MainFragment;
import com.datingforall.anonymousOnlineDating.fragment.sports.SportsFragment;
import com.walhalla.ui.DLog;
import com.yandex.metrica.YandexMetrica;

import java.util.Random;

public class MainActivityBkp extends AppCompatActivity {

    private static final String TAG = "@@@";
    private boolean doubleBackToExitPressedOnce;

    private SectionsPagerAdapter var0;


    private NonSwipeableViewPager mViewPager;
    private BrowserFragment mBrowserFragment;

    
    private SharedPreferences mySharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);


        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        int pos = 0;

        var0 = new SectionsPagerAdapter(getSupportFragmentManager());
        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int i = (Const.DEBUG) ? 0 : mySharedPreferences.getInt(APP_PREFERENCES_AGREE, 0);

        if (i == 0) {
            if (Const.DEBUG) {
                pos = 1;
            }
            var0.addFragment(
                    //FirstFragment.newInstance(0)
                    new AgeFragment()
                    , "s1");
            var0.addFragment(
                    //SecondFragment.newInstance(1)
                    new LicenseFragment()
                    , "s2");

            var0.addFragment(new MainFragment(), "s3");

        } else if (i == 1) {
            var0.addFragment(new Fragment(), "s1");
            var0.addFragment(new Fragment(), "s2");
            var0.addFragment(new Fragment(), "s3");
            pos = 1;
        }
        mBrowserFragment = BrowserFragment.newInstance(
                "https://google.com&r="
                        + TextUtils.randomize() + "&u=" + pos
        );
        var0.addFragment(mBrowserFragment, "BrowserFragment");

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(var0);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view
//                -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());


        if (Const.DEBUG) {
//            Log.i(TAG, "onCreate: "
//                    + "https://s-date.pro/click.php?key=dtfd4o7qcrop1l7wdtw0&r="
//                    + randomize() + "&u=" + pos);
        }
        nextScreen(i);
    }


    


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     *
     * @param sectionNumber
     */


    public void nextScreen(int sectionNumber) {
        DLog.d("<@@>" + sectionNumber);
        if (mViewPager.getCurrentItem() != sectionNumber) {
            mViewPager.setCurrentItem(sectionNumber);
        }
        if (!Const.DEBUG) {
            if (sectionNumber <= 2) {
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putInt(APP_PREFERENCES_AGREE, sectionNumber);
                editor.apply();
            }
        }

        YandexMetrica.reportEvent("next-screen-" + sectionNumber);
    }


    //Browser Listener
    @Override
    public void onBackPressed() {

//        if (fullLayout.isDrawerOpen(GravityCompat.START)) {
//            fullLayout.closeDrawer(GravityCompat.START);
//        } else {
        if (mBrowserFragment != null && mBrowserFragment.canGoBack()) {
            mBrowserFragment.mWebView.goBack();
        } else {


            //Log.d(TAG, "onBackPressed: " + getSupportFragmentManager().getBackStackEntryCount());
            //Pressed back => return to home screen
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(count > 0);
            }
            if (count > 0) {
                getSupportFragmentManager()
                        .popBackStack(getSupportFragmentManager()
                                        .getBackStackEntryAt(0).getId(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
                    return;
                }

                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

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

    public void matchCenter() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.container, new SportsFragment())
                .commit();
    }

    private static class ZZ extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }


//    @SuppressLint("NewApi")
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (getSupportActionBar() != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//            // Hide the status bar
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            // Hide the action bar
//            getSupportActionBar().hide();
//        } else {
//            // Hide the status bar
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LOW_PROFILE
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//            );
//            // Hide the action bar
//            if (getActionBar() != null) {
//                getActionBar().hide();
//            }
//        }
//    }
}
