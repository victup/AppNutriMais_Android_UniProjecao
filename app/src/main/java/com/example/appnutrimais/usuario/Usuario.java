package com.example.appnutrimais.usuario;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

    private String nome_usuario;
    private int id_usuario;
    private int perfil_usuario;


    public Usuario(String login, int perfil, int id)
    {
        this.nome_usuario = login;
        this.perfil_usuario = perfil;
        this.id_usuario = id;
    }

    public int getPerfil() {
        return perfil_usuario;
    }

    public void setPerfil(int perfil) {
        this.perfil_usuario = perfil;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    protected Usuario(Parcel in) {
        nome_usuario = in.readString();
        id_usuario = in.readInt();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome_usuario);
        dest.writeInt(id_usuario);
    }
}
