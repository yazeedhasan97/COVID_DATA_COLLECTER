package com.example.covid2.models;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    @Override
    public String toString() {
        return "User{" +
                "nid='" + nid + '\'' +
                ", cardid='" + cardid + '\'' +
                ", state='" + state + '\'' +
                ", lang=" + lang +
                ", lat=" + lat +
                ", dea=" + dea +
                '}';
    }

    private String nid, cardid, state;
    private double lang, lat;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getDea() {
        return new ArrayList<>(dea);
    }

    public void setDea(List<String> dea) {
        this.dea = new ArrayList<String>(dea);
    }

    private List<String> dea;


    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public User(String nid, String cardid) {
        this.nid = nid;
        this.cardid = cardid;
    }

    public User() {
    }

    public Bitmap generateQRCode() throws WriterException {
        String myCodeText = this.nid + "," + this.state;
        QRCodeWriter writer = new QRCodeWriter();
        int size = 1024;
        BitMatrix bitMatrix = writer.encode(myCodeText, BarcodeFormat.QR_CODE, size, size);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
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
