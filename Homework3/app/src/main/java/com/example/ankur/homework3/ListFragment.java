package com.example.ankur.homework3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private ArrayList<String> mListItems = new ArrayList<>();
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_list, container, false);
        /*mButton = view.findViewById(R.id.lf);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to Detail fragment", Toast.LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ma_fl, new DetailFragment());
                fragmentTransaction.commit();
            }
        });*/

        initRecyclerView(view);

        return view;
    }

    private void initRecyclerView(View view){
        mListItems.add("1");
        mListItems.add("2");
        mListItems.add("3");
        mListItems.add("4");
        mListItems.add("5");
        mListItems.add("6");
        mListItems.add("7");
        mListItems.add("8");
        mListItems.add("9");
        mListItems.add("10");
        mListItems.add("11");
        mListItems.add("12");
        mListItems.add("13");
        mListItems.add("14");
        mListItems.add("15");
        mListItems.add("16");

        RecyclerView recyclerView = view.findViewById(R.id.fl_rv);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mListItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}