package com.sdm.testprojects.lambda;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.sdm.testprojects.R;

public class LambdaTestActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextSwitcher textSwitcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lambda);

        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lambda表达式测试");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        textSwitcher = findViewById(R.id.textTest);
        textSwitcher.setFactory(() -> {
            TextView textView = new TextView(this);
            textView.setTextColor(Color.BLUE);
            return textView;
        });
        TranslateAnimation in = new TranslateAnimation(-1080,0,0,0);
        in.setDuration(1500);
        TranslateAnimation out = new TranslateAnimation(0,1080,0,0);
        out.setDuration(1500);
        textSwitcher.setInAnimation(in);
        textSwitcher.setOutAnimation(out);
        textSwitcher.setText("初始文本");
        textSwitcher.setOnClickListener((view)->textSwitcher.setText("新的值"));
    }
}
