package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {
    protected String un;
    private TextView uname;
    private TextView pw;
    private TextView fn;
    private TextView ln;
    private TextView ad;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        un = getIntent().getStringExtra("Username");
        Hall hall = Hall.getInstance();
        user = hall.getUser(un);

        uname = (TextView) findViewById(R.id.userName);
        uname.setText(user.getU_name());
        pw = (TextView) findViewById(R.id.userPaword);
        pw.setText(user.getPassword());
        fn = (TextView) findViewById(R.id.firstName);
        fn.setText(user.getF_name());
        ln = (TextView) findViewById(R.id.lastName);
        ln.setText(user.getL_name());
        ad = (TextView) findViewById(R.id.adress);
        ad.setText(user.getAdress());

    }
    protected void updateInfo(View v){
        un = uname.getText().toString();
        user.setU_name(un);
        user.setPassword(pw.getText().toString());
        user.setF_name(fn.getText().toString());
        user.setL_name(ln.getText().toString());
        user.setAdress(ad.getText().toString());

        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
        intent.putExtra("Username", un);
        startActivity(intent);
    }
}
