package com.sonlcr1.oneto25;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Tab1Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1,container,false);   //false 리턴할때 붙이니까 false



        return view; //리턴될때 알아서 붙지만 inflate 두번째 파라미터 container로 씀으로 사이즈를 미리 알수 있다.
    }
}
