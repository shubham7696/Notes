package com.shubham.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

import static com.shubham.notes.MainActivity.notes;
import static com.shubham.notes.MainActivity.set;

public class EditYourNote extends AppCompatActivity implements TextWatcher {

    int noteId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_note);
        EditText editText = (EditText)findViewById(R.id.editText);
        Intent i = getIntent();
        noteId = i.getIntExtra("noteId",-1);
        if(noteId != -1)
        {
            editText.setText(notes.get(noteId));
        }
        editText.addTextChangedListener(this);
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        notes.set(noteId, String.valueOf(charSequence));
        MainActivity.arrayAdapter.notifyDataSetChanged();


        SharedPreferences sharedPreferences = this.getSharedPreferences("com.shubham.notes", Context.MODE_PRIVATE);

        if(MainActivity.set == null)
        {
            MainActivity.set = new HashSet<String>();
        }
        else
        {
            MainActivity.set.clear();
        }
        MainActivity.set.addAll(MainActivity.notes);

        sharedPreferences.edit().remove("notes").apply();

        sharedPreferences.edit().putStringSet("notes",MainActivity.set).apply();

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
