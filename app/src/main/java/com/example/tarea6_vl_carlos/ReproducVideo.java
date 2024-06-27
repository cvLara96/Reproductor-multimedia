package com.example.tarea6_vl_carlos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class ReproducVideo extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproduc_video);

        // Recuperar el objeto AppMedia del Intent
        AppMedia appMedia = (AppMedia) getIntent().getSerializableExtra("recurso");

        //Obtenemos la referencia al widget videoView
        videoView = (VideoView) findViewById(R.id.videoView);
        back = findViewById(R.id.imageButtonVolver);
        back.setOnClickListener(this::back);

        switch(appMedia.getTipo()){
            case "1":
                local(appMedia);
                break;
            case "3":
                stream(appMedia);
                break;
        }


    }

    public void local(AppMedia appMedia){

        int idVideo = getResources().getIdentifier(appMedia.getUri(), "raw", getPackageName());

        //Creamos el objeto mediaController
        mediaController = new MediaController(this);
        //Establecemos el ancho del mediaController
        mediaController.setAnchorView(videoView);
        //Al contenedor videoView le añadimos los controles
        videoView.setMediaController(mediaController);
        //Cargamos el contenido multimedia (el video) en el videoview
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+idVideo));
        //Registramos el callback que sera invocado cuando el video este cargado y preparado para
        //la reproduccion
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaController.show(2000);
                videoView.start();
            }
        });

    }

    public void stream(AppMedia appMedia){

        //Creamos el objeto mediaController
        mediaController = new MediaController(this);
        //Establecemos el ancho del mediaController
        mediaController.setAnchorView(videoView);
        //Al contenedor videoView le añadimos los controles
        videoView.setMediaController(mediaController);
        //Cargamos el contenido multimedia (el video) en el videoview
        videoView.setVideoURI(Uri.parse(appMedia.getUri()));
        //Registramos el callback que sera invocado cuando el video este cargado y preparado para
        //la reproduccion
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaController.show(2000);
                videoView.start();
            }
        });

    }

    //Programamos el metodo onTouchEvent para que se muestre el mediacontroller cuando el usuario pulse la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mediaController.show();
        return true;
    }

    public void back(View v){

        Intent intent = new Intent(this, ActivityReproductor.class);
        startActivity(intent);


    }

}