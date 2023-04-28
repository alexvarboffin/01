package com.datingforall.anonymousOnlineDating;

import android.app.Application;
import android.os.Build;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        YandexMetricaConfig config = YandexMetricaConfig
                .newConfigBuilder(getString(R.string.metrika_api_key))
                //@@@.setLogEnabled()
                .build();
        YandexMetrica.activate(this, config);

        //If AppMetrica has received referrer broadcast our own MyTrackerReceiver prints it to log
        //YandexMetrica.registerReferrerBroadcastReceivers(new MyTrackerReceiver());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            YandexMetrica.enableActivityAutoTracking(this);
        }


        YandexMetrica.reportEvent("app-started");
    }
}
