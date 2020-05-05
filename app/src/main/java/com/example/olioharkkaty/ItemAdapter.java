package com.example.olioharkkaty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    // Class is used as adapter for making reservation objects to cards for recycler view in MyReservationActivity
    private ArrayList<Reservation> reservations;
    private OnItemClickListener clickListener;
    private OnLongItemClickListener longClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnLongItemClickListener{
        void onItemLongClick(int position);
    }

    public void setOnLongClickListener(OnLongItemClickListener listener){
        longClickListener = listener;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        TextView textView3;

        public ItemViewHolder(View itemView, final OnItemClickListener clickListener, final OnLongItemClickListener longItemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView1 = itemView.findViewById(R.id.line1);
            textView2 = itemView.findViewById(R.id.line2);
            textView3 = itemView.findViewById(R.id.line3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            clickListener.onItemClick(position);
                        }
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(longItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            longItemClickListener.onItemLongClick(position);
                        }
                    }
                    return true;
                }
            });
        }

    }
    public ItemAdapter(ArrayList<Reservation> reservations){
        this.reservations = reservations;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.resevation_item,parent, false);
        ItemViewHolder ivh = new ItemViewHolder(v,clickListener,longClickListener);
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
        return reservations.size();
    }
}
