package com.example.softices.pankajtrainee.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.activity.RecyclerviewdemoActivity;
import com.example.softices.pankajtrainee.db.DbHelper;
import com.example.softices.pankajtrainee.models.AppModel;

import java.util.ArrayList;

/**
 * Created by Softices on 5/23/2018.
 */
public class itemAdapter extends RecyclerView.Adapter<itemAdapter.MyViewHolder> {

    ArrayList<AppModel> arrayList;
    static Context context;
    private DbHelper dbHelper;

    public itemAdapter(Context context, DbHelper dbHelper, ArrayList<AppModel> data) {
        arrayList = data;
        this.dbHelper = dbHelper;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.line_by_line, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    //multipal condition apply and data scroling data change not work propery then contion aplly only if not else...

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AppModel appModel = arrayList.get(position);
        holder.textViewName.setText(appModel.getFirstName());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c=v.getContext();
                final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
                String isLogin = sharedPreferences.getString("gmailKey", "");
                if(isLogin.equals(appModel.getEmail())){
                    Toast.makeText(c, "you currnt user", Toast.LENGTH_SHORT).show();
                }else {
                    final Boolean isDelet = dbHelper.deleteUser(appModel.getEmail());
                    if (isDelet) {
                        Toast.makeText(context, "Record is Delet", Toast.LENGTH_SHORT).show();
                        arrayList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, arrayList.size());
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Record is not delet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    //this code word data change in runtime..................................................
    //..........................................................
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        Button btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.tv_namelinebyline);
            this.btnDelete = (Button) itemView.findViewById(R.id.btn_deletlinebyline);

        }
    }
}