package com.example.appnutrimais.nutricionista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.appnutrimais.dietas.DietasActivity;
import com.example.appnutrimais.usuario.PerfilActivity;
import com.example.appnutrimais.R;
import com.example.appnutrimais.chat.ChatActivity;
import com.example.appnutrimais.usuario.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class PainelNutricionistaActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationViewPrincipal;

    TextView boasVindas, infosNutri;
    Usuario usuario;
    int perfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_nutricionista);

        bottomNavigationViewPrincipal = findViewById(R.id.bottom_navigation);
        bottomNavigationViewPrincipal.setSelectedItemId(R.id.menu_home);

        initNavigation();

        infosNutri = findViewById(R.id.tvwInfosNutri);
        boasVindas = findViewById(R.id.tvwBoasVindas);
        usuario = getIntent().getExtras().getParcelable("infosUser");

        perfil = getIntent().getExtras().getInt("perfil");
        boasVindas.setText("Que bom te ter aqui, "+usuario.getNome_usuario());
        infosNutri.setText(" Seu ID: "+usuario.getId_usuario()+" Perfil: "+perfil);




    }

    private void initNavigation()
    {
       bottomNavigationViewPrincipal.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.menu_profile:
                       Intent telaPerfil = new Intent(PainelNutricionistaActivity.this, PerfilActivity.class);
                       telaPerfil.putExtra("infosUser", usuario);
                       telaPerfil.putExtra("perfil", perfil);
                       startActivity(telaPerfil);

                       return true;

                   case (R.id.menu_dietas):
                       Intent telaDietas = new Intent(PainelNutricionistaActivity.this, DietasActivity.class);
                       telaDietas.putExtra("infosUser", usuario);
                       telaDietas.putExtra("perfil", perfil);
                       finish();
                       startActivity(telaDietas);
                       return true;

                   case (R.id.menu_chat):
                       Intent telaChat = new Intent(PainelNutricionistaActivity.this, ChatActivity.class);
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