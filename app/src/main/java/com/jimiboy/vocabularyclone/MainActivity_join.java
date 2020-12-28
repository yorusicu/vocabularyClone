package com.jimiboy.vocabularyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity_join extends AppCompatActivity implements View.OnClickListener {
    EditText etId, etPw, etChkPw, etName, etPhone;
    Button btnVerify, btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_join);

        etId = findViewById(R.id.et_join_id);
        etPw = findViewById(R.id.et_join_pw);
        etChkPw = findViewById(R.id.et_join_chkpw);
        etName = findViewById(R.id.et_join_name);
        etPhone = findViewById(R.id.et_join_phone);

        btnVerify = findViewById(R.id.btn_join_verify);
        btnJoin = findViewById(R.id.btn_join_join);

        btnVerify.setOnClickListener(this);
        btnJoin.setOnClickListener(this);

    }

    private boolean isVallid() {
        boolean isVallid = true;
        if (etId.getText().toString().trim().length() < 1) {
            isVallid = false;
            Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (etId.getText().toString().trim().contains(" ")) {
            // 아이디에 공란이 있을 경우
            isVallid = false;
            Toast.makeText(this, "아이디에 공란이 있습니다", Toast.LENGTH_SHORT).show();
        } else if (etPw.getText().toString().trim().length() < 1) {
            isVallid = false;
            Toast.makeText(this, "패스워드를 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (!etPw.getText().toString().trim().equals(etChkPw.getText().toString().trim())) {
            isVallid = false;
            Toast.makeText(this, "패스워드가 같지 않습니다", Toast.LENGTH_SHORT).show();
        } else if (etName.getText().toString().trim().length() < 1) {
            isVallid = false;
            Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (etPhone.getText().toString().trim().length() < 1) {
            isVallid = false;
            Toast.makeText(this, "연락처를 입력해주세요", Toast.LENGTH_SHORT).show();
        }

        return isVallid;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_join_verify:

                break;
            case R.id.btn_join_join:
                if (isVallid()) {
                    String id = etId.getText().toString().trim();
                    String pw = etPw.getText().toString().trim();
                    String name = etName.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();

                    id = "id=" + id;    //id=aaa
                    pw = "join_pass=" + pw;   //pass=aaa
                    name = "name=" + name;
                    phone = "phone=" + phone;
                    String url = "http://192.168.123.112:8080/oop/join.do?" + id + "&" + pw + "&" + name + "&" + phone;
                    Log.d("log", url);
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);
                    requestQueue.add(myReq);

                } else {
                    Toast.makeText(this, "데이터가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("log", response);
        }
    };

}