package com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NotesPojo.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDAO noteDAO();
}
