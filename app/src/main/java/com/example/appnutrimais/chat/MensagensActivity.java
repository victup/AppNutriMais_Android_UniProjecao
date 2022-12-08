package com.example.appnutrimais.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MensagensActivity extends AppCompatActivity {

    String urlWebServicesDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/chat/getMensagens.php"; //arquivo que vai consumir
    String urlWebServicesEnviaMensagemDesenvolvimento = "https://nutrimaisapi.000webhostapp.com/apinutrimais/chat/enviarMensagem.php"; //arquivo que vai consumir
    String urlWebServicesProducao = "http://www.nutrimais.com.br/pasta/arquivo.php";

    //objeto para requisições
    StringRequest stringRequest;
    RequestQueue requestQueue;

    private RecyclerView recyclerView;
    private MensagemAdapter adapter;
    private ArrayList<Mensagem> msg = new ArrayList<Mensagem>();;

    TextView idmsg;
    EditText txtMensagem;
    int idRemetenteChat, idDestinarioChat, idChat, idUsuarioLogado, idRemetente, idDestinatario;
    ImageButton btnEnviarMensagem;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);
        txtMensagem = findViewById(R.id.txtMsg);
        btnEnviarMensagem = findViewById(R.id.btn_enviar_mensagem);

        idmsg = findViewById(R.id.txtIDMensagem);
        idRemetenteChat = getIntent().getExtras().getInt("idRemetente");
        idDestinarioChat = getIntent().getExtras().getInt("idDestinatario");
        idChat = getIntent().getExtras().getInt("idChat");
        idUsuarioLogado = getIntent().getExtras().getInt("usuarioLogado");

        if(idRemetenteChat == idUsuarioLogado){
            idRemetente = idUsuarioLogado;
            idDestinatario = idDestinarioChat;
        }else{
            idRemetente = idDestinarioChat;
            idDestinatario = idUsuarioLogado;
        }

        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Mensagens");


        idmsg.setText(String.valueOf(idUsuarioLogado));

        requestQueue = Volley.newRequestQueue(this);

        configClicks();

        btnEnviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensagem();
            }
        });

        getMensagens();

    }


    private void configClicks(){
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
    }

    private void getMensagens() {
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
                                Toast.makeText(getApplicationContext(), "Nenhuma mensagem foi encontrada", Toast.LENGTH_LONG).show();
                            }else{
                                JSONArray jsonArray = jsonObject.getJSONArray("mensagens");
                                CriaListadeMensagens(jsonArray, msg);

                                recyclerView = findViewById(R.id.recyclerMsg);
                                adapter = new MensagemAdapter(MensagensActivity.this, msg);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MensagensActivity.this, LinearLayoutManager.VERTICAL, false);
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
                params.put("idremetente", String.valueOf(idRemetenteChat));
                params.put("iddestinatario", String.valueOf(idDestinarioChat));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void enviarMensagem() {
        // usuario autorizado = consultar web services, passando login e senha e recebeer ok

        stringRequest = new StringRequest(Request.Method.POST, urlWebServicesEnviaMensagemDesenvolvimento,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("LogRespsota", response);

                        try{

                            JSONObject jsonObject = new JSONObject(response);

                            boolean sucesso = jsonObject.getBoolean("sucesso"); //nome no arquivo php


                            if(!sucesso){
                                Toast.makeText(getApplicationContext(), "Não foi possivel enviar a mensagem", Toast.LENGTH_LONG).show();
                            }else{

                                finish();
                                startActivity(getIntent());


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
                params.put("idremetente", String.valueOf(idRemetente));
                params.put("iddestinatario", String.valueOf(idDestinatario));
                params.put("mensagem", String.valueOf(txtMensagem.getText()));
                params.put("idchat", String.valueOf(idChat));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void CriaListadeMensagens(JSONArray arrayMensagens, ArrayList<Mensagem> msgs){

        int idmensagem;
        int idremetente;
        String usuario_remetente;
        int iddestinatario;
        String mensagem;
        int chat_idchat;
        String usuario_destinatario;

        JSONObject obj = new JSONObject();

        for (int i = 0; i < arrayMensagens.length(); i++){
            try {
                obj = arrayMensagens.getJSONObject(i);

                idmensagem = obj.getInt("idmsg");
                idremetente = obj.getInt("idremetente");
                usuario_remetente = obj.getString("usuario_remetente");
                iddestinatario = obj.getInt("iddestinatario");
                usuario_destinatario = obj.getString("usuario_destinatario");
                mensagem = obj.getString("msg");
                chat_idchat = obj.getInt("chat_idchat");

                if(idremetente == idUsuarioLogado){
                    msgs.add(new Mensagem(idmensagem, idremetente, "você", iddestinatario, usuario_destinatario, mensagem,chat_idchat));
                }
                else{
                    msgs.add(new Mensagem(idmensagem, idremetente, usuario_remetente, iddestinatario, "você", mensagem,chat_idchat));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }
}