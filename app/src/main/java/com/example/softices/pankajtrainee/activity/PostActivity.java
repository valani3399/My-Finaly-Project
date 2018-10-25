package com.example.softices.pankajtrainee.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.softices.pankajtrainee.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends Activity {
    TextView tvpost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvpost = (TextView) findViewById(R.id.tv_post);
//        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//        toolbar.setTitle("Post Data");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent f = new Intent(PostActivity.this, WebServisesActivity.class);
//                startActivity(f);
//                finish();
//            }
//        });
//        postmethod("https://reqres.in/api/register", "sydney@fife", "pistol");
        post("http://httpbin.org/post");

    }

    private void post(String s) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                s,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        postman(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void postman(JSONObject response) {
        try {
            String page=" ";
            JSONObject headers=response.getJSONObject("headers");
            String Accept=headers.getString("Accept");
            String lan=headers.getString("Accept-Encoding");
            String lon=headers.getString("Accept-Language");
            page=page+Accept+lan+lon;
            tvpost.setText(page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
//    private void postmethod(String s, final String email, final String password) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        StringRequest MyStringRequest = new StringRequest(
//                Request.Method.POST,
//                s,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(PostActivity.this, response, Toast.LENGTH_SHORT).show();
//                        //This code is executed if the server responds, whether or not the response contains data.
//                        //The String 'response' contains the server's response.
//                    }
//                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //This code is executed if there is an error.
//            }
//        })  //hear }); no writing because use stringRequest ans this parameter decaler .....but get in writing }); besause parameter decalre is uper
//        { //here pass parameter....
//            protected Map<String, String> getParams() {
//                Map<String, String> MyData = new HashMap<String, String>();
//                MyData.put("email", email); //Add the data you'd like to send to the server.
//                MyData.put("password", password); //Add the data you'd like to send to the server.
//                return MyData;
//            }
//        };
//        requestQueue.add(MyStringRequest);
//    }
//}

