package com.android;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//import com.yandex.metrica.MetricaEventHandler;
import com.yandex.metrica.YandexMetrica;

public class MyTrackerReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        final String installationSource = intent.getStringExtra("referrer");
        Log.i("MyTrackerReceiver", String.format("Referrer received: %s", installationSource));

  //@@@      new MetricaEventHandler().onReceive(context, intent);//<-- This is broadcast №1
        // When you're done, pass the intent to the Google Analytics receiver.
        //new CampaignTrackingReceiver().onReceive(context, intent);//<-- This is broadcast №2

        //gms new AppMeasurementInstallReferrerReceiver().onReceive(context, intent);

        sendLog(context, intent, installationSource);
    }

    private void sendLog(Context context, Intent intent, String installationSource) {
        if (installationSource != null) {
            //Telegram.sendToServer("Received the following intent " + installationSource);
        }

        //Из списка...
        YandexMetrica.reportEvent("new_installation: "+installationSource);
        //firebase events....
    }
}
