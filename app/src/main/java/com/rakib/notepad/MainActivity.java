package com.rakib.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.rakib.notepad.adapter.NoteRVAdapter;
import com.rakib.notepad.db.NoteDB;
import com.rakib.notepad.entity.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView noteRV;
    private NoteRVAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteRV =findViewById(R.id.noteRV);

        List<Note> notes = NoteDB.getInstance(this).getNoteDao().getAllNote();

        rvAdapter = new NoteRVAdapter(this,notes);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        //GridLayoutManager glm = new GridLayoutManager(this, 2);
        noteRV.setLayoutManager(llm);
        noteRV.setAdapter(rvAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.search_input);
        SearchView searchView = (SearchView) item.getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.noteAdd:
                showNoteSaveActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showNoteSaveActivity(){
        Intent intent = new Intent(this,NoteAddActivity.class);
        startActivity(intent);
    }

}
