package com.sonlcr1.oneto25;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter_Rank extends RecyclerView.Adapter {
    Context context;
    ArrayList<VO_Rank> item;

    public RecyclerAdapter_Rank(Context context, ArrayList<VO_Rank> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView ImageView_recy_rank;
        TextView TextView_recy_rank;
        TextView TextView_recy_email;
        TextView TextView_recy_record;

        public VH(@NonNull View itemView) {
            super(itemView);
            
            // todo : recycler 할차례

            ImageView_recy_rank = itemView.findViewById(R.id.ImageView_recy_rank);
            TextView_recy_rank = itemView.findViewById(R.id.TextView_recy_rank);
            TextView_recy_email = itemView.findViewById(R.id.TextView_recy_email);
            TextView_recy_record = itemView.findViewById(R.id.TextView_recy_record);
        }
    }
}
