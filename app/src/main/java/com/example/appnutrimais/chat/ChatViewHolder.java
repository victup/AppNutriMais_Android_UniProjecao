package com.example.appnutrimais.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnutrimais.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {

    public TextView nome, idchat;

    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.txtNomeRemetente);
        idchat = itemView.findViewById(R.id.txtMensagem);

    }
}
