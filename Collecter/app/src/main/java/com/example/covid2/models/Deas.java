package com.example.covid2.models;

public class Deas {
    String dea;
    int img;

    public Deas(String dea, int imPath) {
        this.dea = dea;
        this.img = imPath;
    }

    public String getDea() {
        return dea;
    }

    public void setDea(String dea) {
        this.dea = dea;
    }

    public int getImPath() {
        return img;
    }

    public void setImPath(int imPath) {
        this.img = imPath;
    }
}
