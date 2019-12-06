package com.example.gp2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

public class ModifyActivity extends AppCompatActivity {


    private static final int SEARCH_ACTIVITY_REQUEST_CODE = 42;
    private static final int MAIN_ACTIVITY_REQUEST_CODE=42;

    //Members
    private Button mButton;
    private EditText mPicInput;
    private EditText mMsgInput;
    private Button mReturnButton;
    private ImageView mImageUrl;
    private Button mLogOutButton;

    //Response Request
    private RequestQueue queue;
    private Boolean Connected;
    private String message;

    //Intent
    private String heyUserName;
    private String heyUserPassword;


    //File
     ImageView imageview;
    String imagepath;
    File sourceFile;
    int totalSize = 0;
    String FILE_UPLOAD_URL = "http://www.example.com/upload/UploadToServer.php";
    LinearLayout uploader_area;
    LinearLayout progress_area;
    public DonutProgress donut_progress;
    private static final int REQUEST_WRITE_STORAGE = 112;
    Uri fullPhotoUri;
    Bitmap bitmap;
    Bitmap photo;


//______________________________________________________________________________________________________________________________________________________________________________________
//                   ON CREATE
//______________________________________________________________________________________________________________________________________________________________________________________

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_modify);


        mPicInput = (EditText) findViewById(R.id.ModifyPic);
        mMsgInput = (EditText) findViewById(R.id.ModifyMsg);
        mButton = (Button) findViewById(R.id.modifyButton);
        mReturnButton = (Button) findViewById(R.id.returnButton);
       // mImageUrl = (ImageView) findViewById(R.id.imageUrl);
        mLogOutButton = (Button) findViewById(R.id.logoutButton);
        queue = Volley.newRequestQueue(this);



        uploader_area = (LinearLayout) findViewById(R.id.uploader_area);
        progress_area = (LinearLayout) findViewById(R.id.progress_area);
        Button select_button = (Button) findViewById(R.id.button_selectpic);
        Button upload_button = (Button) findViewById(R.id.button_upload);
        donut_progress = (DonutProgress) findViewById(R.id.donut_progress);
        imageview = (ImageView) findViewById(R.id.imageview);


        // ---------------------------------------------------------- PERMISSION ----------------------------------------------------------

        Boolean hasPermission = (ContextCompat.checkSelfPermission(ModifyActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(ModifyActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }else {

        }


        // String url = "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png";
        //Picasso.get().load(url).into(mImageUrl);



        // ---------------------------------------------------------- GET INTENT ----------- PASSATION DES VALEURS DU HEYUSER ----------------------------------------------------------
        Intent intent = getIntent();

        if (intent.hasExtra("Name")){
            heyUserName = intent.getExtras().getString("Name");
            Log.d("heyUserNameModify", heyUserName);
        }

        if (intent.hasExtra("Password")){
            heyUserPassword = intent.getExtras().getString("Password");
            Log.d("heyUserPasswordModify", heyUserPassword);
        }

        // ---------------------------------------------------------- MODIF DES INPUTS ---------------------------------------------------------- ----------------------------------------------------------
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

        // ----------------------------------------------------------ENVOI DES MODIFICATIONS ---------------------------------------------------------- ----------------------------------------------------------
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    String URL = "http://192.168.8.101:8080/ModifyHeyUserSettings";


                    JSONObject jsonObject1 = new JSONObject();
                    JSONObject auth = new JSONObject();
                    JSONObject profil = new JSONObject();
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "file");

                    Log.d("lol", file.toString());



                    auth.put("heyUserName", heyUserName);
                    auth.put("heyUserPassword", heyUserPassword);
                    profil.put("heyUserMessage", mMsgInput.getText().toString());

                    jsonObject1.put("heyUserAuthentication", auth);
                    jsonObject1.put("heyUserProfil", profil);

                    JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.POST, URL, jsonObject1, new Response.Listener<JSONObject>() {
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



        // ---------------------------------------------------------- RETOUR AU SEARCH  ----------------------------------------------------------
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registeryActivityIntent = new Intent(ModifyActivity.this, SearchActivity.class);
                registeryActivityIntent.putExtra("Name", heyUserName);
                registeryActivityIntent.putExtra("Password", heyUserName);
                startActivityForResult(registeryActivityIntent, SEARCH_ACTIVITY_REQUEST_CODE);


            }
        });

        //LOG OUT
        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOutActivityIntent = new Intent(ModifyActivity.this, SearchActivity.class);
                startActivityForResult(logOutActivityIntent, MAIN_ACTIVITY_REQUEST_CODE);

            }
        });


        //---------------------------------------------------------- SELECT  ----------------------------------------------------------

        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*"); // intent.setType("video/*"); to select videos to upload
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                Log.d("intent",""+ intent.getExtras());

            }
        });



        // ---------------------------------------------------------- UPLOAD  ----------------------------------------------------------
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagepath != null) {
                   /* try {

                        MultipartBody.create()

                        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                        multipartEntity.addPart("Title", new StringBody("Title"));
                        multipartEntity.addPart("Nick", new StringBody("Nick"));
                        multipartEntity.addPart("Email", new StringBody("Email"));
                        multipartEntity.addPart("Description", new StringBody(Settings.SHARE.TEXT));
                        multipartEntity.addPart("Image", new FileBody(image));
                        httppost.setEntity(multipartEntity);

                        mHttpClient.execute(httppost, new PhotoUploadResponseHandler());

                    } catch (Exception e) {
                        Log.e(ServerCommunication.class.getName(), e.getLocalizedMessage(), e);
                    }
*/

                }
            }
        });

    }//Oncreate




    // ---------------------------------------------------------- STORAGE PERMISSION----------------------------------------------------------
   @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
                } else
                {
                    Toast.makeText(ModifyActivity.this, "You must give access to storage.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    // ---------------------------------------------------------- ACTIVITYRESULT  ----------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            fullPhotoUri = data.getData();
//             photo = (Bitmap) data.getExtras().get("intent");
            Log.d("ResultCode", ""+ data.getData());
            imageview.setImageURI(fullPhotoUri);
            Log.d("image :", ""+ data);
            imagepath = data.getDataString();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);
                Log.d("BM", ""+bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(fullPhotoUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

//            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.imageview);
            imageView.setImageBitmap(photo);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fullPhotoUri);

            } catch (IOException e) {
                Log.d("BITMAP","error");
                e.printStackTrace();
            }



        }

