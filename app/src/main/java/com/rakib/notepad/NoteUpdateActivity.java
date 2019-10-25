package com.rakib.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rakib.notepad.adapter.NoteRVAdapter;
import com.rakib.notepad.db.NoteDB;
import com.rakib.notepad.entity.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NoteUpdateActivity extends AppCompatActivity {
    private EditText editInput;

    private Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_update);
        setTitle("Update Note");
        editInput = findViewById(R.id.updateET);
        note = (Note) getIntent().getSerializableExtra("note");
        long id = note.getId();
        Note note1 = NoteDB.getInstance(this).getNoteDao().getNoteByID(id);
        editInput.setText(note1.getNote());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.noteUpdate:
                updateNote();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateNote(){
        String noteText = editInput.getText().toString();
        String date = getCurrentDate();
        note.setNote(noteText);
        note.setDate(date);

        int updateRow = NoteDB.getInstance(NoteUpdateActivity.this).getNoteDao().updateNote(note);
        if (updateRow>0){
            Toast.makeText(NoteUpdateActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();
            List<Note> noteList = NoteDB.getInstance(NoteUpdateActivity.this).getNoteDao().getAllNote();
            NoteRVAdapter rvAdapter = new NoteRVAdapter(NoteUpdateActivity.this,noteList);
            rvAdapter.updateList(noteList);
            startActivity(new Intent(NoteUpdateActivity.this,MainActivity.class));
        }
    }


    //get Current Date & Time
    private String getCurrentDate(){
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                .format(new Date());
    }

}
