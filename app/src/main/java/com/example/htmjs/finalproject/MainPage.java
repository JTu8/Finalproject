package com.example.htmjs.finalproject;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainPage extends AppCompatActivity {
    Button logout;
    Button startWorks;
    Button finishedWorks;
    SessionManager sessionManager;

    private static final String changeStateUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/update_work_state.php";
    private static String TAG_kayttajatunnus = "kayttajatunnus";
    String tunnus;
    private ArrayList<Works> _works;
    private WorksAdapter worksAdapter;
    private ListView lv;
    private static final String TAG = MainPage.class.getSimpleName();
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        sessionManager = new SessionManager(getApplicationContext());

        if (!sessionManager.isLoggedIn()) {
            logOut();
        }

        logout = findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        _works = new ArrayList<Works>();
        lv = findViewById(R.id.lvWorks);

        startWorks = findViewById(R.id.btnStarted);

        startWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, StartedWorks.class);
                intent.putExtra(TAG_kayttajatunnus, tunnus);
                startActivity(intent);
                finish();
            }
        });

        finishedWorks = findViewById(R.id.btnFinished);

        finishedWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, FinishedWorks.class);
                intent.putExtra(TAG_kayttajatunnus, tunnus);
                startActivity(intent);
                finish();
            }
        });

        getWorks();


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Objects.equals(intent.getAction(), Config.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFireBaseRegId();
                }
                else if(Objects.equals(intent.getAction(), Config.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };

    }

    public void getWorks() {

        Intent intent = getIntent();
        tunnus = intent.getStringExtra(TAG_kayttajatunnus);
        Toast.makeText(getApplicationContext(), "Tervetuloa: " + tunnus, Toast.LENGTH_LONG).show();

        String worksURL = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/get_new_works.php?kayttajatunnus=" + tunnus;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, worksURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Log.d("Hae uudet työt", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("works");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        _works.add(new Works(
                                object.getInt("ID"),
                                object.getString("tyo_ID"),
                                "Kuvaus: " + object.getString("kuvaus"),
                                "Valmistumispäivämäärä: " + object.getString("pvm"),
                                "Työn tila: " + object.getString("tila"),
                                object.getString("kayttajatunnus"),
                                object.getString("selitys"),
                                object.getString("tunnit")
                        ));
                    }
                    worksAdapter = new WorksAdapter(MainPage.this, _works);
                    lv.setAdapter(worksAdapter);
                } catch (JSONException ex) {
                    ex.getMessage();
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Hae uudet työt", "Error: " + error.getMessage());
            }
        }
        );

        Volley.newRequestQueue(this).add(stringRequest);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tyo_ID = _works.get(position).getTyo_ID();
                System.out.println(tyo_ID);
                changeState(tyo_ID);
                Toast.makeText(getApplicationContext(), "Työ aloitettu", Toast.LENGTH_LONG).show();
                worksAdapter.notifyDataSetChanged();


            }
        });


    }

    public void changeState(final String tyo_ID) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, changeStateUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Tila vaihdettu", response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Tila vaihdettu", "Error: " + error.getMessage());

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tyo_ID", tyo_ID);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainPage.this);
        requestQueue.add(stringRequest);
    }

    public void logOut() {
        sessionManager.setLogin(false);

        Intent intent = new Intent(MainPage.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void displayFireBaseRegId() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regID = preferences.getString("regID", null);

        Log.e(TAG, "Firebase reg ID: " + regID);

        if(!TextUtils.isEmpty(regID)) {

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));

        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu2:
                Intent intent = new Intent(MainPage.this, ChangePassword.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
