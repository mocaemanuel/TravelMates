package com.example.travelmates3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Guide;



public class GuideDetailsActivity extends AppCompatActivity {

    private EditText hobbies;
    private EditText areaOfInterest;
    private Guide newUser;
    private EditText startDate;
    private EditText endDate;

    public void send_add_guide(Guide guide){
        HttpURLConnection httpURLConnection;
        char data;
        try {
            JSONObject obj = new JSONObject();
            obj.put("_id", guide.email);
            obj.put("type", "guide");
            obj.put("firstName", guide.firstName);
            obj.put("lastName", guide.lastName);
            obj.put("mail", guide.email);
            obj.put("residence",guide.residence);
            obj.put("age", guide.age);
            obj.put("password", guide.passwd);
            obj.put("hobbies", guide.getHobbies());
            obj.put("interests", guide.getAreaOfInterest());
            obj.put("startDate", guide.startDateAvailable);
            obj.put("endDate", guide.endDateAvailable);

            httpURLConnection = (HttpURLConnection) new URL("http://192.168.3.198:5000/add_obj").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(obj.toString());
            wr.flush();
            wr.close();

            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        setContentView(R.layout.activity_guide_details);

        newUser = (Guide) getIntent().getSerializableExtra("User");

        hobbies = (EditText) findViewById(R.id.hobbiesEditText);
        areaOfInterest = (EditText) findViewById(R.id.interestEditText);
        startDate = (EditText) findViewById(R.id.availableSinceEditText);
        endDate = (EditText) findViewById(R.id.tillEditText);

    }

    public void onJoinButtonClick(View view){
        if (validateEditText(hobbies) == false ||
                validateEditText(areaOfInterest) == false ||
                validateEditText(startDate) == false ||
                validateEditText(endDate) == false) {
            return ;
        }

        List<String> list;
        list = Arrays.asList(hobbies.getText().toString().split(", "));
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(list);
        newUser.setHobbies(arrayList);

        list = Arrays.asList(areaOfInterest.getText().toString().split(", "));
        arrayList.addAll(list);
        newUser.setAreaOfInterest(arrayList);

        newUser.setStartDateAvailable(startDate.getText().toString());

        newUser.setEndDateAvailable(endDate.getText().toString());

        Thread thread = new Thread() {
            @Override
            public void run() {
                send_add_guide(newUser);
            }
        };

        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException exec){
            System.out.println(exec.getMessage());
        }

        Intent intent = new Intent(this, LogInActivity.class);
        this.finish();
        startActivity(intent);
    }
}
