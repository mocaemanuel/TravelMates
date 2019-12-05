package com.example.travelmates3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.travelmates3.LogInActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogInActivityClick(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        //this.finish();
        startActivity(intent);
    }

    public void onSignUpActivityClick(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        //this.finish();
        startActivity(intent);
    }
}
