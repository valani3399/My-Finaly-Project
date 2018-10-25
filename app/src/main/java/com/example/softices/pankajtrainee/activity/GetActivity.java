package com.example.softices.pankajtrainee.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.methods.Utils;

import org.json.JSONObject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class GetActivity extends AppCompatActivity {
    TextView tvget;
    public String idString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        tvget = (TextView) findViewById(R.id.tv_get);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Get Data");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(GetActivity.this, WebServisesActivity.class);
                startActivity(f);
                finish();
            }
        });
//        webservises("https://reqres.in/api/users?page=1");
        webservises("http://fakerestapi.azurewebsites.net:80/swagger/docs/v1");

    }

    private void webservises(String s) {
        Utils.showProgressDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                s,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseJsonData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.hideProgressDialog();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void parseJsonData(JSONObject response) {
        try {
            String page = "";
            JSONObject paths = response.getJSONObject("paths");
            JSONObject appi = paths.getJSONObject("/api/Activities");
            JSONObject get = appi.getJSONObject("get");
            String teg = get.getString("get");
            String summary = get.getString("summary");
            String operationId = get.getString("operationId");
            String consumes = get.getString("consumes");
            String produces = get.getString("produces");
            JSONObject responses = get.getJSONObject("responses");
            JSONObject num = response.getJSONObject("200");
            String description = num.getString("description");
            JSONObject schema = num.getJSONObject("schema");
            String type = schema.getString("type");
            JSONObject items = schema.getJSONObject("items");
            String ref = items.getString("$ref");
            Boolean deprecated = get.getBoolean("deprecated");
            page = page + teg + summary + operationId + produces + consumes + description + type + ref + description;
            tvget.setText(page);
            Utils.hideProgressDialog();
        } catch (Exception e) {

        }
    }


//
//    private void parseJsonData(JSONObject response) {
//        try {
//            //String page useing print values
//            String page = "";
//            //first decaler object;
//            JSONObject pathsobject=response.getJSONObject("paths");
//            JSONObject api=pathsobject.getJSONObject( "/api/Activities");
//            JSONObject get=api.getJSONObject("get");
//
//            JSONArray jsonArray = get.getJSONArray("tags");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                String name=jsonArray.getString(i);
//                page=page + name;
//            }
//            String summar=get.getString("summary");
//            String operationid=get.getString("operationId");
//            String consume=get.getString("consumes");
//            String pro=get.getString("produces");
//            JSONObject res=get.getJSONObject("responses");
//            JSONObject number=res.getJSONObject("200");
//            String descript=number.getString("description");
//            JSONObject schem=number.getJSONObject("schema");
//            String type=schem.getString("type");
//            JSONObject item=schem.getJSONObject("items");
//            String re=item.getString("$ref");
//            Boolean deprecate=get.getBoolean("deprecated");
//            page=page + "\n"+summar + "\n"+ operationid +"\n" + consume + "\n" + pro + "\n" +descript +"\n" +type +"\n" + re +"\n" +deprecate;
//            tvget.setText(page);
//
//        } catch (Exception e) {
//            Log.e("parseJsonData", e + "");
//        }
//    }

//    private void webservises(String s) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                s,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        parseJsonData(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }
//
//
//    private void parseJsonData(JSONObject response) {
//        try {
//            String page = "";
//            String perPage = response.getString("per_page");
//            JSONArray jsonArray = response.getJSONArray("data");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject=jsonArray.getJSONObject(i);
//                String Id=jsonObject.getString("id");
//                String First_name=jsonObject.getString("first_name");
//                String Last_name=jsonObject.getString("last_name");
//                page=page+Id +" \n" +First_name + " \n" +Last_name + "\n\n\n";
//
//            }
//            tvget.setText(page);
//
//        } catch (Exception e) {
//            Log.e("parseJsonData", e + "");
//        }
//    }
}
