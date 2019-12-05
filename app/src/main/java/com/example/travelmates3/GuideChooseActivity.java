package com.example.travelmates3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import Model.Guide;

public class GuideChooseActivity extends AppCompatActivity {

    private Guide loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_choose);

        loggedUser = (Guide) getIntent().getSerializableExtra("User");
    }

    public void onMatchButtonClick(View view){
        Intent intent = new Intent(this, ShowMatchActivity.class);
        intent.putExtra("User", loggedUser);
        startActivity(intent);
    }

    public void onUpdateButtonClick(View view){
        Intent intent = new Intent(this, UpdateGuideActivity.class);
        intent.putExtra("User", loggedUser);
        startActivity(intent);
    }
}
