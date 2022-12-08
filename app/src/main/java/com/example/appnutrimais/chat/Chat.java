package com.example.appnutrimais.chat;

public class Chat {
    private int idchat;
    private int idremetente;
    private String usuario_remetente;
    private int iddestinatario;
    private String usuario_destinatario;


    public Chat(int idchat, int idremetente, String usuario_remetente, int iddestinatario, String usuario_destinatario) {
        this.idchat = idchat;
        this.idremetente = idremetente;
        this.usuario_remetente = usuario_remetente;
        this.iddestinatario = iddestinatario;
        this.usuario_destinatario = usuario_destinatario;
    }

    public int getIdchat() {
        return idchat;
    }

    public void setIdchat(int idchat) {
        this.idchat = idchat;
    }

    public int getIdremetente() {
        return idremetente;
    }

    public void setIdremetente(int idremetente) {
        this.idremetente = idremetente;
    }

    public String getUsuario_remetente() {
        return usuario_remetente;
    }

    public void setUsuario_remetente(String usuario_remetente) {
        this.usuario_remetente = usuario_remetente;
    }

    public int getIddestinatario() {
        return iddestinatario;
    }

    public void setIddestinatario(int iddestinatario) {
        this.iddestinatario = iddestinatario;
    }

    public String getUsuario_destinatario() {
        return usuario_destinatario;
    }

    public void setUsuario_destinatario(String usuario_destinatario) {
        this.usuario_destinatario = usuario_destinatario;
    }
}
