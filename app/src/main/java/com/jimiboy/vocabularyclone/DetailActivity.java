package com.jimiboy.vocabularyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    Spinner spinnerSize, spinnerUnit;
    TextView tvSize, tvUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvSize=findViewById(R.id.tv_detail_size);
        tvUnit=findViewById(R.id.tv_detail_unit);
        spinnerSize=(Spinner)findViewById(R.id.sp_detail_size);
        spinnerUnit=(Spinner)findViewById(R.id.sp_detail_unit);

        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}