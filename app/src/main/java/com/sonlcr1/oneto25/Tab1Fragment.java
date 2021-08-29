package com.sonlcr1.oneto25;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class Tab1Fragment extends Fragment {
    private static FragmentManager fm;
    RecyclerView RecyclerView_1to25;
    RecyclerAdapter_Rank recyclerAdapterRank;

    public Tab1Fragment (FragmentManager fm) {
        this.fm = fm;
    }

    public static Tab1Fragment newInstance(int index) {
        Tab1Fragment f = new Tab1Fragment(fm);

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1,container,false);   //false 리턴할때 붙이니까 false
        RecyclerView_1to25 = view.findViewById(R.id.RecyclerView_1to25);
        RelativeLayout ParentLayout =(RelativeLayout)view.findViewById(R.id.ParentLayout);



//        Dialog_Loading loading = (Dialog_Loading) DialogFragment.instantiate(getActivity(),"Dialog_Loading");
//        loading.show(fm,"loading");

        DialogFragment dialogFragment = new Dialog_Loading ();
        dialogFragment.show(fm,"Dialog_Loading");
        
        // todo : fragment에서 dialogFragment 생성시 gif 배경이 흰색으로 나옴.
//        ParentLayout.addView(loading);
//        loading.show(fm,"");

        ArrayList<VO_Rank> arrayList = new ArrayList<>();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference usersRef = firebaseFirestore.collection("MODE25");

        Task<QuerySnapshot> task = usersRef.get();
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    StringBuffer buffer = new StringBuffer();
                    QuerySnapshot querySnapshot = task.getResult();
                    //"users"안에 여러개의 문서가 있으므로..
                    for (QueryDocumentSnapshot snapshot : querySnapshot){
                        Map<String,Object> user = snapshot.getData();
                        String name = (String)user.get("id");
//                        int age = (int)user.get("age"); //int로 했을때 뜨는 에러 해석해보자
//                        long age = (long)user.get("age");
                        int record = Integer.parseInt(user.get("record").toString().replace(":",""));

//                        buffer.append(name+"\n"+age+"\n"+address+"\n===========\n");
                        arrayList.add(new VO_Rank(name, record));
                    }
                    arrayList.sort(new Comparator<VO_Rank>() {
                        @Override
                        public int compare(VO_Rank t1, VO_Rank t2) {
                            int age0 = t1.getRecord();
                            int age1 = t2.getRecord();
                            if (age0 == age1)
                                return 0;
                            else if (age0 > age1)   // 비교 순서를 바꾸면 내림차순으로 바뀜.
                                return 1;
                            else
                                return -1;
                        }
                    });
                    for (VO_Rank e : arrayList) {
                        Log.e("데이터",e.email+", "+e.record);
                    }

                    // todo : 서버에서 가져온 랭킹 데이터를 오름차순으로 나열수 int 기록값을 string 00:00:00 포맷형식으로 바꾸어 recyclerview에 보여줄것.
                    recyclerAdapterRank = new RecyclerAdapter_Rank(getContext(), arrayList);
                    RecyclerView_1to25.setAdapter(recyclerAdapterRank);
                    dialogFragment.dismiss();
                }
            }
        });


        return view; //리턴될때 알아서 붙지만 inflate 두번째 파라미터 container로 씀으로 사이즈를 미리 알수 있다.
    }


}
