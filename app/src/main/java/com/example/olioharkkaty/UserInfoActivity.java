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
    private TextView wr;
    private User user;
    private UserManager um;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        um = UserManager.getInstance();
        user = um.getCurrentUser();
        uname = (TextView) findViewById(R.id.userName);
        uname.setText(user.getUserName());
        pw = (TextView) findViewById(R.id.userPaword);
        pw.setText(user.getPassword());
        fn = (TextView) findViewById(R.id.firstName);
        fn.setText(user.getFirstName());
        ln = (TextView) findViewById(R.id.lastName);
        ln.setText(user.getLastName());
        ad = (TextView) findViewById(R.id.adress);
        ad.setText(user.getAddress());
        wr = (TextView) findViewById(R.id.warning);
    }

    private Boolean checkFields(){
        if (uname.getText().toString().equals("") || pw.getText().toString().equals("") || fn.getText().toString().equals("") || ln.getText().toString().equals("") || ad.getText().toString().equals(""))
            return false;
        return true;
    }

    public void confirm(View v){
        if (checkFields()==false)
            wr.setText("Fill all fields");
        else {
            wr.setText("");
            un = uname.getText().toString();
            um.updateInfo(UserInfoActivity.this, un, pw.getText().toString(), fn.getText().toString(), ln.getText().toString(), ad.getText().toString());
            finish();
        }
    }
}
