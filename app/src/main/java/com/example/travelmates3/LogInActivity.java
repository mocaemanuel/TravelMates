package com.example.travelmates3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.Guide;
import Model.Tourist;
import Model.User;


public class LogInActivity extends AppCompatActivity {
    private boolean found = false;
    private User userRequest;
    private EditText username;
    private EditText password;
    private String response;
    private JSONObject dict;

    public String checkLogIn(String userName, String password){
        HttpURLConnection httpURLConnection;
        char data;
        try {
            JSONObject obj = new JSONObject();
            obj.put("username", userName);
            obj.put("password", password);

            httpURLConnection = (HttpURLConnection) new URL("http://192.168.3.198:5000/add_login").openConnection();
            httpURLConnection.setRequestMethod("GET");
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
            return( sb.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwdEditText);
    }

    public void onLogInClick(View view){
        found = false;
        //sent to database
        Thread thread = new Thread() {
            @Override
            public void run() {
                response = checkLogIn(username.getText().toString(), password.getText().toString());
            }
        };

        thread.start();
        try {
            thread.join();
        }
        catch (InterruptedException exec){
            System.out.println(exec.getMessage());
        }

        //get the user from database
        try {
            dict = new JSONObject(response);
            System.out.println(dict.toString());
            if(dict.get("message") == "username missing"){
                System.out.println("caca\n\n\n");
            }
            else if(dict.get("message") == "password missing"){
                System.out.println("pisu\n\n\n");
            }
            else{
                found = true;
                if (dict.get("type").equals("tourist")) {
                    userRequest = new Tourist(dict.get("firstName").toString(), dict.get("lastName").toString()
                            , dict.get("mail").toString(), Integer.parseInt(dict.get("age").toString()),
                            dict.get("password").toString());
                }
                else if (dict.get("type").equals("guide")){
                    userRequest = new Guide(dict.get("firstName").toString(), dict.get("lastName").toString()
                            , dict.get("mail").toString(), Integer.parseInt(dict.get("age").toString()),
                            dict.get("password").toString());

                }
            }
        }
        catch (JSONException except){
            System.out.println(except.toString());
        }

        if (found == false){
            System.out.println("Done\n\n\n");
        }
        else if (userRequest.getType().equals("Tourist")){
            Intent intent = new Intent(this, newTouristRequestActivity.class);
            intent.putExtra("User", userRequest);
            this.finish();
            startActivity(intent);
        }
        else if (userRequest.getType().equals("Guide")) {
            Intent intent = new Intent(this, GuideChooseActivity.class);
            intent.putExtra("User", userRequest);
            this.finish();
            startActivity(intent);
        }


    }
}
