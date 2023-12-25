package com.example.thaiidocr;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.FieldClassification;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private Button uploadButton, extractButton, previousidButton;
    private static final int PICK_IMAGE_REQUEST = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.imageview);
        uploadButton = findViewById(R.id.upload_button);
        extractButton = findViewById(R.id.extract_text);
        previousidButton = findViewById(R.id.previousid_button);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        extractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractTextFromImage();
            }
        });
        previousidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PreviousIdActivity.class);
                startActivity(i);
            }
        });

    }

    private void extractTextFromImage() {
        Drawable drawable = image.getDrawable();
        if(drawable == null){
            Toast.makeText(this, "Please Uplaod an Image first!", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if(!textRecognizer.isOperational()){
            Toast.makeText(this, "Could not get the text!", Toast.LENGTH_SHORT).show();
        }else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock > items = textRecognizer.detect(frame);
            StringBuilder sb = new StringBuilder();

            for(int i = 0;i<items.size(); i++){
                TextBlock myItem = items.valueAt(i);
                sb.append(myItem.getValue());
                sb.append("$");
            }
            int index = 0;
            while ((index = sb.indexOf("\n", index)) != -1) {
                sb.replace(index, index + 1, " ");
                index += 1; // Move to the next character after the replaced newline
            }
            String result = sb.toString();
            System.out.println("Modified String: " + result);

            String text = sb.toString();
           // System.out.println(text);
            Pattern firstnamePattern = Pattern.compile("Name\\s+([A-Za-z]+\\s+[A-Za-z]+)");
            Pattern lastNamePattern = Pattern.compile("Last name\\s+([A-Za-z]+)");
            Pattern dobPattern = Pattern.compile("Date of Birth (\\d{1,2} [a-zA-Z]{3}[,.] \\d{1,4})");
            Pattern doiPattern = Pattern.compile("(\\d{1,2} [a-zA-Z]{3}[,.] \\d{1,4}) Date of \\p{Alnum}\\p{Alpha}+");
            Pattern doePattern = Pattern.compile("(\\d{1,2} [a-zA-Z]{3}[,.] \\d{1,4}) Date of Expiry");
            Pattern identificationPattern = Pattern.compile("(\\d{1,2} \\d{3,5} \\d{4,6} \\d{2,4})");

            Matcher firstNameMatcher = firstnamePattern.matcher(text);
            Matcher lastNameMatcher = lastNamePattern.matcher(text);
            Matcher dobMatcher = dobPattern.matcher(text);
            Matcher doiMatcher = doiPattern.matcher(text);
            Matcher doeMatcher = doePattern.matcher(text);
            Matcher identificationMatcher = identificationPattern.matcher(text);

            String firstName = findInfo(firstNameMatcher);
            String lastName = findInfo(lastNameMatcher);
            String dob = findInfo(dobMatcher);
            String doi = findInfo(doiMatcher);
            String doe = findInfo(doeMatcher);
            String identification = findInfo(identificationMatcher);

            if(firstName == "Not Found" || lastName == "Not Found" || dob == "Not Found" || doi == "Not Found" || doe == "Not Found" || identification== "Not Found"){
                Toast.makeText(this, "Please Upload a clear image", Toast.LENGTH_SHORT).show();
                return;
            }

            //String temp = firstName + " " + lastName + " " + dob + " " + doi + " " + doe + " " + identification;
            Intent i = new Intent(MainActivity.this, ExtractedTextActivity.class);
            i.putExtra("firstName",firstName);
            i.putExtra("lastName", lastName);
            i.putExtra("dob", dob);
            i.putExtra("doi", doi);
            i.putExtra("doe", doe);
            i.putExtra("identification", identification);
            startActivity(i);
        }
    }

    private static String findInfo(Matcher matcher) {
        if(matcher.find()){
            return matcher.group(1);
        }
        return "Not Found";
    }

    private void selectImage() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            Uri selectedImageUri = data.getData();
            image.setImageURI(selectedImageUri);
        }
    }
}