package com.sonlcr1.oneto25;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

public class Dialog_Loading extends DialogFragment {

    public Dialog_Loading() { }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        LayoutInflater inflater2 = getActivity().getLayoutInflater();
        View view = inflater2.inflate(R.layout.dialog_loading, null);

//        view.setBackground(new ColorDrawable(Color.TRANSPARENT));
//        view.setBackgroundColor(android.R.color.transparent);

        getDialog().setTitle("Dialog_Loading");
        Glide.with(getActivity())
                .asGif()
                .load(R.drawable.loading)
                .into((ImageView) view.findViewById(R.id.ImageView_loading));


//        dialog.setContentView(view);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);



        return view;

    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Dialog dialog = new Dialog(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_loading, null);
//
//        Glide.with(getActivity())
//                .asGif()
//                .load(R.drawable.loading)
//                .into((ImageView) view.findViewById(R.id.ImageView_loading));
//
//        dialog.setContentView(view);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//
//        getDialog().setTitle("Dialog_Loading");
//
//        return dialog;
//    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }
}
