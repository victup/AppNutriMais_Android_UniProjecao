
package com.example.appnutrimais.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appnutrimais.R;
import com.example.appnutrimais.dietas.DietasActivity;
import com.example.appnutrimais.nutricionista.PainelNutricionistaActivity;
import com.example.appnutrimais.paciente.PainelPacienteActivity;
import com.example.appnutrimais.usuario.PerfilActivity;
import com.example.appnutrimais.usuario.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationViewPrincipal;

    String urlWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/chat/getConversas.php"; //arquivo que vai consumir
    String urlWebServicesProducao = "http://www.nutrimais.com.br/pasta/arquivo.php";

    //objeto para requisições
    StringRequest stringRequest;
    RequestQueue requestQueue;

    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private ArrayList<Chat> itens = new ArrayList<Chat>();
    Usuario usuario;
    int perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bottomNavigationViewPrincipal = findViewById(R.id.bottom_navigation);

        bottomNavigationViewPrincipal.setSelectedItemId(R.id.menu_chat);

        usuario = getIntent().getExtras().getParcelable("infosUser");
        perfil = getIntent().getExtras().getInt("perfil");

        requestQueue = Volley.newRequestQueue(this);

        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Minhas Conversas");

        initNavigation();

        getConversas();

        configClicks();



    }

    private void configClicks(){
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());

        findViewById(R.id.ib_adicionar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent telaNovaConversa = new Intent(ChatActivity.this, NovaConversaActivity.class);
                telaNovaConversa.putExtra("infosUser",usuario);
                finish();
                startActivity(telaNovaConversa);
            }
        });
    }

    private void getConversas() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlWebServicesDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogRespsota", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean isErro = jsonObject.getBoolean("erro"); //nome no arquivo php


                            if(isErro){
                                Toast.makeText(getApplicationContext(), "Nenhuma conversa encontrada", Toast.LENGTH_LONG).show();
                            }else{
                                JSONArray jsonArray = jsonObject.getJSONArray("conversas");
                                CriaListadeChats(jsonArray, itens);

                                recyclerView = findViewById(R.id.recyclerChat);
                                adapter = new ChatAdapter(ChatActivity.this, itens, usuario.getId_usuario());
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);

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
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


    private void CriaListadeChats(JSONArray arrayConversas, ArrayList<Chat> itens){

        int idchat;
        int idremetente;
        String usuario_remetente;
        int iddestinatario;
        String usuario_destinatario;
        JSONObject obj = new JSONObject();

        for (int i = 0; i < arrayConversas.length(); i++){
            try {
                obj = arrayConversas.getJSONObject(i);
                idchat = obj.getInt("idchat");
                idremetente = obj.getInt("idremetente");
                usuario_remetente = obj.getString("usuario_remetente");
                iddestinatario = obj.getInt("iddestinatario");
                usuario_destinatario = obj.getString("usuario_destinatario");


                if(idremetente == usuario.getId_usuario() || iddestinatario == usuario.getId_usuario()){
                    if(idremetente == usuario.getId_usuario()){

                        itens.add(new Chat(idchat ,idremetente, usuario_destinatario, iddestinatario, usuario_remetente));

                    }else{
                        itens.add(new Chat(idchat ,idremetente, usuario_remetente, iddestinatario, usuario_destinatario));
                    }

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }

    private void initNavigation() {
        bottomNavigationViewPrincipal.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case (R.id.menu_home):
                        Intent telaHome;
                        if(perfil == 1) {
                            telaHome =  new Intent(ChatActivity.this, PainelNutricionistaActivity.class);
                        }else {
                            telaHome =  new Intent(ChatActivity.this, PainelPacienteActivity.class);
                        }

                        telaHome.putExtra("infosUser", usuario);
                        telaHome.putExtra("perfil", perfil);
                        finish();
                        startActivity(telaHome);
                        return true;

                    case R.id.menu_profile:
                        Intent telaPerfil = new Intent(ChatActivity.this, PerfilActivity.class);
                        telaPerfil.putExtra("infosUser", usuario);
                        telaPerfil.putExtra("perfil", perfil);
                        finish();
                        startActivity(telaPerfil);

                        return true;

                    case (R.id.menu_dietas):
                        Intent telaDietas = new Intent(ChatActivity.this, DietasActivity.class);
                        telaDietas.putExtra("infosUser", usuario);
                        telaDietas.putExtra("perfil", perfil);
                        finish();
                        startActivity(telaDietas);
                        return true;


                }

                return false;
            }
        });

        }
    }