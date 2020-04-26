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
        user = UserManager.getInstance().getCurrentUser();

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
    public void updateInfo(View v){
        un = uname.getText().toString();
        user.setUserName(un);
        user.setPassword(pw.getText().toString());
        user.setFirstName(fn.getText().toString());
        user.setLastName(ln.getText().toString());
        user.setAddress(ad.getText().toString());

        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
        intent.putExtra("Username", un);
        startActivity(intent);
    }
}
