package com.benjaminsinzore.currencyconverterapplicatin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benjaminsinzore.currencyconverterapplicatin.R;
import com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements.NoteDatabaseHelper;
import com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements.NotesPojo;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{

    Context context;
    List<NotesPojo> notes_list;
    public String showText;

    public NotesAdapter(Context context) {
        this.context = context;
    }
    NoteDatabaseHelper noteDatabaseHelper;

    public NotesAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.convertFrom.setText(notes_list.get(position).getConvertFrom());
        holder.convertTo.setText(notes_list.get(position).getConvertTo());
        holder.getConvertFrom.setText(notes_list.get(position).getGetConvertFromValue());
        holder.getConverterValue.setText(notes_list.get(position).getGetConvertedValue());

        showText = holder.convertFrom.getText().toString();
    }



    @Override
    public int getItemCount() {
        return notes_list.size();
    }

    public void addNote(List<NotesPojo> notesPojos) {
        this.notes_list = notesPojos;

    }

    public void showToast() {
        Toast.makeText(context, ""+showText, Toast.LENGTH_SHORT ).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView convertFrom, convertTo, getConvertFrom, getConverterValue;
        ImageView deleteFile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            convertFrom = itemView.findViewById(R.id.convertFrom);
            convertTo = itemView.findViewById(R.id.convertTo);
            getConvertFrom = itemView.findViewById(R.id.getConvertFrom);
            getConverterValue = itemView.findViewById(R.id.getConverterValue);
            deleteFile = itemView.findViewById(R.id.deleteFile);
        }
    }

}
