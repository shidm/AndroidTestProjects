package com.sdm.ptmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);
        Log.d("SecondMainActivity", "onCreate: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondMainActivity", "onDestroy: ");
    }
}
