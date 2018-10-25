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

public class DeletActivity extends AppCompatActivity {
    TextView tvdelet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delet);
        tvdelet=(TextView)findViewById(R.id.tv_deletweb);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Delet Data");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(DeletActivity.this, WebServisesActivity.class);
                startActivity(f);
            }
        });
        deletmethod("https://reqres.in/api/users/2");
    }

    private void deletmethod(String s) {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(
                Request.Method.DELETE,
                s,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DeletActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeletActivity.this, "not", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

    }
}
