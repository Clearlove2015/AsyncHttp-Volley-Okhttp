package com.zc.okhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * http://blog.csdn.net/itachi85/article/details/51190687
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
                okhttp_get();
                break;
            case R.id.btn_post:
                okhttp_post();
                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content = msg.getData().getString("response");
            tvContent.setText(content);
        }
    };

    private void okhttp_get() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .method("GET", null)//可以省略，默认是GET请求
                .url("http://gank.io/api/search/query/listview/category/Android/count/10/page/1")
                .build();

        Call call = client.newCall(request);
        //异步执行
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String json = response.body().string().toString();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("response", json);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        });
    }

    private void okhttp_post() {
        OkHttpClient client = new OkHttpClient();

        /**
         * Android Retrofit2&OkHttp3添加统一的请求头Header
         * http://blog.csdn.net/jdsjlzx/article/details/51578231
         */
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request()
//                                .newBuilder()
//                                .addHeader("key","value")
//                                .build();
//                        return chain.proceed(request);
//                    }
//                }).build();

        RequestBody body = new FormBody.Builder()
                .add("LoginName","tomcat")
                .add("LoginPassword","123456")
                .build();

        String url = "http://192.168.1.102:8080/Aron_Dynamic_Web_Servlet/dologin";

        Request request = new Request.Builder()
                //.addHeader("key","value")//添加请求头
                .post(body)
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string().toString();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("response", json);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        });
    }
}
