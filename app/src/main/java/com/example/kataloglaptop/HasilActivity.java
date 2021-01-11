package com.example.kataloglaptop.model;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kataloglaptop.DaftarLaptopAdapter;
import com.example.kataloglaptop.FormLaptopActivity;
import com.example.kataloglaptop.GenericUtility;
import com.example.kataloglaptop.R;
import com.example.kataloglaptop.SharedPreferenceUtility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class HasilActivity extends AppCompatActivity {
    FloatingActionButton btnTambahTransaksi;
    ImageButton btnChangeUserName;
    ListView lvDaftarTransaksi;
    TextView txNoData, txUsername, txHarga;
    DaftarLaptopAdapter adapter;
    List<Laptop> daftarTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hasil_activity);
        inisialisasiView();
        loadDataTransaksi();
        setupListview();
    }

    private void inisialisasiView() {
        btnTambahTransaksi = findViewById(R.id.btn_add_laptop);
        btnTambahTransaksi.setOnClickListener(view -> bukaFormTambahTransaksi());
        btnChangeUserName = findViewById(R.id.btn_change_username);
        btnChangeUserName.setOnClickListener(view -> changeUserName());
        lvDaftarTransaksi = findViewById(R.id.lv_laptop);
        txNoData = findViewById(R.id.tx_nodata);
        txUsername = findViewById(R.id.tx_user_name);
        txUsername.setText(SharedPreferenceUtility.getUserName(this));
        txHarga = findViewById(R.id.tx_harga);
    }

    private void setupListview() {
        adapter = new DaftarLaptopAdapter(this, daftarTransaksi);
        lvDaftarTransaksi.setAdapter(adapter);
    }

    private void loadDataTransaksi() {
        daftarTransaksi = SharedPreferenceUtility.getAllLaptop(this);
        double saldo = 0;
        if (daftarTransaksi.size()>0) {
            txNoData.setVisibility(View.GONE);
            // hitung saldo
            for (Laptop tr:daftarTransaksi) {
                Log.d("MAIN","TR:"+tr.toJSONObject().toString());
                if (tr.getJenis().equals(Laptop.ASUS)) {
                    saldo -= tr.getNilai();
                } else {
                    saldo += tr.getNilai();
                }
            }

        } else {
            txNoData.setVisibility(View.VISIBLE);
        }
        txHarga.setText(GenericUtility.formatUang(saldo));
    }

    private void refreshListView() {
        adapter.clear();
        loadDataTransaksi();
        adapter.addAll(daftarTransaksi);
    }

    private void bukaFormTambahTransaksi() {
        Intent intent = new Intent(this, FormLaptopActivity.class);
        startActivity(intent);
    }

    private void changeUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ganti nama");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtility.saveUserName(getApplicationContext(),input.getText().toString());
                Toast.makeText(getApplicationContext(),"Nama user berhasil disimpan",Toast.LENGTH_SHORT).show();
                txUsername.setText(SharedPreferenceUtility.getUserName(getApplicationContext()));
            }
        });
        builder.setNegativeButton("Batall", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }
}