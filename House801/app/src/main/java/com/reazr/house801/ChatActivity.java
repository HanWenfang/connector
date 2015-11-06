package com.reazr.house801;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChatActivity extends AppCompatActivity {

    private AsyncConnection asyncConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    public static void actionStart(Context context, int cid) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("cid", cid);
        context.startActivities(new Intent[]{intent});
    }


}
