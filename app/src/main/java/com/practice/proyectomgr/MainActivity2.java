
package com.practice.proyectomgr;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.proyectomgr.interfaces.HomeSpace;
import com.practice.proyectomgr.models.HomeSpacesValues;
import android.content.Intent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {
    Button btn_spaceRegister;
    Button btn_spaceView;
    EditText et_spaceName, et_spaceFunc;
    TextView tv_homeName;
    public String homeIDValueIWantToKeep;
    public String userIDValueIWantToKeep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_spaceRegister =  findViewById(R.id.btn_spaceRegister);
        et_spaceName = findViewById(R.id.et_spaceName);
        et_spaceFunc =  findViewById(R.id.et_spaceFunc);
        tv_homeName =  findViewById(R.id.tv_homeName);
        btn_spaceView=findViewById(R.id.btn_spaceView);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            homeIDValueIWantToKeep = extras.getString("home_id");
            userIDValueIWantToKeep = extras.getString("id");
            String userHome = extras.getString("home");
            tv_homeName.setText(homeIDValueIWantToKeep);


        }
        btn_spaceRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String spaceName = et_spaceName.getText().toString();
                    String spaceFunction = et_spaceFunc.getText().toString();
                    String home_id = homeIDValueIWantToKeep;


                if (spaceName.equals("") || spaceFunction.equals("") ) {
                        Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
                    } else {
                        saveSpace(spaceName, spaceFunction, home_id);
                    }
                }

        });
        btn_spaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentShowContent = new Intent(MainActivity2.this, MainActivity3.class);
                intentShowContent.putExtra("user_id", userIDValueIWantToKeep);
                intentShowContent.putExtra("home_id", homeIDValueIWantToKeep);
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
        Intent intentShowContent = new Intent(MainActivity2.this, Login.class);
        startActivity(intentShowContent);
        return super.onOptionsItemSelected(item);
    }
    private void saveSpace(String spaceName, String spaceFunction, String home_id){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ec2-3-22-119-100.us-east-2.compute.amazonaws.com:3333").addConverterFactory(GsonConverterFactory.create()).build();
        HomeSpace homeSpace=retrofit.create(HomeSpace.class);
        Call<HomeSpacesValues> call=homeSpace.saveSpace(spaceName, spaceFunction, home_id);

        call.enqueue(new Callback<HomeSpacesValues>() {
            @Override
            public void onResponse(Call<HomeSpacesValues> call, Response<HomeSpacesValues> response) {

                try {
                    if (response.isSuccessful()) {
                        HomeSpacesValues p = response.body();
                        //String Email=p.getHome_id();
                        //String userID=p.getId();
                        Intent intentShowContent = new Intent(MainActivity2.this, MainActivity3.class);
                        intentShowContent.putExtra("user_id", userIDValueIWantToKeep);
                        intentShowContent.putExtra("home_id", homeIDValueIWantToKeep);
                        startActivity(intentShowContent);
                    }

                } catch (Exception ex) {
                    Toast.makeText(MainActivity2.this, "Revise datos de registro", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<HomeSpacesValues> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "Error Connecting", Toast.LENGTH_SHORT).show();
            }
        });
    }
}