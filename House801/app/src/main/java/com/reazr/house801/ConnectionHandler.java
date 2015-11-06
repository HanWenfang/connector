package com.reazr.house801;

/**
 * Created by tuyou on 11/6/15.
 */
public interface ConnectionHandler {
    public void didReceiveData(String data);

    public void didDisconnect(Exception error);

    public void didConnect();
}
