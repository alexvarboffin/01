package com.datingforall.anonymousOnlineDating.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.datingforall.anonymousOnlineDating.activity.MainActivity;
import com.datingforall.anonymousOnlineDating.R;

public class AgeFragment extends Fragment {
    private Button adultButton, under18Button;
    private TextView ageTextView;
    private int sectionNumber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_age, container, false);

        adultButton = view.findViewById(R.id.adult_button);
        under18Button = view.findViewById(R.id.under18_button);
        ageTextView = view.findViewById(R.id.age_textview);

        adultButton.setOnClickListener(v -> {
            if (getActivity() != null && R.id.adult_button == v.getId()) {
                ((MainActivity) getActivity()).nextScreen(++sectionNumber);
            }
        });

        under18Button.setOnClickListener(v -> {
            // Закрываем приложение
            getActivity().finish();
        });

        return view;
    }

    public void setAge(int age) {
        // Устанавливаем возраст в текстовое поле
        ageTextView.setText(getString(R.string.age_message, age));
    }
}