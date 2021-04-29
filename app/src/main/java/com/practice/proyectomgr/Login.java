package com.practice.proyectomgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.proyectomgr.interfaces.ProyectoMgrAPI;
import com.practice.proyectomgr.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Login extends AppCompatActivity {

    Button btn_lregister, btn_llogin;
    EditText et_lemail, et_lpassword;
    TextView tvEmail;
  //  DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // databaseHelper = new DatabaseHelper(this);

        et_lemail = (EditText)findViewById(R.id.et_lemail);
        et_lpassword = (EditText)findViewById(R.id.et_lpassword);

        btn_llogin = (Button)findViewById(R.id.btn_llogin);
        btn_lregister = (Button)findViewById(R.id.btn_lregister);
        tvEmail = (TextView)findViewById(R.id.tvEmail);

        btn_lregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(Login.this, MainActivity.class);
                startActivity(intentRegister);
            }
        });
        btn_llogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_lemail.getText().toString();
                String password = et_lpassword.getText().toString();

                if (email.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
                } else {
                    find(email, password);
                }
            }
        });
    }
        private void find(String emailuser, String emailpass){
            Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ec2-3-22-119-100.us-east-2.compute.amazonaws.com:3333").addConverterFactory(GsonConverterFactory.create()).build();
            ProyectoMgrAPI proyectoMgrAPI=retrofit.create(ProyectoMgrAPI.class);
            Call<User> call=proyectoMgrAPI.find(emailuser, emailpass);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    try {
                        if (response.isSuccessful()) {
                            User p = response.body();

                            String userID=p.getId();
                            String userHome=p.getHome();
                            String userHomeID=p.getHome_id();
                            Intent intentShowContent = new Intent(Login.this, MainActivity2.class);
                            intentShowContent.putExtra("id", userID);
                            intentShowContent.putExtra("home_id", userHomeID);
                            intentShowContent.putExtra("home", userHome);
                            startActivity(intentShowContent);

                        }
                    } catch (Exception ex) {
                        //Toast.makeText(Login.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Login.this, "email o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                return;
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Login.this, "Error Connecting", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
