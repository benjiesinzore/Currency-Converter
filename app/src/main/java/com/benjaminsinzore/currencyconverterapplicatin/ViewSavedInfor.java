package com.benjaminsinzore.currencyconverterapplicatin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements.NoteDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewSavedInfor extends AppCompatActivity {

    private RecyclerView recycler_view;
    private NoteDatabaseHelper noteDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_saved_infor);

        recycler_view = findViewById(R.id.recyclerView);
        noteDatabaseHelper = new NoteDatabaseHelper(this);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recycler_view.hasFixedSize();
        recycler_view.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        noteDatabaseHelper.getAllNotes(recycler_view);
    }
}