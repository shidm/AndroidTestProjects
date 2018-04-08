package com.sdm.testprojects.view.textureview;

import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.view.Window;

import com.sdm.testprojects.R;


public class TextureViewActivity extends AppCompatActivity {

    private TextureView textureView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_textureview);

        init();
    }

    private void init() {
        textureView = findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                //在SurfaceTexture准备使用时调用
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                //当SurfaceTexture缓冲区大小更改时调用
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                //当指定SurfaceTexture即将被销毁时调用。如果返回true，则调用此方法后，表面纹理中不会发生渲染。
                //如果返回false，则客户端需要调用release()。大多数应用程序应该返回true
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                //当指定SurfaceTexture的更新时调用updateTexImage()
            }
        });
    }
}
