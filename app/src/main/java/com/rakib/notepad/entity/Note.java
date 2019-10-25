package com.rakib.notepad.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String note;
    private String date;

    @Ignore
    public Note() {
    }

    public Note(String note, String date) {
        this.note = note;
        this.date = date;
    }

    @Ignore
    public Note(long id, String note, String date) {
        this.id = id;
        this.note = note;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
