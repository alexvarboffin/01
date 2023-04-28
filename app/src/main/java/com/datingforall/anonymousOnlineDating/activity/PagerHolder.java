package com.datingforall.anonymousOnlineDating.activity;

import static com.datingforall.anonymousOnlineDating.Pref.APP_PREFERENCES_AGREE;
import static com.datingforall.anonymousOnlineDating.TextUtils.randomize;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.android.widget.NonSwipeableViewPager;
import com.datingforall.anonymousOnlineDating.Const;
import com.datingforall.anonymousOnlineDating.Pref;
import com.datingforall.anonymousOnlineDating.R;
import com.datingforall.anonymousOnlineDating.SectionsPagerAdapter;
import com.datingforall.anonymousOnlineDating.fragment.AgeFragment;
import com.datingforall.anonymousOnlineDating.fragment.BaseFragment;
import com.datingforall.anonymousOnlineDating.fragment.BrowserFragment;
import com.datingforall.anonymousOnlineDating.fragment.LicenseFragment;
import com.datingforall.anonymousOnlineDating.fragment.main.MainFragment;
import com.walhalla.ui.DLog;
import com.yandex.metrica.YandexMetrica;

public class PagerHolder extends BaseFragment {
    private NonSwipeableViewPager mViewPager;
    private SectionsPagerAdapter var0;
    private SharedPreferences mySharedPreferences;
    private BrowserFragment mBrowserFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        int pos = 0;
        var0 = new SectionsPagerAdapter(getChildFragmentManager());
        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int i = (Const.DEBUG) ? 0 : mySharedPreferences.getInt(Pref.APP_PREFERENCES_AGREE, 0);

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
                        + randomize() + "&u=" + pos
        );
        var0.addFragment(mBrowserFragment, "BrowserFragment");

        mViewPager = view.findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(var0);

        if (Const.DEBUG) {
//            Log.i(TAG, "onCreate: "
//                    + "https://s-date.pro/click.php?key=dtfd4o7qcrop1l7wdtw0&r="
//                    + randomize() + "&u=" + pos);
        }
        nextScreen(i);
    }

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
}
