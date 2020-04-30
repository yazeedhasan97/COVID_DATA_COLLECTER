package com.example.covidh2.models;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {
    @Override
    public String toString() {
        return "User{" +
                "nid='" + nid + '\'' +
                ", cardid='" + cardid + '\'' +
                ", state='" + state + '\'' +
                ", lang=" + lang +
                ", lat=" + lat +
//                ", dea=" + dea +
                '}';
    }

    private String nid, cardid, state;
    private double lang, lat;

    public String getState() {
        return state;
    }

//    private List<String> dea;
//    public List<String> getDea() {
//        return new ArrayList<>(dea);
//    }




    public double getLang() {
        return lang;
    }

    public double getLat() {
        return lat;
    }

    public Person(String nid, String cardid) {
        this.nid = nid;
        this.cardid = cardid;
    }

    public Person() {
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

}

