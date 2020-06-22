package com.example.airconditionerremote;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTask extends AsyncTask<Void, Void, Void> {
    HttpURLConnection http;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            http.connect();
            http.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            http.disconnect();
        }
        return null;
    }
    public static void sendGet(String surl) throws IOException {
        URL url = new URL(surl);
        HttpTask httpGetTask = new HttpTask();
        httpGetTask.http = (HttpURLConnection) url.openConnection();
        httpGetTask.http.setRequestMethod("GET");
        httpGetTask.execute();
    }
}