package com.example.appnutrimais.dietas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import com.example.appnutrimais.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NovaDietaActivity extends AppCompatActivity {

    String urlWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/insertDietas.php"; //arquivo que vai consumir
    String urlWebServicesProducao = "http://www.nutrimais.com.br/pasta/arquivo.php";

    //objeto para requisições
    StringRequest stringRequest;
    RequestQueue requestQueue;

    Button btnCriarDieta;
    EditText descricao, vencimento, paciente;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_dieta);

        requestQueue = Volley.newRequestQueue(this);

        btnCriarDieta = findViewById(R.id.btnCriarDieta);
        descricao = findViewById(R.id.etxtDescricao);
        vencimento = findViewById(R.id.etxtVencimento);
        paciente = findViewById(R.id.etxtPaciente);

        btnCriarDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDiet();
            }
        });


    }

    private void CreateDiet() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlWebServicesDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogLogin", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean cadastrado = jsonObject.getBoolean("cadastrado"); //nome no arquivo php

                            if(!cadastrado){
                                Toast.makeText(getApplicationContext(), "Não foi possível cadastrar", Toast.LENGTH_LONG).show();
                            }else{

                                (new AlertDialog.Builder(NovaDietaActivity.this))
                                        .setTitle(" Aviso ")
                                        .setMessage(" Cadastro efetuado com sucesso ")
                                        .setNeutralButton(" OK ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                NovaDietaActivity.this.finish();
                                            }
                                        })
                                        .show();

                            }

                        }catch (Exception e){

                            Log.v("LogLogin", e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LogLogin", error.getMessage());
                    }
                }) {
            @Override //passar o que o usuario digitou
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("descricao", descricao.getText().toString());
                params.put("vencimento", vencimento.getText().toString());
                params.put("idPaciente", paciente.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
