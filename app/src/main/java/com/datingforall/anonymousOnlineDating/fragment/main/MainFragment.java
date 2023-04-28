package com.datingforall.anonymousOnlineDating.fragment.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.datingforall.anonymousOnlineDating.R;
import com.datingforall.anonymousOnlineDating.fragment.BaseFragment;

public class MainFragment extends BaseFragment implements View.OnClickListener {

    private Button loginButton;
    private Button matchCenterButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        View mainView = rootView.findViewById(R.id.main_view);

        // Получить размер экрана
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        // Установить высоту главного вида фрагмента равной высоте экрана
        mainView.getLayoutParams().height = height;

        loginButton = rootView.findViewById(R.id.btn_login);
        matchCenterButton = rootView.findViewById(R.id.btn_match_center);

        loginButton.setOnClickListener(this);
        matchCenterButton.setOnClickListener(this);

        return rootView;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                // обработка нажатия на кнопку "Войти"
                break;
            case R.id.btn_match_center:
                m.matchCenter();
                break;
        }
    }
}
