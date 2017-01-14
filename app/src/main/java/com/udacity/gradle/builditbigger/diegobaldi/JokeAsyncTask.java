package com.udacity.gradle.builditbigger.diegobaldi;

import android.content.Context;
import android.os.AsyncTask;

import com.example.diego.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by diego on 13/01/2017.
 */

public class JokeAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    public JokeResponse delegate = null;

    // you may separate this or combined to caller class.
    public interface JokeResponse {
        void jokeTaken(String joke);
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://nanodegree-builditbigger.appspot.com/_ah/api/");
            // end options for devappserver
            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.jokeTaken(result);
    }
}