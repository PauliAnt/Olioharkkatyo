package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
// https://freesvg.org/badminton-shuttlecock-full sulkapallo kuva
// https://freesvg.org/tennis-ball-clip-art-vector-image Tennispallo kuva
// https://svgsilh.com/image/37359.html Squash pallo

public class MyReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
    }
}
