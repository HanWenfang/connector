package com.reazr.house801;

/**
 * Created by tuyou on 11/7/15.
 */
public class Msg {
    public int mid;
    public int cid;
    public int author;
    public String text;

    public Msg(int mid, int cid, int author, String text) {
        this.mid = mid;
        this.cid = cid;
        this.author = author;
        this.text = text;
    }

    public Msg(int cid, int author, String text) {
        this.cid = cid;
        this.author = author;
        this.text = text;
    }

}
