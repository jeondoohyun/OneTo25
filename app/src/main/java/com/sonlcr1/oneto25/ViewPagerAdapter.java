package com.sonlcr1.oneto25;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Fragment[] fragments = new Fragment[2];
    //참조변수를 배열로 가짐
    String[] tabTexts = new String[]{"1to25","1to50"};

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments[0] = new Tab1Fragment(fm);
        fragments[1] = new Tab2Fragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    //탭레이아웃과 Viewpager를 연동한다면 탭버튼에 보여질 글씨를 리턴해주는 메소드
    //이 메소드를 쓰면 탭 타이틀에 아이콘은 쓰지 못한다.
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTexts[position];
    }
}
