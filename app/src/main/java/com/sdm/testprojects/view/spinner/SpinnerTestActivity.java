package com.sdm.testprojects.view.spinner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sdm.testprojects.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shidongming on 18-4-8.
 */

public class SpinnerTestActivity extends AppCompatActivity {

    private Spinner spinner, spinner2;
    private List<SpinnerContent> spinnerContents = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_spinner_test);

        for (int i = 0; i < 10; i++) {
            spinnerContents.add(new SpinnerContent(R.mipmap.ic_launcher_round, "ITEM----" + i));
        }

        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new SpinnerAdapter(this, R.layout.item_spinner_test, spinnerContents));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "点击选项" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 设置某一项，效果类似于viewPager.setCurrentItem(position);
        spinner.setSelection(2);

        spinner2 = findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "点击选项" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    class SpinnerAdapter extends BaseAdapter {
        List<SpinnerContent> list;
        Context context;
        int resource;

        public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<SpinnerContent> objects) {
            this.context = context;
            this.list = objects;
            this.resource = resource;
        }

        @Override
        public int getCount() {
            return (list != null) ? list.size() : 0;
        }

        @Nullable
        @Override
        public SpinnerContent getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(resource, parent, false);
                holder.tv = convertView.findViewById(R.id.stv);
                holder.iv = convertView.findViewById(R.id.siv);

                holder.tv.setText(list.get(position).getTv());
                holder.iv.setImageResource(list.get(position).getIv());
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.tv.setText(list.get(position).getTv());
                holder.iv.setImageResource(list.get(position).getIv());
            }

            return convertView;
        }
    }

    class ViewHolder {
        private TextView tv;
        private ImageView iv;
    }
}
