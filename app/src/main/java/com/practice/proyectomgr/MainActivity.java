package com.practice.proyectomgr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.proyectomgr.interfaces.ProyectoMgrAPI;
import com.practice.proyectomgr.models.User;
import android.content.Intent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText et_username, et_email, et_home, et_password, et_cpassword;
    Button btn_register, btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //  databaseHelper = new DatabaseHelper(this);
            et_username = (EditText) findViewById(R.id.et_username);
            et_email = (EditText) findViewById(R.id.et_email);
            et_password = (EditText) findViewById(R.id.et_password);
            et_cpassword = (EditText) findViewById(R.id.et_cpassword);
            et_home = (EditText) findViewById(R.id.et_home);
            btn_register = (Button) findViewById(R.id.btn_register);
            btn_login = (Button) findViewById(R.id.btn_login);

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                }
            });

            btn_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = et_username.getText().toString();
                    String email = et_email.getText().toString();
                    String password = et_password.getText().toString();
                    String home = et_home.getText().toString();
                    String confirm_password = et_cpassword.getText().toString();

                    if (username.equals("") || email.equals("") || password.equals("") || confirm_password.equals("") || home.equals("")) {
                        Toast.makeText(getApplicationContext(), "Hay campos vacios", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password.equals(confirm_password)) {
                            save(username, email, password, home);
                        } else {
                            Toast.makeText(getApplicationContext(), "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        private void save(String userName, String emailUser, String passUser, String userHome){
            Retrofit retrofit=new Retrofit.Builder().baseUrl("http://ec2-3-22-119-100.us-east-2.compute.amazonaws.com:3333").addConverterFactory(GsonConverterFactory.create()).build();
            ProyectoMgrAPI proyectoMgrAPI=retrofit.create(ProyectoMgrAPI.class);
            Call<User> call=proyectoMgrAPI.save(userName, emailUser, passUser, userHome);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    try {
                        if (response.isSuccessful()) {
                            User p = response.body();

                        }

                    } catch (Exception ex) {
                        Toast.makeText(MainActivity.this, "Revise datos de registro", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error Connecting", Toast.LENGTH_SHORT).show();
                }
            });
        }
}

