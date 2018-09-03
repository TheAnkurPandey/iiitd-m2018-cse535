package com.example.ankur.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TopFragment extends Fragment {

    static boolean mCalled = false;

    // Fragment creates its View object hierarchy.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, parent, false);
    }

    // onViewCreated() callback is triggered soon after onCreateView().
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Event listener for Start button of top fragment.
        Button mButton = view.findViewById(R.id.tf_strt_b);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCalled)
                    return;
                else
                    mCalled = true;
                getActivity().startService(new Intent(getActivity(), MusicService_A2_MT18070.class));
            }
        });

        // Event listener for Stop button of top fragment.
        mButton = view.findViewById(R.id.tf_stop_b);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalled = false;
                getActivity().stopService(new Intent(getActivity(), MusicService_A2_MT18070.class));
            }
        });
    }
}
