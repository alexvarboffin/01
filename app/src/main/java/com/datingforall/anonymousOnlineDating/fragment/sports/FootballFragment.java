package com.datingforall.anonymousOnlineDating.fragment.sports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.datingforall.anonymousOnlineDating.R;
import com.datingforall.anonymousOnlineDating.fragment.BaseFragment;

public class FootballFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_football, container, false);
        return view;
    }
}