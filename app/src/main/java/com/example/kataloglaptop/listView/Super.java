package com.example.kataloglaptop.listView;

import java.io.Serializable;

public class Super implements Serializable {
    private String deskripsi;
    private int drawableRes;
    private String jenis;

    public Super(String jenis,String deskripsi, int drawableRes) {
        this.deskripsi = deskripsi;
        this.drawableRes = drawableRes;
        this.jenis = jenis;
    }

}