package com.example.tarea6_vl_carlos;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//(*)ESTA CLASE HEREDA DE RecyclerView.Adapter
//ESTO NOS SACARA VARIOS ERRORES
// - EN PRIMER LUGAR NOS HARA IMPLEMENTAR LA CLASE ViewHolderDatos
// - LUEGO NOS HARA IMPLEMENTAR LOS METODOS DE LA CLASE PADRE
// - LUEGO NOS HARA HACER QUE INDIQUEMOS QUE LA CLASE ViewHolderDatos
//EXTIENDE DE RecyclerView.ViewHolder
// - POR ULTIMO NOS HARA IMPLEMENTAR EL CONSTRUCTOR DE ViewHolderDatos
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolderDatos> {

    //ESTE ADAPTADOR RECIBIRA UNA LISTA DE DATOS, DE MANERA QUE CREAMOS UN ARRAYLIST
    ArrayList<AppMedia> listaRecursos;

    //CREAMOS UN LAYOUTINFLATER QUE INFLARA LA VISTA QUE TENDRA QUE MOSTRAR (la definida en list_elementos.xml):
    private LayoutInflater inflater;

    //CREAMOS UN CONTEXT PARA INDICAR DE QUE CLASE ESTAMOS LLAMANDO ESTE ADAPTADOR
    private Context context;

    //CREAMOS UNA INSTANCIA DE activityReproductor
    ActivityReproductor activityReproductor;
    AppMedia appMedia;


    //CREAREMOS EL CONSTRUCTOR
    public MyAdapter(ArrayList<AppMedia> listaJugadores, Context context,ActivityReproductor activityReproductor) {

        this.listaRecursos = listaJugadores;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.activityReproductor = activityReproductor;
    }

    @NonNull
    @Override
    //ESTE METODO ENLAZA EL ADAPTADOR CON EL FICHERO list_elementos.xml
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //DE MANERA QUE AQUI GENERAREMOS UN VIEW INFLADO CON ESE LAYOUT:
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_elementos, null, false);

        return new ViewHolderDatos(view);
    }

    //ESTE METODO SE ENCARGARA DE ESTABLECER LA COMUNICACION ENTRE NUESTRO ADAPTADOR Y
    //LA CLASE ViewHolderDatos
    @Override
    @SuppressLint("RecyclerView")
    public void onBindViewHolder(@NonNull ViewHolderDatos holder,  int position) {
        //UTILIZAREMOS EL holder Y CREAREMOS UN METODO LLAMADO asignarDatos QUE
        //RECIBIRA COMO PARAMETRO LA INFORMACION QUE QUEREMOS QUE MUESTRE:
        //EL METODO asignarDatos debera estar creado en la clase ViewHolderDatos
        //DE MANERA QUE PULSAREMOS SOBRE EL PARA QUE LO GENERE EN ESTA CLASE:
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_transition));
        holder.asignarDatos(listaRecursos.get(position));

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activityReproductor.play(listaRecursos.get(position));

            }
        });

    }

    //ESTE METODO RETORNARA EL TAMAÑO DE LA LISTA
    @Override
    public int getItemCount() {
        return listaRecursos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        //AQUI REFERENCIAMOS LOS ELEMENTOS QUE TENDRA EL RECYCLERVIEW
        TextView nombre;
        TextView descripcion;
        ImageView tipo;
        ImageView fotoAlbum;
        ImageView play;
        View view;
        CardView cardView;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.relativePulsable);
            //PARA REFERENCIARLO USAMOS EL itemView:
            nombre = itemView.findViewById(R.id.textCantante);
            descripcion = itemView.findViewById(R.id.textCancion);
            tipo = itemView.findViewById(R.id.imageTipo);
            fotoAlbum = itemView.findViewById(R.id.imageCard);
            play = itemView.findViewById(R.id.imagePlay);
            cardView = itemView.findViewById(R.id.CardView);
        }

        public void asignarDatos(AppMedia recurso) {

            nombre.setText(recurso.getNombre());
            descripcion.setText(recurso.getDescripcion());
            if(recurso.getTipo().equals("0")){
                tipo.setImageResource(R.drawable.musica);
            }else if(recurso.getTipo().equals("1")){
                tipo.setImageResource(R.drawable.video);
            }else if(recurso.getTipo().equals("2")){
                tipo.setImageResource(R.drawable.live);
                //tipo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }else{
                tipo.setImageResource(R.drawable.live);
                //tipo.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            int idImagen = context.getResources().getIdentifier(recurso.getImagen(), "drawable", context.getPackageName());
            fotoAlbum.setImageResource(idImagen);


        }
    }

//--> UNA VEZ CREADO EL ADAPTADOR PASAREMOS AL PASO 13 EN MAINACTIVITY2
}
