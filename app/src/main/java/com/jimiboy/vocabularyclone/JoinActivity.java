package com.jimiboy.vocabularyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etId, etPw, etChkPw, etName, etPhone;
    Button btnVerify, btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

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

//    private void loginChk(JSONObject jsonObject){
//        try {
//            String inputId=etId.getText().toString().trim();
//            String dbId=jsonObject.getString("result");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    // JSON테스트
//    private void jsonTest() {
//        try {
            // JSP에서 할 것
//            JSONObject jsonObject1 = new JSONObject();
//            jsonObject1.put("name", "장서정");
//            jsonObject1.put("gender", "F");
//
//            JSONObject jsonObject2 = new JSONObject();
//            jsonObject2.put("name", "박진혁");
//            jsonObject2.put("gender", "M");
//
//            JSONArray arr = new JSONArray();
//            arr.put(jsonObject1);
//            arr.put(jsonObject2);

            // 꺼내오기 안드로이드에서 할 것
//            for (int i = 0; i < arr.length(); i++) {
//                JSONObject temp = arr.getJSONObject(i);
//                String tempName = temp.getString("name");
//                String tempGender = temp.getString("gender");
//            }
//            Log.d("log", "chk: " + arr.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    // 정규화
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
    // 클릭리스너
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_join_verify:

                break;
            case R.id.btn_join_join:
                if (isVallid()) {
                     /* GET방식 사용 */
//                    String id = etId.getText().toString().trim();
//                    String pw = etPw.getText().toString().trim();
//                    String name = etName.getText().toString().trim();
//                    String phone = etPhone.getText().toString().trim();
//                    id = "id=" + id;    //id=aaa
//                    pw = "join_pass=" + pw;   //pass=aaa
//                    name = "name=" + name;
//                    phone = "phone=" + phone;
//                    String url = "http://192.168.123.112:8080/oop/join.do?" + id + "&" + pw + "&" + name + "&" + phone; //집
//                    String url = "http://172.16.146.28:8080/oop/mjoin.do?" + id + "&" + pw + "&" + name + "&" + phone;   //학원
//                    Log.d("log", url);
//
//                    RequestQueue requestQueue = Volley.newRequestQueue(this);
//                    StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);
//                    requestQueue.add(myReq);
                    /* POST */
                    final String id = etId.getText().toString().trim();
                    final String pw = etPw.getText().toString().trim();
                    final String name = etName.getText().toString().trim();
                    final String phone = etPhone.getText().toString().trim();

                    RequestQueue stringRequest = Volley.newRequestQueue(this);
//                    String url = "http://172.16.146.14:8080/oop/mjoin.do";   // 동욱씨 자리 IP
//                    String url = "http://172.16.146.28:8080/oop/mjoin.do";   // 학원 IP
                    String url = "http://192.168.123.112:8080/oop/mjoin.do";   // 집 IP
                    final StringRequest myReq = new StringRequest(Request.Method.POST, url,
                            successListener, errorListener) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", id);
                            params.put("join_pass", pw);
                            params.put("name", name);
                            params.put("phone", phone);

                            return params;
                        }
                    };
                    myReq.setRetryPolicy(new DefaultRetryPolicy(5000, 0, 1f)
                    );
                    stringRequest.add(myReq);
                } else {
//                    Toast.makeText(this, "데이터가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("log", "네트워크연결 실패");
        }
    };

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("log", response);
            try {
                JSONObject jsonObject1 =new JSONObject(response);
                Log.d("log", "json: "+jsonObject1);
                    if (jsonObject1.getString("result").equals("OK")){
                        finish();
                    }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}