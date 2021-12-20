package com.example.lab06_noteslukyanov;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter <Note> adp;
    ListView lst;
    int pos, sel = -1;
    String title, content;
    Note n, edit;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);

        lst = findViewById(R.id.lst_notes);
        lst.setAdapter(adp);
        
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                sel = position;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (data != null)
        {
            pos = data.getIntExtra("my-note-index", -1);

            title = data.getStringExtra("my-note-title");
            content = data.getStringExtra("my-note-content");

            n = adp.getItem(pos);
            n.title = title;
            n.content = content;

            adp.notifyDataSetChanged();
        }
        else if (requestCode == 12345) adp.remove(n);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void New_OnClick(View v)
    {
        n = new Note();
        n.title = "New note";
        n.content = "New content";

        adp.add(n);
        pos = adp.getPosition(n);

        i = new Intent(this, NoteActivity.class);
        i.putExtra("my-note-index", pos);
        i.putExtra("my-note-title", n.title);
        i.putExtra("my-note-content", n.content);

        startActivityForResult(i, 12345);
    }

    public void Edit_OnClick(View v)
    {
        if (sel == -1) Toast.makeText(this, "Note is not selected", Toast.LENGTH_SHORT).show();
        else
        {
            edit = new Note();
            edit.title = adp.getItem(sel).title;
            edit.content = adp.getItem(sel).content;

            pos = sel;

            i = new Intent(this, NoteActivity.class);
            i.putExtra("my-note-index", pos);
            i.putExtra("my-note-title", edit.title);
            i.putExtra("my-note-content", edit.content);

            startActivityForResult(i, 54321);
        }
    }

    public void Delete_OnClick(View v)
    {
        if (sel == -1) Toast.makeText(this, "Note is not selected", Toast.LENGTH_SHORT).show();
        else
        {
            LayoutInflater dialogLayout = LayoutInflater.from(this);
            View dialogView = dialogLayout.inflate(R.layout.delete_dialog, null);
            AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
            deleteDialog.setView(dialogView);
            deleteDialog.show();

            Button btnYes = dialogView.findViewById(R.id.btn_Yes);
            Button btnNo = dialogView.findViewById(R.id.btn_No);

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adp.remove(adp.getItem(sel));
                    sel = -1;
                    deleteDialog.cancel();
                }
            });
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteDialog.cancel();
                }
            });
        }
    }
}