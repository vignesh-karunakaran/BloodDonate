package com.example.vicky.blooddonate;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    EditText useranme,password,confirmPassword,mobileNum,Email,Area,District,BloodGroup;
    Button Register;
    String InsertURL = "http://192.168.42.158/donorInsert.php";
    String user,pass,cpass,num,mail,area,district,bloodgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        useranme = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.pass);
        confirmPassword = (EditText)findViewById(R.id.cpass);
        mobileNum = (EditText)findViewById(R.id.number);
        Email = (EditText)findViewById(R.id.mail);
        Area = (EditText)findViewById(R.id.area);
        District = (EditText)findViewById(R.id.district);
        BloodGroup = (EditText)findViewById(R.id.bloogroup);

        Register = (Button)findViewById(R.id.register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = useranme.getText().toString();
                pass = password.getText().toString();
                cpass = confirmPassword.getText().toString();
                num = mobileNum.getText().toString();
                mail = Email.getText().toString();
                area = Area.getText().toString();
                district = District.getText().toString();
                bloodgroup = BloodGroup.getText().toString();

                if(pass.equals(cpass))
                {
                    if(user.equals("")||pass.equals("")||num.equals("")||mail.equals("")||area.equals("")||district.equals("")||bloodgroup.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please Fill all the Fields To Register..!!!",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, InsertURL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this,ShowDataActivity.class);
                                startActivity(i);
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

                                Map<String,String> params = new HashMap<>();
                                params.put("name",user);
                                params.put("password",pass);
                                params.put("mobile",num);
                                params.put("email",mail);
                                params.put("area",area);
                                params.put("district",district);
                                params.put("blood",bloodgroup);

                                return params;
                            }
                        };
                        MySingleton.getInstance(RegisterActivity.this).addRequstqueue(stringRequest);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"PassWord's dose Not Match",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
