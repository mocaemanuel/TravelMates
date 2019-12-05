package com.example.travelmates3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import Model.Guide;
import Model.Tourist;
import Model.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText mail;
    private EditText age;
    private EditText passwd;

    private boolean validateEditText (EditText txt) {
        if (txt.getText().toString().length() == 0) {
            txt.setError("Fill in");
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = (EditText) findViewById(R.id.nameEditText);
        lastName = (EditText) findViewById(R.id.surnameEditText);
        mail = (EditText) findViewById(R.id.mailEditText);
        age = (EditText) findViewById(R.id.ageEditText);
        age = (EditText) findViewById(R.id.ageEditText);
        passwd  =(EditText) findViewById(R.id.passwdEditText);
    }

    public void onTouristButtonClick(View view){

        if (validateEditText(firstName) == false ||
                validateEditText(lastName) == false ||
                validateEditText(mail) == false ||
                validateEditText(age) == false ||
                validateEditText(passwd) == false) {
            return ;
        }

        User newUser = new Tourist(firstName.getText().toString(),
                lastName.getText().toString(), mail.getText().toString(),
                Integer.parseInt(age.getText().toString()), passwd.getText().toString());


        Intent intent = new Intent(this, TouristDetailsActivity.class);
        intent.putExtra("User", newUser);
        startActivity(intent);
    }

    public void onGuideButtonClick(View view){
        if (validateEditText(firstName) == false ||
                validateEditText(lastName) == false ||
                validateEditText(mail) == false ||
                validateEditText(age) == false ||
                validateEditText(passwd) == false) {
            return ;
        }

        User newUser = new Guide(firstName.getText().toString(),
                lastName.getText().toString(), mail.getText().toString(),
                Integer.parseInt(age.getText().toString()), passwd.getText().toString());


        Intent intent = new Intent(this, GuideDetailsActivity.class);
        intent.putExtra("User", newUser);
        startActivity(intent);
    }
}
