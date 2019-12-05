package com.example.travelmates3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import Model.Tourist;




public class MatchActivity extends AppCompatActivity {

    private TextView showMessage;
    private String response;
    private String startDate;
    private String endDate;
    private Tourist loggedUser;
    private JSONArray list;
    private ArrayList<JSONObject> lst;
    private int index = 0;

    public void add_match(Tourist tourist, String guide, String startDate, String endDate){
        HttpURLConnection httpURLConnection;
        char data;
        try {
            JSONObject obj = new JSONObject();
            obj.put("startDate", startDate);
            obj.put("endDate", endDate);
            obj.put("tourist", tourist.email);
            obj.put("guide",guide);


            httpURLConnection = (HttpURLConnection) new URL("http://192.168.3.198:5000/add_match").openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            httpURLConnection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(obj.toString());
            wr.flush();
            wr.close();

            StringBuilder content;

        // Get the input stream of the connection
            try (BufferedReader input = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                String line;
                content = new StringBuilder();
                while ((line = input.readLine()) != null) {
                    // Append each line of the response and separate them
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            } finally {
                httpURLConnection.disconnect();
            }
            System.out.println(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);


        showMessage = (TextView) findViewById(R.id.messageTextView);

        loggedUser = (Tourist) getIntent().getSerializableExtra("User");
        response = (String) getIntent().getSerializableExtra("Info");
        startDate = (String) getIntent().getSerializableExtra("SD");
        endDate = (String) getIntent().getSerializableExtra("ED");


        try {
            list =  (JSONArray) new JSONObject(response).get("guides");
            lst = new ArrayList<>();
            for (int index = 0; index < list.length(); index++){
                lst.add((JSONObject)list.get(index));
            }

            if (lst.size() == 0)
                showMessage.setText("Sorry, no guides around");
            else
                showMessage.setText("Name: " + lst.get(0).get("firstName").toString() + " " + lst.get(0).get("lastName").toString()
                     + "\n Mail:  " + lst.get(0).get("mail").toString() + "\nAge: " +  Integer.parseInt(lst.get(0).get("age").toString()));
        }
        catch (JSONException err){
            System.out.println(err.getMessage());
        }

    }

    public void onNextButtonClick(View view){
        index += 1;
        if (index < lst.size()){
            try {
                showMessage.setText("Name: " + lst.get(0).get("firstName").toString() + " " + lst.get(0).get("lastName").toString()
                        + "\n Mail:  " + lst.get(0).get("mail").toString() + "\nAge: " +  Integer.parseInt(lst.get(0).get("age").toString()));
            }
            catch (JSONException err){
                System.out.println(err.getMessage());
            }
        }
        else if (lst.size() != 0){
            index = 0;
            try {
                showMessage.setText("Name: " + lst.get(0).get("firstName").toString() + " " + lst.get(0).get("lastName").toString()
                        + "\n Mail:  " + lst.get(0).get("mail").toString() + "\nAge: " +  Integer.parseInt(lst.get(0).get("age").toString()));
            }
            catch (JSONException err){
                System.out.println(err.getMessage());
            }
        }
    }

    public void onSelectButtonClick(View view){
        if (lst.size() != 0) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        add_match(loggedUser, lst.get(index).get("mail").toString(), startDate, endDate);
                    } catch (JSONException err) {
                        System.out.println(err.getMessage());
                    }
                }
            };

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException exec) {
                System.out.println(exec.getMessage());
            }
        }
    }
}