String charset = "UTF-8";
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "file");
        String requestURL = "http://192.168.8.102:8080/upload";
        //MultipartRequest multipartRequest = new MultipartRequest();

    }


// ---------------------------------------------------------- ---------------------------------------------------------- ---------------------------------------------------------- ----------------------------------------------------------

    private class UploadFileToServer extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            Log.d("uploadfile onPreExecute","launch");
            // setting progress bar to zero
            donut_progress.setProgress(0);
            uploader_area.setVisibility(View.GONE); // Making the uploader area screen invisible
            progress_area.setVisibility(View.VISIBLE); // Showing the stylish material progressbar
            sourceFile = new File(imagepath);
            totalSize = (int)sourceFile.length();
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            Log.d("uploadfile onProgressU","launch");
            Log.d("PROG", progress[0]);
            donut_progress.setProgress(Integer.parseInt(progress[0])); //Updating progress
        }

        @Override
        protected String doInBackground(String... args) {
            Log.d("uploadfile doInBack","launch");
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection connection = null;
            String fileName = sourceFile.getName();
            Log.d("hello", sourceFile.getName());

            try {
                Log.d("uploadfile Post","launch");
                connection = (HttpURLConnection) new URL("http://192.168.8.102:8080/upload").openConnection();
                connection.setRequestMethod("POST");
                String boundary = "---------------------------boundary";
                String tail = "\r\n--" + boundary + "--\r\n";
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                connection.setDoOutput(true);

                String metadataPart = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                        + "" + "\r\n";

                String fileHeader1 = "--" + boundary + "\r\n"
                        + "Content-Disposition: form-data; name=\"fileToUpload\"; filename=\""
                        + fileName + "\"\r\n"
                        + "Content-Type: application/octet-stream\r\n"
                        + "Content-Transfer-Encoding: binary\r\n";

                long fileLength = sourceFile.length() + tail.length();
                String fileHeader2 = "Content-length: " + fileLength + "\r\n";
                String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
                String stringData = metadataPart + fileHeader;

                long requestLength = stringData.length() + fileLength;
                connection.setRequestProperty("Content-length", "" + requestLength);
                connection.setFixedLengthStreamingMode((int) requestLength);
                connection.connect();

                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(stringData);
                out.flush();

                int progress = 0;
                int bytesRead = 0;
                byte buf[] = new byte[1024];
                BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(sourceFile));
                while ((bytesRead = bufInput.read(buf)) != -1) {
                    // write output
                    out.write(buf, 0, bytesRead);
                    out.flush();
                    progress += bytesRead; // Here progress is total uploaded bytes

                    publishProgress(""+(int)((progress*100)/totalSize)); // sending progress percent to publishProgress
                }

                // Write closing boundary and close stream
                out.writeBytes(tail);
                out.flush();
                out.close();

                // Get server response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder builder = new StringBuilder();
                while((line = reader.readLine()) != null) {
                    builder.append(line);
                }

            } catch (Exception e) {
                // Exception
            } finally {
                if (connection != null) connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("Response", "Response from server: " + result);
            super.onPostExecute(result);
        }

    }

}//Main


