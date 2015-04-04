package com.nisostech.dynamiclistview.networkcalls;

import android.os.AsyncTask;

import com.nisostech.dynamiclistview.Interface.OnAsynTaskCompleted;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Soham Banerjee
 */
public class AsyncListLoader extends AsyncTask<Integer, String, String> {

    String url;
    String response = "";
    OnAsynTaskCompleted onAsynTaskCompleted;
     public AsyncListLoader(String url,OnAsynTaskCompleted asynTaskCompleted){
        this.url=url;
        this.onAsynTaskCompleted=onAsynTaskCompleted;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(Integer... args) {

        // Parsing 20 more items and adding them to the adapter
        ////

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse execute = client.execute(httpGet);
            InputStream content = execute.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                response += s;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String args) {
       onAsynTaskCompleted.afterSuccess(args);
    }
}