package com.sonlcr1.oneto25;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Activity_Main extends AppCompatActivity {

    DrawerLayout drawer_view;
    LinearLayout LinearLayout_main;
    TextView tv;
    TextView TextView_Record;
    TextView TextView_Menu_MyRecord;
    TextView TextView_Menu_Rank;
    TextView TextView_Menu_Login;
    TextView TextView_Menu_Logout;
    TextView TextView_Menu_Withdraw;
    Button btn_retry;

    Button[] btns = new Button[25]; //Button참조변수 25개짜리 배열객체

    int cnt = 1;

    //버튼의 배경그림을 참조하는 참조변수(버튼의 회색을 얻기 위한 참조변수)
    Drawable btnBack;

    static final int DRAWER_CLOSE = 0;
    static final int DRAWER_OPEN = 1;
    int flag_drawer_state = DRAWER_CLOSE;

    public static final int START = 0;//처음
    public static final int END = 1;//실행중
//    public static int status = START;

    private long baseTime,pauseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        drawer_view = findViewById(R.id.drawer_view);
        LinearLayout_main = findViewById(R.id.LinearLayout_main);
        tv = findViewById(R.id.TextView_count);
        TextView_Record = findViewById(R.id.TextView_Record);
        TextView_Menu_MyRecord = findViewById(R.id.TextView_Menu_MyRecord);
        TextView_Menu_Rank = findViewById(R.id.TextView_Menu_Rank);
        TextView_Menu_Login = findViewById(R.id.TextView_Menu_Login);
        TextView_Menu_Logout = findViewById(R.id.TextView_Menu_Logout);
        TextView_Menu_Withdraw = findViewById(R.id.TextView_Menu_Withdraw);
        btn_retry = findViewById(R.id.btn_retry);
        ImageButton ImageButton_menu = findViewById(R.id.ImageButton_menu);



        for(int i=0;i<btns.length;i++){
            btns[i] = findViewById(R.id.btn01 + i);
        }

        //버튼의 배경그림 얻어오기
        btnBack = btns[0].getBackground();  //배경그림 (색)을 준다, 버튼 처음 회색 그림(색)을 얻는다.

        //우선, 버튼들에서 보여줄 글씨 설정..
        initial();

        //버튼들 눌렀을 때 반응하기!!! 리스너 추가하기말고..
        //xml에서 각 Button을 눌렀을때 Activity의 특정 메소드를
        //자동으로 발동하도록 하는 속성을 추가

//        ImageButton_menu.setOnClickListener((v) -> {
//            if (!drawer_view.isDrawerOpen(GravityCompat.START)) {
//                drawer_view.openDrawer(GravityCompat.START);
//            }
//        });


        drawer_view.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Toast.makeText(Activity_Main.this, "열림", Toast.LENGTH_SHORT).show();
                LinearLayout_main.setClickable(false);
                flag_drawer_state = DRAWER_OPEN;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Toast.makeText(Activity_Main.this, "닫힘", Toast.LENGTH_SHORT).show();
                LinearLayout_main.setClickable(true);
                flag_drawer_state = DRAWER_CLOSE;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        ImageButton_menu.setOnClickListener(clickListener);
        TextView_Menu_Login.setOnClickListener(clickListener);

    }//onCreate method...

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ImageButton_menu:
                    if (!drawer_view.isDrawerOpen(GravityCompat.START)) {
                        drawer_view.openDrawer(GravityCompat.START);
                    }
                    break;
                case R.id.TextView_Menu_Login:
                    startActivity(new Intent(Activity_Main.this, Activity_Login.class));
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (drawer_view.isDrawerOpen(GravityCompat.START)) {
            drawer_view.closeDrawer(GravityCompat.START);
            flag_drawer_state = DRAWER_CLOSE;
        } else {
            AlertDialog.Builder ab = new AlertDialog.Builder(Activity_Main.this);
            ab.setMessage("종료 하시겠습니까?")
                    .setNegativeButton("확인", (dialogInterface, i) -> finishAffinity())
                    .setPositiveButton("취소",null)
                    .create().show();
        }
    }

    //리트라이 버튼 눌렀을때 반응 하는 메소드
    public void clickRetry(View v){
        initial();
    }

    //onClick속성이 부여된 버튼이 클릭되면 자동으로 실행 되는 메소드
    //단 접근제한자는 public이여야 한다.리턴타입은 반드시 void, 파라미터는 View객체를 1개를 받아야 한다,
    public void clickBtn(View v){
        if (flag_drawer_state == DRAWER_OPEN) {
            return;
        }
//        //눌러진 버튼(v,id값임)에 써있는 글씨 얻어오기
        Button btn = (Button)v;
//        String s = btn.getText().toString();    //charsequence자료형을 문자열 자료형으로 변환
//
//        //얻어온 글씨를 숫자로 변경
//        int num = Integer.parseInt(s);

        //글씨가 아닐때 해결하는 방법
        //버튼에 저장되어 있는 값(Tag:태그)을 읽어와서비교
        String s = v.getTag().toString();
        int num = Integer.parseInt(s);

        //얻어온 글씨 숫자가 현재 눌러야 할 번호와 같은지 비교
        if( cnt == num ){
            if ( cnt == 1) {
                recordTime(START);
            }
            //현재 눌러야할 번호를 정확히 누른 상황!!
            btn.setText("");

            //btn.setVisibility(View.INVISIBLE);
//            btn.setTextColor(Color.RED);   //투명도 2개 RGB 6개
            btn.setBackgroundColor(Color.TRANSPARENT);  //배경색을 부모 한테 맞춘다 (투명하게)

            tv.setText(num+"");
            cnt++;
        }

        //모든 번호를 다 눌렀다면...
        if(cnt>25){
            recordTime(END);
            tv.setText("END");

            btn_retry.setEnabled(true); //기능 활성화
        }
    }

    //게임의 초기설정 기능 메소드
    void initial(){

        //현재 눌러야할 번호 초기화
        cnt = 1;
        tv.setText("");
        TextView_Record.setText("00:00:00");

        //각 버튼에 1~25중에 하나의 숫자가 랜덤하게 설정 되야함.당연히 중복되면 안된다.
        //1~25의 숫자를 순서대로 가진 ArrayList 만들기
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i=1;i<=25;i++){
            arrayList.add(i);
        }

        //ArrayList의 요소를 뒤섞기!!! 중복되지 않는 랜덤을 for문을 안쓰고 구현 가능
        Collections.shuffle(arrayList);

        for (int i=0;i<btns.length;i++){
            btns[i].setText( arrayList.get(i) + "");
            btns[i].setTextColor(Color.BLACK);
            btns[i].setBackground(btnBack);

            //버튼객체에 버튼이 보여주고 있는 글씨값을 Tag로 저장
            btns[i].setTag( arrayList.get(i) + "");
        }
    }

    private void recordTime(int status){
        switch (status){
            case START:
                //어플리케이션이 실행되고 나서 실제로 경과된 시간...
                baseTime = SystemClock.elapsedRealtime();
                //핸들러 실행
                handler.sendEmptyMessage(0);
                break;
            case END:
                //핸들러 정지
                handler.removeMessages(0);
                Log.e("기록시간",TextView_Record.getText().toString());
                //정지 시간 체크
//                pauseTime = SystemClock.elapsedRealtime();
                break;
//            case PAUSE:
//                long reStart = SystemClock.elapsedRealtime();
//                baseTime += (reStart - pauseTime);
//
//                handler.sendEmptyMessage(0);
//
//                bt_sta.setText("멈춤");
//                bt_rec.setText("기록");
//
//                status = RUN;
        }
    }

    private String getTime(){
        //경과된 시간 체크

        long nowTime = SystemClock.elapsedRealtime();
        //시스템이 부팅된 이후의 시간?
        long overTime = nowTime - baseTime;

        long m = overTime/1000/60;
        long s = (overTime/1000)%60;
        long ms = (overTime%1000)/10;

        String recTime = String.format("%02d:%02d:%02d",m,s,ms);

        return recTime;
    }

    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case 0:
                    TextView_Record.setText(getTime());
//                    Log.e("기록시간",getTime());
                    handler.sendEmptyMessage(0);
                    break;
            }

//            timer.setText(getTime());
//            handler.sendEmptyMessage(0);
        }

    };

}//MainActivity class...
