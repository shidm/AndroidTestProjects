package com.sdm.testprojects.json_test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sdm.testprojects.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by shidongming on 18-4-17.
 */

public class JsonTestActivity extends AppCompatActivity {

    TextView textView;
    private static final String TAG = "JsonActivity_onCreate: ";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText((CharSequence) msg.obj);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_json_test);

        Log.d(TAG, "onCreate: ");
        textView = findViewById(R.id.myjson_tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "run: ");
                    URL url = new URL("http://10.0.2.2:8080/AppServer/TestServlet");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder stringBuilder = new StringBuilder();
                    String str1 = null;
                    while (!TextUtils.isEmpty(str1 = reader.readLine())){
                        stringBuilder.append(str1);
                        Log.d(TAG, "while");
                    }
                    Gson gson = new Gson();
                    Test test = gson.fromJson(stringBuilder.toString(), Test.class);
                    StringBuilder sb = new StringBuilder();
                    for (String s:test.getName()) {
                        Log.d(TAG, "name: " + s);
                        sb.append(s+"\n");
                    }
                    Message message = Message.obtain();
                    message.obj = sb.toString();
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class Test{
        private List<String> name;

        public Test(List<String> name) {
            this.name = name;
        }

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "name=" + name +
                    '}';
        }
    }
}
