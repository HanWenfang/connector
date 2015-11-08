package com.reazr.house801;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    private String text;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        Intent intent = getIntent();
        text = intent.getStringExtra("text");

        TextView textView = (TextView)findViewById(R.id.chat_text_view);
        textView.setText(text);
    }


    public static void actionStart(Context context, String text) {
        Intent intent = new Intent(context, TextActivity.class);
        intent.putExtra("text", text);
        context.startActivities(new Intent[]{intent});
    }


}
