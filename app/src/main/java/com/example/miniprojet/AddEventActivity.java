package com.example.miniprojet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miniprojet.Model.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {
    EditText nameEd , descEd , dateEd , placeEd , imageEd;
    FirebaseDatabase firebaseDatabase;
    Button b1;
    DatePickerDialog.OnDateSetListener  dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        nameEd = findViewById(R.id.name);
        descEd = findViewById(R.id.desc);
        dateEd = findViewById(R.id.date);
        placeEd = findViewById(R.id.place);
        imageEd = findViewById(R.id.image);
        b1=findViewById(R.id.add);
        DatabaseReference db=firebaseDatabase.getInstance().getReference().child("events");

        dateEd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Calendar cal= Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        AddEventActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        dateSetListener =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = month+"/"+day+"/"+year;
                dateEd.setText(date);
            }
        };
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = db.push().getKey();
                Event e = new Event(
                        id ,
                        nameEd.getText().toString(),
                        descEd.getText().toString(),
                        dateEd.getText().toString(),
                        placeEd.getText().toString(),
                        imageEd.getText().toString()
                );

                Map<String,Object> map=new HashMap<>();
                map.put("id",e.getId());
                map.put("name",e.getName());
                map.put("desc",e.getDesc());
                map.put("date",e.getDate());
                map.put("place",e.getPlace());
                map.put("image",e.getImage());

                db.push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                nameEd.setText("");
                                descEd.setText("");
                                dateEd.setText("");
                                placeEd.setText("");
                                imageEd.setText("");
                                Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
                                Intent i= new Intent(AddEventActivity.this,MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}