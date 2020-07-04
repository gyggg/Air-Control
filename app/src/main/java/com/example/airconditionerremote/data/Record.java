package com.example.airconditionerremote.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Record implements Serializable, Cloneable {
    final public static String pKey = "Main";
    final public static String pName = "Record";

    final public static int MODE_WARM = 0;
    final public static int MODE_COOL = 1;
    private int temperature = 26;
    private int mode = MODE_COOL;
    private int power = 1;
    private boolean running = false;
    private String name = "default";
    private String ipAddress = "192.168.1.1";
    private boolean resetFlag = false;
    static private Record mainRecord;

    public Record() {
    }

    public Record(Record record) {
        this.temperature = record.temperature;
        this.mode = record.mode;
        this.power = record.power;
        this.running = record.running;
        this.name = record.name;
        this.ipAddress = record.ipAddress;
    }


    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isResetFlag() {
        return resetFlag;
    }

    public void setResetFlag(boolean resetFlag) {
        this.resetFlag = resetFlag;
    }

    public String getModeString() {
        String result = "";
        switch (mode) {
            case MODE_COOL: result =  "冷房"; break;
            case MODE_WARM: result = "暖房"; break;
        }
        return result;
    }

    public String getPowerString() {
        String result = "";
        switch(power) {
//            case 0: result = "しずか";break;
            case 0: result = "◀";break;
//            case 2: result = "◀◀";break;
            case 1: result = "◀◀◀◀◀";break;
//            case 4: result = "自動";break;
        }
        return result;
    }

    public String getTemputerString() {
        return "" + temperature + "°C";
    }

    public boolean isRunning() {
        return running;
    }

    public static Record getMainRecord() {
        return mainRecord;
    }

    public static void setMainRecord(Record mainRecord) {
        Record.mainRecord = new Record(mainRecord);
        Record.mainRecord.setResetFlag(true);
    }

    public static void saveRecord(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(pName, Context.MODE_PRIVATE).edit();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(mainRecord);
            String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(pKey, temp);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Record loadRecord(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pName, context.MODE_PRIVATE);
        String temp = sharedPreferences.getString(pKey, "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        mainRecord = new Record();
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            mainRecord = (Record) ois.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e1) {

        }
        return mainRecord;
    }

    public static void resetRecord(Context context) {
        mainRecord = new Record();
        saveRecord(context);
        mainRecord.setResetFlag(true);
    }

    public void temperatureUp() {
        temperature = temperature + 1;
        if(temperature > 30)
            temperature = 30;
    }

    public void temperatureDown() {
        temperature = temperature - 1;
        if(temperature < 17)
            temperature = 17;
    }

    public void modeChange() {
        mode = (mode + 1) % 2;
    }

    public void powerChange() {
        power = (power + 1) % 2;
    }



    public String generateUrl() {
        int runId = isRunning() ? 1 : 0;
        String url = String.format("http://%s/control.php?run=%d&temp=%d&mode=%d&power=%d", ipAddress, runId, temperature, mode, power);
        return url;
    }

}
