package com.example.htmjs.finalproject;

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

public class ChangePassword extends AppCompatActivity {

    Button changePassword;
    EditText tunnus;
    EditText salasana;
    EditText salasana2;
    EditText salasana3;
    private static final String newPasswordUrl = "http://192.168.56.1/jerephp/Mobiiliohjelmointi/change_password.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        tunnus = findViewById(R.id.editTextTunnus);
        salasana = findViewById(R.id.editTextOldPassword);
        salasana2 = findViewById(R.id.editTextNewPassword);
        salasana3 = findViewById(R.id.editTextNewPassword2);

        changePassword = findViewById(R.id.btnChangePassword);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String k_tunnus = tunnus.getText().toString().trim();
                String oldPwd = salasana.getText().toString().trim();
                String pwd = salasana2.getText().toString().trim();
                String pwd2 = salasana3.getText().toString().trim();

                if (!k_tunnus.isEmpty() && !oldPwd.isEmpty() && !pwd.isEmpty() && !pwd2.isEmpty()) {
                    changePwd(k_tunnus, pwd);
                    Toast.makeText(ChangePassword.this, "Salasana vaihdettu", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ChangePassword.this, "Salasana on annettava kaksi kertaa", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void changePwd(final String kayttajatunnus, final String salasana) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, newPasswordUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Test", response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Test", "Error: " + error.getMessage());

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

        RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
        requestQueue.add(stringRequest);
    }
}
