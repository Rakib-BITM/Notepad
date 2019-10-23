package com.rakib.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.rakib.notepad.adapter.NoteRVAdapter;
import com.rakib.notepad.db.NoteDB;
import com.rakib.notepad.entity.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteAddActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.noteSave:
                saveNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote(){
        EditText noteInput = findViewById(R.id.noteET);
        String notetext = noteInput.getText().toString();

        Note note = new Note(notetext,getCurrentDate());

        //insert into table
        long insertedRow = NoteDB.getInstance(NoteAddActivity.this).getNoteDao().insertNote(note);
        if (insertedRow > 0){
            Toast.makeText(NoteAddActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
            List<Note> notes = NoteDB.getInstance(NoteAddActivity.this).getNoteDao().getAllNote();
            NoteRVAdapter rvAdapter = new NoteRVAdapter(this,notes);
            rvAdapter.updateList(notes);
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    //get Current Date & Time
    private String getCurrentDate(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(new Date());
    }
}
