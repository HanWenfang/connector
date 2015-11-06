package com.reazr.house801;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by tuyou on 11/6/15.
 */
public class AsyncConnection extends AsyncTask<Void, String, Exception> {

    public static final String TAG = "AsyncConnection";

    private String host;
    private int port;
    private int timeout;
    private ConnectionHandler connectionHandler;

    private BufferedReader in;
    private BufferedWriter out;
    private Socket socket;
    private boolean interrupted = false;

    public AsyncConnection(String host, int port, int timeout, ConnectionHandler connectionHandler) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.connectionHandler = connectionHandler;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Exception result) {
        super.onPostExecute(result);
        Log.d(TAG, "Finished communication with the socket. Result = " + result);
        //TODO If needed move the didDisconnect(error); method call here to implement it on UI thread.
    }

    @Override
    protected Exception doInBackground(Void... params) {
        Exception error = null;

        try {
            Log.d(TAG, "Opening socket connection.");
            socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), timeout);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            connectionHandler.didConnect();

            while(!interrupted) {
                String line = in.readLine();
                //Log.d(TAG, "Received:" + line);
                connectionHandler.didReceiveData(line);
            }
        } catch (UnknownHostException ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } catch (IOException ex) {
            Log.d(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } catch (Exception ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } finally {
            try {
                socket.close();
                out.close();
                in.close();
            } catch (Exception ex) {}
        }

        connectionHandler.didDisconnect(error);
        return error;
    }

    public void write(final String data) {
        try {
            Log.d(TAG, "writ(): data = " + data);
            out.write(data + "\n");
            out.flush();
        } catch (IOException ex) {
            Log.e(TAG, "write(): " + ex.toString());
        } catch (NullPointerException ex) {
            Log.e(TAG, "write(): " + ex.toString());
        }
    }

    public void disconnect() {
        try {
            Log.d(TAG, "Closing the socket connection.");

            interrupted = true;
            if(socket != null) {
                socket.close();
            }
            if(out != null & in != null) {
                out.close();
                in.close();
            }
        } catch (IOException ex) {
            Log.e(TAG, "disconnect(): " + ex.toString());
        }
    }
}
