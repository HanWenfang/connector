package com.reazr.house801;

/**
 * Created by susan on 15-11-5.
 */
public class Connector {
    public int cid;
    public int type;
    public String host;
    public int port;
    public int status; //status -> 0 断开 status -> 1 连接中

    public Connector(int ptype, String phost, int pport) {
        type = ptype;
        host = phost;
        port = pport;
        status = 0;
    }

    public Connector(int pcid, int ptype, String phost, int pport) {
        cid = pcid;
        type = ptype;
        host = phost;
        port = pport;
        status = 0;
    }
}
