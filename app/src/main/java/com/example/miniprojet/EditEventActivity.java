package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.miniprojet.Model.Event;

public class EditEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        Intent i = getIntent();
        String id = i.getStringExtra("edit");
        TextView uid =  findViewById(R.id.id);
        uid.setText(id);
    }
}