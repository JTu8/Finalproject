package com.example.htmjs.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StartedWorks extends AppCompatActivity {

    Button mainPage;

    private ArrayList<Works> _works;
    private WorksAdapter adapter;
    private ListView lv;
    private static String TAG_kayttajatunnus = "kayttajatunnus";
    private static String TAG_tyoID = "tyo_ID";
    String tunnus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_works);

        Intent intent = getIntent();
        tunnus = intent.getStringExtra(TAG_kayttajatunnus);

        _works = new ArrayList<Works>();
        lv = findViewById(R.id.lvStartedWorks);

        getStartedWorks();

        mainPage = findViewById(R.id.btnMainPage);

        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartedWorks.this, MainPage.class);
                intent.putExtra(TAG_kayttajatunnus, tunnus);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getStartedWorks() {

        Intent intent = getIntent();
        tunnus = intent.getStringExtra(TAG_kayttajatunnus);

        final String startedWorksUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/get_started_works.php?kayttajatunnus=" + tunnus;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, startedWorksUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Hae aloitetut työt", response);
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
                    adapter = new WorksAdapter(StartedWorks.this, _works);
                    lv.setAdapter(adapter);
                } catch (JSONException ex) {
                    ex.getMessage();
                    ex.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Hae työt", "Error: " + error.getMessage());
            }
        }
        );

        Volley.newRequestQueue(this).add(stringRequest);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tyo_ID = _works.get(i).getTyo_ID();
                Intent intent = new Intent(StartedWorks.this, FinishWork.class);
                intent.putExtra(TAG_tyoID, tyo_ID);
                startActivity(intent);
                finish();
            }
        });

    }

}
