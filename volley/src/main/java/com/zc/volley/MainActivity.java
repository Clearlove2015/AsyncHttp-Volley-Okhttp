package com.zc.volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * http://gank.io/api
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn_get)
    Button btnGet;
    @Bind(R.id.btn_post)
    Button btnPost;
    @Bind(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_get, R.id.btn_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                volley_get();
                break;
            case R.id.btn_post:
                volley_post();
                break;
        }
    }

    private void volley_get() {
        //1.创建一个请求队列
        RequestQueue queue = Volley.newRequestQueue(this);

        //2.创建一个请求
        String url = "http://gank.io/api/search/query/listview/category/Android/count/10/page/1";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tvContent.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvContent.setText(volleyError.getMessage());
            }
        });

        //3.将创建的请求添加到请求队列中
        queue.add(stringRequest);
    }

    private void volley_post() {
        //1.创建一个请求队列
        RequestQueue queue = Volley.newRequestQueue(this);

        //2.创建一个请求
        String url = "http://192.168.1.102:8080/Aron_Dynamic_Web_Servlet/dologin";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tvContent.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tvContent.setText(volleyError.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //请求参数
                Map<String,String> params = new HashMap<>();
                params.put("LoginName","tomcat");
                params.put("LoginPassword","123456");
                return params;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                //添加请求头
//                Map<String,String> headers = new HashMap<>();
//                headers.put("key","value");
//                return headers;
//            }
        };

        //3.将创建的请求添加到请求队列中
        queue.add(stringRequest);

    }

}
