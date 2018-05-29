package com.example.htmjs.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
     Button register;
     Button login;
     EditText kayttajatunnus;
     EditText salasana;
     private static String loginUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/login.php";
     private static String TAG_kayttajatunnus = "kayttajatunnus";
     ProgressDialog progressDialog;
     SessionManager sessionManager;
     //RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        kayttajatunnus = findViewById(R.id.tbUserName);
        salasana = findViewById(R.id.tbPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, MainPage.class);
            startActivity(intent);
            finish();
        }

        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tunnus = kayttajatunnus.getText().toString().trim();
                String pwd = salasana.getText().toString().trim();

                if (!tunnus.isEmpty() && !pwd.isEmpty()) {
                    checkLogin(tunnus, pwd);
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    intent.putExtra(TAG_kayttajatunnus, tunnus);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this, "Syötä käyttäjätunnus ja/tai salasana", Toast.LENGTH_LONG).show();
                }
            }
        });

        register = findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItem:
                Intent intent = new Intent(getApplicationContext(), Toiminnot.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkLogin(final String kayttajatunnus, final String salasana) {

        String tag_string_tag = "req_login";

        progressDialog.setMessage("Kirjaudutaan");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.d("Kirjaudutaan", response);
                    sessionManager.setLogin(true);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Test", "Error: " + error.getMessage());
                progressDialog.dismiss();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("kayttajatunnus", kayttajatunnus);
                params.put("salasana", salasana);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}
