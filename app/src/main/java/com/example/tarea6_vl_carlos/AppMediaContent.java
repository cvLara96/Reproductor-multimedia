package com.example.tarea6_vl_carlos;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppMediaContent {

    public static ArrayList<AppMedia> ITEMS = new ArrayList<AppMedia>();

    public static void clearMedia(){
        ITEMS.clear();
    }

    public static void loadMediaFromJSON(Context c) {

        String json = null;
        try {
            InputStream is = c.getAssets().open("recursos_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray couchList = jsonObject.getJSONArray("recursos_list");
            for (int i = 0; i < couchList.length(); i++) {
                JSONObject jsonCouch = couchList.getJSONObject(i);
                String nombre = jsonCouch.getString("nombre");
                String description = jsonCouch.getString("descripcion");
                String tipo=jsonCouch.getString("tipo");
                String uri=jsonCouch.getString("URI");
                String imagen=jsonCouch.getString("imagen");

                ITEMS.add(new AppMedia(nombre,description,tipo,uri,imagen));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

}
