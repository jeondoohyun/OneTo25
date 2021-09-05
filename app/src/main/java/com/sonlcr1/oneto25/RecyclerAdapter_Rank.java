package com.sonlcr1.oneto25;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerAdapter_Rank extends RecyclerView.Adapter {
    Context context;
    ArrayList<VO_Rank> items;

    public RecyclerAdapter_Rank(Context context, ArrayList<VO_Rank> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recycler_rank,parent,false);
        VH holder = new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder; //VH클래스 생성때 제네릭 하면 다운캐스팅 생략 할수 있다

        //현재번째(position)데이터를 가진 item객체 얻어오기
        VO_Rank item = items.get(position);
        if (position == 0) {
            Glide.with(context).load( R.drawable.ic_first ).into( vh.ImageView_recy_rank );
            vh.TextView_recy_rank.setVisibility(View.GONE);
        } else if (position == 1) {
            Glide.with(context).load( R.drawable.ic_second ).into( vh.ImageView_recy_rank );
            vh.TextView_recy_rank.setVisibility(View.GONE);
        } else if (position == 2) {
            Glide.with(context).load( R.drawable.ic_third ).into( vh.ImageView_recy_rank );
            vh.TextView_recy_rank.setVisibility(View.GONE);
        } else {
            vh.ImageView_recy_rank.setVisibility(View.GONE);
            vh.TextView_recy_rank.setVisibility(View.VISIBLE);
            vh.TextView_recy_rank.setText((position+1)+"");
        }

        vh.TextView_recy_email.setText(item.email);
        vh.TextView_recy_record.setText(item.st_record+"");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ImageView ImageView_recy_rank;
        TextView TextView_recy_rank;
        TextView TextView_recy_email;
        TextView TextView_recy_record;

        public VH(@NonNull View itemView) {
            super(itemView);

            ImageView_recy_rank = itemView.findViewById(R.id.ImageView_recy_rank);
            TextView_recy_rank = itemView.findViewById(R.id.TextView_recy_rank);
            TextView_recy_email = itemView.findViewById(R.id.TextView_recy_email);
            TextView_recy_record = itemView.findViewById(R.id.TextView_recy_record);
        }
    }
}
