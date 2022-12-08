package com.example.appnutrimais.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NovaConversaActivity extends AppCompatActivity {

    String urlWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/chat/criarConversa.php"; //arquivo que vai consumir


    //objeto para requisições
    StringRequest stringRequest;
    RequestQueue requestQueue;


    ImageButton btn_criar_conversa;
    Usuario usuario;
    EditText idNovaConversa;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_conversa);

        requestQueue = Volley.newRequestQueue(this);

        usuario = getIntent().getExtras().getParcelable("infosUser");
        idNovaConversa = findViewById(R.id.idNovaConversa);

        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Iniciar Nova Conversa");


        configClicks();

        btn_criar_conversa = findViewById(R.id.btn_criar_conversa);
        btn_criar_conversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarConversa();
            }
        });

    }
    private void configClicks(){
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
    }

    private void criarConversa() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlWebServicesDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogRespsota", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean sucesso = jsonObject.getBoolean("sucesso"); //nome no arquivo php


                            if(!sucesso){
                                Toast.makeText(getApplicationContext(), "Não foi possivel criar a conversa", Toast.LENGTH_LONG).show();
                            }else{
                                Intent telaChat = new Intent(NovaConversaActivity.this, ChatActivity.class);
                                telaChat.putExtra("infosUser",usuario);
                                finish();
                                startActivity(telaChat);
                            }

                        }catch (Exception e){

                            Log.v("LogCatch", e.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LogErroVolley", error.getMessage());
                    }
                }) {
            @Override //passar o que o usuario digitou
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("idremetente", String.valueOf(usuario.getId_usuario()));
                params.put("iddestinatario", String.valueOf(idNovaConversa.getText()));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

}