package com.example.appnutrimais.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnutrimais.R;

import java.util.ArrayList;

public class MensagemAdapter extends RecyclerView.Adapter<MensagemViewHolder> {

    private Context contexto;
    private ArrayList<Mensagem> mensagem;
    private View view;

    public MensagemAdapter(Context contexto, ArrayList<Mensagem> mensagem) {
        this.contexto = contexto;
        this.mensagem = mensagem;
    }

    @NonNull
    @Override
    public MensagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(contexto).inflate(R.layout.mensagens_chat, parent, false);
        MensagemViewHolder viewHolder = new MensagemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MensagemViewHolder holder, int position) {
        Mensagem msg = mensagem.get(position);
        holder.remetente.setText(msg.getUsuario_remetente());
        holder.mensagem.setText(String.valueOf(msg.getMsg()));


    }

    @Override
    public int getItemCount() {
        return mensagem.size();
    }
}
