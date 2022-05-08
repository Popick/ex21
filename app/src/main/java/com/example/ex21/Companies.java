package com.example.ex21;

import java.io.Serializable;

/**
 * @author Etay Sabag <itay45520@gmail.com>
 * @version 2
 * @since 4/5/2022
 * Constants for the companies' table in the data base
 */
public class Companies implements Serializable {
    private String NAME;
    private String MAIN_PHONE;
    private String SECONDARY_PHONE;
    private String TAX_ID;
    private String ACTIVE;

    public Companies(String NAME, String MAIN_PHONE, String SECONDARY_PHONE, String TAX_ID, String ACTIVE) {
        this.NAME = NAME;
        this.MAIN_PHONE = MAIN_PHONE;
        this.SECONDARY_PHONE = SECONDARY_PHONE;
        this.TAX_ID = TAX_ID;
        this.ACTIVE = ACTIVE;
    }

    public Companies() {
    }

    public Companies(String user) {
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getMAIN_PHONE() {
        return MAIN_PHONE;
    }

    public void setMAIN_PHONE(String MAIN_PHONE) {
        this.MAIN_PHONE = MAIN_PHONE;
    }

    public String getSECONDARY_PHONE() {
        return SECONDARY_PHONE;
    }

    public void setSECONDARY_PHONE(String SECONDARY_PHONE) {
        this.SECONDARY_PHONE = SECONDARY_PHONE;
    }

    public String getTAX_ID() {
        return TAX_ID;
    }

    public void setTAX_ID(String TAX_ID) {
        this.TAX_ID = TAX_ID;
    }

    public String getACTIVE() {
        return ACTIVE;
    }

    public void setACTIVE(String ACTIVE) {
        this.ACTIVE = ACTIVE;
    }


}
