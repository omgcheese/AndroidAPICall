package com.example.jinwooklee.outbreaks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by jinwooklee on 16-03-29.
 */
public class ApiConnection{

    //This class will pull data from API server in different thread
    public class DownloadTask extends AsyncTask<String, Void, String> {

        //This class will be taking url from instantiation
        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

             try {
                 url = new URL(urls[0]);
                 urlConnection = (HttpURLConnection) url.openConnection();

                 InputStream in = urlConnection.getInputStream();
                 InputStreamReader reader = new InputStreamReader(in);

                 int data = reader.read();
                 while (data != -1){
                     char current = (char)data;
                     result += current;
                     data = reader.read();
                 }

                 return result;

                 //catching if url is correct
            } catch (MalformedURLException e) {

                e.printStackTrace();

                 //catching if url connection is opened
            } catch (IOException e) {
                 e.printStackTrace();
             }

            return null;
        }

        //method is called when DoinBackground is complete
        //btw original parameter was s but basically result from doinbackground is passed
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //incase json is malformed
            try {
                //parsing the result to JSON object
                JSONObject jsonObject = new JSONObject(result);

                //real data is hidden in list Array thus JSONArray
                String virusdata = jsonObject.getString("list");

                JSONArray data = new JSONArray(virusdata);

                for (int i = 0; i < data.length();i++){
                    JSONObject jsonpart = data.getJSONObject(i);
                    Log.i("Virus", jsonpart.getString("virusname"));
                    Log.i("Country", jsonpart.getString("country"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
