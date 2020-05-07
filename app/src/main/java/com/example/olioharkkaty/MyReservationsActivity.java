package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.ListIterator;
// https://freesvg.org/badminton-shuttlecock-full sulkapallo kuva
// https://freesvg.org/tennis-ball-clip-art-vector-image Tennispallo kuva
// https://svgsilh.com/image/37359.html Squash pallo

public class MyReservationsActivity extends AppCompatActivity {
    // Class is used to handle users reservations
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Dialog infoPopUp, editPopUp;
    private ArrayList<Reservation> reservations;
    private Toast editError;
    private Spinner searchspinner;
    private int sportfilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // PopUp initiate
        infoPopUp = new Dialog(this);
        editPopUp = new Dialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
        editError = Toast.makeText(MyReservationsActivity.this,"You can't edit reservation on the same day",Toast.LENGTH_SHORT);
        refreshView();

        //Initiating spinner for finding reservations
        searchspinner = findViewById(R.id.search_spinner);
        ArrayList<String> sports = new ArrayList<>();
        sports.addAll(Arrays.asList(Hall.getInstance().getSports()));
        // Adding option to view all reservations
        sports.add(0,"All reservations");
        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,sports);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchspinner.setAdapter(spinneradapter);
        sportfilter = -1;
        searchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* sport filter needs to correlate directly to list of sports in hall
                and we added one element to index 0 so we need to lower the index by one.*/
                sportfilter = position-1;
                refreshView();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void refreshView(){
        // Used to refresh/create recyclerview
        reservations = Hall.getInstance().findReservationsByIdList(UserManager.getInstance().getCurrentUser().getReservations());
        if (reservations == null){
            finish();
            return;
        }
        // if filter == -1 -> all reservations is selected
        if (sportfilter != -1) {
            ListIterator<Reservation> iterator = reservations.listIterator();
            while(iterator.hasNext()){
                if(iterator.next().getSportid()!=sportfilter)
                    iterator.remove();
            }
        }
        // Sorting reservations by date
        Collections.sort(reservations);
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
                if(reservation instanceof RegularReservation)
                    regularReservationInfoPopUp(reservation,infoPopUp);
                else
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
                    // Setting calendar to midnight following day
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                    if(reservation instanceof RegularReservation)
                        reservationEditPopUp(reservation,editPopUp);
                    // Only edit reservations that are at least booked for following day
                    else if (calendar.getTime().compareTo(sdf.parse(reservation.getDate())) > 0) {
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

    public void regularReservationInfoPopUp(Reservation reservation, final Dialog infoPopUp){

        RegularReservation reservation1 = (RegularReservation)reservation;
        infoPopUp.setContentView(R.layout.regular_reservation_info_popup);
        infoPopUp.show();
        infoPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView roomname,time,sport,describtion,slots;
        roomname = infoPopUp.findViewById(R.id.roomname);
        time = infoPopUp.findViewById(R.id.time);
        sport = infoPopUp.findViewById(R.id.editsport);
        describtion = infoPopUp.findViewById(R.id.editdesc);
        roomname.setText(reservation.getRoom());
        time.setText(reservation.getTime());
        sport.setText(reservation.getSport());
        describtion.setText(reservation.getDescribtion());

        ArrayList<String> dates =  Hall.getInstance().findNextAvailableDays(reservation1.getRoom(),reservation1.getWeekday(),reservation1.getTime(),reservation1.getFirstdate());
        String str = "";
        for (String date:dates){
            str += date + " "+ reservation.time + "\n";
        }

        slots = infoPopUp.findViewById(R.id.threeslots);
        slots.setText(str);

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

    public void reservationInfoPopUp(Reservation reservation, final Dialog infoPopUp){
        infoPopUp.setContentView(R.layout.reservation_info_popup);
        infoPopUp.show();
        infoPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView roomname,date,time,sport,description;
        roomname = infoPopUp.findViewById(R.id.roomname);
        date = infoPopUp.findViewById(R.id.editdate);
        time = infoPopUp.findViewById(R.id.edittime);
        sport = infoPopUp.findViewById(R.id.editsport);
        description = infoPopUp.findViewById(R.id.editdesc);
        roomname.setText(reservation.getRoom());
        date.setText(reservation.getDate());
        time.setText(reservation.getTime());
        sport.setText(reservation.getSport());
        description.setText(reservation.getDescribtion());

        // OnClickListener for back button
        Button button;
        button = infoPopUp.findViewById(R.id.back2);
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
        editPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editPopUp.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

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
        desc.setText(reservation.getDescribtion());

        // Find available times for the spinner
        ArrayAdapter<String> spinneradapter = null;
        ArrayList<String> times;
        if(reservation instanceof RegularReservation) {
            RegularReservation reservation1 = (RegularReservation)reservation;
            times = hall.getAvailableRegularReservations(reservation.getRoom(), reservation1.getWeekday());
            date.setText(hall.findNextAvailableDays(reservation.getRoom(),reservation1.getWeekday(),reservation.getTime(),reservation1.getFirstdate()).get(0));
        }
        else {
            times = hall.getAvailableReservations(reservation.getRoom(),reservation.getDate());
            date.setText(reservation.getDate());
        }
        if(times == null)
            // Parse error with xml or with java Calendar class
            System.exit(1);
        times.add(reservation.getTime());
        Collections.sort(times);
        spinneradapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,times);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        availableSlots.setAdapter(spinneradapter);

        String[] sportlist = hall.getSports();
        spinneradapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,sportlist);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sports.setAdapter(spinneradapter);

        // setting default selections for spinners
        availableSlots.setSelection(times.indexOf(reservation.getTime()));
        sports.setSelection(reservation.getSportid());



        // OnClickListener for back button
        final Button backbutton,confirmbutton,cancelbutton;
        backbutton = editPopUp.findViewById(R.id.back2);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPopUp.dismiss();
            }
        });

        // OnClickListener for confirm button
        confirmbutton = editPopUp.findViewById(R.id.confirm2);
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
        cancelbutton = editPopUp.findViewById(R.id.cancelbutton);
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            int clickCounter = 0;
            @Override
            public void onClick(View v) {
                if(clickCounter == 0) {
                    cancelbutton.setText("Click again to confirm");
                    clickCounter++;
                } else {
                    hall.removeReservation(reservation);
                    UserManager.getInstance().removeReservation(reservation);
                    editPopUp.dismiss();
                    refreshView();
                }
            }
        });




    }

}
