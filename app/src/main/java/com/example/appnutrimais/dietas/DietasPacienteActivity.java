package com.example.appnutrimais.dietas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnutrimais.R;
import com.example.appnutrimais.usuario.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DietasPacienteActivity extends AppCompatActivity {

    String urlWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/getDieta.php"; //arquivo que vai consumir
    String urlWebServicesProducao = "http://www.nutrimais.com.br/pasta/arquivo.php";

    //objeto para requisições
    StringRequest stringRequest;
    RequestQueue requestQueue;

    EditText etxtNomePaciente, etxtSobrenomePaciente, etxtDescricao, etxtVencimento;
    ImageView btnNextDieta, btnPreviousDieta;

    //arrays que recebem os arrays do banco de dados na consulta
    int i=0;
    JSONArray arrayDadosPorUsuario, jArray;

    Usuario usuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietas_paciente);

        requestQueue = Volley.newRequestQueue(this);


        usuario = getIntent().getExtras().getParcelable("infosUser");

        etxtNomePaciente = findViewById(R.id.etxtNomePaciente);
        etxtSobrenomePaciente = findViewById(R.id.etxtSobrenomePaciente);
        etxtDescricao = findViewById(R.id.etxtDietaPaciente);
        etxtVencimento = findViewById(R.id.etxtVencimentoDieta);

        btnNextDieta = findViewById(R.id.btnNextDieta);
        btnPreviousDieta = findViewById(R.id.btnPreviousDieta);

        getDietas();


    }

    private void getDietas() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlWebServicesDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogLogin", response);

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean encontrado = jsonObject.getBoolean("encontrado"); //nome no arquivo php

                            if(encontrado == false){
                                Toast.makeText(getApplicationContext(), "Dieta não encontrada", Toast.LENGTH_LONG).show();
                            }else{

                                jArray = jsonObject.getJSONArray("dados");

                                int qtddDietas = jArray.length();

                                Toast.makeText(getApplicationContext(), "Dietas encontradas: "+qtddDietas, Toast.LENGTH_LONG).show();

                                arrayDadosPorUsuario = (jArray.getJSONArray(i));
                                populaCamposDieta(arrayDadosPorUsuario);



                                btnNextDieta.setOnClickListener(new View.OnClickListener() {
                                    @SuppressLint("SuspiciousIndentation")
                                    @Override
                                    public void onClick(View v) {
                                        if(i < (qtddDietas-1))
                                            i++;
                                        try {
                                            arrayDadosPorUsuario = (jArray.getJSONArray(i));
                                            populaCamposDieta(arrayDadosPorUsuario);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                                btnPreviousDieta.setOnClickListener(new View.OnClickListener() {
                                    @SuppressLint("SuspiciousIndentation")
                                    @Override
                                    public void onClick(View v) {
                                        if(i > 0)
                                            i--;

                                        try {
                                            arrayDadosPorUsuario = (jArray.getJSONArray(i));
                                            populaCamposDieta(arrayDadosPorUsuario);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });



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
                params.put("idPaciente", String.valueOf(usuario.getId_usuario()));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void populaCamposDieta(JSONArray array){
        try {
            etxtNomePaciente.setText(array.get(1).toString());
            etxtSobrenomePaciente.setText(array.get(2).toString());
            etxtDescricao.setText(array.get(3).toString());
            etxtVencimento.setText(array.get(4).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}