package com.sdm.testprojects.permission;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.sdm.testprojects.R;

/**
 * Created by shidongming on 18-2-5.
 */

public class PermissionTestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_permission);

        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("自定义权限测试");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }
}
