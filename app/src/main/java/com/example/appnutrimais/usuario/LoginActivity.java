package com.example.appnutrimais.usuario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnutrimais.R;
import com.example.appnutrimais.nutricionista.PainelNutricionistaActivity;
import com.example.appnutrimais.paciente.PainelPacienteActivity;
import com.example.appnutrimais.usuario.CriarContaActivity;
import com.example.appnutrimais.usuario.Usuario;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    String urlWebServicesDesenvolvimento = "http://nutrimaisapi.000webhostapp.com/apinutrimais/getUsuarios.php"; //arquivo que vai consumir
    String urlWebServicesProducao = "http://www.nutrimais.com.br/pasta/arquivo.php";

    //objeto para requisições
    StringRequest stringRequest;
    RequestQueue requestQueue;

    TextView criarConta;
    Button btnLogin;
    EditText etxtLogin;
    EditText etxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        btnLogin = findViewById(R.id.btnlogin);
        etxtLogin = findViewById(R.id.etxtLogin);
        etxtPassword = findViewById(R.id.etxtPassword);
        criarConta = findViewById(R.id.tvwCriarConta);

        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccaunt = new Intent(LoginActivity.this, CriarContaActivity.class);
                startActivity(createAccaunt);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validated = true;

                if(etxtLogin.getText().length() == 0){
                    etxtLogin.setError("Campo login obrigatório");
                    etxtLogin.requestFocus();
                    validated = false;
                }

                if(etxtPassword.getText().length() == 0){
                    etxtPassword.setError("Campo senha obrigatório");
                    etxtPassword.requestFocus();
                    validated = false;
                }

                if(validated){
                    Toast.makeText(getApplicationContext(),"Validando seus dados, por favor aguarde.", Toast.LENGTH_SHORT).show();
                    ValidateLogin();
                    Toast.makeText(getApplicationContext(),"Logou.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"FALHA LOGIN.", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
    private void ValidateLogin() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlWebServicesDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogLogin", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro"); //nome no arquivo php

                            if(isErro){
                                Toast.makeText(getApplicationContext(), "Dados inválidos", Toast.LENGTH_LONG).show();
                            }else{


                                int perfil = jsonObject.getInt("tipo_usuario");
                                String login = jsonObject.getString("login");
                                int idusuario = Integer.valueOf(jsonObject.getInt("idusuario"));
                                Usuario user = new Usuario(login, perfil, idusuario);

                                if(perfil == 1)
                                {
                                    Intent newScreen = new Intent(LoginActivity.this, PainelNutricionistaActivity.class);
                                    newScreen.putExtra("infosUser", user);
                                    newScreen.putExtra("perfil", perfil);
                                    startActivity(newScreen);

                                }
                                else if(perfil == 2)
                                {
                                    Intent newScreen = new Intent(LoginActivity.this, PainelPacienteActivity.class);
                                    newScreen.putExtra("infosUser", user);
                                    newScreen.putExtra("perfil", perfil);
                                    startActivity(newScreen);


                                }
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
                params.put("nome_usuario", etxtLogin.getText().toString());
                params.put("senha", etxtPassword.getText().toString());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}