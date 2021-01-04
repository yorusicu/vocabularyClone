package com.jimiboy.vocabularyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {
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
    // 로그인 정규화
    public boolean isVallid() {
        String id=etId.getText().toString().trim();
        String pw=etPw.getText().toString().trim();

        boolean isVallid = true;
        if (id.length() < 1) {
            isVallid = false;
            showToast("아이디를 입력해주세요");
        } else if (id.contains(" ")) {
            isVallid = false;
            showToast("아이디에 공백이 있습니다");
        }else if (pw.length() < 1) {
            isVallid = false;
            showToast("패스워드를 입력해주세요");
        }

        return isVallid;
    }
    // 클릭리스너
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_log_login:
                if (isVallid()) {
                    final String id=etId.getText().toString().trim();
                    final String pw=etPw.getText().toString().trim();
                    // 네트워크 시도
                    RequestQueue stringRequest = Volley.newRequestQueue(this);
//                    String url = "http://172.16.146.14:8080/oop/mlogin.do";   // 동욱씨 자리 IP
//                    String url = "http://172.16.146.28:8080/oop/mlogin.do";   // 학원 IP
                    String url = "http://192.168.123.112:8080/oop/mlogin.do"; //집 IP
                    Log.d("log","Login_url: "+url);
                    final StringRequest myReq = new StringRequest(Request.Method.POST, url,
                            successLoginListener, errorListener) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", id);
                            params.put("pass", pw);
                            return params;
                        }
                    };
                    // 5초이상 걸릴시 errorListener로 이동
                    myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 0, 1f)
                    );
                    stringRequest.add(myReq);
                }
                break;

            case R.id.btn_log_join:
                Intent intentJoin = new Intent(this, JoinActivity.class );
                startActivity(intentJoin);
                break;
        }
    }


    // 로그인 연결 성공
    Response.Listener<String> successLoginListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
//            Log.d("log", response);
            try {
                JSONObject jsonObject1 =new JSONObject(response);
                Log.d("log", "json: "+jsonObject1);
                if (jsonObject1.getString("result").equalsIgnoreCase("OK")){
                    Intent intentList=new Intent(MainActivity.this, ListActivity.class);
                    startActivity(intentList);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    // 로그인 연결 실패
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("log", "네트워크연결 실패");
        }
    };

}