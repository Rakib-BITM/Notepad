package com.rakib.notepad.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rakib.notepad.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insertNote(Note note);

    @Query("select * from Note order by id desc")
    List<Note> getAllNote();

    @Query("select * from Note where id ==:noteID")
    Note getNoteByID(long noteID);

    @Update
    int updateNote(Note note);

    @Delete
    int deleteTodo(Note note);

}
