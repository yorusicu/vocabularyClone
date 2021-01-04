package com.jimiboy.vocabularyclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    /**
     * 1. item 위한 레이아웃을 만든다
     * 3. item 위한 데이터 클래스 만든다
     * 4. 어레이 리스트를 만든다
     * 5. listView를 만든다
     * 6. 아답터 클래스 복붙하기
     * 7. 아답터 빨간 부분 수정(대부분 대문자는 import 하면 됨 holder 제외)
     * 8. 이전 과 동일
     */
    Map<String, String> params = new HashMap<String, String>();
    ArrayList<ItemData> arr = new ArrayList<>();
    MyAdapter adapter = null;

    ListView lvMain;
    TextView tvTitle;
    ImageView ivMain;
    Button btnMode;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout loadingLayout;

    int mode = 0;   // 수정/삭제 버튼
    String idx ="0";     // 리스트불러오기 위한 idx
    boolean isEnd = false;  // 리스트가 끝인지 확인
    boolean lastItemVisibleFlag = false;    // 리스트가 바닥에 닿았는지 확인
//    String url = "http://172.16.146.14:8080/oop/mcontentlist.do";   // 동욱씨 자리 IP
    String url = "http://192.168.123.112:8080/oop/mcontentlist.do";   // 집 IP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        tvTitle = findViewById(R.id.tv_list_title);
        lvMain = findViewById(R.id.li_list_list);
        ivMain = findViewById(R.id.iv_item_main);
        btnMode = findViewById(R.id.btn_list_mode);
        swipeRefreshLayout = findViewById(R.id.sw_list_swipe1);
        loadingLayout = findViewById(R.id.loading_layout);

        // 신호 전송
        request(url, successListListener);

        adapter = new MyAdapter(this);
        btnMode.setOnClickListener(this);
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(this);

        lvMain.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                //OnScrollListener.SCROLL_STATE_IDLE은 스크롤이 이동하다가 멈추었을때 발생되는 스크롤 상태입니다.
                //즉 스크롤이 바닦에 닿아 멈춘 상태에 처리를 하겠다는 뜻
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && !isEnd) {
                    //TODO 화면이 바닦에 닿을때 처리
                    idx = arr.get(arr.size()-1).idx;
                    requestForData();
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });
        // 최상단 새로고침
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arr.clear();
//                idx = "0";
                requestForData();
            }
        });
    }
    private void requestForData(){
        loadingLayout.setVisibility(View.VISIBLE);
        params.clear();
        params.put("idx", idx);
        request(url, successListListener);
    }

    Response.Listener<String> successListListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
//            Log.d("log", "response: "+response);
            try {
                swipeRefreshLayout.setRefreshing(false);
                loadingLayout.setVisibility(View.GONE);
                JSONArray jsonArray = new JSONArray(response);
//                String result=jsonArray.getJSONObject(0).getString("result");
//                Log.d("log", "result: "+result);

                String idx = null, title = null, write = null, date = null, img = null, explan = null;
                int price = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = jsonArray.getJSONObject(i);
//                    Log.d("log", "temp: " + temp);
                    idx = temp.getString("idx");
                    title = temp.getString("title");
                    write = temp.optString("name");
                    date = temp.getString("date");
//                    img = "http://172.16.146.14:8080/oop/img/shoes/" + temp.getString("img").trim();    // 학원(동욱씨)
                    img = "http://192.168.123.112:8080/oop/img/shoes/" + temp.getString("img").trim();    // 집
                    price = Integer.parseInt(temp.getString("price"));
                    explan = temp.getString("explan");

                    arr.add(new ItemData(idx, title, write, date, img, price, explan));
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String idx = arr.get(position).idx;
        Log.d("log", idx);
        Intent intent = new Intent(this, com.jimiboy.vocabularyclone.DetailActivity.class);
        intent.putExtra("idx", idx);
        intent.putExtra("title", arr.get(position).title);
        intent.putExtra("name", arr.get(position).writer);
        intent.putExtra("date", arr.get(position).date);
        intent.putExtra("img", arr.get(position).img);
        intent.putExtra("price", arr.get(position).price);
        intent.putExtra("explan", arr.get(position).explan);

        startActivity(intent);
    }

    // 모드 버튼 클릭시 수정삭제 버튼 나오기
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list_mode:
                mode = mode == 0 ? 1 : 0;
                adapter.notifyDataSetChanged();
                break;
        }
    }

    class ItemHolder {
        TextView tvTitleHolder;
        TextView tvDateHolder;
        TextView tvWriterHolder;
        ImageView ivMainHolder;
        Button btnEditHolder;
        Button btnDelHolder;
    }

    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.item, arr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arr.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder viewHolder;
            if (convertView == null) {

                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new ItemHolder();

                viewHolder.tvTitleHolder = convertView.findViewById(R.id.tv_item_title);
                viewHolder.tvWriterHolder = convertView.findViewById(R.id.tv_item_date);
                viewHolder.tvDateHolder = convertView.findViewById(R.id.tv_item_writer);
                viewHolder.ivMainHolder = convertView.findViewById(R.id.iv_item_main);
                viewHolder.btnEditHolder = convertView.findViewById(R.id.btn_item_edit);
                viewHolder.btnDelHolder = convertView.findViewById(R.id.btn_item_del);

                viewHolder.btnDelHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = Integer.parseInt(String.valueOf(v.getTag()));
//                        Log.d("log", "DELpos: " + pos);
                    }
                });
                viewHolder.btnEditHolder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = Integer.parseInt(String.valueOf(v.getTag()));
//                        Log.d("log", "EDITpos: " + pos);
                    }
                });
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemHolder) convertView.getTag();
            }

            if (mode == 0) {
                viewHolder.btnEditHolder.setVisibility(View.GONE);
                viewHolder.btnDelHolder.setVisibility(View.GONE);
            } else {
                viewHolder.btnEditHolder.setVisibility(View.VISIBLE);
                viewHolder.btnDelHolder.setVisibility(View.VISIBLE);
            }

            viewHolder.tvTitleHolder.setText(arr.get(position).title);
            viewHolder.tvWriterHolder.setText(arr.get(position).writer);
            viewHolder.tvDateHolder.setText(arr.get(position).date);
//            viewHolder.ivMainHolder.setImageDrawable(arr.get(position).img);
            Glide.with(ListActivity.this)
                    .load(arr.get(position).img)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(viewHolder.ivMainHolder);
//            Log.d("log", arr.get(position).img);
            return convertView;
        }
    }
}