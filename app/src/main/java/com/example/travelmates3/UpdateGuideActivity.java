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

import Model.Guide;



public class UpdateGuideActivity extends AppCompatActivity {

    private Guide loggedUser;
    private EditText startDate;
    private EditText endDate;

    public void send_update_guide(Guide guide){
        HttpURLConnection httpURLConnection;
        char data;
        try {
            JSONObject obj = new JSONObject();
            obj.put("id", guide.email);
            obj.put("type", "guide");
            obj.put("startDate",guide.startDateAvailable);
            obj.put("endDate",guide.endDateAvailable);


            httpURLConnection = (HttpURLConnection) new URL("http://192.168.3.198:5000/update_obj").openConnection();
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
        setContentView(R.layout.activity_update_guide);

        loggedUser = (Guide) getIntent().getSerializableExtra("User");
        startDate = (EditText) findViewById(R.id.availableSinceEditText);
        endDate = (EditText) findViewById(R.id.tillEditText);
    }

    public void onUpdateButtonClick(View view){

        if (validateEditText(startDate) == false ||
            validateEditText(endDate) == false){
            return;
        }

        loggedUser.setStartDateAvailable(startDate.getText().toString());
        loggedUser.setEndDateAvailable(endDate.getText().toString());

        Thread thread = new Thread() {
            @Override
            public void run() {
                send_update_guide(loggedUser);
            }
        };

        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException exec){
            System.out.println(exec.getMessage());
        }

        Intent intent = new Intent(this, GuideChooseActivity.class);
        intent.putExtra("User", loggedUser);
        this.finish();
        startActivity(intent);
    }
}
