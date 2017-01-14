package app.diegobaldi.it.jokeviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowJokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_joke);
        Intent intent = getIntent();
        TextView jokeView = (TextView) findViewById(R.id.joke_text);
        if(intent.hasExtra("joke")){
            jokeView.setText(intent.getStringExtra("joke"));
        }
    }
}
