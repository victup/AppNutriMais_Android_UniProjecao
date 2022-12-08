package com.example.appnutrimais.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnutrimais.R;

public class MensagemViewHolder extends RecyclerView.ViewHolder {

    public TextView remetente, mensagem;


    public MensagemViewHolder(@NonNull View itemView) {
        super(itemView);

        this.remetente = itemView.findViewById(R.id.txtNomeRemetente);
        this.mensagem = itemView.findViewById(R.id.txtMensagem);
    }
}
