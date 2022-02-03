package com.benjaminsinzore.currencyconverterapplicatin.RoomRequirements;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class NotesPojo implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "convertFrom")
    private String convertFrom;

    @ColumnInfo(name = "getConvertFromValue")
    private String getConvertFromValue;

    @ColumnInfo(name = "convertTo")
    private String convertTo;

    @ColumnInfo(name = "getConvertedValue")
    private String getConvertedValue;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConvertFrom() {
        return convertFrom;
    }

    public void setConvertFrom(String convertFrom) {
        this.convertFrom = convertFrom;
    }

    public String getGetConvertFromValue() {
        return getConvertFromValue;
    }

    public void setGetConvertFromValue(String getConvertFromValue) {
        this.getConvertFromValue = getConvertFromValue;
    }

    public String getConvertTo() {
        return convertTo;
    }

    public void setConvertTo(String convertTo) {
        this.convertTo = convertTo;
    }

    public String getGetConvertedValue() {
        return getConvertedValue;
    }

    public void setGetConvertedValue(String getConvertedValue) {
        this.getConvertedValue = getConvertedValue;
    }
}
