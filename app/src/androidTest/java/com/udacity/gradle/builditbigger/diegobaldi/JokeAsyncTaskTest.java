package com.udacity.gradle.builditbigger.diegobaldi;

import android.os.AsyncTask;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.diego.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by diego on 13/01/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class JokeAsyncTaskTest {

    private static MyApi myApiService = null;
    private String mJoke;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    /**
     * This demonstrates how to test AsyncTasks in android JUnit. Below I used
     * an in line implementation of a asyncTask, but in real life you would want
     * to replace that with some task in your application.
     * @throws Throwable
     */
    @Test
    public void testSomeAsynTask () throws Throwable {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     */
        final AsyncTask<String, Void, String> myTask = new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... arg0) {
                if(myApiService == null) {  // Only do this once
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // options for running against local devappserver
                            // - 10.0.2.2 is localhost's IP address in Android emulator
                            // - turn off compression when running against local devappserver
                            .setRootUrl("http://192.168.1.66:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
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
                mJoke = result;
                signal.countDown();
            }
        };

        // Execute the async task on the UI thread! THIS IS KEY!
        mActivityRule.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                myTask.execute();
            }
        });

    /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 30 seconds passes and it times out.
     */
        assertTrue(signal.await(30, TimeUnit.SECONDS));

        assertNotNull(mJoke);

        assertTrue(mJoke.length()>1);
    }

}