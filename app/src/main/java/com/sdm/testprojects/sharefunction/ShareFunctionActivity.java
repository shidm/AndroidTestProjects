package com.sdm.testprojects.sharefunction;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.testprojects.R;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shidongming on 18-2-28.
 */

public class ShareFunctionActivity extends AppCompatActivity {

    List<MusicEntity> musicEntities = new ArrayList<>();
    RecyclerView musicListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_share);

        init();
    }

    private void init() {

        musicListView = findViewById(R.id.musicList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        musicListView.setLayoutManager(manager);
        RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_music, parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "点击" + v.getTag(), Toast.LENGTH_SHORT).show();
                    }
                });
                view.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(getApplicationContext(), "长按" + v.getTag(), Toast.LENGTH_SHORT).show();

                        shareLink(v);
                        return true;
                    }
                });
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ViewHolder vh = (ViewHolder) holder;
                vh.itemView.setTag(position);
                vh.name.setText(musicEntities.get(position).getName());
                vh.name.setSelected(true);
                vh.title.setText(musicEntities.get(position).getTitle());
            }

            @Override
            public int getItemCount() {
                return musicEntities.size();
            }
        };
        musicListView.setAdapter(adapter);

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.YEAR,
                        MediaStore.Audio.Media.MIME_TYPE,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DATA},
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        if (cursor.moveToFirst()) {
            MusicEntity musicEntity;
            while (cursor.moveToNext()) {
                musicEntity = new MusicEntity();

                musicEntity.setId(cursor.getString(0));

                musicEntity.setName(cursor.getString(1));

                musicEntity.setTitle(cursor.getString(2));

                musicEntity.setDuration(cursor.getString(3));

                musicEntity.setSinger(cursor.getString(4));

                musicEntity.setAlbum(cursor.getString(5));

                musicEntity.setYear(cursor.getString(6));

                musicEntity.setType(cursor.getString(7));

                musicEntity.setSize(cursor.getString(8) == null ? "未知" : cursor.getString(8));

                musicEntity.setDataUrl(cursor.getString(9));

                musicEntities.add(musicEntity);
            }
        }

        if (musicEntities != null) {
            adapter.notifyDataSetChanged();
        }
    }


    //分享音乐
    private void shareAudio(View v){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("audio/*");
        Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                Long.parseLong(musicEntities.get((Integer) v.getTag()).getId()));
        Log.d("URI: ",uri.getPath());

        intent.putExtra(Intent.EXTRA_STREAM,uri);
        startActivity(Intent.createChooser(intent,"分享"));
    }

    //分享链接
    private void shareLink(View v){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.tencent.mm");
        intent.setType("text/html");
        String s = "http://blog.csdn.net/qq_28055429/article/details/51442643";
        intent.putExtra(Intent.EXTRA_SUBJECT,"如何把链接分享到QQ等上");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        intent.putExtra(Intent.EXTRA_TITLE,"标题");
        startActivity(Intent.createChooser(intent,"分享"));
    }

    //分享图片
    private void shareImage(View v){
        //分享图片
        BitmapDrawable drawable = (BitmapDrawable) getDrawable(R.mipmap.ic_launcher);
        Bitmap bitmap = drawable.getBitmap();
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", ""));
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor.moveToFirst()) {
            Log.d("url", cursor.getString(0));
        }
        Intent picIntent = new Intent(Intent.ACTION_SEND);
        picIntent.setType("image/*");
        picIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(picIntent, "图片分享"));
    }

    public void shareAll(View view) {

        /*//分享文字
        String s = "分享的文字";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,s);
        startActivity(Intent.createChooser(intent,"share"));*/

    }

    public void shareWX(View view) {
        String s = "分享的文字";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.tencent.mm");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, s);
        startActivity(intent);
    }

    public void shareQQ(View view) {
        String s = "分享的文字";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setPackage("com.tencent.mobileqq");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, s);
        startActivity(intent);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.music_title);

            name = itemView.findViewById(R.id.music_name);
        }
    }
}
