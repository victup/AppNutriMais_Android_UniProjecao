package com.example.appnutrimais.dietas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.appnutrimais.R;
import com.example.appnutrimais.chat.ChatActivity;
import com.example.appnutrimais.nutricionista.PainelNutricionistaActivity;
import com.example.appnutrimais.usuario.PerfilActivity;
import com.example.appnutrimais.usuario.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DietasActivity extends AppCompatActivity {

    Button btnNovaDieta, btnConsultarDieta;
    Usuario usuario;
    int perfil;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietas);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_dietas);

        btnNovaDieta = findViewById(R.id.btnNovaDieta);
        btnConsultarDieta = findViewById(R.id.btnConsultarDietas);

        usuario = getIntent().getExtras().getParcelable("infosUser");
        perfil = getIntent().getExtras().getInt("perfil");

        initNavigation();

        btnNovaDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaNovaDieta = new Intent(DietasActivity.this, NovaDietaActivity.class);
                startActivity(telaNovaDieta);
            }
        });

        btnConsultarDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent telaConsultarDietas = new Intent(DietasActivity.this, ConsultarDietaActivity.class);
                startActivity(telaConsultarDietas);
            }
        });
    }

    private void initNavigation()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        Intent telaHome = new Intent(DietasActivity.this, PainelNutricionistaActivity.class);
                        telaHome.putExtra("infosUser", usuario);
                        telaHome.putExtra("perfil", perfil);
                        finish();
                        startActivity(telaHome);
                        return true;

                    case (R.id.menu_profile):
                        Intent telaPerfil = new Intent(DietasActivity.this, PerfilActivity.class);
                        telaPerfil.putExtra("infosUser", usuario);
                        telaPerfil.putExtra("perfil", perfil);
                        finish();
                        startActivity(telaPerfil);
                        return true;

                    case (R.id.menu_chat):
                        Intent telaChat = new Intent(DietasActivity.this, ChatActivity.class);
                        telaChat.putExtra("infosUser", usuario);
                        telaChat.putExtra("perfil", perfil);
                        finish();
                        startActivity(telaChat);
                        return true;
                }
                return false;
            }
        });

    }
}