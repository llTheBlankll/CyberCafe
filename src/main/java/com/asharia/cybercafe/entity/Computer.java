package com.asharia.cybercafe.entity;

public class Computer {

    private String computerName;
    private String computerOS;
    private String computerLocalIP;
    private boolean computerIsBeingUse;

    public boolean isComputerIsBeingUse() {
        return computerIsBeingUse;
    }

    public void setComputerIsBeingUse(boolean computerIsBeingUse) {
        this.computerIsBeingUse = computerIsBeingUse;
    }

    public String getComputerLocalIP() {
        return computerLocalIP;
    }

    public void setComputerLocalIP(String computerLocalIP) {
        this.computerLocalIP = computerLocalIP;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerOS() {
        return computerOS;
    }

    public void setComputerOS(String computerOS) {
        this.computerOS = computerOS;
    }
}
