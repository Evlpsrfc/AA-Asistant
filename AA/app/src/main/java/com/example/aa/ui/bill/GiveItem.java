package com.example.aa.ui.bill;

class GiveItem {
    private String nameFrom;
    private String nameTo;
    private double giveMoney;

    GiveItem(String nameFrom, String nameTo, double giveMoney) {
        this.nameFrom = nameFrom;
        this.nameTo = nameTo;
        this.giveMoney = giveMoney;
    }

    String getNameFrom() {
        return nameFrom;
    }

    String getNameTo() {
        return nameTo;
    }

    double getGiveMoney() {
        return giveMoney;
    }

}
