package com.example.pagepoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jerry.pagepoint.PagePointView;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PagePointView point;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        point = (PagePointView) findViewById(R.id.point);

        viewPager.setAdapter(adapter);
        point.setViewPager(viewPager);

    }

    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.layout_page,null);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(String.valueOf(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    };
}
