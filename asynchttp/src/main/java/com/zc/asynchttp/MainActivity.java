package com.zc.asynchttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
                async_get();
                break;
            case R.id.btn_post:
                async_post();
                break;
        }
    }

    private void async_get() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://gank.io/api/search/query/listview/category/Android/count/10/page/1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String json) {
                super.onSuccess(json);
                tvContent.setText(json);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                tvContent.setText(throwable.getMessage());
            }
        });
    }

    private void async_post() {
        AsyncHttpClient client = new AsyncHttpClient();
        //client.addHeader("key","value");//添加请求头
        String url = "http://192.168.1.102:8080/Aron_Dynamic_Web_Servlet/dologin";
        //添加请求参数
        RequestParams params = new RequestParams();
        params.put("LoginName","tomcat");
        params.put("LoginPassword","123456");
        client.post(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                tvContent.setText(s);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                tvContent.setText(throwable.getMessage());
            }
        });
    }

}
