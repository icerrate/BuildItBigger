package com.icerrate.jokemodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * @author Ivan Cerrate.
 */
public class JokerActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "key_joke";

    public static Intent makeIntent(Context context, String joke) {
        Intent intent = new Intent(context, JokerActivity.class);
        intent.putExtra(JOKE_KEY, joke);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joker);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.get(JOKE_KEY) != null) {
            String joke = bundle.getString(JOKE_KEY);
            ((TextView) findViewById(R.id.joke)).setText(joke);
        }
    }
}
