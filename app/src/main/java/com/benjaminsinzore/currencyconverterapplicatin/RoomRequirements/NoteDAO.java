package com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Query("SELECT * FROM notespojo")
    List<NotesPojo> getAll();

    @Insert
    void insert(NotesPojo notesPojo);

    @Update
    void update(NotesPojo notesPojo);

    @Delete
    void delete(NotesPojo notesPojo);
}
