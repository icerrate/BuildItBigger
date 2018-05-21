package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.icerrate.jokemodule.JokerActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * @author Ivan Cerrate.
 */
public class GetJokeAsyncTask extends AsyncTask<Void, Void, String> {

    private AlertDialog alertDialog;

    private static MyApi myApiService = null;

    private WeakReference<MainActivity> activityReference;

    public GetJokeAsyncTask(MainActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        MainActivity activity = activityReference.get();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setMessage(R.string.loading);
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }
        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        MainActivity activity = activityReference.get();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
        activity.startActivity(JokerActivity.makeIntent(activity, joke));
    }
}