package com.example.htmjs.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FinishedWorks extends AppCompatActivity {

    private ArrayList<Works> _works;
    private FinishedWorksAdapter adapter;
    private ListView lv;
    private static String TAG_kayttajatunnus = "kayttajatunnus";
    private static String TAG_tyoID = "tyo_ID";
    String tunnus;

    Button toMainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_works);

        Intent intent = getIntent();
        tunnus = intent.getStringExtra(TAG_kayttajatunnus);

        _works = new ArrayList<Works>();
        lv = findViewById(R.id.lvFinishedWorks);

        getFinishedWorks();

        toMainPage = findViewById(R.id.btnToMainPage);

        toMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinishedWorks.this, MainPage.class);
                intent.putExtra(TAG_kayttajatunnus, tunnus);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getFinishedWorks() {

        Intent intent = getIntent();
        tunnus = intent.getStringExtra(TAG_kayttajatunnus);

        final String finishedWorksUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/get_finished_works.php?kayttajatunnus=" + tunnus;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, finishedWorksUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Hae valmiit työt", response);
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
                                "Työn selitys: " + object.getString("selitys"),
                                "Tehdyt tunnit: " + object.getString("tunnit")
                        ));



                    }
                    adapter = new FinishedWorksAdapter(FinishedWorks.this, _works);
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


    }
}
