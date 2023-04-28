package com.datingforall.anonymousOnlineDating.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.datingforall.anonymousOnlineDating.activity.MainActivity;
import com.datingforall.anonymousOnlineDating.R;

public class SecondFragment extends BaseFragment implements View.OnClickListener {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final String TAG = "@@@";
    private int sectionNumber;

    public SecondFragment() {
        Log.i(TAG, "SecondFragment: ");
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SecondFragment newInstance(int sectionNumber) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //        TextView textView = rootView.findViewById(R.id.section_label);
//        textView.setText();
        if (getArguments() != null) {
            this.sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        (view.findViewById(R.id.button)).setOnClickListener(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null && R.id.button == v.getId()) {
            ((MainActivity) getActivity()).nextScreen(++sectionNumber);
        }
    }
}