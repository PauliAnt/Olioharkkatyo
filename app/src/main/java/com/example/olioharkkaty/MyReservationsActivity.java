package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
// https://freesvg.org/badminton-shuttlecock-full sulkapallo kuva
// https://freesvg.org/tennis-ball-clip-art-vector-image Tennispallo kuva
// https://svgsilh.com/image/37359.html Squash pallo

public class MyReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Dialog infoPopUp, editPopUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        infoPopUp = new Dialog(this);
        editPopUp = new Dialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        final ArrayList<Reservation> reservations = Hall.getInstance().findReservationsByIdList(UserManager.getInstance().getCurrentUser().getReservations());
        if (reservations == null)
            finish();
        adapter = new ItemAdapter(reservations);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Reservation reservation = reservations.get(position);
                reservationInfoPopUp(reservation,infoPopUp);
            }
        });
        adapter.setOnLongClickListener(new ItemAdapter.OnLongItemClickListener() {
            @Override
            public void onItemLongClick(int position) {
                Reservation reservation = reservations.get(position);
                reservationEditPopUp(reservation,editPopUp);
            }
        });

    }

    public void reservationInfoPopUp(Reservation reservation, final Dialog infoPopUp){
        // avataan popup ikkuna
        infoPopUp.setContentView(R.layout.reservation_info_popup);
        infoPopUp.show();

        // Asetetaan tekstikenttien arvot
        TextView roomname,date,time,sport,description;
        roomname = (TextView)infoPopUp.findViewById(R.id.roomname);
        date = (TextView)infoPopUp.findViewById(R.id.editdate);
        time = (TextView)infoPopUp.findViewById(R.id.edittime);
        sport = (TextView)infoPopUp.findViewById(R.id.editsport);
        description = (TextView)infoPopUp.findViewById(R.id.editdesc);
        roomname.setText(reservation.getRoom());
        date.setText(reservation.getDate());
        time.setText(reservation.getTime());
        sport.setText(reservation.getSport());
        description.setText(reservation.getDescribtion());

        // OnClickListener buttonille
        Button button;
        button = infoPopUp.findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPopUp.dismiss();
            }
        });



    }
    public void reservationEditPopUp(Reservation reservation, final Dialog editPopUp){
        // avataan popup ikkuna
        editPopUp.setContentView(R.layout.reservation_edit_popup);
        editPopUp.show();



        // OnClickListener buttonille
        Button button;
        button = editPopUp.findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPopUp.dismiss();
            }
        });
    }

}
