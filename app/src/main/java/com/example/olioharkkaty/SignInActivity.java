package com.example.olioharkkaty;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class SignInActivity extends AppCompatActivity {
    private TextView userReg;
    private TextView passReg1;
    private TextView passReg2;
    private TextView warning2;
    private TextView userSign;
    private TextView passSign;
    private TextView warning1;
    private Dialog numberPopUp;


    protected String un;
    private String pw1;
    private String pw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numberPopUp = new Dialog(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // configuroidaan varausjärjestelmä
        try {
            Hall.getInstance().config(SignInActivity.this);
            UserManager.getInstance().setContext(SignInActivity.this);
        } catch (Exception e) {
            // config tiedoston parsiminen epäonnistui
            e.printStackTrace();
            finish();
            System.exit(0);
        }
        // initiate textviews
        userSign = (TextView) findViewById(R.id.userSign);
        passSign = (TextView) findViewById(R.id.passSign);
        warning1 = (TextView) findViewById(R.id.warning1);
        userReg = (TextView) findViewById(R.id.userReg);
        passReg1 = (TextView) findViewById(R.id.passReg1);
        passReg2 = (TextView) findViewById(R.id.passReg2);
        warning2 = (TextView) findViewById(R.id.warning2);


    }

    public void createPopUp(final Dialog numberPopUp){
        // Creates popup which ask for 6 digit code
        numberPopUp.setContentView(R.layout.signin_number_popup);
        numberPopUp.show();
        numberPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        numberPopUp.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        final TextView popUpText, numbers, randNumbers, warningPop;
        // initiate popups textviews
        randNumbers = (TextView) numberPopUp.findViewById(R.id.randNumber) ;
        numbers = (TextView) numberPopUp.findViewById(R.id.codeText);
        warningPop = (TextView) numberPopUp.findViewById(R.id.warningPop);
        warningPop.setText("");

        // generates random 6 digit code
        Random rand = new Random();
        final String rn = String.format("%06d", rand.nextInt(999999));
        randNumbers.setText(rn);

        Button back, confirm;
        back = numberPopUp.findViewById(R.id.back2);
        confirm = numberPopUp.findViewById(R.id.confirm2);
        // Checks if given code matches the 6 digit code
        confirm.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String code = numbers.getText().toString();
               if (rn.trim().equals(code.trim())){
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    numberPopUp.dismiss();
                }
               else
                   warningPop.setText("Code doesn't match");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberPopUp.dismiss();
            }

        });

    }

    private void clearFields(){
        userSign.setText("");
        passSign.setText("");
        warning1.setText("");
        warning2.setText("");
        userReg.setText("");
        passReg1.setText("");
        passReg2.setText("");

    }

    public void register(View v) {
        // Register button, checks passwords, starts MainActivity
        UserManager um = UserManager.getInstance();
        un = userReg.getText().toString();
        pw1 =  passReg1.getText().toString();
        pw2 = passReg2.getText().toString();
        if (pw1.equals("") && pw2.equals("") && un.equals(""))
            warning2.setText("Give username and password");
        else if (pw1.equals("") || pw2.equals(""))
            warning2.setText("Give password");
        else if (un.equals(""))
            warning2.setText("Give username");
        else if (pw1.length()<12)
            warning2.setText("Password too short");
        else if (pw1.length()>32)
            warning2.setText("Password too long");
        else if (un.equals(pw1))
            warning2.setText("Username and password can't be same");
        else if (pw2.equals(pw1)) {
                if (um.checkPassword(pw1)) {
                    um.addUser(un, pw1);
                    Intent intent = new Intent(SignInActivity.this, UserInfoActivity.class);
                    this.clearFields();
                    startActivity(intent);
                } else
                    warning2.setText("Password must contain number and special char");
        } else
            warning2.setText("Passwords don't match");

    }
    public void signInButton(View v){
        UserManager um = UserManager.getInstance();
        un = userSign.getText().toString();
        pw1 = passSign.getText().toString();

        if (un.equals(""))
            warning1.setText("Give username");
        else if (pw1.equals(""))
            warning1.setText("Give password");
        else {
            // Usermanager have method to check login
            if (um.checkLogin(un, pw1)) {
                if (um.checkAdmin()){
                    // if username and password match admin open ManageRoomsActivity
                    Intent intent = new Intent(SignInActivity.this, AdminViewActivity.class);
                    this.clearFields();
                    startActivity(intent);
                } else {
                    createPopUp(numberPopUp);
                    this.clearFields();
                }
            } else {
                warning1.setText("Username or password invalid");
            }
        }
    }
}
