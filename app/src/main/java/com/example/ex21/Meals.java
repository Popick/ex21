package com.example.ex21;

import java.io.Serializable;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 2
 * @since 4/5/2022
 * Constants for the meals' table in the data base
 */
public class Meals implements Serializable {
    private String APPETIZER;
    private String MAIN;
    private String SIDE;
    private String DESSERT;
    private String DRINK;

    public String getAPPETIZER() {
        return APPETIZER;
    }

    public void setAPPETIZER(String APPETIZER) {
        this.APPETIZER = APPETIZER;
    }

    public String getMAIN() {
        return MAIN;
    }

    public void setMAIN(String MAIN) {
        this.MAIN = MAIN;
    }

    public String getSIDE() {
        return SIDE;
    }

    public void setSIDE(String SIDE) {
        this.SIDE = SIDE;
    }

    public String getDESSERT() {
        return DESSERT;
    }

    public void setDESSERT(String DESSERT) {
        this.DESSERT = DESSERT;
    }

    public String getDRINK() {
        return DRINK;
    }

    public void setDRINK(String DRINK) {
        this.DRINK = DRINK;
    }

    public Meals(String APPETIZER, String MAIN, String SIDE, String DESSERT, String DRINK) {
        this.APPETIZER = APPETIZER;
        this.MAIN = MAIN;
        this.SIDE = SIDE;
        this.DESSERT = DESSERT;
        this.DRINK = DRINK;
    }

    public Meals(){}

    public Meals(String user) {
    }

}