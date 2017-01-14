package com.udacity.gradle.builditbigger.diegobaldi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import app.diegobaldi.it.jokeviewer.ShowJokeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements JokeAsyncTask.JokeResponse {

    private String mJoke;

    @BindView(R.id.joke_progress) ProgressBar jokeProgress;
    @BindView(R.id.joke_progress_text) TextView jokeProgressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    private void showJoke() {
        Intent intent = new Intent(this, ShowJokeActivity.class);
        intent.putExtra("joke", mJoke);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if(jokeProgressText!=null && jokeProgress!=null){
            jokeProgress.setVisibility(View.VISIBLE);
            jokeProgressText.setVisibility(View.VISIBLE);
        }
        JokeAsyncTask mAsyncTask = new JokeAsyncTask();
        mAsyncTask.delegate = this;
        mAsyncTask.execute();
    }


    @Override
    public void jokeTaken(String joke) {
        if(jokeProgressText!=null && jokeProgress!=null){
            jokeProgress.setVisibility(View.GONE);
            jokeProgressText.setVisibility(View.GONE);
        }
        mJoke = joke;
        showJoke();
    }
}
