package com.jimiboy.vocabularyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText etId, etPw;
Button btnLogin, btnJoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId=findViewById(R.id.et_log_id);
        etPw=findViewById(R.id.et_log_pw);
        btnLogin=findViewById(R.id.btn_log_login);
        btnJoin=findViewById(R.id.btn_log_join);

        btnLogin.setOnClickListener(this);
        btnJoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_log_login:

                break;
            case R.id.btn_log_join:
                Intent intentJoin = new Intent(this,com.jimiboy.vocabularyclone.MainActivity_join.class );
                startActivity(intentJoin);
                break;

        }
    }
}