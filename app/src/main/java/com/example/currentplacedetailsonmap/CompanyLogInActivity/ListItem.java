package com.example.currentplacedetailsonmap.CompanyLogInActivity;

public class ListItem {
    int id ;
    String point_name;
    int capcity;
    int current;
    boolean paper;
    boolean plastic;
    boolean glass;
    String types = "";

    public ListItem(int id ,String point_name, boolean paper, boolean plastic, boolean glass, int capacity , int current) {
        this.id = id;
        this.point_name = point_name;
        this.capcity = capacity;
        this.paper = paper;
        this.plastic = plastic;
        this.glass = glass;
        if (paper)
            this.types = this.types + "Paper/";
        if (plastic)
            this.types = this.types + "Plastic/";
        if(glass)
            this.types = this.types + "Glass";
        this.current = current;
    }

    public String getPoint_name() {
        return point_name;
    }

    public int getCapcity() {
        return capcity;
    }

    public String getTypes() {
        return types;
    }

    public int getCurrent() {
        return current;
    }
}
