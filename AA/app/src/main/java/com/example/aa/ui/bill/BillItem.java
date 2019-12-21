package com.example.aa.ui.bill;

public class BillItem implements Comparable<BillItem> {
    private String spendName;
    private Double spendMoney;
    private String date;
    private String remark;

    public BillItem(String spendName, Double spendMoney, String date, String remark) {
        this.spendName = spendName;
        this.spendMoney = spendMoney;
        this.date = date;
        this.remark = remark;
    }

    String getSpendName() {
        return spendName;
    }

    Double getSpendMoney() {
        return spendMoney;
    }

    void addSpendMoney(Double spendMoneyAdd) {
        this.spendMoney += spendMoneyAdd;
    }

    @Override
    public int compareTo(BillItem oth){
        return (int) (this.spendMoney - oth.spendMoney);
    }

    @Override
    protected BillItem clone(){
        BillItem ret = new BillItem("", 0.0, "", "");
        ret.spendMoney = this.spendMoney;
        ret.spendName = this.spendName;
        return ret;
    }
}
