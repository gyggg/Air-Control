package com.example.airconditionerremote;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpGetTask extends AsyncTask<Integer, Void, Void> {
    private String IPAdress ;
    private StringBuilder mUrl;
    private Activity parentActivity;

    public HttpGetTask(Activity mainActivity, String IPAdress ) {
        this.parentActivity = mainActivity;
        this.IPAdress = IPAdress;
    }

    @Override
    protected Void doInBackground(Integer... arg0) {
        mUrl = new StringBuilder("http://"+IPAdress+"/control.php?");
        mUrl.append("num=").append(arg0[0].toString()).append("&stat=").append(arg0[1].toString());
        exec_get();
        return null;
    }

    private String exec_get() {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String src = "";
        try {
            URL url = new URL(mUrl.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            byte[] line = new byte[1024];
            int size;
            while (true) {
                size = inputStream.read(line);
                if (size <= 0) {
                    break;
                }
                src += new String(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
            try {
                if (inputStream != null)
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return src;
    }

}