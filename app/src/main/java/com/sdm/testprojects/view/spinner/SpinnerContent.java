package com.sdm.testprojects.view.spinner;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shidongming on 18-4-8.
 */

public class SpinnerContent {

    private int iv;
    private String tv;

    public SpinnerContent() {
    }

    public SpinnerContent(int iv, String tv) {
        this.iv = iv;
        this.tv = tv;
    }

    public int getIv() {
        return iv;
    }

    public void setIv(int iv) {
        this.iv = iv;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }
}
