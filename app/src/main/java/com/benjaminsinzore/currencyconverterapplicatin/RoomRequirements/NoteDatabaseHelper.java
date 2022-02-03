package com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.benjaminsinzore.currencyconverterapplicatin.Adapter.NotesAdapter;
import com.benjaminsinzore.currencyconverterapplicatin.ViewSavedInfor;

import java.util.List;

public class NoteDatabaseHelper {

    Context context;
    NotesAdapter notesAdapter;

    public NoteDatabaseHelper(Context context) {
        this.context = context;
        notesAdapter = new NotesAdapter(context);
    }



    public void addNote(String convertFrom, String getConvertFromValue, String convertTo, String getConvertedValue){
        class AddNote extends AsyncTask<Void ,Void, NotesPojo>{

            @Override
            protected NotesPojo doInBackground(Void... voids) {
                NotesPojo notesPojo = new NotesPojo();
                notesPojo.setConvertFrom(convertFrom);
                notesPojo.setConvertTo(convertTo);
                notesPojo.setGetConvertFromValue(getConvertFromValue);
                notesPojo.setGetConvertedValue(getConvertedValue);

                NotesClient.getInstance(context)
                        .getNotesDatabase()
                        .noteDAO()
                        .insert(notesPojo);
                return notesPojo;
            }

            @Override
            protected void onPostExecute(NotesPojo notesPojo) {
                super.onPostExecute(notesPojo);

                if (notesPojo != null){
                    Intent intent = new Intent(context, ViewSavedInfor.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivities(new Intent[]{intent});
                    Toast.makeText(context, "Conversion Saved", Toast.LENGTH_SHORT).show();
                } else {
                    return;
                }
            }
        }

        AddNote addNote = new AddNote();
        addNote.execute();
    }

    public void getAllNotes(RecyclerView recyclerView){
        class GetAllNotes extends AsyncTask<Void, Void, List<NotesPojo>>{

            @Override
            protected List<NotesPojo> doInBackground(Void... voids) {
                List<NotesPojo> notes_list = NotesClient
                        .getInstance(context)
                        .getNotesDatabase()
                        .noteDAO()
                        .getAll();
                return notes_list;
            }

            @Override
            protected void onPostExecute(List<NotesPojo> notesPojos) {
                super.onPostExecute(notesPojos);

                if (notesPojos != null && notesPojos.size() > 0){
                    notesAdapter.addNote(notesPojos);
                    recyclerView.setAdapter(notesAdapter);
                }
            }
        }

        GetAllNotes allNote = new GetAllNotes();
        allNote.execute();
    }

    /*public void updateNotes(NotesPojo notesPojo, String title, String description){
        class UpdateNotes extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                notesPojo.setDescription(description);
                notesPojo.setTitle(title);
                NotesClient.getInstance(context)
                        .getNotesDatabase()
                        .noteDAO()
                        .update(notesPojo);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (notesPojo != null){
                    Intent intent = new Intent(context, ViewSavedInfor.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivities(new Intent[]{intent});
                    Toast.makeText(context, "Note Updated", Toast.LENGTH_SHORT).show();
                } else {
                    return;
                }
            }
        }

        UpdateNotes updateNotes = new UpdateNotes();
        updateNotes.execute();
    }*/

    public void deleteNotes(NotesPojo notesPojo) {
        class DeleteNote extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                NotesClient.getInstance(context)
                        .getNotesDatabase()
                        .noteDAO()
                        .delete(notesPojo);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


                Intent intent = new Intent(context, ViewSavedInfor.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivities(new Intent[]{intent});
                Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }

        DeleteNote deleteNote = new DeleteNote();
        deleteNote.execute();
    }
}
