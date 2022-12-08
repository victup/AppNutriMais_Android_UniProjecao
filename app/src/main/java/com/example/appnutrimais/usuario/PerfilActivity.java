package com.example.appnutrimais.usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnutrimais.chat.ChatActivity;
import com.example.appnutrimais.dietas.DietasActivity;
import com.example.appnutrimais.R;
import com.example.appnutrimais.dietas.DietasPacienteActivity;
import com.example.appnutrimais.nutricionista.PainelNutricionistaActivity;
import com.example.appnutrimais.paciente.PainelPacienteActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    String urlConsultaWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/getDadosUsuario.php"; //arquivo que vai consumir
    String urlUpdateWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/updateDadosUsuario.php"; //arquivo que vai consumir
    String urlApagarWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/deleteUsuario.php"; //arquivo que vai consumir
    String urlCepWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/apiViaCep.php"; //arquivo que vai consumir
    String urlWebServicesProducao = "http://www.nutrimais.com.br/pasta/arquivo.php";

    //objeto para requisições
    StringRequest stringRequest;
    RequestQueue requestQueue;

    private BottomNavigationView bottomNavigationView;

    Usuario usuario;
    Button btnAtualizarConta;
    EditText etxtNome, etxtSobrenome, etxtUsuario, etxtSenha, etxtCep, etxtEndereco, etxtNumero, etxtBairro, etxtCidade,etxtUf;
    ImageView imgCadeadoAberto;
    TextView opcApagarConta;
    int perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);



        usuario = getIntent().getExtras().getParcelable("infosUser");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);

        opcApagarConta = findViewById(R.id.tvwApagarConta);
        btnAtualizarConta = findViewById(R.id.btnAtualizarConta);
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
        imgCadeadoAberto = findViewById(R.id.imgCadeadoAberto);

        requestQueue = Volley.newRequestQueue(this);

        getInfoUser();

        setEnableEditTextProfile(false);
        initNavigation();
        imgCadeadoAberto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgCadeadoAberto.setVisibility(View.INVISIBLE);
                setEnableEditTextProfile(true);
            }
        });

        btnAtualizarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();

            }
        });


        etxtCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    CepApi();
                }
            }
        });

        opcApagarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new AlertDialog.Builder(PerfilActivity.this))
                        .setTitle("ATENÇÃO")
                        .setMessage("Tem certeza que deseja excluir sua conta? Você perderá seu acesso, incluindo dietas já cadastradas. Esse processo é irrevercível.")
                        .setPositiveButton(" Sim, Apagar ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               DeleteUser();
                                Intent telLogin = new Intent(PerfilActivity.this, LoginActivity.class);
                                finish();
                                startActivity(telLogin);

                            }
                        })
                        .setNeutralButton(" Cancelar ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }

    private void getInfoUser() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlConsultaWebServicesDesenvolvimento,
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

                                perfil = (jsonObject.getInt("tipo_usuario"));
                                etxtNome.setText(jsonObject.getString("nome")) ;
                                etxtSobrenome.setText(jsonObject.getString("sobrenome"));
                                etxtUsuario.setText(jsonObject.getString("login"));
                                etxtSenha.setText(jsonObject.getString("senha"));

                                etxtCep.setText(jsonObject.getString("cep"));
                                etxtEndereco.setText(jsonObject.getString("endereco"));
                                etxtNumero.setText(jsonObject.getString("numero"));
                                etxtBairro.setText(jsonObject.getString("bairro"));
                                etxtCidade.setText(jsonObject.getString("cidade"));
                                etxtUf.setText(jsonObject.getString("estado"));

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
                params.put("nome_usuario", usuario.getNome_usuario());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void updateUser() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlUpdateWebServicesDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogLogin", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean cadastrado = jsonObject.getBoolean("atualizado"); //nome no arquivo php

                            if(!cadastrado){
                                Toast.makeText(getApplicationContext(), "Não foi possível cadastrar", Toast.LENGTH_LONG).show();
                            }else{

                                (new AlertDialog.Builder(PerfilActivity.this))
                                        .setTitle(" Aviso ")
                                        .setMessage(" Dados atualizados com sucesso")
                                        .setNeutralButton(" OK ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                PerfilActivity.this.finish();
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
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void setEnableEditTextProfile(boolean enable){
        etxbEnable(etxtNome, enable);
        etxbEnable(etxtSobrenome, enable);
        etxbEnable(etxtUsuario, enable);
        etxbEnable(etxtSenha, enable);
        etxbEnable(etxtCep,enable);
        etxbEnable(etxtEndereco, enable);
        etxbEnable(etxtNumero, enable);
        etxbEnable(etxtBairro, enable);
        etxbEnable(etxtCidade, enable);
        etxbEnable(etxtUf, enable);

    }

    private void etxbEnable(EditText extb, boolean enable){
        extb.setEnabled(enable);
    }

    private void initNavigation()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        Intent telaHome;
                        if(perfil == 1){
                            telaHome = new Intent(PerfilActivity.this, PainelNutricionistaActivity.class);
                        }else{
                            telaHome = new Intent(PerfilActivity.this, PainelPacienteActivity.class);
                        }

                        telaHome.putExtra("infosUser", usuario);
                        telaHome.putExtra("perfil", perfil);
                        finish();
                        startActivity(telaHome);
                        return true;

                        case (R.id.menu_dietas):
                        Intent telaDietas;

                            if(perfil == 1){
                                telaDietas = new Intent(PerfilActivity.this, DietasActivity.class);
                            }else{
                                telaDietas = new Intent(PerfilActivity.this, DietasPacienteActivity.class);
                            }

                        telaDietas.putExtra("infosUser", usuario);
                        telaDietas.putExtra("perfil", perfil);

                        finish();
                        startActivity(telaDietas);
                        return true;

                    case (R.id.menu_chat):
                        Intent telaChat = new Intent(PerfilActivity.this, ChatActivity.class);
                        telaChat.putExtra("infosUser", usuario);
                        telaChat.putExtra("perfil", perfil);
                        finish();
                        startActivity(telaChat);
                        return true;
                }
                return false;
            }
        });

    }

    private void DeleteUser() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlApagarWebServicesDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogLogin", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean deletado = jsonObject.getBoolean("status-delete-usuario"); //nome no arquivo php

                            if(!deletado){
                                Toast.makeText(getApplicationContext(), "Não foi possível apagar sua conta. Tente mais tarde.", Toast.LENGTH_LONG).show();
                            }else{


                                (new AlertDialog.Builder(PerfilActivity.this))
                                        .setTitle(" Aviso ")
                                        .setMessage(" Sua conta foi apagada com sucesso.")
                                        .setNeutralButton(" OK ", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) { 
                                                PerfilActivity.this.finish();
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
                params.put("nome_usuario", usuario.getNome_usuario());
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