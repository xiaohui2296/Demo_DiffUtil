package com.yuwan.demo_diffutil;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHodler> {

    private List<TestBean> list;
    private final Context context;


    public MyRecyclerViewAdapter(Context context, List<TestBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        MyViewHodler viewHolder = new MyViewHodler(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodler holder, int position) {
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodler holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Bundle payload = (Bundle) payloads.get(0);
            String name = payload.getString("KEY_NAME");
            holder.name.setText(name);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class MyViewHodler extends RecyclerView.ViewHolder {
        private TextView name;

        public MyViewHodler(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
        }
    }

    public void setDatas(List<TestBean> list) {
        this.list = list;
    }
}
