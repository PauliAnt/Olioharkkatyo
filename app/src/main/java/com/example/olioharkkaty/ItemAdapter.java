package com.example.olioharkkaty;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ArrayList<Reservation> reservations;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemView = itemView.findViewById(R.id.image);
            textView1 = itemView.findViewById(R.id.line1);
            textView2 = itemView.findViewById(R.id.line2);
            textView3 = itemView.findViewById(R.id.line3);
        }
    }
    public ItemAdapter(ArrayList<Reservation> reservations){
        this.reservations = reservations;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resevation_item,parent, false);
        ItemViewHolder evh = new ItemViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        holder.imageView.setImageResource(reservation.getSportImageResource());
        holder.textView1.setText(reservation.getRoom());
        holder.textView2.setText(reservation.getDate());
        holder.textView1.setText(reservation.getTime());
    }


    @Override
    public int getItemCount() {
        return reservations.size();
    }
}
