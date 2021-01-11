package com.example.kataloglaptop.listView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.kataloglaptop.R;

import com.example.kataloglaptop.model.HasilActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton btnHp,btnAsus;
    public static final String JENIS_GALERI_KEY = "JENIS_GALERI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitity_main);
        inisialisasiView();

    }

    private void inisialisasiView() {
        btnAsus = findViewById(R.id.btn_buka_model_asus);
        btnAsus.setOnClickListener(view -> bukaGaleri("Asus"));
    }

    private void bukaGaleri(String modelLaptop) {
        Log.d("MAIN","Buka activity galeri");
        Intent intent = new Intent(this, HasilActivity.class);
        intent.putExtra(JENIS_GALERI_KEY, modelLaptop);
        startActivity(intent);
    }

}