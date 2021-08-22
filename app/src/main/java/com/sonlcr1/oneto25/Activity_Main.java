package com.sonlcr1.oneto25;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Activity_Main extends AppCompatActivity {

    DrawerLayout drawer_view;
    LinearLayout LinearLayout_main;
    TextView TextView_count;
    TextView TextView_Record;
    TextView TextView_Menu_MyRecord;
    TextView TextView_Menu_Rank;
    TextView TextView_Menu_LoginOrOut;
    TextView TextView_Menu_Withdraw;
    TextView TextView_GameMode;
    TextView TextView_Mode_Change;
    Button btn_retry;

    Button[] btns = new Button[25]; //Button참조변수 25개짜리 배열객체

    ArrayList<Integer> arrayList_25 = new ArrayList<>();
    ArrayList<Integer> arrayList_50 = new ArrayList<>();

    final String gameRecord = "gameRecord";
    final static String SAVE_25 = "25";
    final static String SAVE_50 = "50";

    static String record_25;
    static String record_50;

    int cnt = 1;

    //버튼의 배경그림을 참조하는 참조변수(버튼의 회색을 얻기 위한 참조변수)
    Drawable btnBack;

    static final int DRAWER_CLOSE = 0;
    static final int DRAWER_OPEN = 1;
    int flag_drawer_state = DRAWER_CLOSE;

    public static final int START = 0;//처음
    public static final int END = 1;//실행중

    static final int MODE_25 = 0;
    static final int MODE_50 = 1;
    int flag_game_mode = MODE_25;

    private long baseTime, pauseTime;

    public static FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRecord();

//        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        drawer_view = findViewById(R.id.drawer_view);
        LinearLayout_main = findViewById(R.id.LinearLayout_main);
        TextView_count = findViewById(R.id.TextView_count);
        TextView_Record = findViewById(R.id.TextView_Record);
        TextView_Menu_MyRecord = findViewById(R.id.TextView_Menu_MyRecord);
        TextView_Menu_Rank = findViewById(R.id.TextView_Menu_Rank);
        TextView_Menu_LoginOrOut = findViewById(R.id.TextView_Menu_LoginOrOut);
        TextView_Menu_Withdraw = findViewById(R.id.TextView_Menu_Withdraw);
        TextView_GameMode = findViewById(R.id.TextView_GameMode);
        TextView_Mode_Change = findViewById(R.id.TextView_Mode_Change);
        btn_retry = findViewById(R.id.btn_retry);
        ImageButton ImageButton_menu = findViewById(R.id.ImageButton_menu);


        for (int i = 0; i < btns.length; i++) {
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
                LinearLayout_main.setClickable(false);
                flag_drawer_state = DRAWER_OPEN;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                LinearLayout_main.setClickable(true);
                flag_drawer_state = DRAWER_CLOSE;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        ImageButton_menu.setOnClickListener(clickListener);
        TextView_Menu_LoginOrOut.setOnClickListener(clickListener);
        TextView_Menu_Withdraw.setOnClickListener(clickListener);
        btn_retry.setOnClickListener(clickListener);
        TextView_Mode_Change.setOnClickListener(clickListener);
        TextView_Menu_MyRecord.setOnClickListener(clickListener);
        TextView_Menu_Rank.setOnClickListener(clickListener);

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
                case R.id.TextView_Menu_LoginOrOut:
                    if (mAuth.getCurrentUser() != null) {
                        mAuth.signOut();
                        TextView_Menu_LoginOrOut.setText("로그인");
                        if (drawer_view.isDrawerOpen(GravityCompat.START)) {
                            drawer_view.closeDrawer(GravityCompat.START);
                            flag_drawer_state = DRAWER_CLOSE;
                            Toast.makeText(Activity_Main.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        startActivity(new Intent(Activity_Main.this, Activity_Login.class));
                    }
                    break;
                case R.id.TextView_Menu_Withdraw:
                    if (mAuth.getCurrentUser() != null) {
                        Toast.makeText(Activity_Main.this, "로그인상태", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Main.this, "로그아웃상태", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_retry:
                    initial();
                    break;
                case R.id.TextView_Mode_Change:
                    android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(Activity_Main.this);
                    ab.setTitle("모드를 선택하세요.");

                    CharSequence[] items = new CharSequence[2];
                    items[0] = "1 to 25";
                    items[1] = "1 to 50";

                    ab.setItems(items, (dialog, id) -> {
                        if (id == 0) {
                            flag_game_mode = MODE_25;
                            TextView_GameMode.setText("1 to 25");
                        } else if (id == 1) {
                            flag_game_mode = MODE_50;
                            TextView_GameMode.setText("1 to 50");
                        }
                    });

                    ab.show();
                    break;
                case R.id.TextView_Menu_MyRecord:
                    startActivity(new Intent(Activity_Main.this, Activity_MyRecord.class));
                    break;
                case R.id.TextView_Menu_Rank:
                    startActivity(new Intent(Activity_Main.this, Activity_Rank.class));
                    break;
            }
        }
    };  // clickListener

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser() != null) {
            TextView_Menu_LoginOrOut.setText("로그아웃");
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer_view.isDrawerOpen(GravityCompat.START)) {
            drawer_view.closeDrawer(GravityCompat.START);
            flag_drawer_state = DRAWER_CLOSE;
        } else {
            AlertDialog.Builder ab = new AlertDialog.Builder(Activity_Main.this);
            ab.setMessage("종료 하시겠습니까?")
                    .setNegativeButton("확인", (dialogInterface, i) -> finishAffinity())
                    .setPositiveButton("취소", null)
                    .create().show();
        }
    } // onBackPressed

    //게임의 초기설정 기능 메소드
    void initial() {

//        recordTime(END);
        arrayList_25.clear();
        arrayList_50.clear();
        cnt = 1;
        TextView_count.setText("");
        TextView_Record.setText("00:00:00");

        //각 버튼에 1~25중에 하나의 숫자가 랜덤하게 설정 되야함.당연히 중복되면 안된다.
        //1~25의 숫자를 순서대로 가진 ArrayList 만들기
        for (int i = 1; i <= 25; i++) {
            arrayList_25.add(i);
            arrayList_50.add(i+25);
        }

        //ArrayList의 요소를 뒤섞기!!! 중복되지 않는 랜덤을 for문을 안쓰고 구현 가능
        Collections.shuffle(arrayList_25);
        Collections.shuffle(arrayList_50);

        buttonShuffle(arrayList_25);

    }

    //숫자 누르는 버튼 
    //단 접근제한자는 public이여야 한다.리턴타입은 반드시 void, 파라미터는 View객체를 1개를 받아야 한다,
    public void clickBtn(View v) {
        if (flag_drawer_state == DRAWER_OPEN) {
            return;
        }
//        //눌러진 버튼(v,id값임)에 써있는 글씨 얻어오기
        Button btn = (Button) v;
//        String s = btn.getText().toString();    //charsequence자료형을 문자열 자료형으로 변환
//
//        //얻어온 글씨를 숫자로 변경
//        int num = Integer.parseInt(s);

        //글씨가 아닐때 해결하는 방법
        //버튼에 저장되어 있는 값(Tag:태그)을 읽어와서비교
        String s = v.getTag().toString();
        int num = Integer.parseInt(s);

        //얻어온 글씨 숫자가 현재 눌러야 할 번호와 같은지 비교
        if (cnt == num) {
            if (cnt == 1) {
                recordTime(START);
                btn_retry.setEnabled(false);
            }
            //현재 눌러야할 번호를 정확히 누른 상황!!
            btn.setText("");

            //btn.setVisibility(View.INVISIBLE);
//            btn.setTextColor(Color.RED);   //투명도 2개 RGB 6개
            btn.setBackgroundColor(Color.TRANSPARENT);  //배경색을 부모 한테 맞춘다 (투명하게)

            TextView_count.setText(num+1 + "");
            cnt++;
        }

        //모든 번호를 다 눌렀다면...
//        if (cnt == 26) {
//            if (flag_game_mode == MODE_25) {
//                recordTime(END);
//                TextView_count.setText("END");
//                btn_retry.setEnabled(true); //기능 활성화
//            } else if (flag_game_mode == MODE_50) {
//                buttonShuffle(arrayList_50);
//            }
//        } else if (cnt == 51) {
//            recordTime(END);
//            TextView_count.setText("END");
//            btn_retry.setEnabled(true); //기능 활성화
//        }

        //test code
        if ( flag_game_mode == MODE_50 && cnt == 3 ) {
            recordTime(END);
            TextView_count.setText("END");
            btn_retry.setEnabled(true); //기능 활성화
        }

        if ( flag_game_mode == MODE_25 && cnt == 3 ) {
            recordTime(END);
            TextView_count.setText("END");
            btn_retry.setEnabled(true); //기능 활성화
        }


    }



    void buttonShuffle(ArrayList<Integer> list) {
        for (int i = 0; i < btns.length; i++) {
            btns[i].setText(list.get(i) + "");
            btns[i].setTextColor(Color.BLACK);
            btns[i].setBackground(btnBack);

            //버튼객체에 버튼이 보여주고 있는 글씨값을 Tag로 저장
            btns[i].setTag(list.get(i) + "");
        }
    }

    private void recordTime(int status) {
        switch (status) {
            case START:
                //어플리케이션이 실행되고 나서 실제로 경과된 시간...
                baseTime = SystemClock.elapsedRealtime();
                //핸들러 실행
                handler.sendEmptyMessage(0);
                break;
            case END:
                //핸들러 정지
                handler.removeMessages(0);
                Log.e("기록시간", TextView_Record.getText().toString());
                saveRecord();
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

    private String getTime() {
        //경과된 시간 체크

        long nowTime = SystemClock.elapsedRealtime();
        //시스템이 부팅된 이후의 시간?
        long overTime = nowTime - baseTime;

        long m = overTime / 1000 / 60;
        long s = (overTime / 1000) % 60;
        long ms = (overTime % 1000) / 10;

        String recTime = String.format("%02d:%02d:%02d", m, s, ms);

        return recTime;
    }

    Handler handler = new Handler() {

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

    public void saveRecord() {
        String record_now = TextView_Record.getText().toString().replace(":","");
        int record_now_int = 0;
        if (!TextUtils.isEmpty(record_now)) {
            record_now_int = Integer.parseInt(record_now);
        }
        Log.e("string:record_now",record_now);
        if (flag_game_mode == MODE_25) {
            Log.e("string:record_25",record_25);
            int record_25_int = 0;
            if (!TextUtils.isEmpty(record_25)) {
                record_25_int = Integer.parseInt(record_25);
            }
            Log.e("int : 25,now",record_25_int+", "+record_now_int);
            if (TextUtils.isEmpty(record_25) || (record_25_int >= record_now_int)) {
                SharedPreferences preferences = getSharedPreferences(gameRecord, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SAVE_25, record_now);
                if ( editor.commit() ) {
                    Toast.makeText(this, "신기록 달성!", Toast.LENGTH_SHORT).show();
                    getRecord();
                    serverRecordUpdate();
                }
            }
        } else if (flag_game_mode == MODE_50) {
            Log.e("string:record_50",record_50);
            int record_50_int = 0;
            if (!TextUtils.isEmpty(record_50)) {
                record_50_int = Integer.parseInt(record_50);
            }
            Log.e("int : 50,now",record_50_int+", "+record_now_int);
            if (TextUtils.isEmpty(record_50) || (record_50_int >= record_now_int)) {
                SharedPreferences preferences = getSharedPreferences(gameRecord, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(SAVE_50, record_now);
                if ( editor.commit() ) {
                    Toast.makeText(this, "신기록 달성!", Toast.LENGTH_SHORT).show();
                    getRecord();
                    serverRecordUpdate();
                }
            }
        }
    }

    public void getRecord() {
        SharedPreferences pref_record = getSharedPreferences(gameRecord, MODE_PRIVATE);
        record_25 = pref_record.getString(SAVE_25,"");
        record_50 = pref_record.getString(SAVE_50,"");

        Log.e("record:25, 50", record_25+", "+record_50);
    }
    
    public void serverRecordUpdate() {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference userRef = null;
        final Map<String, Object> user = new HashMap<>();
        if (flag_game_mode == MODE_25) {
            user.put("id", "sonlcr1@naver.com");    // 로그인 정보 불러오는 기능이 아직 없어서 임시 하드코딩
            user.put("record", record_25);
            
            userRef = firebaseFirestore.collection("MODE25");
        } else if (flag_game_mode == MODE_50) {
            user.put("id", "sonlcr1@naver.com");    // 로그인 정보 불러오는 기능이 아직 없어서 임시 하드코딩
            user.put("record", record_50);
            
            userRef = firebaseFirestore.collection("MODE50");
        }

        CollectionReference finalUserRef = userRef;
        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot snapshots = task.getResult();
                if (snapshots.size() > 0) {
                    for (QueryDocumentSnapshot snapshot : snapshots) {
                        Map<String,Object> user = snapshot.getData();
                        if (user.get("id").equals("sonlcr1@naver.com")) {   // 로그인 정보 불러오는 기능이 아직 없어서 임시 하드코딩
                            snapshot.getReference().delete();
                        }
                    }
                }
                finalUserRef.add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Activity_Main.this, "saved", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}//MainActivity class...
