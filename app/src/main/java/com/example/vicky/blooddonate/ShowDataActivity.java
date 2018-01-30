package com.example.vicky.blooddonate;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

public class ShowDataActivity extends AppCompatActivity {

    EditText TArea,TDistrict;
    Button Search;
    String SearchURL = "http://192.168.42.158/donorSearch.php";
    String Sarea,Sdistrict,Sbloodgroup;
    String[] SpinerArray;
    TextView ResultText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        TArea = (EditText)findViewById(R.id.Varea);
        TDistrict = (EditText)findViewById(R.id.Vdistrict);
        Search = (Button)findViewById(R.id.VSearch);
        ResultText = (TextView)findViewById(R.id.VResultText);

        final ShowDataActivity sh = new ShowDataActivity();

        SpinerArray = getResources().getStringArray(R.array.BloodGroups);
        Spinner sp1 = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,SpinerArray);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                Sbloodgroup = SpinerArray[index];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sarea = TArea.getText().toString();
                Sdistrict = TDistrict.getText().toString();

                if(Sbloodgroup.equals("SelectBloodGroup")||Sarea.equals("")||Sdistrict.equals(""))
                {

                    Toast.makeText(getApplicationContext(),"Please Fill all the Fields..!!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, SearchURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            StringBuffer sb = new StringBuffer();

                            String jasonString;
                            jasonString = response;

                            Log.i("Value before giving", jasonString);

                            try {
                                Intent view = new Intent(ShowDataActivity.this,ViewDetailsActivity.class);
                                view.putExtra("JasonData",jasonString);
                                startActivity(view);

                            }catch (Exception e){
                                e.printStackTrace();
                                Log.i("What Happened", "Intent Failed");
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> search = new HashMap<>();
                            search.put("area",Sarea);
                            search.put("district",Sdistrict);
                            search.put("blood",Sbloodgroup);
                            return search;
                        }
                    };
                    MySingleton.getInstance(ShowDataActivity.this).addRequstqueue(stringRequest);
                }
            }
        });
    }

}
