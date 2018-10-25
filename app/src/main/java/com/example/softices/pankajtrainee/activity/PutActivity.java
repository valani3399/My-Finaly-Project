package com.example.softices.pankajtrainee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.softices.pankajtrainee.R;

import java.util.HashMap;
import java.util.Map;

public class PutActivity extends AppCompatActivity {
    TextView tvput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);
        tvput=(TextView)findViewById(R.id.tv_put);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Put Data");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(PutActivity.this, WebServisesActivity.class);
                startActivity(f);
                finish();
            }
        });
        pustmethod("https://reqres.in/api/users/2", "morpheus", "zion resident");

    }
    private void pustmethod(String s, final String name, final String job) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest MyStringRequest = new StringRequest(
                Request.Method.POST,
                s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PutActivity.this, response, Toast.LENGTH_SHORT).show();
                        //This code is executed if the server responds, whether or not the response contains data.
                        //The String 'response' contains the server's response.
                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PutActivity.this, "not", Toast.LENGTH_SHORT).show();
                //This code is executed if there is an error.
            }
        })  //hear }); no writing because use stringRequest ans this parameter decaler .....but get in writing }); besause parameter decalre is uper
        { //here pass parameter....
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", name); //Add the data you'd like to send to the server.
                MyData.put("job", job); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        requestQueue.add(MyStringRequest);
    }

}


