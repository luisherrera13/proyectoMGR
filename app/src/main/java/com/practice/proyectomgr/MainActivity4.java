package com.practice.proyectomgr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.practice.proyectomgr.interfaces.HomeSpace;
import com.practice.proyectomgr.interfaces.Objeto;
import com.practice.proyectomgr.models.HomeSpacesValues;
import com.practice.proyectomgr.models.Objetos;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//GPS imports
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;

import android.util.Log;


public class MainActivity4 extends AppCompatActivity implements LocationListener {
    EditText et_name, et_description, et_itemFunction, et_itemType, et_price;
    Button btn_registrar;
    public String userIDValueIWantToKeep;
    public String spaceIDValueIWantToKeep;
    public String spaceNameValueIWantToKeep;

    //GPs variables

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        //GPS LOCALIZATION CONFIG

        txtLat = (TextView) findViewById(R.id.tvGPSLocal);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

       // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        //initializing input texts
        et_name = (EditText) findViewById(R.id.et_name);
        et_description = (EditText) findViewById(R.id.et_description);
        et_itemFunction = (EditText) findViewById(R.id.et_itemFunction);
        et_itemType = (EditText) findViewById(R.id.et_itemType);
        et_price = (EditText) findViewById(R.id.et_price);
        btn_registrar = (Button) findViewById(R.id.btn_registrar);
        //Getting received data from spaces list screen (activity_main3)
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userIDValueIWantToKeep = extras.getString("user_id");
            String spaceANDname = extras.getString("space_id_and_name");

            spaceANDname=spaceANDname.replace("ID: ", "");

            String[] parts = spaceANDname.split(" Nombre: ");
            spaceIDValueIWantToKeep=parts[0];
            spaceNameValueIWantToKeep=parts[1];
        }


        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString();
                String description = et_description.getText().toString();
                String itemFunction = et_itemFunction.getText().toString();
                String itemType = et_itemType.getText().toString();
                String price = et_price.getText().toString();



               /* if (spaceName.equals("") || spaceFunction.equals("") ) {
                    Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
                } else {*/
                    saveItem(name, description, itemFunction, itemType, price, spaceIDValueIWantToKeep, userIDValueIWantToKeep);
               // }
            }

        });
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.tvGPSLocal);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }




    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void saveItem(String name, String description, String itemFunction, String itemType, String price, String home_space_id, String user_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ec2-3-22-119-100.us-east-2.compute.amazonaws.com:3333").addConverterFactory(GsonConverterFactory.create()).build();

        Objeto objeto= retrofit.create(Objeto.class);

        Call<Objetos> call=objeto.saveItem(name, description, itemFunction, itemType, price, home_space_id, user_id);
        call.enqueue(new Callback<Objetos>() {
            @Override
            public void onResponse(Call<Objetos> call, Response<Objetos> response) {
                try {
                    if (response.isSuccessful()) {
                        Objetos p = response.body();
                        String namex=p.getDescription();
                        String Email=p.getDescription();
                        System.out.println("Descripcion: "+Email);
                        System.out.println("nombre: "+namex);

                        Toast.makeText(MainActivity4.this, "Objeto Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception ex) {
                    Toast.makeText(MainActivity4.this, "Revise datos de registro", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Objetos> call, Throwable t) {

            }
        });
    }
}