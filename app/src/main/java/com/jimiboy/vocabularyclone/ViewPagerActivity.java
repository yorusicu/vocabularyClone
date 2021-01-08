package com.jimiboy.vocabularyclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity {
    ViewPager pager;
    PagerAdapter adapter;
    ArrayList<Fragment> fragArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        pager = findViewById(R.id.pager);

        fragArr=new ArrayList<>();
        fragArr.add(new MyInfoFragment());
        fragArr.add(new MyInfoFragment2());
        fragArr.add(new MyInfoFragment3());
        fragArr.add(new MyInfoFragment4());

        adapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        // getItem으로 현재위치를 알기 어렵기 때문에 addOnpageChangeListener로 현재위치 확인
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    //정지 후 1~2후 호출

                } else if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    //이동
                }
            }
        });
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Log.d("abb", "pos: " + position);
            //ViewPager에서 fragment는 현재페이지와 다음페이지까지 미리 같이 호출됨

//            if (position == 0) {
//                return new MyInfoFragment();
//            } else if (position == 1) {
//                return new MyListFragment();
//            } else if (position == 2) {
//                return new MyInfoFragment2();
//            } else if (position == 3) {
//                return new MyInfoFragment3();
//            } else if (position == 4) {
//                return new MyInfoFragment4();
//            }

            return fragArr.get(position);
        }

        @Override
        public int getCount() {
            return fragArr.size();
        }
    }
}