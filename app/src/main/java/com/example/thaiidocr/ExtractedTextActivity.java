package com.example.thaiidocr;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ExtractedTextActivity extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteTextView;
    FirebaseFirestore firestore;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extracted_text);
        autoCompleteTextView = findViewById(R.id.autocomplete_textview);
        firestore = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        String firstName = i.getStringExtra("firstName");
        String lastName = i.getStringExtra("lastName");
        String dob = i.getStringExtra("dob");
        String doi = i.getStringExtra("doi");
        String doe = i.getStringExtra("doe");
        String identification = i.getStringExtra("identification");
        autoCompleteTextView.setText("First name: "+firstName+"\n"+"last name: "+lastName+"\n"+"Date of Birth: "+dob+"\n"+"Date of Issue: "+doi+"\n"+"Date of Expiry: "+doe+"\n"+"Identification Number: "+identification);
        HashMap<String, String> data = new HashMap<>();
        data.put("firstName" , firstName);
        data.put("lastName", lastName);
        data.put("dob", dob);
        data.put("doi", doi);
        data.put("doe", doe);
        data.put("identification", identification);

        firestore.collection("Thai ID's").document(identification).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ExtractedTextActivity.this, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}