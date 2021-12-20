package com.example.lab06_noteslukyanov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {

    EditText txt_Title, txt_Content;
    int pos;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        txt_Title = findViewById(R.id.EditTextName);
        txt_Content = findViewById(R.id.EditTextNote);

        i = getIntent();
        pos = i.getIntExtra("my-note-index", -1);
        txt_Title.setText(i.getStringExtra("my-note-title"));
        txt_Content.setText(i.getStringExtra("my-note-content"));
    }

    public void Cancel_OnClick(View v)
    {
        finish();
    }

    public void Save_OnClick(View v)
    {
        i = new Intent();
        i.putExtra("my-note-index", pos);
        i.putExtra("my-note-title", txt_Title.getText().toString());
        i.putExtra("my-note-content", txt_Content.getText().toString());

        setResult(RESULT_OK, i);
        finish();
    }
}