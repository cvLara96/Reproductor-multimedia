package com.example.tarea6_vl_carlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton acceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acceso = findViewById(R.id.imageButtonAcceder);
        acceso.setOnClickListener(this::btnAccess);

    }

    //Metodo para cuando pulsemos sobre acceder

    public void btnAccess(View v){

        Intent intent = new Intent(this,ActivityReproductor.class);
        startActivity(intent);

    }

}