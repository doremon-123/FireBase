package com.example.firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
  EditText ed1,ed2;
  Button btn,btn2;
  TextView tv;
  String s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=findViewById(R.id.editTextTextPersonName);
        ed2=findViewById(R.id.editTextTextPersonName2);
        btn=findViewById(R.id.button);
        tv=findViewById(R.id.textView);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1=ed1.getText().toString();
                s2=ed2.getText().toString();
                tv.setText(s1+s2);
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Name", s2);
                user.put("Phone", s2);





// Add a new document with a generated ID
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("abcdTAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("abcdTAG", "Error adding document", e);
                            }
                        });
            }
        });


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String result=" ";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("abTAG", document.getId() + " => " + document.getData());
                                result+=document.getData().get("Name").toString();
                                result+=" ";
                                result+=document.getData().get("Phone").toString();
                                result+="\n";
                            }
                            tv.setText(result);
                        } else {
                            Log.w("abTAG", "Error getting documents.", task.getException());
                        }
                    }
                });


    }
}