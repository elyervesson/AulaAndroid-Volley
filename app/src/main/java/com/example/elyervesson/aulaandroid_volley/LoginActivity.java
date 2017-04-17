package com.example.elyervesson.aulaandroid_volley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.elyervesson.aulaandroid_volley.cdp.CustomJsonArrayRequest;
import com.example.elyervesson.aulaandroid_volley.cdp.CustomJsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {
    private RequestQueue requestQueue;
    private Map<String, String> params;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        url = "http://www.gowalk.com.br/package/android/android-volley.php";
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        requestQueue = Volley.newRequestQueue(LoginActivity.this);
    }


    //CALLS VOLLEY
    public void callByJsonObjectRequest(View view){
//        Essa forma não consegue enviar dados apenas recebe em JSON
//        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener< JSONObject>(){
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//            }
//        }, new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });

        params = new HashMap<String, String>();
        // Caso o servidor precise que seja enviado um Json podemos pegar apenas um dos campos e colocar:
        // params.put("JSON", stringJson); ao invez de campo por campo
        // Não esta sendo feita nenhuma validação
        params.put("email", editTextEmail.getText().toString());
        params.put("password", editTextPassword.getText().toString());
        params.put("method", "web-data-jor"); //Explicação dessa linha em 22:00 min

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST, url, params,
            new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("Script", "SUCCESS: " + response);
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        request.setTag("tag");
        requestQueue.add(request);
    }

    public void callByJsonArrayRequest(View view){
        params = new HashMap<String, String>();
        params.put("email", editTextEmail.getText().toString());
        params.put("password", editTextPassword.getText().toString());
        params.put("method", "web-data-jar"); //Explicação dessa linha em 27:00 min

        CustomJsonArrayRequest request = new CustomJsonArrayRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("Script", "SUCCESS: " + response);
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        request.setTag("tag");
        requestQueue.add(request);

    }

    public void callByStringRequest(View view){
        StringRequest request = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Log.i("Script", "SUCCESS: " + response);
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){

                @Override
                public Map<String,String> getParams() throws AuthFailureError {
                    params = new HashMap<String, String>();
                    params.put("email", editTextEmail.getText().toString());
                    params.put("password", editTextPassword.getText().toString());
                    params.put("method", "web-data-sr"); //Explicação dessa linha em 27:12 min

                    return params;
                }
        };

        request.setTag("tag");
        requestQueue.add(request);
    }

    // Caso esteja sendo feita uma requisição e a tela seja trocada, a activity é destruida e reconstruida
    @Override
    public void onStop() {
        super.onStop();

        requestQueue.cancelAll("tag");
    }
}

