package com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements;

import android.content.Context;

import androidx.room.Room;

public class NotesClient {
    Context context;
    private static NotesClient instance;
    private NoteDatabase notesDatabase;

    public NotesClient (Context context){
        this.context = context;
        notesDatabase = Room.databaseBuilder(context, NoteDatabase.class, "NotePad").build();
    }

    public static synchronized NotesClient getInstance(Context context) {
        if (instance == null){
            instance = new NotesClient(context);
        }
        return instance;
    }

    public NoteDatabase getNotesDatabase(){
        return notesDatabase;
    }
}
