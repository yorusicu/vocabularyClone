package com.jimiboy.vocabularyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class BaseActivity extends AppCompatActivity {
    RelativeLayout loadingLayout;

    Map<String, String> params = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    // 네트워크 시도
    public void request(String url, Response.Listener<String> successListener) {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
        }

        RequestQueue stringRequest = Volley.newRequestQueue(this);

        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
//                params.put("token", Storage.token);
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f)

        );
        stringRequest.add(myReq);
    }

    // 네트워크 오류
    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (loadingLayout != null) {
                loadingLayout.setVisibility(View.INVISIBLE);
            }
        showToast("네트워크연결 실패");
        }
    };

    // 토스트
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // 암호화
    public String sha256(String str) {
        String SHA = "";
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++)
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            SHA = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }


}