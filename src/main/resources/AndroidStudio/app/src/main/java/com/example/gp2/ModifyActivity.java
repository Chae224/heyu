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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModifyActivity extends AppCompatActivity {


    private static final int SEARCH_ACTIVITY_REQUEST_CODE = 42;
//-------------MEMBERS------------------
    private Button mButton;
    private EditText mPicInput;
    private EditText mMsgInput;
    private Button mReturnButton;
    private ImageView mImageUrl;

    // RESPONSE REQUEST
    private RequestQueue queue;
    private Boolean Connected;
    private String message;

// ----------- Intent ------------------
    private String heyUserName;
    private String heyUserPassword;

//______________________________________________________________________________________________________________________________________________________________________________________
//                   ON CREATE
//______________________________________________________________________________________________________________________________________________________________________________________

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        mPicInput = (EditText) findViewById(R.id.ModifyPic);
        mMsgInput = (EditText) findViewById(R.id.ModifyMsg);
        mButton = (Button) findViewById(R.id.modifyButton);
        mReturnButton = (Button) findViewById(R.id.returnButton);
        mImageUrl = (ImageView) findViewById(R.id.imageUrl);

        queue = Volley.newRequestQueue(this);

        String url = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";
        Picasso.get().load(url).into(mImageUrl);

    //GET INTENT ----------- PASSATION DES VALEURS DU HEYUSER
        Intent intent = getIntent();

        if (intent.hasExtra("Name")){
            heyUserName = intent.getExtras().getString("Name");
            Log.d("heyUserNameModify", heyUserName);
        }

        if (intent.hasExtra("Password")){
            heyUserPassword = intent.getExtras().getString("Password");
            Log.d("heyUserPasswordModify", heyUserPassword);
        }

//MODIF DES INPUTS
        mPicInput.addTextChangedListener(new TextWatcher() {

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

         mMsgInput.addTextChangedListener(new TextWatcher() {
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

    //ENVOI DES MODIFICATIONS
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String URL = "http://192.168.8.105:8080/ModifyHeyUserSettings";
                    JSONObject jsonBody = new JSONObject();

                    jsonBody.put("heyUserName", heyUserName);
                    jsonBody.put("heyUserPassword", heyUserPassword);
                    jsonBody.put("heyUserPic", mPicInput.getText().toString());
                    jsonBody.put("heyUserMessage", mMsgInput.getText().toString());

                    JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("response", response.toString());
                            try{
                                Connected = response.getBoolean("connected");
                                message = response.getString("messageSent");

                                Log.d("es-tu connecté?", Connected.toString());
                                if(Connected) {
                                    Intent registeryActivityIntent = new Intent(ModifyActivity.this, SearchActivity.class);
                                    registeryActivityIntent.putExtra("Name", heyUserName);
                                    registeryActivityIntent.putExtra("Password", heyUserPassword);
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



    //RETOUR AU SEARCH
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registeryActivityIntent = new Intent(ModifyActivity.this, SearchActivity.class);
                registeryActivityIntent.putExtra("Name", heyUserName);
                registeryActivityIntent.putExtra("Password", heyUserName);
                startActivityForResult(registeryActivityIntent, SEARCH_ACTIVITY_REQUEST_CODE);


            }
        });

    }//Oncreate
}//Main
