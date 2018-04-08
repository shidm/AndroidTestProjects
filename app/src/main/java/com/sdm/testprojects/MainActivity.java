package com.sdm.testprojects;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sdm.ptmodule.SecondMainActivity;
import com.sdm.testprojects.lambda.LambdaTestActivity;
import com.sdm.testprojects.permission.PermissionTestActivity;
import com.sdm.testprojects.sharefunction.ShareFunctionActivity;
import com.sdm.testprojects.socket.SocketTestActivity;
import com.sdm.testprojects.view.spinner.SpinnerTestActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button lambda, permission, socket;
    private Toolbar toolbar;

    private static final String TAG = "MainActivity";
    private long firstTouchBack = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        NetworkInfo.State state = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        Log.d(TAG+"State", "onCreate: "+state.name());

//        Intent intent = new Intent(MainActivity.this, SecondMainActivity.class);
//        startActivity(intent);

//        Intent intent1 = new Intent("com.meizu.flyme.EASY_MODE_SWITCH");
//        intent1.setPackage("com.meizu.flyme.easylauncher");
//        startActivity(intent1);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
            }
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        lambda = findViewById(R.id.lambda_test);
        permission = findViewById(R.id.permission_test);
        socket = findViewById(R.id.socket_test);

        lambda.setOnClickListener(this);
        permission.setOnClickListener(this);
        socket.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lambda_test:
                startActivity(new Intent(this, LambdaTestActivity.class));
                break;
            case R.id.permission_test:
                startActivity(new Intent(this, PermissionTestActivity.class));
                break;
            case R.id.socket_test:
                startActivity(new Intent(this, SocketTestActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * md5加密
     *
     * @param message 需要加密的数据
     * @return
     */
    public byte[] getMd5(String message) {
        MessageDigest md5;
        byte[] newPwd = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            newPwd = md5.digest(message.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return newPwd;
    }

    public void shareActivity(View view) {
        startActivity(new Intent(this, ShareFunctionActivity.class));
    }

    public void jumpToFlymeAccount(View view) {
        Intent intent = new Intent("com.meizu.account.ACCOUNTCENTER");
        intent.setPackage("com.meizu.account");
        intent.putExtra("source", this.getPackageName());
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown()"+firstTouchBack);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 按下BACK，同时没有重复

        }

        return super.onKeyDown(keyCode,event);
    }

    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");

        Log.d(TAG, "firstTouchBack: "+firstTouchBack);
        if (System.currentTimeMillis() - firstTouchBack < 2000) {
            super.onBackPressed();
        }else {
            Toast.makeText(this,"再次点击退出应用",Toast.LENGTH_SHORT).show();
            firstTouchBack = System.currentTimeMillis();
        }
    }

    public void testActivity(View view) {
        startActivity(new Intent(MainActivity.this, TestActivity.class));
    }

    public void jumpToSpinnerActivity(View view) {
        startActivity(new Intent(MainActivity.this, SpinnerTestActivity.class));
    }
}
