package com.walhalla.sportlib;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;


public class AboutBox {

    private static final String FLAG11 = "privacy_enabled";


    //    public static void Show(Activity activity) {
//        //Use a Spannable to allow for links highlighting
//        SpannableString aboutText = new SpannableString("Version " + DLog.getAppVersion(activity)
//                + String.valueOf(new char[]{(char) 10, (char) 10})
//                + activity.getString(R.string.security_text_full, activity.getString(R.string.publisher_feedback_email)));
//        View about;
//        TextView tvAbout;
//        try {
//            //Inflate the custom view
//            LayoutInflater inflater = activity.getLayoutInflater();
//            about = inflater.inflate(R.layout.dialog_about, activity.findViewById(R.id.aboutView));
//            tvAbout = about.findViewById(R.id.aboutText);
//        } catch (InflateException e) {
//            //Inflater can throw exception, unlikely but default to TextView if it occurs
//            about = tvAbout = new TextView(activity);
//        }
//        //Set the about text
//        tvAbout.setText(aboutText);
//        // Now Linkify the text
//        Linkify.addLinks(tvAbout, Linkify.ALL);
//        //Build and show the dialog
//        new AlertDialog.Builder(activity, R.style.AlertDialogTheme)
//                .setTitle("About " + activity.getString(R.string.app_name))
//                .setCancelable(true)
//                .setIcon(R.mipmap.ic_launcher)
//                .setPositiveButton(android.R.string.ok, null)
//                .setView(about)
//                .show();    //Builder method returns allow for method chaining
//    }

    public static void privacy_dialog_request(Activity context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean flag = preferences.getBoolean(FLAG11, false);
        if (!flag) {
            AboutBox.Show(context);
        }
    }

    public static void Show(Activity context) {
        int prim = context.getResources().getColor(R.color.colorPrimaryDark);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                AboutBox.showPolicy(context);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

//        //Use a Spannable to allow for links highlighting
//        SpannableString aboutText = new SpannableString("Version " + DLog.getAppVersion(context)
//                + String.valueOf(new char[]{(char) 10, (char) 10})
//                + context.getString(R.string.security_text_full,
//                " App privacy policy."));
        String _full = String.format(context.getString(R.string.security_text_full),
                context.getString(R.string.app_name));
        int start_index = _full.length();
        _full = _full + " App privacy policy.";
        int end_index = _full.length();

        Spannable spannable = new SpannableString(_full);
        spannable.setSpan(new UnderlineSpan(),/* start index */ start_index, /* end index */ end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //spannable.setSpan(new ForegroundColorSpan(..), start_index, end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //spannable.setSpan(new StyleSpan(BOLD), start_index, end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(clickableSpan, start_index, end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        View about;
        TextView tvAbout;
        try {
            //Inflate the custom view
            LayoutInflater inflater = context.getLayoutInflater();
            about = inflater.inflate(R.layout.dialog_about, context.findViewById(R.id.aboutView));
            tvAbout = about.findViewById(R.id.aboutText);
        } catch (InflateException e) {
            //Inflater can throw exception, unlikely but default to TextView if it occurs
            about = tvAbout = new TextView(context);
        }
        tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
        //tvAbout.setHighlightColor(Color.BLACK);// background color when pressed
        tvAbout.setLinkTextColor(prim);
        //Set the about text
        tvAbout.setText(spannable, TextView.BufferType.SPANNABLE);
        // Now Linkify the text
        //Linkify.addLinks(tvAbout, Linkify.ALL);
        //Build and show the dialog
        new AlertDialog.Builder(context, R.style.AlertDialogTheme)
                .setTitle("About " + context.getString(R.string.app_name))
                .setCancelable(true)
                .setIcon(R.mipmap.ic_launcher)
                //.setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(R.string.action_no,
                        (dialog, id) -> {
                            dialog.cancel();
                            context.finish();
                        })
                .setPositiveButton(R.string.action_continue,
                        (dialog, id) -> {
                            dialog.cancel();

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            preferences.edit().putBoolean(FLAG11, true).apply();

                        })
                .setView(about)
                .show();    //Builder method returns allow for method chaining
    }

    public static void showPolicy(Activity activity) {

//        //Use a Spannable to allow for links highlighting
//        SpannableString aboutText = new SpannableString("Version " + DLog.getAppVersion(activity)
//                + String.valueOf(new char[]{(char) 10, (char) 10})
//                + activity.getString(R.string.security_text_full,
//                " App privacy policy."));
        String _full = String.format(activity.getString(R.string.policy_text),
                activity.getString(R.string.app_name),
                activity.getString(R.string.publisher_feedback_email)
        );

        //spannable.setSpan(new UnderlineSpan(),/* start index */ start_index, /* end index */ end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //spannable.setSpan(new ForegroundColorSpan(activity.getResources().getColor(R.color.colorPrimaryDark)), start_index, end_index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        View about;
        TextView tvAbout;
        try {
            //Inflate the custom view
            LayoutInflater inflater = activity.getLayoutInflater();
            about = inflater.inflate(R.layout.dialog_about, activity.findViewById(R.id.aboutView));
            tvAbout = about.findViewById(R.id.aboutText);
        } catch (InflateException e) {
            //Inflater can throw exception, unlikely but default to TextView if it occurs
            about = tvAbout = new TextView(activity);
        }
        tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
        tvAbout.setHighlightColor(Color.BLACK);// background color when pressed
        tvAbout.setLinkTextColor(Color.RED);
        //Set the about text
        tvAbout.setText(_full);
        // Now Linkify the text
        //Linkify.addLinks(tvAbout, Linkify.ALL);
        //Build and show the dialog
        new AlertDialog.Builder(activity, R.style.AlertDialogTheme)
                .setTitle("Policy " + activity.getString(R.string.app_name))
                .setCancelable(true)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton("Back", null)
                .setView(about)
                .show();    //Builder method returns allow for method chaining
    }
}