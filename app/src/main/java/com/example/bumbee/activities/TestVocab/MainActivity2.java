package com.example.bumbee.activities.TestVocab;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.bumbee.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private ArrayList<Vocab> listVocab = new ArrayList<>();;
    private Button btnTest;
    private FirebaseFirestore firebaseFirestore;
    private String name;
    private ArrayList<String> ques = new ArrayList<>();
    private ArrayList<String> ans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnTest = (Button) findViewById(R.id.btnTest);

//        String[] animalVocab = {"Dog", "Cat", "Lion", "Tiger", "Elephant", "Chicken", "Duck", "Ant"};
//        String[] animalMeaning = {"Con Chó", "Con Mèo", "Con Sư Tử", "Con Hổ", "Con Voi", "Con Gà", "Con Vịt", "Con Kiến"};
//        String[] colorVocab = {"Red", "Blue", "Yellow", "White", "Black", "Green", "Orange", "Brown"};
//        String[] colorMeaning = {"Màu Đỏ", "Màu Xanh Dương", "Màu Và     ng", "Màu Trắng", "Màu Đen", "Màu Xanh Lá", "Màu Cam", "Màu Nâu"};
//        String[] familyVocab = {"Father", "Mother", "Son", "Daughter", "Aunt", "Uncle", "Grand-father", "Grand-mother"};
//        String[] familyMeaning = {"Cha", "Mẹ", "Con Trai", "Con Gái", "Dì", "Bác", "Ông", "Bà"};
//        String[] jobsVocab = {"Teacher", "Doctor", "Engineer", "Programmer", "Dentist", "Scientist", "Laborer", "Baker"};
//        String[] jobsMeaning = {"Giáo Viên", "Bác Sĩ", "Kỹ sư", "Lập Trình Viên", "Nha Sĩ", "Nhà Khoa Học", "Lao Công", "Thợ Làm Bánh"};
//        String[] sportVocab = {"Football", "Basketball", "Volleyball", "Swimming", "Tennis", "Golf", "Badminton", "Table Tennis"};
//        String[] sportMeaning = {"Môn Bóng Đá", "Môn Bóng Rổ", "Môn Bóng Chuyền", "Môn Bơi Lội", "Môn Đánh Tennis", "Môn Đánh Gôn", "Môn Quần Vợt", "Môn Bóng Bàn"};
//        String[] subjectsVocab = {"Math", "Physics", "Chemistry", "Biology", "Geography", "History", "Literature", "English"};
//        String[] subjectsMeaning = {"Môn Toán", "Môn Vật Lí", "Môn Hóa Học", "Môn Sinh Học", "Môn Địa Lý", "̀Môn Lịch Sử", "Môn Ngữ Văn", "Môn Tiếng Anh"};
//        String[] transportVocab = {"Car", "Bicycle", "Bus", "Motorbike", "Train", "Ship", "Plane", "Helicopter"};
//        String[] transportMeaning = {"Xe Ô Tô", "Xe Đạp", "Xe Buýt", "Xe Mô Tô", "Xe Lửa", "Tàu Thủy", "Máy Bay", "Máy Bay Trực Thăng"}
        ActionBar ac = getSupportActionBar();
        ac.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        ac.setTitle(name);

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Topic")
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                ArrayList<String> vocab = new ArrayList<>();
                                ArrayList<String> mean = new ArrayList<>();
                                vocab = (ArrayList<String>) document.get("vocab");
                                mean = (ArrayList<String>) document.get("meaning");

                                Bundle bundle = new Bundle();

                                bundle.putStringArrayList("vocab",vocab);
                                bundle.putStringArrayList("mean",mean);

                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.add(R.id.Fragment1,Fragment1.class,bundle).commit();

                                FragmentManager fragmentManager2 = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                fragmentTransaction2.add(R.id.Fragment2,Fragment2.class,bundle).commit();
                                ques = mean;
                                ans = vocab;
                            }

                        }else{
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuickTest.class);
                intent.putExtra("name", name);
                intent.putStringArrayListExtra("ques",ques);
                intent.putStringArrayListExtra("ans",ans);
                startActivity(intent);
            }
        });





//        switch (name)
//        {
//            case "Animal":
//                ques = animalMeaning;
//                ans = animalVocab;
//                for(int i=0; i<animalVocab.length; i++){
//                    Vocab animal = new Vocab(animalVocab[i],animalMeaning[i]);
//                    listVocab.add(animal);
//                    adtVocab.notifyDataSetChanged();
//                }
//                break;
//            case "Color":
//                ques = colorMeaning;
//                ans = colorVocab;
//                for(int i=0; i<colorVocab.length; i++){
//                    Vocab color = new Vocab(colorVocab[i],colorMeaning[i]);
//                    listVocab.add(color);
//                    adtVocab.notifyDataSetChanged();
//                }
//                break;
//            case "Family":
//                ques = familyMeaning;
//                ans = familyVocab;
//                for(int i=0; i<colorVocab.length; i++){
//                    Vocab family = new Vocab(familyVocab[i],familyMeaning[i]);
//                    listVocab.add(family);
//                    adtVocab.notifyDataSetChanged();
//                }
//                break;
//            case "Jobs":
//                ques = jobsMeaning;
//                ans = jobsVocab;
//                for(int i=0; i<jobsVocab.length; i++){
//                    Vocab jobs = new Vocab(jobsVocab[i],jobsMeaning[i]);
//                    listVocab.add(jobs);
//                    adtVocab.notifyDataSetChanged();
//                }
//                break;
//            case "Sport":
//                ques = sportMeaning;
//                ans = sportVocab;
//                for(int i=0; i<sportVocab.length; i++){
//                    Vocab sport = new Vocab(sportVocab[i],sportMeaning[i]);
//                    listVocab.add(sport);
//                    adtVocab.notifyDataSetChanged();
//                }
//                break;
//            case "Subjects":
//                ques = subjectsMeaning;
//                ans = subjectsVocab;
//                for(int i=0; i<subjectsVocab.length; i++){
//                    Vocab subjects = new Vocab(subjectsVocab[i], subjectsMeaning[i]);
//                    listVocab.add(subjects);
//                    adtVocab.notifyDataSetChanged();
//                }
//                break;
//            case "Transport":
//                ques = transportMeaning;
//                ans = transportVocab;
//                for(int i=0; i<transportVocab.length; i++){
//                    Vocab transport = new Vocab(transportVocab[i],transportMeaning[i]);
//                    listVocab.add(transport);
//                    adtVocab.notifyDataSetChanged();
//                }
//                break;
//        }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}