package com.practice.proyectomgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import com.practice.proyectomgr.interfaces.Home;
import com.practice.proyectomgr.interfaces.HomeSpace;
import com.practice.proyectomgr.models.HomeSpaces;
import com.practice.proyectomgr.models.HomeSpacesValues;
import com.practice.proyectomgr.models.Homes;
import com.practice.proyectomgr.models.User;

import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity3 extends AppCompatActivity {

    //EditText edtCodigo;
    //TextView tvDescription;
    //ImageView imgProducto;
    TextView tvHomeName;
    TextView tvHomeSpaceName;
   // TextView tvSpaceFunc;
    Button btnMostrar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        btnMostrar=findViewById(R.id.btnMostrar);
        tvHomeName=findViewById(R.id.tvHomeName);
        tvHomeSpaceName=findViewById(R.id.tvHomeSpaceName);
       // tvSpaceFunc=findViewById(R.id.tvSpaceFunc);
        listView = findViewById(R.id.lvSpaces);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userID = extras.getString("user_id");
            tvHomeName.setText(userID);
            findHome(userID);
        }
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String idHomevalue = (String) v.getTag(R.id.etiqueta_idhome);
                String userHome = extras.getString("home_id");
                findSpace(userHome);
            }
        });
        //on click any row of list view it sends to item screen capture
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;
// ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
// Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+" ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
                String userID = extras.getString("user_id");
                Intent intentShowContent = new Intent(MainActivity3.this, MainActivity4.class);
                intentShowContent.putExtra("space_id_and_name", itemValue);
                intentShowContent.putExtra("user_id", userID);
                startActivity(intentShowContent);
            }
        });



    }
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentShowContent = new Intent(MainActivity3.this, Login.class);
        startActivity(intentShowContent);
        return super.onOptionsItemSelected(item);
    }

    private void findHome(String idHome){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ec2-3-22-119-100.us-east-2.compute.amazonaws.com:3333").addConverterFactory(GsonConverterFactory.create()).build();
        Home home=retrofit.create(Home.class);
        Call<Homes> call=home.findHome(idHome);
        call.enqueue(new Callback<Homes>() {
            @Override
            public void onResponse(Call<Homes> call, Response<Homes> response) {

                try {
                    if (response.isSuccessful()) {
                        Homes p = response.body();
                        // String URL_IMG = "http://192.168.0.2:3333/img" + p.getId() + ".jpg";
                        tvHomeName.setText(p.getHome_name());
                        // tvHomeName.setTag(idHome, p.getId());
                        //someValueIWantToKeep=p.getUser_id();

                        //edText1.setTag(R.id.etiqueta_idhome, p.getId());
                        // tvHomeName.setTag(R.id.etiqueta_idhome, p.getId());
                    }
                } catch (Exception ex) {
                    Toast.makeText(MainActivity3.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Homes> call, Throwable t) {
                Toast.makeText(MainActivity3.this, "Error Connecting", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void findSpace(String home_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ec2-3-22-119-100.us-east-2.compute.amazonaws.com:3333").addConverterFactory(GsonConverterFactory.create()).build();
        HomeSpace homeSpace=retrofit.create(HomeSpace.class);
        Call<List<HomeSpacesValues>> call = homeSpace.findSpace(home_id);
        call.enqueue(new Callback<List<HomeSpacesValues>>() {
            @Override
            public void onResponse(Call<List<HomeSpacesValues>> call, Response<List<HomeSpacesValues>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<HomeSpacesValues> p = response.body();
                        String[] espacios = new String[p.size()];
                        //String[] funciones = new String[p.size()];
                        //System.out.println(p.size());
//looping through all the spaces and inserting the names inside the string array
                        for (int i = 0; i < p.size(); i++) {
                            espacios[i] = "ID: "+p.get(i).getId()+" Nombre: "+p.get(i).getSpaceName();

                        //displaying the string array into listview
                        listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, espacios));
                        }
                    /*    for (HomeSpacesValues item : p) {
                            //   Logger("Body %s", item.HomeSpacesValues.get((String), home_id));
                            //    Log.d("Body %s", ); // print every text item in list
                            //tvHomeSpaceName.setText(item.getSpaceFunction());
                            tvHomeSpaceName.setText(p.size());

                        }*/
                    }
                        //tvHomeSpaceName.setText(p.get(HomeSpacesValues)
                        // tvSpaceFunc.setText(p.getSpaceFunction());

                } catch (Exception ex) {
                    Toast.makeText(MainActivity3.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<HomeSpacesValues>> call, Throwable t) {
                Toast.makeText(MainActivity3.this, "Error Connecting", Toast.LENGTH_SHORT).show();
            }

        });

    }
}



