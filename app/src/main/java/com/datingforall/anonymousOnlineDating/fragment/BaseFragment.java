package com.datingforall.anonymousOnlineDating.fragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.datingforall.anonymousOnlineDating.activity.MainActivity;

public class BaseFragment extends Fragment {

    protected MainActivity m;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        m = (MainActivity) context;
    }
}
