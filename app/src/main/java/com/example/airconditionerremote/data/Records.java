package com.example.airconditionerremote.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Records extends ArrayList<Record> implements Serializable {
    public static String pKey = "List";
    public static String pName = "Record";
    private static Records records;

    public static Records getRecords() {
        return records;
    }

    public static void setRecords(Records records) {
        Records.records = records;
    }

    public static void saveRecords(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(pName, Context.MODE_PRIVATE).edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(records);
            String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(pKey, temp);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Records loadRecords(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pName, context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(pKey, "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        records = new Records();
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            records = (Records) ois.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e1) {

        }
        return records;
    }

    public static void addRecord(Record record) {
        records.add(new Record(record));
    }

    public static void resetRecords(Context context) {
        records = new Records();
        saveRecords(context);
    }
}
