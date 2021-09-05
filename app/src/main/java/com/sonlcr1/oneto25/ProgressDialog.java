package com.sonlcr1.oneto25;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

public class ProgressDialog extends Dialog {
    public ProgressDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_progress);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView loading = findViewById(R.id.ImageView_loading2);

        Glide.with(getContext())
                .load(R.drawable.loading)
                .into(loading);
    }
}
