package com.reazr.house801;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static String TAG = "ChatActivity";

    private AsyncConnection asyncConnection;
    private int cid;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        cid = intent.getIntExtra("cid", 0);

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_text_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<String> texts = new ArrayList<String>();
        texts.add("Hello");
        texts.add("Hi");
        texts.add("FuckOff");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");
        texts.add("Why are you so angry?");


        mAdapter = new MyAdapter(texts);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static void actionStart(Context context, int cid) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("cid", cid);
        context.startActivities(new Intent[]{intent});
    }


    private class ChatHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView chatText;

        private Connector connector;

        public ChatHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            chatText = (TextView) itemView.findViewById(R.id.chat_text);
        }

        public void bindCrime(String text) {

            Log.d(TAG, text);
            if (chatText == null) {
                Log.d(TAG, "chatText is null");
            }

            chatText.setText(text);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class MyAdapter extends RecyclerView.Adapter<ChatHolder> {

        private List<String> texts;

        public MyAdapter(List<String> texts) {
            this.texts = texts;
        }

        @Override
        public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.chat_view_item, parent, false);
            return new ChatHolder(view);
        }

        @Override
        public void onBindViewHolder(ChatHolder holder, int position) {
            String text = texts.get(position);
            holder.bindCrime(text);
        }

        @Override
        public int getItemCount() {
            return texts.size();
        }
    }

}
