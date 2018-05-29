package com.example.htmjs.finalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FinishWork extends AppCompatActivity {

    private CheckBox checkBox;
    private Spinner suoriteryhma;
    private Spinner suoritteet;
    private Spinner yksikko;
    private EditText maara;
    private static String TAG_tyoID = "tyo_ID";
    //String tyo_ID;
    private ArrayList<SuoriteRyhma> _suoriteRyhma;
    private ArrayList<Suoritteet> _suoritteet;
    private SuoriteAdapter adapter;
    private SuoritteetAdapter suoritteetAdapter;
    private static final String finishWorkUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/finish_work.php";

    Button finish;
    EditText selite;
    EditText tunnit;

    Button uploadImage;
    ImageView imageView;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 99;
    private static final int CAMERA_PIC_REQUEST = 100;
    Bitmap bitmap;
    private static final String uploadImageUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/upload_photo.php";
    Button openCamera;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_work);

        Intent intent = getIntent();
        final String tyo_ID = intent.getStringExtra(TAG_tyoID);

        selite = findViewById(R.id.tbSelite);
        tunnit = findViewById(R.id.tbTehdytTunnit);

        finish = findViewById(R.id.btnFinish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sel = selite.getText().toString().trim();
                String hours = tunnit.getText().toString().trim();

                if(!sel.isEmpty() && !hours.isEmpty()) {
                    updateWorks(tyo_ID, sel, hours);
                    Toast.makeText(getApplicationContext(), "Työ merkattu valmiiksi", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Tunnit tai selitys puuttuvat", Toast.LENGTH_LONG).show();
                }
            }
        });

        _suoriteRyhma = new ArrayList<SuoriteRyhma>();
        _suoritteet = new ArrayList<Suoritteet>();

        suoriteryhma = findViewById(R.id.spinnerSuoriteryhma);
        suoriteryhma.setVisibility(View.INVISIBLE);
        suoritteet = findViewById(R.id.spinnerSuoritteet);
        suoritteet.setVisibility(View.INVISIBLE);
        yksikko = findViewById(R.id.spinnerYksikko);
        yksikko.setVisibility(View.INVISIBLE);
        maara = findViewById(R.id.editTextMaara);
        maara.setVisibility(View.INVISIBLE);


        checkBox = findViewById(R.id.checkBoxSuorite);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()) {

                    suoriteryhma.setVisibility(View.VISIBLE);
                    suoritteet.setVisibility(View.VISIBLE);
                    yksikko.setVisibility(View.VISIBLE);
                    maara.setVisibility(View.VISIBLE);
                    getSuoriteRyhmat();

                }
                else {
                    suoriteryhma.setVisibility(View.INVISIBLE);
                    suoritteet.setVisibility(View.INVISIBLE);
                    yksikko.setVisibility(View.INVISIBLE);
                    maara.setVisibility(View.INVISIBLE);
                }
            }
        });

        uploadImage = findViewById(R.id.btnSendToServer);
        imageView = findViewById(R.id.imgView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(tyo_ID);
            }
        });


        if(Build.VERSION.SDK_INT >= 23) {
            if(checkPermission()) {

            }
            else {
                requestPermission();
            }
        }

        openCamera = findViewById(R.id.btnOpenCamera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });

    }



    public void updateWorks(final String tyo_ID, final String selite, final String tunnit) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, finishWorkUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Työn viimeistely", response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Työn viimeistely", "Error: " + error.getMessage());

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tyo_ID", tyo_ID);
                params.put("selitys", selite);
                params.put("tunnit", tunnit);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FinishWork.this);
        requestQueue.add(stringRequest);
    }

    public void getSuoriteRyhmat() {


        final String suoriteRyhmaUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/get_suoritteryhma.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, suoriteRyhmaUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Hae suoriteryhmät", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("workgroup");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        _suoriteRyhma.add(new SuoriteRyhma(
                                object.getString("workgroup_name")
                        ));
                    }
                    adapter = new SuoriteAdapter(FinishWork.this, _suoriteRyhma);
                    suoriteryhma.setAdapter(adapter);
                    suoriteryhma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String suoritrRyhma = _suoriteRyhma.get(position).getWorkgroup_name();
                            getSuoritteet(suoritrRyhma);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException ex) {
                    ex.getMessage();
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Hae suoriteryhmät", "Error " + error.getMessage());
            }
        }
        );

        Volley.newRequestQueue(this).add(stringRequest);

    }

    public void updateSuoritteet() {

    }

    public void getSuoritteet(final String _suoriteRyhma) {

        final String suorittetUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/get_suoritteet.php?workgroup_name=" + _suoriteRyhma;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, suorittetUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Hae suoritteet", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("workstatus");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        _suoritteet.add(new Suoritteet(
                                object.getInt("ID"),
                                object.getString("suorite"),
                                object.getString("yksikko"),
                                object.getInt("maara"),
                                object.getString("workgroup_name"),
                                object.getString("tyo_ID")


                        ));

                    }
                    suoritteetAdapter = new SuoritteetAdapter(FinishWork.this, _suoritteet);
                    suoritteet.setAdapter(suoritteetAdapter);
                    suoritteet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } catch (JSONException ex) {
                    ex.printStackTrace();
                    ex.getMessage();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Suoritteiden haku", "Error: " + error.getMessage());
            }
        }
        );

        Volley.newRequestQueue(this).add(stringRequest);
    }



    private void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(FinishWork.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(getApplicationContext(), "Anna lupa", Toast.LENGTH_LONG).show();
        }
        else {
            ActivityCompat.requestPermissions(FinishWork.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(FinishWork.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Lupa myönnetty", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Lupa evätty", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException ex) {
                ex.printStackTrace();
                ex.getMessage();
            }
        }




    }

    public void uploadImage(final String tyo_ID) {
        RequestQueue requestQueue = Volley.newRequestQueue(FinishWork.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadImageUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Kuvan lisäys tietokantaan", response);
                Toast.makeText(getApplicationContext(), "Kuva lisätty tietokantaan", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Kuvan lisäys tietokantaan", "Error: " + error.getMessage());
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                String images = getStringImage(bitmap);
                Log.d("Kuvan lisäys", images);
                param.put("image_path", images);
                param.put("tyo_ID", tyo_ID);

                return param;
            }
        };

        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap) {
        Log.d("Bitmap", "" + bitmap);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }
}
