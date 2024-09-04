package com.icia.weatherhelper.dto;

import lombok.Data;

@Data
public class Weather {
    private int locationCode;
    private String date;
    private String time;
    private double PTY;
    private double REH;
    private double RN1;
    private double T1H;
    private double UUU;
    private double VVV;
    private double VEC;
    private double WSD;

    @Override
    public String toString() {
        return "locationCode = " + locationCode +
                "\ndate = " + date +
                "\ntime = " + time +
                "\nPTY = " + PTY +
                "\nREH = " + REH +
                "\nRN1 = " + RN1 +
                "\nT1H = " + T1H +
                "\nUUU = " + UUU +
                "\nVEC = " + VEC +
                "\nVVV = " + VVV +
                "\nWSD = " + WSD;
    }
}