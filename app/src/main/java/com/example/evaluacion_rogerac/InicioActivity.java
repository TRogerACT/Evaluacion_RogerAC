package com.example.evaluacion_rogerac;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.evaluacion_rogerac.service.api.ConfigApi;
import com.example.evaluacion_rogerac.service.entity.service.Usuario;
import com.example.evaluacion_rogerac.service.util.DateSerializer;
import com.example.evaluacion_rogerac.service.util.TimeSerializer;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.evaluacion_rogerac.databinding.ActivityInicioBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.sql.Time;

import de.hdodenhof.circleimageview.CircleImageView;

public class InicioActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarInicio.toolbar);
        binding.appBarInicio.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);
        if(usuarioJson != null){
            final Usuario u = g.fromJson(usuarioJson, Usuario.class);
            final View vistaHeader = binding.navView.getHeaderView(0);
            final TextView tvNombre = vistaHeader.findViewById(R.id.tvNombre),
                    tvCorreo = vistaHeader.findViewById(R.id.tvCorreo);
            final CircleImageView imgFoto = vistaHeader.findViewById(R.id.imgFotoPerfil);
            tvNombre.setText(u.getCliente().getNombres());
            tvCorreo.setText(u.getEmail());
            String url = ConfigApi.baseUrlE + "/api/file/download/" + u.getCliente().getFoto().getFileName();
            final Picasso picasso = new Picasso.Builder(this)
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.imagen_no_encontrada)
                    .into(imgFoto);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cerrarSesion:
                this.logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Método para cerrar sesión
    private void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}