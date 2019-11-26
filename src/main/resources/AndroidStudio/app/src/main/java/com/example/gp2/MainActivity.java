package com.example.gp2;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private ImageView mImageUrl;
    private static final int SEARCH_ACTIVITY_REQUEST_CODE = 42;
    private static final int REGISTERY_ACTIVITY_REQUEST_CODE = 42;
private Button mButton2;
    private EditText mNameInput;
    private EditText mPasswordInput;
    private Boolean isConnected;
    private String message;
    private RequestQueue queue;



   // @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        queue = Volley.newRequestQueue(this);
        mButton = (Button) findViewById(R.id.button);
        mImageUrl = (ImageView) findViewById(R.id.imageUrl);
        mNameInput = (EditText) findViewById(R.id.MainName);
        mPasswordInput = (EditText) findViewById(R.id.MainPassword);
        mButton2 = (Button) findViewById(R.id.button2);


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
                                               String URL = "http://192.168.8.105:8080/login";
                                               JSONObject jsonBody = new JSONObject();
                                               Log.d("mNameInputmainActivity", mNameInput.getText().toString());

                                               jsonBody.put("heyUserName", mNameInput.getText().toString());
                                               jsonBody.put("heyUserPassword", mPasswordInput.getText().toString());

                                               JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                                                   @Override
                                                   public void onResponse(JSONObject response) {
                                                       Log.d("response", response.toString());
                                                       // Process the JSON
                                                       try{
                                                           isConnected = response.getBoolean("connected");
                                                           message = response.getString("messageSent");

                                                           Log.d("es-tu connecté?", isConnected.toString());
                                                           if(isConnected) {
                                                               Toast.makeText(getApplicationContext(), (isConnected.toString()), Toast.LENGTH_SHORT).show();

                                                               Intent gameActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
                                                               gameActivityIntent.putExtra("Name", mNameInput.getText().toString());
                                                               gameActivityIntent.putExtra("Password", mPasswordInput.getText().toString());
                                                               startActivityForResult(gameActivityIntent, SEARCH_ACTIVITY_REQUEST_CODE);
                                                           }else{
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

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gameActivityIntent = new Intent(MainActivity.this, RegisteryActivity.class);
                startActivityForResult(gameActivityIntent, REGISTERY_ACTIVITY_REQUEST_CODE);

            }

        });
    }



/*    public void downloadImage() throws IOException {

        URL url = null;
        try {
            url = new URL("https://s.yimg.com/uu/api/res/1.2/Bexb6QOS4icfU0kXaC86oQ--~B/aD0yMDA7dz0yNjA7c209MTthcHBpZD15dGFjaHlvbg--/http://media.zenfs.com/fr_FR/News/TeleLoisirs/50306-que-devient-rowan-atkinson-alias-mr-bean.jpg");

        } catch (MalformedURLException e) {
            Log.d("badurl", e.toString());
        }
        try {
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageUrl.setImageBitmap(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL url = new URL("https://s.yimg.com/uu/api/res/1.2/Bexb6QOS4icfU0kXaC86oQ--~B/aD0yMDA7dz0yNjA7c209MTthcHBpZD15dGFjaHlvbg--/http://media.zenfs.com/fr_FR/News/TeleLoisirs/50306-que-devient-rowan-atkinson-alias-mr-bean.jpg");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

        httpConn.connect();
        int resCode = httpConn.getResponseCode();

        if (resCode == HttpURLConnection.HTTP_OK) {
            InputStream in = httpConn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            imageUrl.setImageBitmap(bitmap);
        }
    }*/




    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("GameActivity::onStop()");
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("GameActivity::onDestroy()");
    }



}
