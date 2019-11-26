package com.example.gp2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {


    private static final int MODIFY_ACTIVITY_REQUEST_CODE = 42;

    //Members
    private Button mButton;
    private TextView mTextView;
    private TextView mText2View;
    private Button mModifySettingsButton;
    private TextView mUserNearU;
    private ImageView mImageUrl;


    //GPS
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double longitude;
    private double latitude;

    //Response Request
    private String UserNearUString = "Vous n'êtes pas seul ! ";

    //Intent
    private String heyUserName;
    private String heyUserPassword;



//______________________________________________________________________________________________________________________________________________________________________________________
//                   ON CREATE
//______________________________________________________________________________________________________________________________________________________________________________________


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textView);
        mText2View = (TextView) findViewById(R.id.text2View);
        mUserNearU = (TextView) findViewById(R.id.userNearU);
        mImageUrl = (ImageView) findViewById(R.id.imageUrl);
        mModifySettingsButton = (Button) findViewById(R.id.modifySettingButton);

       String url = "https://s.yimg.com/uu/api/res/1.2/Bexb6QOS4icfU0kXaC86oQ--~B/aD0yMDA7dz0yNjA7c209MTthcHBpZD15dGFjaHlvbg--/http://media.zenfs.com/fr_FR/News/TeleLoisirs/50306-que-devient-rowan-atkinson-alias-mr-bean.jpg";
       Picasso.get().load(url).into(mImageUrl);





//GET INTENT ----------- PASSATION DE VALEURS DU HEYUSER
        Intent intent = getIntent();

        if (intent.hasExtra("Name")){
            heyUserName = intent.getExtras().getString("Name");
            Log.d("heyUserNameSearchActiv", heyUserName);
        }

        if (intent.hasExtra("Password")){
            heyUserPassword = intent.getExtras().getString("Password");
            Log.d("heyUserPasswordSearchAc", heyUserPassword);
        }


        mText2View.setText("Où es-tu " + heyUserName + " ?");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //getInfo();
                mTextView.append("\n" + location.getLatitude() + " " + location.getLongitude()+ "  " + location.getAccuracy());
                mTextView.setText(location.getLatitude() + " " + location.getLongitude() + " " + location.getAccuracy());

                latitude = location.getLatitude();
                longitude = location.getLongitude();


                sendLocationUpdates();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

        };


        if (

                checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&

                        checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 10);
            return;

        } else {
            configureButton();
        }



    }




/*    public void downloadImage() throws IOException {
/*
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }

    }


    private void configureButton() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                    }, 10);
                    return;
                }
                sendLocationUpdates();
                locationManager.requestLocationUpdates("gps", 15000, 50, locationListener);
            }
        });


        mModifySettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registeryActivityIntent = new Intent(SearchActivity.this, ModifyActivity.class);
                registeryActivityIntent.putExtra("Name", heyUserName);
                registeryActivityIntent.putExtra("Password", heyUserPassword);
                startActivityForResult(registeryActivityIntent, MODIFY_ACTIVITY_REQUEST_CODE);

            }
        });

    }


    private void getInfo() {
        Log.d("getInfo", "launch");
        String url = "http://192.168.8.105:8080/getTest";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("Error.Response", error.toString());



                    }
                }
        ) { //no semicolon or coma
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }};
        Log.d("getjson", getRequest.toString());
        queue.add(getRequest);
    }


    private void sendLocationUpdates() {


        try {
            String URL = "http://192.168.8.105:8080/updateLocation";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("heyUserName", heyUserName);
            jsonBody.put("heyUserPassword", heyUserPassword);
            jsonBody.put("heyUserSearchRadius", "10000");
            jsonBody.put("heyUserLongitude", longitude);
            jsonBody.put("heyUserLatitude", latitude);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("response", response.toString());


                    // Process the JSON
                    try{
                        // Loop through the array elements
                        for(int i=0;i<response.length();i++){
                            // Get current json object
                            JSONArray usernear = response.getJSONArray("heyUserNearU");
                            JSONObject oneUserNear = usernear.getJSONObject(i);

                            Log.d("oneUser", oneUserNear.getString("heyUserName"));
                            UserNearUString = "Vous n'êtes pas seul ! ";
                            UserNearUString += (oneUserNear.getString("heyUserName")) + " est près de vous.";
                            Toast.makeText(getApplicationContext(), (oneUserNear.getString("heyUserName") +" est près de vous !"), Toast.LENGTH_SHORT).show();

                        }
                        mUserNearU.setText(UserNearUString);
                    }catch (JSONException e){
                        Log.d("erreurLoop", e.toString());
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

            Volley.newRequestQueue(this).add(jsonObject);


        } catch (JSONException e) {
            Log.d("error premier try", e.toString());
        }


    }



    @Override
    protected void onStop() {
        super.onStop();
        try {
            String URL = "http://192.168.8.105:8080/updateLocation";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("heyUserName", "eloise");
            jsonBody.put("heyUserPassword", "ADRAR1112");
            jsonBody.put("heyUserSearchRadius", "10000");
            jsonBody.put("heyUserLongitude", 71.000);
            jsonBody.put("heyUserLatitude", 25.000);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("responseDestroyed", response.toString());

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("errorDestroyed ", error.toString());

                }
            });

            Volley.newRequestQueue(this).add(jsonObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            String URL = "http://192.168.8.105:8080/updateLocation";
            JSONObject jsonBody = new JSONObject();

            jsonBody.put("heyUserName", "eloise");
            jsonBody.put("heyUserPassword", "ADRAR1112");
            jsonBody.put("heyUserSearchRadius", "10000");
            jsonBody.put("heyUserLongitude", 71.000);
            jsonBody.put("heyUserLatitude", 25.000);

            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("responseDestroyed", response.toString());

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("errorDestroyed ", error.toString());

                }
            });

            Volley.newRequestQueue(this).add(jsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}