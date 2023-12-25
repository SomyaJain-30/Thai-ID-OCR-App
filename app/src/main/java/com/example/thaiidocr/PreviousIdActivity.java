package com.example.thaiidocr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PreviousIdActivity extends AppCompatActivity {
    RecyclerView rv;
    TextView noId;
    FirebaseFirestore firestore;
    IDAdapter idAdapter;
    IDModel idModel;
    List<IDModel> idModelList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_id);
        noId = findViewById(R.id.no_id_available);
        rv = findViewById(R.id.recycleview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        idModelList = new ArrayList<>();
        idAdapter = new IDAdapter(this, idModelList);
        rv.setAdapter(idAdapter);
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Thai ID's").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot ds : task.getResult()){
                        IDModel idModel = ds.toObject(IDModel.class);
                        idModelList.add(idModel);
                        idAdapter.notifyDataSetChanged();;
                    }
                    if(idModelList.size()>0){
                        rv.setVisibility(View.VISIBLE);
                        noId.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });


    }
}