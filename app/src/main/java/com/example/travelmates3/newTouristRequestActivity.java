package com.example.travelmates3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.Tourist;

public class newTouristRequestActivity extends AppCompatActivity {

    private TextView txtLocation;
    private Tourist loggedUser;
    private EditText startDate;
    private EditText endDate;
    private String response;

    public String get_suitable_guides(Tourist tourist, String startDate, String endDate, String location){
        HttpURLConnection httpURLConnection;

        try {
            JSONObject obj = new JSONObject();
            obj.put("startDate", startDate);
            obj.put("endDate", endDate);
            obj.put("location", location);
            obj.put("hobbies",tourist.email);


            httpURLConnection = (HttpURLConnection) new URL("http://192.168.3.198:5000/get_guides").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(obj.toString());
            wr.flush();
            wr.close();

            BufferedReader br = new BufferedReader(new InputStreamReader((httpURLConnection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            httpURLConnection.disconnect();
            return( sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

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
        setContentView(R.layout.activity_new_tourist_request);

        loggedUser = (Tourist) getIntent().getSerializableExtra("User");

        txtLocation = (TextView) findViewById(R.id.locationTextView);
        startDate = (EditText) findViewById(R.id.availableSinceEditText);
        endDate = (EditText) findViewById(R.id.tillEditText);
    }

    public void onLocationClick(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        if (txtLocation.getText() != "") {
            intent.putExtra("NEWLOCATION", txtLocation.getText());
        }
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if (data != null) {
                txtLocation.setText(data.getCharSequenceExtra("LOCATION").toString());
            }
        }
    }

    public void onSaveClick(View view){
        if (validateEditText(startDate) == false ||
            validateEditText(endDate) == false ||
            txtLocation.toString().length() == 0) {
            return;
        }

        Thread thread = new Thread() {
            @Override
            public void run() {
                response = get_suitable_guides(loggedUser, startDate.getText().toString(),
                        endDate.getText().toString(), txtLocation.getText().toString());
            }
        };

        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException exec){
            System.out.println(exec.getMessage());
        }

        Intent intent = new Intent(this, MatchActivity.class);
        intent.putExtra("Info", response);
        intent.putExtra("User", loggedUser);
        intent.putExtra("SD", startDate.getText().toString());
        intent.putExtra("ED", endDate.getText().toString());
        startActivity(intent);

    }
}
