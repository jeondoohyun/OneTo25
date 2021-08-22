package com.sonlcr1.oneto25;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import static com.sonlcr1.oneto25.Activity_Main.*;

public class Activity_MyRecord extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__my_record);

        TextView TextView_1to25_record = findViewById(R.id.TextView_1to25_record);
        TextView TextView_1to50_record = findViewById(R.id.TextView_1to50_record);

        if (!TextUtils.isEmpty(record_25)) {
            TextView_1to25_record.setText(record_25.substring(0,2)+":"+record_25.substring(2,4)+":"+record_25.substring(4,6));
        } else {
            TextView_1to25_record.setText("기록없음");
        }
        if (!TextUtils.isEmpty(record_50)) {
            TextView_1to50_record.setText(record_50.substring(0,2)+":"+record_50.substring(2,4)+":"+record_50.substring(4,6));
        } else {
            TextView_1to50_record.setText("기록없음");
        }
    }
}