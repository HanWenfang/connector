package com.reazr.house801;

/**
 * Created by susan on 15-11-5.
 */
public class Connector {
    public long cid;
    public int type;
    public String host;
    public int port;

    public Connector(int ptype, String phost, int pport) {
        type = ptype;
        host = phost;
        port = pport;
    }

    public Connector(int pcid, int ptype, String phost, int pport) {
        cid = pcid;
        type = ptype;
        host = phost;
        port = pport;
    }
}
