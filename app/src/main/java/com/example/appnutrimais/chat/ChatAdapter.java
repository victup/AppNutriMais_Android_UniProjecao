package com.example.appnutrimais.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnutrimais.R;
import com.example.appnutrimais.nutricionista.PainelNutricionistaActivity;
import com.example.appnutrimais.usuario.LoginActivity;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

    private Context contexto;
    private ArrayList<Chat> itens;
    private View view;
    private int id_usuario_logado;

    public ChatAdapter(Context contexto, ArrayList<Chat> itens, int idusuario) {
        this.contexto = contexto;
        this.itens = itens;
        this.id_usuario_logado = idusuario;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(contexto).inflate(R.layout.conversas_chat, parent, false);
        ChatViewHolder viewHolder = new ChatViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = itens.get(position);
        holder.nome.setText(chat.getUsuario_remetente());
        holder.idchat.setText(String.valueOf(chat.getIdchat()));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaMensagem = new Intent(contexto, MensagensActivity.class);
                telaMensagem.putExtra("idRemetente", itens.get(position).getIdremetente());
                telaMensagem.putExtra("idDestinatario", itens.get(position).getIddestinatario());
                telaMensagem.putExtra("idChat", itens.get(position).getIdchat());
                telaMensagem.putExtra("usuarioLogado", id_usuario_logado);
                telaMensagem.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(telaMensagem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}
