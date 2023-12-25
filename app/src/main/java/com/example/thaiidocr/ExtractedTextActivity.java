package com.example.thaiidocr;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class ExtractedTextActivity extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteTextView;
    FirebaseFirestore firestore;
    Button editButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extracted_text);
        autoCompleteTextView = findViewById(R.id.autocomplete_textview);
        editButton = findViewById(R.id.edit_button);
        firestore = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        String firstName = i.getStringExtra("firstName");
        String lastName = i.getStringExtra("lastName");
        String dob = i.getStringExtra("dob");
        String doi = i.getStringExtra("doi");
        String doe = i.getStringExtra("doe");
        String identification = i.getStringExtra("identification");
        autoCompleteTextView.setText("First name: "+firstName+"\n"+"last name: "+lastName+"\n"+"Date of Birth: "+dob+"\n"+"Date of Issue: "+doi+"\n"+"Date of Expiry: "+doe+"\n"+"Identification Number: "+identification);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextView.isInEditMode();
            }
        });
    }
}