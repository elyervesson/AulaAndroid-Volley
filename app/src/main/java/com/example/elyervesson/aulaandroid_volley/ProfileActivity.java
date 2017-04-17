package com.example.elyervesson.aulaandroid_volley;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class ProfileActivity extends Activity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView = (TextView) findViewById(R.id.textViewAnswer);
        textView.setText("Resposta servidor: " + getIntent().getExtras().getString("answer"));
    }
}
