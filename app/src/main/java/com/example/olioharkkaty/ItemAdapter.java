package com.example.olioharkkaty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ArrayList<Reservation> reservations;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
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
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        holder.imageView.setImageResource(reservation.getSportImageResource());
        holder.textView1.setText(reservation.getRoom());
        holder.textView2.setText(reservation.getDate());
        holder.textView3.setText(reservation.getTime());
    }


    @Override
    public int getItemCount() {
        Log.e("Itemcount",Integer.toString(reservations.size()));
        return reservations.size();
    }
}
