package com.reazr.house801;
/*
* http://stackoverflow.com/questions/9360461/changing-edittext-height-and-width-on-runtime-with-some-animation
*
* */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        cid = intent.getIntExtra("cid", 0);
        asyncConnection = AsyncConnPool.pool.get(cid);

        mRecyclerView = (RecyclerView) findViewById(R.id.chat_text_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Msg> texts = DatabaseHelper.getsInstance(ChatActivity.this).getChatMessage(cid);

        texts.add(0, new Msg(cid, 1, "Hello Mr.!") );
        texts.add(1, new Msg(cid, 0, "Hi Miss!") );

        mAdapter = new MyAdapter(texts);
        mRecyclerView.setAdapter(mAdapter);

        sendButton = (Button)findViewById(R.id.chat_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "chat send click!");
            }
        });
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

        public void bindCrime(Msg text) {

            Log.d(TAG, text.text);
            if (chatText == null) {
                Log.d(TAG, "chatText is null");
            }

            if (text.author == 0) {
                chatText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            }
            chatText.setText(text.text);
        }

        @Override
        public void onClick(View v) {
            TextActivity.actionStart(ChatActivity.this, chatText.getText().toString());
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<ChatHolder> {

        private List<Msg> texts;

        public MyAdapter(List<Msg> texts) {
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
            Msg text = texts.get(position);
            holder.bindCrime(text);
        }

        @Override
        public int getItemCount() {
            return texts.size();
        }
    }

}
