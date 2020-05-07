package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends AppCompatActivity {
    protected String un;
    private TextView uname, fn, ln, ad, wr, pwOld, pwNew1, pwNew2, wr2;
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
        fn = (TextView) findViewById(R.id.firstName);
        fn.setText(user.getFirstName());
        ln = (TextView) findViewById(R.id.lastName);
        ln.setText(user.getLastName());
        ad = (TextView) findViewById(R.id.adress);
        ad.setText(user.getAddress());
        wr = (TextView) findViewById(R.id.warning);
        wr.setText("");
        pwOld = (TextView) findViewById(R.id.currPw);
        pwNew1 = (TextView) findViewById(R.id.newPw1);
        pwNew2 = (TextView) findViewById(R.id.newPw2);
        wr2 = (TextView) findViewById(R.id.warnPass);
        wr2.setText("");
    }

    private Boolean checkFields(){
        if (uname.getText().toString().equals("") || fn.getText().toString().equals("") || ln.getText().toString().equals("") || ad.getText().toString().equals(""))
            return false;
        return true;
    }

    public void confirm(View v){
        if (checkFields()==false)
            wr.setText("Fill all fields");
        else {
            wr.setText("");
            un = uname.getText().toString();
            um.updateInfo(un, fn.getText().toString(), ln.getText().toString(), ad.getText().toString());
            Toast.makeText(this,"User info successfully saved",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void changePassword(View v){
        String old = pwOld.getText().toString();
        if (um.hashPassword(old, user.getSalt()).equals(user.getPassword())){
            String pw1 = pwNew1.getText().toString();
            String pw2 = pwNew2.getText().toString();
            if (pw1.equals(pw2) && um.checkPassword(pw1)){
                wr2.setText("");
                user.setPassword(um.hashPassword(pw1, user.getSalt()));
                if(checkFields()) {
                    wr2.setText("");
                    finish();
                } else
                    wr2.setText("Fill all fields");
            } else
                wr2.setText("New passwords must be same and contain number and special char");
        } else
            wr2.setText("Current password invalid");

    }
}
