package com.benjaminsinzore.currencyconverterapplicatin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.benjaminsinzore.currencyconverterapplicatin.Adapter.NotesAdapter;
import com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements.NoteDatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {


    private NoteDatabaseHelper noteDatabaseHelper;

    private NotesAdapter notesAdapter;

    TextView convertFromDropdownTextView, convertToDropdownTextView, conversionRateText;
    EditText amountToConvert;
    ArrayList<String> arrayList;
    Dialog fromDialog;
    Dialog toDialog;
    Button convertButton, conversionHistory, conversionSave;
    String convertFromValue, convertToValue, conversionValue, convertFromString, convertToString, getConvertedValue, getConvertFromValue;
    String[] country = {"AFN", "EUR", "DZD", "USD", "AOA", "XCD", "AMD", "AWG", "AUD", "AZN", "BSD", "BHD", "BBD", "BYN", "BZD", "XOF", "BMD", "INR", "BTN",};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        notesAdapter = new NotesAdapter();

        noteDatabaseHelper = new NoteDatabaseHelper(this);

        convertFromDropdownTextView = findViewById(R.id.convert_from_dropdown_menu);
        convertToDropdownTextView = findViewById(R.id.convert_to_dropdown_menu);
        convertButton = findViewById(R.id.conversionButton);
        conversionRateText = findViewById(R.id.conversionRateText);
        amountToConvert = findViewById(R.id.amountToConvertValueEditText);
        conversionHistory = findViewById(R.id.conversionHistory);
        conversionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ViewSavedInfor.class);
                startActivities(new Intent[]{intent});
            }
        });
        conversionSave = findViewById(R.id.conversionSave);
        conversionSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveInformation();
            }
        });



        arrayList = new ArrayList<>();
        for (String i: country){
            arrayList.add(i);
        }

        convertFromDropdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromDialog = new Dialog(MainActivity.this);
                fromDialog.setContentView(R.layout.from_spiner);
                fromDialog.getWindow().setLayout(650, 800);
                fromDialog.show();
                fromDialog.show();

                EditText editText = fromDialog.findViewById(R.id.edit_text);
                ListView listView = fromDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        convertFromDropdownTextView.setText(adapter.getItem(i));
                        fromDialog.dismiss();
                        convertFromValue = adapter.getItem(i);

                        convertFromString = adapter.getItem(i).toString();
                    }
                });
            }
        });

        convertToDropdownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDialog =  new Dialog(MainActivity.this);
                toDialog.setContentView(R.layout.to_spiner);
                toDialog.getWindow().setLayout(650, 800);
                toDialog.show();

                EditText editText = toDialog.findViewById(R.id.edit_text);
                ListView listView = toDialog.findViewById(R.id.list_view);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
                listView.setAdapter(adapter);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        adapter.getFilter().filter(charSequence);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        convertToDropdownTextView.setText(adapter.getItem(i));
                        toDialog.dismiss();
                        convertToValue = adapter.getItem(i);


                        convertToString = convertToDropdownTextView.getText().toString();
                    }
                });
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    Double amountToConvert = Double.valueOf(MainActivity.this.amountToConvert.getText().toString());
                    getConversionRate(convertFromValue, convertToValue, amountToConvert);

                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public  String getConversionRate(String convertFrom, String convertTo, Double amountToConvert){

        RequestQueue queue = new Volley().newRequestQueue(this);
        String url= "https://free.currconv.com/api/v7/convert?q="+convertFrom+"_"+convertTo+"&compact=ultra&apiKey=fca3435d8f0cbd225924";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Double conversionRateValue = round(((Double) jsonObject.get(convertFrom+"_"+convertTo)),2);
                    conversionValue = "" + round((conversionRateValue * amountToConvert), 2);
                    conversionRateText.setText(conversionValue);


                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
        return null;
    }

    private void proceedToSaving(TextView conversionRateText, String convertFrom, String convertTo) {

        getConvertedValue = conversionRateText.toString();
        getConvertFromValue = amountToConvert.toString();
    }

    public static double round(double value, int places){
        if (places<0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void SaveInformation() {

        getConvertedValue = conversionValue.toString();
        getConvertFromValue = amountToConvert.getText().toString();
        String  convertTo = convertToString.toString();
        String  convertFrom = convertFromString.toString();
        saveDataToRoom(convertFrom, getConvertFromValue, convertTo, getConvertedValue);

        }



    private void saveDataToRoom(String convertFrom, String getConvertFromValue, String convertTo, String getConvertedValue) {
        noteDatabaseHelper.addNote(convertFrom, getConvertFromValue, convertTo, getConvertedValue);

       }
}