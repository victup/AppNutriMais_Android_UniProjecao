package com.example.appnutrimais.paciente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.appnutrimais.R;
import com.example.appnutrimais.chat.ChatActivity;
import com.example.appnutrimais.dietas.DietasPacienteActivity;
import com.example.appnutrimais.usuario.PerfilActivity;
import com.example.appnutrimais.usuario.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PainelPacienteActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationViewPrincipal;

    TextView boasVindas, infosPaciente;
    Usuario usuario;
    int perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_paciente);

        bottomNavigationViewPrincipal = findViewById(R.id.bottom_navigation);
        bottomNavigationViewPrincipal.setSelectedItemId(R.id.menu_home);

        infosPaciente = findViewById(R.id.txtInfosPaciente);
        boasVindas = findViewById(R.id.txtBoasVindasPaciente);
        usuario = getIntent().getExtras().getParcelable("infosUser");
        perfil = getIntent().getExtras().getInt("perfil");

        initNavigation();




        boasVindas.setText("Que bom te ter aqui, " + usuario.getNome_usuario());
        infosPaciente.setText(" Seu ID: " + usuario.getId_usuario()+" Perfil: "+perfil);
    }

    private void initNavigation() {
        bottomNavigationViewPrincipal.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_profile:
                        Intent telaPerfil = new Intent(PainelPacienteActivity.this, PerfilActivity.class);
                        telaPerfil.putExtra("infosUser", usuario);
                        telaPerfil.putExtra("perfil", perfil);
                        startActivity(telaPerfil);
                        return true;

                    case (R.id.menu_dietas):
                        Intent telaDietas = new Intent(PainelPacienteActivity.this, DietasPacienteActivity.class);
                        telaDietas.putExtra("infosUser", usuario);
                        telaDietas.putExtra("perfil", perfil);
                        startActivity(telaDietas);
                        return true;

                    case (R.id.menu_chat):
                        Intent telaChat = new Intent(PainelPacienteActivity.this, ChatActivity.class);
                        telaChat.putExtra("infosUser", usuario);
                        telaChat.putExtra("perfil", perfil);
                        startActivity(telaChat);
                        return true;
                }

                return false;
            }
        });
    }
}