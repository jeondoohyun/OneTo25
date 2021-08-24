package com.sonlcr1.oneto25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Activity_Rank extends AppCompatActivity {
    ViewPager ViewPager_Rank;
    TabLayout TabLayout_tab;
    ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__rank);

        fm = getSupportFragmentManager();

//        DialogFragment dialogFragment = new Dialog_Loading();
//        dialogFragment.show(fm,"");

        TabLayout_tab = findViewById(R.id.TabLayout_tab);
        ViewPager_Rank = findViewById(R.id.ViewPager_Rank);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        ViewPager_Rank.setAdapter(viewPagerAdapter);
        TabLayout_tab.setupWithViewPager(ViewPager_Rank);




    }
}