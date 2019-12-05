package com.example.travelmates3;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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

import Model.Tourist;



public class TouristDetailsActivity extends AppCompatActivity {

    private EditText hobbies;
    private EditText areaOfInterest;
    private Tourist newUser;

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void send_add_tourist(Tourist tourist){
        HttpURLConnection httpURLConnection;
        char data;

        if (!isNetworkConnected())
            System.out.println("HELP");
        else
            System.out.println("DONE");

        try {
            JSONObject obj = new JSONObject();
            obj.put("_id", tourist.email);
            obj.put("type", "tourist");
            obj.put("firstName", tourist.firstName);
            obj.put("lastName", tourist.lastName);
            obj.put("mail", tourist.email);
            obj.put("age", tourist.age);
            obj.put("password", tourist.passwd);
            obj.put("hobbies", tourist.hobbies);
            obj.put("interests", tourist.areaOfInterest);

            httpURLConnection = (HttpURLConnection) new URL("http://192.168.3.198:5000/add_obj").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.getRequestMethod();

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
        setContentView(R.layout.activity_tourist_details);

        newUser = (Tourist) getIntent().getSerializableExtra("User");

        hobbies = (EditText) findViewById(R.id.hobbiesEditText);
        areaOfInterest = (EditText) findViewById(R.id.interestEditText);
    }

    public void onJoinButtonClick(View view){
        if (validateEditText(hobbies) == false ||
                validateEditText(areaOfInterest) == false) {
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


        Thread thread = new Thread() {
            @Override
            public void run() {
                send_add_tourist(newUser);
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
