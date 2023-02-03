package com.example.bumbee.activities.note;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.example.bumbee.R;

public class note extends LanguageTranslator {
    private NoteAdapter noteAdapter;
    private ListView list_view_vocab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        if (list_vocab.size() == 0)
        {
            Toast.makeText(getApplicationContext(), "You should create new some vocabulary to be saved", Toast.LENGTH_SHORT).show();
            noteAdapter = new NoteAdapter(list_vocab);
            noteAdapter.notifyDataSetChanged();
            list_view_vocab = findViewById(R.id.listview);
            list_view_vocab.setAdapter(noteAdapter);
        }else
        {
            noteAdapter = new NoteAdapter(list_vocab);
            noteAdapter.notifyDataSetChanged();
            list_view_vocab = findViewById(R.id.listview);
            list_view_vocab.setAdapter(noteAdapter);
        }
    }


}