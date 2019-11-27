package com.example.gp2;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.goodiebag.carouselpicker.CarouselPicker;

public class SearchActivity extends AppCompatActivity {


    private static final int MODIFY_ACTIVITY_REQUEST_CODE = 42;

    //Members
    private Button mButton;
    private TextView mTextView;
    private TextView mText2View;
    private Button mModifySettingsButton;
    private TextView mUserNearU;
    private ImageView mImageUrl;
    private SeekBar mSeekBar;
    private TextView mTextViewSeekBar;
    private CarouselPicker mCarouselPicker;

    //GPS
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double longitude;
    private double latitude;
    private double radius = 100;

    //Response Request
    private String UserNearUString = "Vous n'êtes pas seul ! ";

    //Intent
    private String heyUserName;
    private String heyUserPassword;


    List<CarouselPicker.PickerItem> mixItems = new ArrayList<>();

    //______________________________________________________________________________________________________________________________________________________________________________________
    //                   ON CREATE
    //______________________________________________________________________________________________________________________________________________________________________________________


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Find By View
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textView);
        mText2View = (TextView) findViewById(R.id.text2View);
        //mUserNearU = (TextView) findViewById(R.id.userNearU);
        mImageUrl = (ImageView) findViewById(R.id.imageUrl);
        mModifySettingsButton = (Button) findViewById(R.id.modifySettingButton);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mTextViewSeekBar = (TextView) findViewById(R.id.textViewSeekBar);

        mCarouselPicker = findViewById(R.id.mCarouselPicker);

        //CAROUSEL
        CarouselPicker.CarouselViewAdapter mixAdapter = new CarouselPicker.CarouselViewAdapter(this, mixItems, 0);
        mCarouselPicker.setAdapter(mixAdapter);
        mixAdapter.notifyDataSetChanged();


        sendLocationUpdates();

        // Initialize the textview with '0'.
        mTextViewSeekBar.setText("Covered: " + mSeekBar.getProgress() + "/" + mSeekBar.getMax());

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {




            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                radius = 10*Math.exp(progresValue)-10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mTextViewSeekBar.setText("Covered: " + radius + "/" + seekBar.getMax());

                sendLocationUpdates();
            }



        });



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
            sendLocationUpdates();
        }



    }



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
        Log.d("sendLocationUpdates","launch");

        try {
            String URL = "http://192.168.8.105:8080/updateLocation";
            JSONObject jsonObject1 = new JSONObject();
            JSONObject auth = new JSONObject();
            JSONObject location = new JSONObject();

            auth.put("heyUserName", heyUserName);
            auth.put("heyUserPassword", heyUserPassword);
            location.put("heyUserSearchRadius", radius);
            location.put("heyUserLongitude", longitude);
            location.put("heyUserLatitude", latitude);

            jsonObject1.put("heyUserAuthentication", auth);
            jsonObject1.put("heyUserLocation", location);




            JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonObject1, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("response", response.toString());
                    //UserNearUString = "Vous n'êtes pas seul ! ";

                    try{

                        for(int i=0;i<response.length();i++){



                            JSONArray usernear = response.getJSONArray("heyUserNearU");
                            JSONObject oneUserNear = usernear.getJSONObject(i);
                            Log.d("oneUser", oneUserNear.getString("heyUserName"));
                            mixItems.add(new CarouselPicker.TextItem(oneUserNear.getString("heyUserName"), 20));
                            CarouselPicker.CarouselViewAdapter mixAdapter = new CarouselPicker.CarouselViewAdapter(SearchActivity.this, mixItems, 0);
                            mCarouselPicker.setAdapter(mixAdapter);
                            mixAdapter.notifyDataSetChanged();
                            UserNearUString += (oneUserNear.getString("heyUserName")) + " est près de vous.";
                            Toast.makeText(getApplicationContext(), (oneUserNear.getString("heyUserName") +" est près de vous !"), Toast.LENGTH_SHORT).show();

                        }
                        //mUserNearU.setText(UserNearUString);



                    }catch (JSONException e){
                        Log.d("erreurLoop", e.toString());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("error ", error.toString());

                }
            }){
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
            jsonBody.put("heyUserSearchRadius", radius);
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
            jsonBody.put("heyUserSearchRadius", radius);
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