package com.datingforall.anonymousOnlineDating.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.datingforall.anonymousOnlineDating.R;


public class LicenseFragmentEx extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_license2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Получаем ссылки на элементы интерфейса
        TextView licenseTextView = view.findViewById(R.id.license_textview);
        Button acceptButton = view.findViewById(R.id.accept_button);
        Button rejectButton = view.findViewById(R.id.reject_button);

        // Загружаем текст лицензионного соглашения из ресурсов
        licenseTextView.setText(getString(R.string.license_text));

        // Назначаем обработчик нажатия на кнопку "Принять"
        acceptButton.setOnClickListener(v -> {
            // Открываем новый фрагмент
            //@@@
        });

        // Назначаем обработчик нажатия на кнопку "Отклонить"
        rejectButton.setOnClickListener(v -> {
            // Закрываем приложение
            getActivity().finish();
        });

        // Настройка тулбара
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); // Показываем кнопку "Назад"
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Нажата кнопка "Назад" на тулбаре
            //@@@Navigation.findNavController(requireView()).navigateUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}