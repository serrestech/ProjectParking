package com.example.makis.smartpark;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.PendingIntent.getActivity;


public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText password;
    Button register;
    Button login;
    private RequestQueue requestQueue;
    private static final String URL = "http://next-tech.techlimittv.eu/ProjectParkingAPI/api/v1/users/register/index.php";
    private StringRequest request;
    Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context=this;
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        register=(Button) findViewById(R.id.sing_or_register);
        login=(Button)findViewById(R.id.Login);
        requestQueue = Volley.newRequestQueue(this);
        login.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent=new Intent(v.getContext(),LoginActivity.class);
                        startActivity(myIntent);


                    }
                }
        );
        register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    String finUrl=URL+"?email="+email.getText()+"&password="+password.getText()+"&username="+username.getText();
                    request = new StringRequest(Request.Method.GET, finUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.getString("Status").equals("OK"))
                                {
                                    Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_SHORT).show();

                                    new FileHandler().saveFile("token.txt",jsonObject.getString("Token"),view);

                                    startActivity(new Intent(getApplicationContext(),MainLayerActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    requestQueue.add(request);



                }

        });







    }




}
