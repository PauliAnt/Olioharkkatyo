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
    private TextView userSign;
    private TextView passSign;
    private TextView warning1;
    private User user;


    // Näitä ei tarvitse merkitä koko luokan välisiksi. Riittää että käyttää register() metodissa
    protected String un;
    private String pw1;
    private String pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        userSign = (TextView) findViewById(R.id.userSign);
        passSign = (TextView) findViewById(R.id.passSign);
        warning1 = (TextView) findViewById(R.id.warning1);
        userReg = (TextView) findViewById(R.id.userReg);
        passReg1 = (TextView) findViewById(R.id.passReg1);
        passReg2 = (TextView) findViewById(R.id.passReg2);
        warning2 = (TextView) findViewById(R.id.warning2);

    }

    public void register(View v) {
        un = userReg.getText().toString();
        pw1 =  passReg1.getText().toString();
        pw2 = passReg2.getText().toString();
        if (pw1.equals("") && pw2.equals("") && un.equals(""))
            warning2.setText("Give username and password");
        else if (pw1.equals("") || pw2.equals(""))
            warning2.setText("Give password");
        else if (un.equals(""))
            warning2.setText("Give username");
        else if (pw1.length()<6)
            warning2.setText("Password too short");
        else if (pw1.length()>16)
            warning2.setText("Password too long");
        else if (un.equals(pw1))
            warning2.setText("Username and password can't be same");
        else if (pw2.equals(pw1)) {
                UserManager.getInstance().addUser(un, pw1);
                Intent intent = new Intent(SignInActivity.this, UserInfoActivity.class);
                startActivity(intent);
        } else
            warning2.setText("Passwords don't match");

    }
    public void signInButton(View v){
        UserManager um = UserManager.getInstance();
        un = userSign.getText().toString();
        user = um.findUser(un);
        pw1 = passSign.getText().toString();
        if (un.equals(""))
            warning1.setText("Give username");
        else if (pw1.equals(""))
            warning1.setText("Give password");
        else if (user==null)
            warning1.setText("Username not found");
        else if (user.getPassword().equals(pw1)!=true)
            warning1.setText("Password doesn't match username");
        else if (user.getPassword().equals(pw1)) {
            um.setCurrentUser(user);
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
