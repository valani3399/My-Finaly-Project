package com.example.softices.pankajtrainee.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.models.ContextModel;

import java.util.ArrayList;

public class ContextAdepter extends RecyclerView.Adapter<ContextAdepter.MyViewHolder> {
    ArrayList<ContextModel> arrayList;
    Context context;

    public ContextAdepter(ArrayList<ContextModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ContextAdepter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.context_disign, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContextAdepter.MyViewHolder holder, int position) {
        ContextModel contextModel=arrayList.get(position);
        holder.tvnamecontext.setText(contextModel.getName());
        holder.tvmobailnumber.setText(contextModel.getNumber());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvnamecontext,tvmobailnumber;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvnamecontext=(TextView)itemView.findViewById(R.id.tv_contextname);
            tvmobailnumber=(TextView)itemView.findViewById(R.id.tv_mobailnumber);
        }
    }
}
