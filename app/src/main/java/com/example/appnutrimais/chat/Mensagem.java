package com.example.appnutrimais.chat;

public class Mensagem {

    int idmsg;
    int idremetente;
    String usuario_remetente;
    int iddestinatario;
    String usuario_destinatario;
    String msg;
    int chat_idchat;


    public Mensagem(int idmsg, int idremetente, String usuario_remetente, int iddestinatario, String usuario_destinatario, String msg, int chat_idchat) {
        this.idmsg = idmsg;
        this.idremetente = idremetente;
        this.usuario_remetente = usuario_remetente;
        this.iddestinatario = iddestinatario;
        this.usuario_destinatario = usuario_destinatario;
        this.msg = msg;
        this.chat_idchat = chat_idchat;
    }

    public int getIdmsg() {
        return idmsg;
    }

    public void setIdmsg(int idmsg) {
        this.idmsg = idmsg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getChat_idchat() {
        return chat_idchat;
    }

    public void setChat_idchat(int chat_idchat) {
        this.chat_idchat = chat_idchat;
    }
}
