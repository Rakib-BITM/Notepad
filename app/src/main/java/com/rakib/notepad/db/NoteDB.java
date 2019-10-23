package com.rakib.notepad.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rakib.notepad.dao.NoteDao;
import com.rakib.notepad.entity.Note;

@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class NoteDB extends RoomDatabase {
    private static NoteDB db;
    public abstract NoteDao getNoteDao();

    public static NoteDB getInstance(Context context){
        if (db != null){
            return db;
        }

        db = Room
                .databaseBuilder(context, NoteDB.class, "notes_db")
                .allowMainThreadQueries()
                .build();
        return db;
    }
}
