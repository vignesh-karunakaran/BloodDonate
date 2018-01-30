package com.example.vicky.blooddonate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ViewDetailsActivity extends AppCompatActivity {
    ArrayList<String> names,mobile,areaArray,districtArray;
    String jsonData,jmessege,jname,jmobile,jarea,jdistrict;
    JSONArray jArray;
    AlertDialog.Builder ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        names = new ArrayList<>();
        mobile = new ArrayList<>();
        areaArray = new ArrayList<>();
        districtArray = new ArrayList<>();

        ab = new AlertDialog.Builder(ViewDetailsActivity.this);


        CustomAdapter ca = new CustomAdapter();
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(ca);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sb="";
                sb += areaArray.get(position).toString()+",";
                sb += districtArray.get(position).toString();
                Toast.makeText(getApplicationContext(),sb,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ViewDetailsActivity.this,MapsActivity.class);
                intent.putExtra("Place",sb);
                startActivity(intent);
            }
        });

        jsonData = getIntent().getExtras().getString("JasonData");
        Log.i("Given Json Data's",jsonData);

        try {
            JSONObject Job1 = new JSONObject(jsonData);
            String JsonResult = Job1.getString("serverResponse");
            jArray = new JSONArray(JsonResult);
            for (int i=0;i<jArray.length();i++)
            {
                JSONObject jsonObject = jArray.getJSONObject(i);
                jmessege = jsonObject.getString("Messege");
                if(jmessege.equals("FullData"))
                {
                    jmobile = jsonObject.getString("mobile");
                    jname = jsonObject.getString("name");
                    jarea = jsonObject.getString("area");
                    jdistrict = jsonObject.getString("district");
                    names.add(jname);
                    mobile.add(jmobile);
                    areaArray.add(jarea);
                    districtArray.add(jdistrict);
                }
                else if(jmessege.equals("DistrictVise"))
                {
                    jmobile = jsonObject.getString("mobile");
                    jname = jsonObject.getString("name");
                    jarea = jsonObject.getString("area");
                    jdistrict = jsonObject.getString("district");
                    names.add(jname);
                    mobile.add(jmobile);
                    areaArray.add(jarea);
                    districtArray.add(jdistrict);
                    ab.setTitle("Donor Status");
                    ab.setMessage("Currently there is no donor's available in the Required Area \n Showing the Result of Donor's from your District...!!!");
                    ab.setPositiveButton("ok",new Dialog.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                else if(jmessege.equals("BloodVise"))
                {
                    jmobile = jsonObject.getString("mobile");
                    jname = jsonObject.getString("name");
                    jarea = jsonObject.getString("area");
                    jdistrict = jsonObject.getString("district");
                    names.add(jname);
                    mobile.add(jmobile);
                    areaArray.add(jarea);
                    districtArray.add(jdistrict);
                    ab.setTitle("Donor Status");
                    ab.setMessage("Currently there is no donor's available in the Required Area/District \n Showing the Result of Donor's from selected Blood Group...!!!");
                    ab.setPositiveButton("ok",new Dialog.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                }
                else if (jmessege.equals("NoDonorsData"))
                {
                    ab.setTitle("Donor Status");
                    ab.setMessage("Sorry Currently no Donor's is Available for the Required Blood Group...!!!");
                    ab.setPositiveButton("ok",new Dialog.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent showData = new Intent(ViewDetailsActivity.this,ShowDataActivity.class);
                            startActivity(showData);
                        }
                    });
                }
            }
            AlertDialog alertDialog = ab.create();
            alertDialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    class CustomAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return names.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            view = getLayoutInflater().inflate(R.layout.custom_layout,null);
            TextView vname = (TextView)view.findViewById(R.id.nametext);
            TextView vnumber = (TextView)view.findViewById(R.id.numbertext);


            vname.setText(names.get(position));
            vnumber.setText(mobile.get(position));

            return view;
        }
    }
}
