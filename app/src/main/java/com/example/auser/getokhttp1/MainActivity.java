package com.example.auser.getokhttp1;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);

    }

    public void getMethod(View v) {
        OkHttpClient client = new OkHttpClient();
        String param = "userid=" + et1.getText().toString() + "&temperature=" + et2.getText().toString();

        Request request = new Request.Builder()
                .url("http://192.168.58.015:8080/code/android_api/get_api_return.php?" + param)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test2", "失敗");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "連線失敗", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("test3", "成功");
                final String json = response.body().string();
                if (json.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test3", json);
                            Toast.makeText(MainActivity.this, "帳號已存在", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (json.equals("1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test3", json);
                            Toast.makeText(MainActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test3", json);
                            Toast.makeText(MainActivity.this, "新增失敗", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            }
        });
    }
}


