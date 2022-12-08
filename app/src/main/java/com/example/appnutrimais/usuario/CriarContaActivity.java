package com.example.appnutrimais.usuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnutrimais.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CriarContaActivity extends AppCompatActivity {

    String urlWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/insertUsuario.php"; //arquivo que vai consumir
    String urlCepWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/apiViaCep.php"; //arquivo que vai consumir
    String urlWebServicesProducao = "http://www.nutrimais.com.br/pasta/arquivo.php";

    //objeto para requisições
    StringRequest stringRequest;
    RequestQueue requestQueue;

    Button btnCriarConta;
    EditText etxtNome, etxtSobrenome, etxtUsuario, etxtSenha, etxtCep, etxtEndereco, etxtNumero, etxtBairro, etxtCidade,etxtUf;
    RadioButton rbNutricionista, rbPaciente;
    String tipo_usuario;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        btnCriarConta = findViewById(R.id.btnCriarConta);
        etxtNome = findViewById(R.id.etxtNome);
        etxtSobrenome = findViewById(R.id.etxtSobrenome);
        etxtUsuario = findViewById(R.id.etxtUsuario);
        etxtSenha = findViewById(R.id.etxtSenha);
        etxtCep = findViewById(R.id.etxtCep);
        etxtEndereco = findViewById(R.id.etxtEndereco);
        etxtNumero = findViewById(R.id.etxtNumero);
        etxtBairro = findViewById(R.id.etxtBairro);
        etxtCidade = findViewById(R.id.etxtCidade);
        etxtUf = findViewById(R.id.etxtUf);
        rbNutricionista = findViewById(R.id.rbNuticionista);
        rbPaciente = findViewById(R.id.rbPaciente);


        requestQueue = Volley.newRequestQueue(this);

        etxtCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    CepApi();
                }
            }
        });

        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbNutricionista.isChecked())
                    tipo_usuario = "1";
                else
                    tipo_usuario = "2";

                CreateAccaunt();
            }
        });


    }

    private void CreateAccaunt() {
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

                                (new AlertDialog.Builder(CriarContaActivity.this))
                                        .setTitle(" Aviso ")
                                        .setMessage(" Conta criada com sucesso ")
                                        .setNeutralButton(" OK ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                CriarContaActivity.this.finish();
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
                params.put("cep", etxtCep.getText().toString());
                params.put("endereco", etxtEndereco.getText().toString());
                params.put("numero", etxtNumero.getText().toString());
                params.put("bairro", etxtBairro.getText().toString());
                params.put("cidade", etxtCidade.getText().toString());
                params.put("estado", etxtUf.getText().toString());

                params.put("nome_usuario", etxtUsuario.getText().toString());
                params.put("senha", etxtSenha.getText().toString());
                params.put("nome", etxtNome.getText().toString());
                params.put("sobrenome", etxtSobrenome.getText().toString());
                params.put("tipo_usuario", tipo_usuario);
                return params;

            }
        };

        requestQueue.add(stringRequest);
    }

    private void CepApi(){
        stringRequest = new StringRequest(Request.Method.POST, urlCepWebServicesDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogLogin", response);

                        try{

                            JSONObject cepApi = new JSONObject(response);

                            PreencheCampusEnderecoAPI(cepApi);


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
                params.put("cep_informado", etxtCep.getText().toString());
                return params;

            }
        };

        requestQueue.add(stringRequest);
    }

    private void PreencheCampusEnderecoAPI(JSONObject cepApi){
        try {
            etxtCep.setText(cepApi.getString("cep").toString());
            etxtEndereco.setText(cepApi.getString("endereco").toString());
            etxtBairro.setText(cepApi.getString("bairro").toString());
            etxtCidade.setText(cepApi.getString("cidade").toString());
            etxtUf.setText(cepApi.getString("uf").toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}