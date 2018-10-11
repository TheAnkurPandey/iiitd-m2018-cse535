package com.example.ankur.homework3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<String> mQuestionBank;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> questionBank) {
        mQuestionBank = questionBank;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.question.setText(mQuestionBank.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mQuestionBank.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "Going to Detail fragment", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("Question", mQuestionBank.get(position));
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = ((MainActivity)mContext).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ma_fl, detailFragment);
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestionBank.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView question;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.li_tv);
            parentLayout = itemView.findViewById(R.id.li);
        }
    }
}


