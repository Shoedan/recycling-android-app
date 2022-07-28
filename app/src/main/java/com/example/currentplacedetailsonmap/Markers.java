package com.example.currentplacedetailsonmap;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Markers implements Serializable {
    private final int id ;                //  ID
    private final double lat;             //  latitudine
    private final double lon;             //  longitudine
    private final boolean paper;          //  Bool if can collect paper
    private final boolean plastic;        //  Bool if can collect plastic
    private final boolean glass;          //  Bool if can collect glass
    private final int cp;                 //  Current Paper
    private final int cpl;                //  Current Plastic
    private final int cg;                 //  Current Glass
    private final int pc;                 //  Paper Max Capacity
    private final int plc;                //  Plastic Max Capacity
    private final int pg;                 //  Glass Max Capacity
    private final int total;              //  Total Current Waste(?)
    private final int city;
    private final String point_name;


    public Markers(int id, double lat, double lon, boolean paper, boolean plastic, boolean glass, int cp, int cpl, int cg, int pc, int plc, int pg, int total , int city , String point_name) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.paper = paper;
        this.plastic = plastic;
        this.glass = glass;
        this.cp = cp;
        this.cpl = cpl;
        this.cg = cg;
        this.pc = pc;
        this.plc = plc;
        this.pg = pg;
        this.total = total;
        this.city = city;
        this.point_name = point_name;
    }

    public Markers() {
        id = 0 ;
        lat = 0f;
        lon = 0f;
        paper = false;
        plastic = false;
        glass = false;
        cp = 0;
        cpl = 0;
        cg = 0;
        pc = 0;
        plc = 0;
        pg = 0;
        total = 0;
        city = 0;
        point_name ="";
    }

    public String available(){
        String r = "";
        if (paper)
            r = r+"Paper ";
        if (plastic)
            r = r + "Plastic ";
        if (glass)
            r = r + "Glass ";
        return r;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean isPaper() {
        return paper;
    }

    public boolean isPlastic() {
        return plastic;
    }

    public boolean isGlass() {
        return glass;
    }

    public int getCp() {
        return cp;
    }

    public int getCpl() {
        return cpl;
    }

    public int getCg() {
        return cg;
    }

    public int getPc() {
        return pc;
    }

    public int getPlc() {
        return plc;
    }

    public int getPg() {
        return pg;
    }

    public int getTotal() {
        return total;
    }

    public int getId() {
        return id;
    }

    public int getCity() {
        return city;
    }

    public int get_max_capacity(){
        return pc + plc + pg;
    }

    public String getPoint_name() {
        return point_name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Markers{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", paper=" + paper +
                ", plastic=" + plastic +
                ", glass=" + glass +
                ", cp=" + cp +
                ", cpl=" + cpl +
                ", cg=" + cg +
                ", pc=" + pc +
                ", plc=" + plc +
                ", pg=" + pg +
                ", total=" + total +
                '}';
    }
}
