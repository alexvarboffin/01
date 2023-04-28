package com.datingforall.anonymousOnlineDating.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.datingforall.anonymousOnlineDating.activity.MainActivity;
import com.datingforall.anonymousOnlineDating.R;
import com.datingforall.anonymousOnlineDating.activity.LicenseActivity;

public class LicenseFragment extends BaseFragment implements View.OnClickListener {

    private int sectionNumber = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_license, container, false);

        // Find the TextView for the license agreement text
        TextView licenseText = view.findViewById(R.id.license_text);
        view.findViewById(R.id.license_link).setOnClickListener(this);
        // Set the text of the license agreement
        licenseText.setText(getString(R.string.license_agreement));

        // Find the Button for accepting the license
        Button acceptButton = view.findViewById(R.id.accept_button);

        // Set the OnClickListener for the accept button
        acceptButton.setOnClickListener(v -> {
            if (getActivity() != null && R.id.accept_button == v.getId()) {
                ((MainActivity) getActivity()).nextScreen(++sectionNumber);
            }
        });

        // Find the Button for declining the license
        Button declineButton = view.findViewById(R.id.decline_button);

        // Set the OnClickListener for the decline button
        declineButton.setOnClickListener(view1 -> {
            // Close the app
            getActivity().finish();
        });

        // Return the inflated layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null &&v.getId() == R.id.license_link) {
            getActivity().startActivity(
                    new Intent(getActivity(), LicenseActivity.class)
            );
        }
    }
}