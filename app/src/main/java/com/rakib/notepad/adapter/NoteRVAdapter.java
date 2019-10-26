package com.rakib.notepad.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.rakib.notepad.NoteUpdateActivity;
import com.rakib.notepad.R;
import com.rakib.notepad.db.NoteDB;
import com.rakib.notepad.entity.Note;


import java.util.ArrayList;
import java.util.List;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.NoteViewHolder> {
    private Context context;
    private List<Note> noteList;
    private List<Note> noteAllList;

    public NoteRVAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
        this.noteAllList = new ArrayList<>(noteList);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.note_row,
                parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        //step 3
        holder.noteTV.setText(noteList.get(position).getNote());
        holder.noteDateTV.setText(noteList.get(position).getDate());

        final int pp =  position;

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showDeleteNoteDialog(position);
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note n = noteList.get(position);
                long id = n.getId();
                Note list = NoteDB.getInstance(context).getNoteDao().getNoteByID(id);
                Intent intent = new Intent(context, NoteUpdateActivity.class);
                intent.putExtra("note",n);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTV,noteDateTV;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            //step 2
            noteTV = itemView.findViewById(R.id.row_note);
            noteDateTV = itemView.findViewById(R.id.row_date);
        }
    }

    public void updateList(List<Note> notes){
        this.noteList = notes;
        notifyDataSetChanged();
    }

    private void deleteNote(Note note) {
        int deletedRow = NoteDB.getInstance(context).getNoteDao().deleteTodo(note);
        if (deletedRow > 0){
            Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
            noteList.remove(note);
            notifyDataSetChanged();
        }
    }


    private void showDeleteNoteDialog(final int p) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.delete_note_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("");
        builder.setView(view);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Note todo = noteList.get(p);
                deleteNote(todo);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
