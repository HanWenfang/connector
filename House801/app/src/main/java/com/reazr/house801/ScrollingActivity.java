package com.reazr.house801;
/*
* android list item layout:
* http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
*
*
*
* */
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity implements Runnable{
    private static final String TAG = "ScrollingActivity";


    private static final String Host = "172.16.0.29";
    private static final int Port = 3591;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String content = "";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate() called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Server> servers = new ArrayList<Server>();
        for (int i=0; i<100; ++i) {
            servers.add(new Server("closed", "172.16.0.29", 3591));
        }


        mAdapter = new MyAdapter(servers);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                //        .setAction("Action", null).show();

                ConnectorActivity.actionStart(ScrollingActivity.this);
            }
        });

        new Thread(ScrollingActivity.this).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    public void run() {
        try {

            try {
                socket = new Socket(Host, Port);
                in = new BufferedReader( new InputStreamReader(socket.getInputStream()) );
                out = new PrintWriter(new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())), true);

            } catch (IOException ex) {
                ex.printStackTrace();

            }

            while (true) {
                if (socket.isConnected()) {
                    if (!socket.isInputShutdown()) {
                        if ((content = in.readLine()) != null) {
                            content += "\n";
                            //mHandler.sendMessage(mHandler.obtainMessage());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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

    private class ServerHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView status;
        private TextView host;
        private TextView port;

        private Server server;

        public ServerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            status = (TextView) itemView.findViewById(R.id.status);
            host = (TextView) itemView.findViewById(R.id.host);
            port = (TextView) itemView.findViewById(R.id.port);
        }

        public void bindCrime(Server server) {
            this.server = server;
            status.setText(server.status);
            host.setText(server.host);
            port.setText(String.valueOf(server.port));
        }

        @Override
        public void onClick(View v) {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<ServerHolder> {

        private List<Server> servers;

        public MyAdapter(List<Server> servers) {
            this.servers = servers;
        }

        @Override
        public ServerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.list_view_item, parent, false);
            return new ServerHolder(view);
        }

        @Override
        public void onBindViewHolder(ServerHolder holder, int position) {
            Server server = servers.get(position);
            holder.bindCrime(server);
        }

        @Override
        public int getItemCount() {
            return servers.size();
        }
    }
}
