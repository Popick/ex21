package com.example.ex21;

import java.io.Serializable;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 2.1
 * @since 4/5/2022
 * Constants for the orders' table in the data base
 */
public class Orders implements Serializable {
    private String RESTAURANT_ID;
    private String RESTAURANT_NAME;
    private String USER_ID;
    private String USER_NAME;
    private String DATE;




    public String getRESTAURANT_ID() {
        return RESTAURANT_ID;
    }

    public void setRESTAURANT_ID(String RESTAURANT_ID) {
        this.RESTAURANT_ID = RESTAURANT_ID;
    }

    public String getRESTAURANT_NAME() {
        return RESTAURANT_NAME;
    }

    public void setRESTAURANT_NAME(String RESTAURANT_NAME) {
        this.RESTAURANT_NAME = RESTAURANT_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public Orders(String RESTAURANT_ID, String RESTAURANT_NAME, String USER_ID, String USER_NAME, String DATE) {
        this.RESTAURANT_ID = RESTAURANT_ID;
        this.RESTAURANT_NAME = RESTAURANT_NAME;
        this.USER_ID = USER_ID;
        this.USER_NAME = USER_NAME;
        this.DATE = DATE;
    }

    public Orders(){}

    public Orders(String user) {
    }



}
