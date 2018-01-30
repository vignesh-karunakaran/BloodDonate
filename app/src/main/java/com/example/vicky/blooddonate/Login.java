package com.example.vicky.blooddonate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText user,password;
    Button login;
    String username,pass;
    String ReciveURL = "http://192.168.42.158/donorRecive.php";
    SharedPreferences sp;

    public void registerActivty(View view)
    {
        Intent reg = new Intent(this,RegisterActivity.class);
        startActivity(reg)  ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        sp = this.getSharedPreferences("com.example.vicky.blooddonate", Context.MODE_PRIVATE);

        String Status = sp.getString("UserStatus","");
        Log.i("My Name is ",Status);

        if (Status.equals("LoggedIn"))
        {
            Intent i = new Intent(Login.this,ShowDataActivity.class);
            startActivity(i);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = user.getText().toString();
                pass = password.getText().toString();
                if(username.equals("")||pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Fill all the Fields..!!!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ReciveURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String value = response.toString();
                            if(value.equalsIgnoreCase("Succcess"))
                            {
                                sp.edit().putString("UserStatus","LoggedIn").apply();
                                Toast.makeText(getApplicationContext(),"Succfully LoggedIn",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this,ShowDataActivity.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Invalid User Please Give Correct UserName/PassWord",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> login = new HashMap<>();
                            login.put("name",username);
                            login.put("password",pass);
                            return login;
                        }
                    };

                    MySingleton.getInstance(Login.this).addRequstqueue(stringRequest);
                }
            }
        });
    }
}
