package com.example.htmjs.finalproject;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private ProgressDialog progressDialog;

    EditText kayttajatunnus;
    EditText nimi;
    EditText salasana;
    EditText lisatieto;
    Button register;

    String KayttajatunnusHolder;
    String NimiHolder;
    String SalasanaHolder;
    String LisatietoHolder;

    private static String lisaaKayttajaUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/create_user.php";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        kayttajatunnus = findViewById(R.id.textUserName);
        nimi = findViewById(R.id.textName);
        salasana = findViewById(R.id.textPassword);
        lisatieto = findViewById(R.id.textInfo);

        register = findViewById(R.id.btnRekisteroidy);

        requestQueue = Volley.newRequestQueue(Register.this);

        progressDialog = new ProgressDialog(Register.this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Rekisteröidytään");
                progressDialog.show();

                GetValueFromEditText();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, lisaaKayttajaUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressDialog.dismiss();
                                Log.d("Test", response);
                                Toast.makeText(Register.this, "Rekisteröinti onnistui", Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {


                                progressDialog.dismiss();


                                Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {


                        Map<String, String> params = new HashMap<String, String>();


                        params.put("kayttajatunnus", KayttajatunnusHolder);
                        params.put("nimi", NimiHolder);
                        params.put("salasana", SalasanaHolder);
                        params.put("lisatieto", LisatietoHolder);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                requestQueue.add(stringRequest);
            }
        });

    }

    public void GetValueFromEditText() {

        KayttajatunnusHolder = kayttajatunnus.getText().toString().trim();
        NimiHolder = nimi.getText().toString().trim();
        SalasanaHolder = salasana.getText().toString().trim();
        LisatietoHolder = lisatieto.getText().toString().trim();
    }
}
