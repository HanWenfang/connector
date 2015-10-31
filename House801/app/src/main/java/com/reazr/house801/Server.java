package com.reazr.house801;

/**
 * Created by tuyou on 10/31/15.
 */
public class Server {
    public String status = "";
    public String host = "";
    public int port = 0;

    public Server(String status, String host, int port) {
        this.host = host;
        this.port = port;
        this.status = status;
    }
}
