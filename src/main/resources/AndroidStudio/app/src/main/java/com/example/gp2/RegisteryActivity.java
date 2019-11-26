package com.example.gp2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisteryActivity extends AppCompatActivity {

    private Button mButton;
    private static final int SEARCH_ACTIVITY_REQUEST_CODE = 42;
    private EditText mNameInput;
    private EditText mPasswordInput;
    private EditText mConfirmPasswordInput;
    private String message;
    private RequestQueue queue;
    private Boolean Connected;

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registery);

        mNameInput = (EditText) findViewById(R.id.RegisteryName);
        mPasswordInput = (EditText) findViewById(R.id.RegisteryPassword);
        mConfirmPasswordInput = (EditText) findViewById(R.id.RegisteryConfirmPassword);
        mButton = (Button) findViewById(R.id.button);

        queue = Volley.newRequestQueue(this);


        mNameInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String URL = "http://192.168.8.105:8080/registering";
                    JSONObject jsonBody = new JSONObject();
                    Log.d("mNameInput", mNameInput.getText().toString());

                    jsonBody.put("heyUserName", mNameInput.getText().toString());
                    jsonBody.put("heyUserPassword", mPasswordInput.getText().toString());
                    jsonBody.put("heyUserConfirmPassword", mConfirmPasswordInput.getText().toString());

                    JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response", response.toString());
                            // Process the JSON
                            try{
                                Connected = response.getBoolean("connected");
                                message = response.getString("messageSent");

                                Log.d("es-tu connecté?", Connected.toString());
                               if(Connected) {
                                    Toast.makeText(getApplicationContext(), (Connected.toString()), Toast.LENGTH_SHORT).show();

                                    Intent registeryActivityIntent = new Intent(RegisteryActivity.this, SearchActivity.class);
                                    registeryActivityIntent.putExtra("Name", mNameInput.getText().toString());
                                   registeryActivityIntent.putExtra("Password", mPasswordInput.getText().toString());
                                    startActivityForResult(registeryActivityIntent, SEARCH_ACTIVITY_REQUEST_CODE);
                                }else {
                                   Toast.makeText(getApplicationContext(), (message), Toast.LENGTH_SHORT).show();
                               }


                            }catch (JSONException e){
                                Log.d("erreur", e.toString());
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Log.d("error ", error.toString());

                        }
                    }){ //no semicolon or coma
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Content-Type", "application/json");
                            return params;
                        }};


                    queue.add(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }



        });

    }
}