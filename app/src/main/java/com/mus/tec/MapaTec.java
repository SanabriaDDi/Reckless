package com.mus.tec;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.mus.tec.Clases.AnimacionCircular;

public class MapaTec extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnPolygonClickListener {

    Polygon lista[];

    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_tec);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.layout_activity_mapa_tec);
        mapFragment.getMapAsync(this);


    }
    //------------------------------------Metodo para interactuar con el layout_activity_mapa_tec-------------------------------------------------------------
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(2);// 0 :ninguno, 1:normal, 2:satelite, 3:Hibrido, 4:Terreno
        mMap.setOnPolygonClickListener(this);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true); //Modifica disponibilidad para los gestos de zoom
        uiSettings.setScrollGesturesEnabled(true); //Modifica gestos de desplazamiento
        uiSettings.setTiltGesturesEnabled(false); //Modifica gestos de la inclinación
        uiSettings.setRotateGesturesEnabled(false); //Modifica la disponibilidad de rotación
        //uiSettings.setAllGesturesEnabled(false); //Modifica la disponibilidad de todos los gestos al mismo tiempo
        LatLng tec = new LatLng(18.437212, -97.397943);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tec,17));
        Crea_Poligonos();

    }
    private void Crea_Poligonos()
    {
        String Poligonos[][]=new String[][]{
                {"18.437630","-97.396196","18.437176","-97.395790","18.437652","-97.395127","18.438141","-97.395556","#FFFFFFFF","#a7936163","1","true","cancha fut"},
                {"18.436356","-97.398283","18.436129","-97.398361","18.436071","-97.398116","18.436290","-97.398032","#FFFFFFFF","#a7936163","1","true","23"},
                {"18.437208","-97.396494","18.437016","-97.396554","18.436915","-97.396159","18.437111","-97.396092","#FFFFFFFF","#a7936163","1","true","37"}
        };
        // color guinda #a7936163
        lista=new Polygon[Poligonos.length];
        for(int i=0;i<Poligonos.length;i++)
        {
            LatLng lat1=new LatLng(Double.parseDouble(Poligonos[i][0]),Double.parseDouble(Poligonos[i][1]));
            LatLng lat2=new LatLng(Double.parseDouble(Poligonos[i][2]),Double.parseDouble(Poligonos[i][3]));
            LatLng lat3=new LatLng(Double.parseDouble(Poligonos[i][4]),Double.parseDouble(Poligonos[i][5]));
            LatLng lat4=new LatLng(Double.parseDouble(Poligonos[i][6]),Double.parseDouble(Poligonos[i][7]));
            Polygon nuevo=mMap.addPolygon(new PolygonOptions().add(lat1,lat2,lat3,lat4)
                    .strokeColor(Color.parseColor(Poligonos[i][8]))
                    .fillColor(Color.parseColor(Poligonos[i][9]))
                    .strokeWidth(Float.parseFloat(Poligonos[i][10]))
                    .clickable(Boolean.parseBoolean(Poligonos[i][11])));
            nuevo.setTag(Poligonos[i][12]);
            lista[i]=nuevo;
            Log.i("CREA UN POLIGONO",Poligonos[i][12]);
        }
    }


    //---------- Detección de evento al presonar el área de un poligono----------------
    @Override
    public void onPolygonClick(Polygon polygon) {
        String tipo="";
        if(polygon.getTag()!=null)
        {
            tipo =polygon.getTag().toString();
        }
        switch (tipo)
        {
            case "cancha fut":
                break;
            case "37":
                break;
            case "23":
                DialogLista("Edificio de cómputo").show();
                break;
        }

    }
    //----------------------------------------------------------------------------------

    //----Cuadro de dialogo que muestra el nombreo o departamentos de cada edificio-------------------
    public AlertDialog DialogLista(String titulo_lista)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapaTec.this);

        final CharSequence[] items = new CharSequence[3];

        items[0] = "23-A";
        items[1] = "23-F";
        items[2] = "No se";

        builder.setTitle(titulo_lista).setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                switch (which)
                {
                    case 0:
                        LlamaFragLugar("Opcion uno seleccionada  :v ",items[0].toString());
                        break;
                    case 1:
                        LlamaFragLugar("Opcion dos seleccionada :3",items[1].toString());
                        break;
                    case 2:
                        LlamaFragLugar("Opcion tres seleccionada xD",items[2].toString());
                        break;
                }

            }
        });

        return builder.create();
    }
    //------------------------------------------------------------------------------------------------

    //---------Se recibe como parametros el contenido para el dialogo de pantalla completa------------
    public void LlamaFragLugar(String contenido,String titulo)
    {
        String t,c;
        t=ContenedorInfoLugar.TITULO;
        c=ContenedorInfoLugar.CONTENIDO;
        //Intent para enviar el contenido recibido
        Intent intent=new Intent(this,ContenedorInfoLugar.class);
        intent.putExtra(t,titulo);
        intent.putExtra(c,contenido);
        startActivity(intent);
    }
    //------------------------------------------------------------------------------------------------


}