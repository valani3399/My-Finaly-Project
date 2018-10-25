package com.example.softices.pankajtrainee.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.softices.pankajtrainee.FRAGMENT.OneFragment;
import com.example.softices.pankajtrainee.FRAGMENT.TwoFragment;
import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.adapter.ViewPagerAdapter;


public class FragmentActivity extends AppCompatActivity {
    ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adepter=new ViewPagerAdapter(getSupportFragmentManager());
        adepter.addFradment(new OneFragment(),"ONE");
        adepter.addFradment(new TwoFragment(),"TWO");
        viewPager.setAdapter(adepter);

    }

}
