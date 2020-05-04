package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
// https://freesvg.org/badminton-shuttlecock-full sulkapallo kuva
// https://freesvg.org/tennis-ball-clip-art-vector-image Tennispallo kuva
// https://svgsilh.com/image/37359.html Squash pallo

public class MyReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Dialog infoPopUp, editPopUp;
    private ArrayList<Reservation> reservations;
    private Toast editError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // PopUp initiate
        infoPopUp = new Dialog(this);
        editPopUp = new Dialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        editError = Toast.makeText(MyReservationsActivity.this,"You can't edit reservation on the same day",Toast.LENGTH_SHORT);
        refreshView();

    }

    private void refreshView(){
        reservations = Hall.getInstance().findReservationsByIdList(UserManager.getInstance().getCurrentUser().getReservations());
        if (reservations == null)
            finish();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
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
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    // Set calendar to midnight following day
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.add(Calendar.HOUR_OF_DAY,24);
                    Log.e("OnItemLongClick",sdf.format(calendar.getTime()));
                    if (calendar.getTime().compareTo(sdf.parse(reservation.getDate())) > 0) {
                        editError.show();
                    } else {
                        reservationEditPopUp(reservation,editPopUp);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }

    public void reservationInfoPopUp(Reservation reservation, final Dialog infoPopUp){
        infoPopUp.setContentView(R.layout.reservation_info_popup);
        infoPopUp.show();

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

        // OnClickListener for back button
        Button button;
        button = infoPopUp.findViewById(R.id.backbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPopUp.dismiss();
            }
        });



    }
    public void reservationEditPopUp(final Reservation reservation, final Dialog editPopUp){

        editPopUp.setContentView(R.layout.reservation_edit_popup);
        editPopUp.show();

        final Hall hall = Hall.getInstance();
        final Spinner availableSlots,sports;
        TextView roomname, date;
        final EditText desc;
        availableSlots = editPopUp.findViewById(R.id.edittime);
        sports = editPopUp.findViewById(R.id.editsport);
        roomname = editPopUp.findViewById(R.id.roomname);
        date = editPopUp.findViewById(R.id.editdate);
        desc = editPopUp.findViewById(R.id.editdesc);

        roomname.setText(reservation.getRoom());
        date.setText(reservation.getDate());
        desc.setText(reservation.getDescribtion());

        // Find available times for the spinner
        ArrayAdapter<String> adapter = null;
        ArrayList<String> times = hall.getAvailableReservations(reservation.getRoom(),reservation.getDate());
        if(times == null)
            // Parse error with xml or with java Calendar class
            System.exit(1);
        times.add(reservation.getTime());
        Collections.sort(times);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availableSlots.setAdapter(adapter);

        String[] sportlist = hall.getSports();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,sportlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sports.setAdapter(adapter);

        // setting default selections for spinners
        availableSlots.setSelection(times.indexOf(reservation.getTime()));
        sports.setSelection(reservation.getSportid());



        // OnClickListener for back button
        Button backbutton,confirmbutton;
        backbutton = editPopUp.findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPopUp.dismiss();
            }
        });

        // OnClickListener for confirm button
        confirmbutton = editPopUp.findViewById(R.id.confirmbutton);
        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservation.setDescribtion(desc.getText().toString());
                reservation.setTime(availableSlots.getSelectedItem().toString());
                reservation.setSportid(sports.getSelectedItemPosition());
                hall.editReservation(reservation);
                editPopUp.dismiss();
                refreshView();
            }
        });



    }

}
