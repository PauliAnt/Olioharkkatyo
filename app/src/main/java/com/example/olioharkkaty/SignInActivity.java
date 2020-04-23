package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private TextView userReg;
    private TextView passReg1;
    private TextView passReg2;
    private TextView warning2;
    private String un;
    private String pw1;
    private String pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userReg = (TextView) findViewById(R.id.userReg);
        passReg1 = (TextView) findViewById(R.id.passReg1);
        passReg2 = (TextView) findViewById(R.id.passReg2);
        warning2 = (TextView) findViewById(R.id.warning2);

    }

    public void signIn(View v) {
        Hall hall = Hall.getInstance();
        un = (String) userReg.getText();
        pw1 = (String) passReg1.getText();
        pw2 = (String) passReg2.getText();
        if (passReg1.equals("") || passReg2.equals(""))
            warning2.setText("Give password");
        else if (userReg.equals(""))
            warning2.setText("Give username");
        else if (passReg2.equals(passReg1)) {
                User user = new User(un, pw1);
                hall.addUser(user);
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        } else
            warning2.setText("Passwords dont match");

    }
}
