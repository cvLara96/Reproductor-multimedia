package com.example.tarea6_vl_carlos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;

import java.io.IOException;
import java.util.ArrayList;

public class ActivityReproductor extends AppCompatActivity implements MediaController.MediaPlayerControl{

    //Creamos el recycler
    RecyclerView recyclerView;

    //Creamos el adaptador
    MyAdapter adapter;

    //Audio
    MediaPlayer mediaPlayer;
    MediaController mc;
    Handler h;

    ArrayList<AppMedia> elementos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor);

        AppMediaContent.loadMediaFromJSON(this);

        //Creamos el array list de elementos
         elementos = AppMediaContent.ITEMS;

        //REFERENCIAMOS EL ADAPTADOR (recibe como parametros la lista de elementos y el contexto (de donde viene))
        adapter = new MyAdapter(elementos, this, this);

        //Referenciamos el recycler
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        //.setHasFixedSieze(true) se utiliza en un RecyclerView en Android y tiene un impacto en
        //el rendimiento al establecer si el tamaño del RecyclerView es fijo
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //.setLayoutManager(new LinearLayoutManager(this)); establece un tipo de listado lineal
        //de arriba a abajo

        //ASIGNAMOS EL ADAPTADOR
        recyclerView.setAdapter(adapter);

    }

    public void play(AppMedia appMedia) {

        switch(appMedia.getTipo()){

            case "0":
                playAudio(appMedia);
                break;
            case "1":
                playVideo(appMedia);

            case "2":
                playStreamingAudio(appMedia);
                break;

            case "3":
                playStreamingVideo(appMedia);
                break;

        }

    }

    public void playAudio(AppMedia appMedia){

        if(mediaPlayer!= null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        mediaPlayer = new MediaPlayer();
        mc = new MediaController(this);
        mc.setMediaPlayer(this);
        mc.setAnchorView(findViewById(R.id.constraintLayout));
        h = new Handler();

            int idRecurso = getResources().getIdentifier(appMedia.getUri(), "raw", getPackageName());

            try {
                mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + idRecurso));
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            // Se muestra el control en la pantalla. Tras 20 segundos de inactividad, el control se ocultará
                            mc.show(20000);
                            mediaPlayer.start();
                        }
                    });
                }
            });

    }

    public void playVideo(AppMedia appMedia){

        // Detener la reproducción de audio si está en curso
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        Intent intent = new Intent(this,ReproducVideo.class);
        intent.putExtra("recurso",appMedia);
        startActivity(intent);

    }

    public void playStreamingAudio(AppMedia appMedia){

        if(mediaPlayer!= null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }

        mediaPlayer = new MediaPlayer();
        mc = new MediaController(this);
        mc.setMediaPlayer(this);
        mc.setAnchorView(findViewById(R.id.constraintLayout));
        h = new Handler();

        try {
            mediaPlayer.setDataSource(this, Uri.parse(appMedia.getUri()));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        // Se muestra el control en la pantalla. Tras 20 segundos de inactividad, el control se ocultará
                        mc.show(20000);
                        mediaPlayer.start();
                    }
                });
            }
        });

    }

    public void playStreamingVideo(AppMedia appMedia){

        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }

        Intent intent = new Intent(this,ReproducVideo.class);
        intent.putExtra("recurso",appMedia);
        startActivity(intent);


    }


    //Metodos de la interfaz MediaController.MediaPlayerControl
    @Override
    public void start() {
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    // El método onTouchEvent nos permite controlar qué hacer cuando el usuario toca la pantalla
    public boolean onTouchEvent(MotionEvent event) {
        // En este caso, cuando el usuario toque la pantalla,
        // mostramos los controles de reproducción
        mc.show();
        return false;
    }

    @Override
    protected void onPause() {
        elementos.clear();
        super.onPause();
    }
}